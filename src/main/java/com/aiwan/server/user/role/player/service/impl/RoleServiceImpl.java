package com.aiwan.server.user.role.player.service.impl;

import com.aiwan.server.prop.resource.Equipment;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.player.protocol.CM_RoleMessage;
import com.aiwan.server.user.role.player.protocol.SM_CreateRole;
import com.aiwan.server.user.role.player.protocol.SM_RoleMessage;
import com.aiwan.server.user.role.player.service.RoleManager;
import com.aiwan.server.user.role.player.service.RoleService;
import com.aiwan.server.user.account.model.User;
import com.aiwan.server.user.account.protocol.CM_CreateRole;
import com.aiwan.server.user.account.service.UserManager;
import com.aiwan.server.util.AttributeUtil;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.StatusCode;
import com.aiwan.server.util.SMToDecodeData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    public void create(Session session, CM_CreateRole cm_createRole) {
        //创建角色
        Long id =  roleManager.createRole(cm_createRole.getAccountId(),cm_createRole.getSex(),cm_createRole.getJob());
        //把角色id存到用户数据库中
        User user = userManager.getUserByAccountId(cm_createRole.getAccountId());
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
        //发送协议
        DecodeData decodeData = SMToDecodeData.shift(StatusCode.CREATEROLESUCESS,sm_createRole);
        logger.debug(decodeData.getLength()+"");
        //返回信息到客户端
        session.messageSend(decodeData);
        GetBean.getMapManager().sendMessageToUsers(user.getMap());
    }

    @Override
    public void getRoleMessage(Session session, CM_RoleMessage cm_roleMessage) {
        Role role = roleManager.load(cm_roleMessage.getrId());
        if (role == null){
            logger.error("角色id："+cm_roleMessage.getrId()+"为空");
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
        if (level >= GetBean.getRoleResourceManager().getMaxlevel()){
            //人物已达到最高级
            return 0;
        }
        int totalExperience = role.getExperience()+experienceNum;
        while (totalExperience >= role.getUpgradeRequest()){
            //循环增加经验
            totalExperience = totalExperience - getUpgradeRequest(level);
            level = level+1;
        }
        role.setLevel(level);
        role.setExperience(totalExperience);
        roleManager.sava(role);

        //更新人物属性
        putAttributeModule("role", getAttributes(rId),rId);
        return 1;
    }

    @Override
    public int equip(String accountId, Long rId, int pId) {
        Role role = roleManager.load(rId);
        Equipment equipment = GetBean.getPropsManager().getEquipment(pId);
        if (role.getLevel()<equipment.getLevel()){
            //等级达不到要求等级
            return -1;
        }
        //获取旧装备的id
        int id = GetBean.getEquipmentService().equip(accountId,rId,pId);
        return id;
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
    public void putAttributeModule(String name,Map<AttributeType, AttributeElement> map,Long rId) {
        roleManager.setRoleAttribute(name,map ,rId);

    }

    /**获取升级要求(暂用)*/
    private int getUpgradeRequest(int level){
        return (level+1)*50;
    }

}
