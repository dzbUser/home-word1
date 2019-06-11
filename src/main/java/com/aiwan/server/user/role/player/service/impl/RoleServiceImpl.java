package com.aiwan.server.user.role.player.service.impl;

import com.aiwan.server.prop.resource.Equipment;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.user.role.attributes.model.AttributeItem;
import com.aiwan.server.user.role.attributes.model.AttributeModule;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.player.protocol.CM_RoleMessage;
import com.aiwan.server.user.role.player.protocol.SM_CreateRole;
import com.aiwan.server.user.role.player.service.RoleManager;
import com.aiwan.server.user.role.player.service.RoleService;
import com.aiwan.server.user.account.model.User;
import com.aiwan.server.user.account.protocol.CM_CreateRole;
import com.aiwan.server.user.account.service.UserManager;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.StatusCode;
import com.aiwan.server.util.SMToDecodeData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    }

    @Override
    public void getRoleMessage(Session session, CM_RoleMessage cm_roleMessage) {
        Role role = roleManager.load(cm_roleMessage.getrId());
        if (role == null){
            logger.error("角色id："+cm_roleMessage.getrId()+"为空");
        }
        String message = role.getRoleMessage();
        DecodeData decodeData = SMToDecodeData.shift(StatusCode.ROLEMESSAGE,message);
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
        putAttributeModule("role", getBaseAttribute(level),rId);
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

    @Override
    public AttributeModule getBaseAttribute(int level) {
        AttributeModule attributeModule = new AttributeModule();
        //获取人物基本属性
        String[] roleAttribute = GetBean.getRoleResourceManager().getRoleResource().getRoleAttributes();
        for (String element:roleAttribute){
            //遍历添加基本属性
            AttributeItem attributeItem = new AttributeItem();
            attributeItem.setName(element);
            attributeItem.setValue(level*3);
            attributeModule.putAttributeItem(attributeItem);
        }
        return attributeModule;
    }

    @Override
    public void putAttributeModule(String name,AttributeModule attributeModule,Long rId) {
        roleManager.setRoleAttribute(name,attributeModule,rId);
    }

    /**获取升级要求(暂用)*/
    private int getUpgradeRequest(int level){
        return (level+1)*50;
    }

}
