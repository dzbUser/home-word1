package com.aiwan.server.role.player.service.impl;

import com.aiwan.server.prop.resource.Equipment;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.role.player.model.Role;
import com.aiwan.server.role.player.protocol.CM_RoleMessage;
import com.aiwan.server.role.player.protocol.SM_CreateRole;
import com.aiwan.server.role.player.service.RoleManager;
import com.aiwan.server.role.player.service.RoleService;
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
        DecodeData decodeData = SMToDecodeData.shift(StatusCode.ROLEMESSAGE,role.getRoleMessage());
        session.messageSend(decodeData);
    }

    /**
     * 经验添加
     * */
    @Override
    public void addExperience(String accountId, Long rId, int experienceNum) {
        //获取经验，循环解决升级
        Role role = roleManager.load(rId);
        int level = role.getLevel();
        int totalExperience = role.getExperience()+experienceNum;
        while (totalExperience >= getUpgradeRequest(level)){
            totalExperience = totalExperience - getUpgradeRequest(level);
            level = level+1;
        }
        role.setLevel(level);
        role.setExperience(totalExperience);
        roleManager.sava(role);
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

    /**获取升级要求(暂用)*/
    private int getUpgradeRequest(int level){
        return level*50;
    }
}
