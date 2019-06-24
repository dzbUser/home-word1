package com.aiwan.server.user.role.player.service.impl;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.player.protocol.SM_CreateRole;
import com.aiwan.server.user.role.player.protocol.SM_RoleMessage;
import com.aiwan.server.user.role.player.service.RoleManager;
import com.aiwan.server.user.role.player.service.RoleService;
import com.aiwan.server.user.account.model.User;
import com.aiwan.server.user.account.service.UserManager;
import com.aiwan.server.util.AttributeUtil;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.StatusCode;
import com.aiwan.server.util.SMToDecodeData;
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

    Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private UserManager userManager;


    @Override
    public void create(Session session, String accountId, int job, int sex) {
        //创建角色
        Long id = roleManager.createRole(accountId, sex, job);
        //把角色id存到用户数据库中
        User user = userManager.getUserByAccountId(accountId);
        user.addRole(id);
        //写回
        userManager.save(user);
        //创建装备栏
        GetBean.getEquipmentService().createEquipmentBar(id);
        //创建坐骑
        GetBean.getMountService().createMount(id);
        //保存用户状态到session
        session.setUser(user);
        //创建返回信息
        SM_CreateRole sm_createRole = new SM_CreateRole();
        sm_createRole.setRoles(user.getUserBaseInfo().getRoles());
        sm_createRole.setMessage("角色创建成功");
        sm_createRole.setMapMessage( GetBean.getMapManager().getMapContent(user.getCurrentX(),user.getCurrentY(),user.getMap()));
        //发送协议
        DecodeData decodeData = SMToDecodeData.shift(StatusCode.CREATEROLESUCESS,sm_createRole);
        logger.info(user.getAcountId()+"创建角色"+id+"成功");
        //返回信息到客户端
        session.messageSend(decodeData);
        GetBean.getMapManager().sendMessageToUsers(user.getMap(),user.getAcountId());
    }

    @Override
    public void getRoleMessage(String accountId, Long rId, final Session session) {
        Role role = roleManager.load(rId);
        if (role == null){
            logger.error("角色id：" + rId + "为空");
        }
        SM_RoleMessage sm_roleMessage = SM_RoleMessage.valueOf(role.getJob(),role.getSex(),role.getLevel(),role.getExperience(),getUpgradeRequest(role.getLevel()),role.getAttribute().getFinalAttribute());

        DecodeData decodeData = SMToDecodeData.shift(StatusCode.VIEWROLEMESSAGE,sm_roleMessage);
        session.messageSend(decodeData);
    }

    /**
     * 经验添加
     * */
    @Override
    public int addExperience(String accountId, Long rId, int experienceNum) {
        //获取经验，循环解决升级
        Role role = roleManager.load(rId);
        int level = role.getLevel();
        int totalExperience = role.getExperience()+experienceNum;
        while (level < GetBean.getRoleResourceManager().getMaxLevel() && totalExperience >= getUpgradeRequest(level)) {
            //循环增加经验
            totalExperience = totalExperience - getUpgradeRequest(level);
            level = level+1;
        }

        if (level < GetBean.getRoleResourceManager().getMaxLevel()) {
            //叠加后等级人小于最高等级
            if (role.getLevel() != level) {
                //等级发生改变
                role.setLevel(level);
            }
            role.setExperience(totalExperience);
            roleManager.save(role);
            updateAttributeModule("role", getAttributes(rId), rId);
            return 0;
        } else if (level == GetBean.getRoleResourceManager().getMaxLevel() && role.getLevel() != level) {
            //达到最高级，且等级改变
            role.setLevel(level);
            role.setExperience(0);
            roleManager.save(role);
        }
        //等级发生改变更新人物属性
        updateAttributeModule("role", getAttributes(rId), rId);
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
        roleManager.updateAttributeModule(name, map, rId);
    }

    /**获取升级要求(暂用)*/
    private int getUpgradeRequest(int level){
        return (level+1)*50;
    }

}
