package com.aiwan.server.user.role.player.service.impl;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.world.scenes.command.SignInMapCommand;
import com.aiwan.server.world.scenes.command.UpdateSceneAttributeCommand;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.command.ResetStatusCommand;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.player.protocol.SM_CreateRole;
import com.aiwan.server.user.role.player.protocol.SM_RoleMessage;
import com.aiwan.server.user.role.player.resource.RoleResource;
import com.aiwan.server.user.role.player.service.RoleManager;
import com.aiwan.server.user.role.player.service.RoleService;
import com.aiwan.server.user.account.model.User;
import com.aiwan.server.user.account.service.UserManager;
import com.aiwan.server.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 角色业务类
 * @author dengzbiao
 * */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    /**
     * 新用户初始地址
     */
    private final static int ORINGINMAP = 1;
    /**
     * 新用户初始坐标
     */
    private final static int ORINGINX = 1;
    private final static int ORINGINY = 3;

    Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    /**
     * 名字最长长度
     */
    private final static int NAME_LENGTH = 10;

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private UserManager userManager;


    @Override
    public void create(Session session, String accountId, String name, int job, int sex) {
        if ("".equals(name) || name.length() > NAME_LENGTH) {
            session.sendPromptMessage(PromptCode.NAME_EXCEED_MAXLENGTH, "");
            return;
        }
        final RoleResource roleResource = GetBean.getRoleResourceManager().getRoleResource();
        //创建角色
        Role role = roleManager.createRole(accountId, name, sex, job, roleResource.getOriginMap(), roleResource.getOriginX(), roleResource.getOriginY());
        //把角色id存到用户数据库中
        User user = userManager.getUserByAccountId(accountId);
        user.addRole(role.getId());
        //写回
        userManager.save(user);
        session.setrId(role.getId());
        //进入地图
        GetBean.getSceneExecutorService().submit(new SignInMapCommand(role));

        //保存用户状态到session
        session.setUser(user);
        //创建返回信息
        SM_CreateRole sm_createRole = new SM_CreateRole();
        sm_createRole.setRoles(user.getUserBaseInfo().getRoles());
        sm_createRole.setMessage("角色创建成功");
        //发送协议
        DecodeData decodeData = SMToDecodeData.shift(StatusCode.CREATEROLESUCESS,sm_createRole);
        logger.info("{}创建角色{}成功", user.getAcountId(), role.getId());
        //返回信息到客户端
        session.messageSend(decodeData);
        //把角色添加到地图中

    }

    @Override
    public void getRoleMessage(String accountId, Long rId, final Session session) {
        Role role = roleManager.load(rId);
        if (role == null) {
            logger.error("角色id{}发送错误包", rId);
        }
        if (role == null){
            logger.error("角色id：" + rId + "为空");
        }
        SM_RoleMessage sm_roleMessage = SM_RoleMessage.valueOf(role.getJob(), role.getSex(), role.getLevel(), role.getExperience(), getUpgradeRequest(role.getLevel()), role.getAttribute().getFinalAttribute(),role.getName());

        DecodeData decodeData = SMToDecodeData.shift(StatusCode.VIEWROLEMESSAGE,sm_roleMessage);
        session.messageSend(decodeData);
    }

    /**
     * 经验添加
     * */
    @Override
    public int addExperience(Long rId, int experienceNum) {
        //获取经验，循环解决升级
        Role role = roleManager.load(rId);
        int level = role.getLevel();
        int oldLevel = level;
        int totalExperience = role.getExperience()+experienceNum;
        while (level < GetBean.getRoleResourceManager().getMaxLevel() && totalExperience >= getUpgradeRequest(level)) {
            //循环增加经验
            totalExperience = totalExperience - getUpgradeRequest(level);
            level = level+1;
        }

        if (level < GetBean.getRoleResourceManager().getMaxLevel()) {
            //叠加后等级人小于最高等级
            if (oldLevel != level) {
                //等级发生改变
                role.setLevel(level);
            }
            role.setExperience(totalExperience);
            roleManager.save(role);
            totalExperience = 0;
        } else if (level == GetBean.getRoleResourceManager().getMaxLevel() && role.getLevel() != level) {
            //达到最高级，且等级改变
            role.setLevel(level);
            role.setExperience(0);
            roleManager.save(role);
        }
        if (oldLevel != level) {
            //等级发生改变更新人物属性
            updateAttributeModule("role", getAttributes(rId), rId);
            GetBean.getSceneExecutorService().submit(new ResetStatusCommand(null, role.getMap(), role));
        }

        //返回剩余经验
        return totalExperience;
    }


    /** 返回角色属性 */
    @Override
    public Map<AttributeType, AttributeElement> getAttributes(Long rId) {
        //获取角色
        Role role = roleManager.load(rId);
        //获取人物属性项
        List<AttributeElement> list = GetBean.getRoleResourceManager().getRoleResource().getList();
        //创建属性列表
        Map<AttributeType, AttributeElement> map = AttributeUtil.getMapAttributeWithLevel(list,role.getLevel());
        return map;
    }

    @Override
    public void updateAttributeModule(String name, Map<AttributeType, AttributeElement> map, Long rId) {
        Role role = roleManager.load(rId);
        role.updateAttribute(name, map);
        //更新场景属性
        GetBean.getSceneExecutorService().submit(new UpdateSceneAttributeCommand(role));
    }

    /**获取升级要求(暂用)*/
    private int getUpgradeRequest(int level){
        return (level + 1) * 300;
    }

}
