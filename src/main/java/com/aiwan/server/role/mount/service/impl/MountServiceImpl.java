package com.aiwan.server.role.mount.service.impl;

import com.aiwan.server.role.attributes.model.Attributes;
import com.aiwan.server.role.attributes.model.RoleAttributes;
import com.aiwan.server.role.mount.model.MountModel;
import com.aiwan.server.role.mount.service.MountManager;
import com.aiwan.server.role.mount.service.MountService;
import com.aiwan.server.util.GetBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MountServiceImpl implements MountService {
    @Autowired
    private MountManager mountManager;

    @Override
    public void createMount(Long rId) {
        mountManager.createMount(rId);
    }

    @Override
    public void addExperience(Long rId,int experienceNum) {
        //获取等级，计算升阶经验，循环升阶
        MountModel mountModel = mountManager.load(rId);
        int level = mountModel.getLevel();
        int totalExperience = mountModel.getExperience()+experienceNum;
        while (totalExperience >= getUpgradeRequest(level)){
            //循环判断
            totalExperience = totalExperience - getUpgradeRequest(level);
            level = level+1;
        }
        mountModel.setLevel(level);
        mountModel.setExperience(totalExperience);
        mountManager.writeBack(mountModel);

        //更新人物属性
        RoleAttributes roleAttributes = GetBean.getAttributesManager().getRoleAttributes(rId);
        if (roleAttributes != null){
            roleAttributes.setMountAttribute(getMountAttributes(rId));
        }
    }

    @Override
    public String viewMount(Long rId) {
        StringBuffer stringBuffer =new StringBuffer();
        //获取坐骑模型
        MountModel mountModel = mountManager.load(rId);
        stringBuffer.append("坐骑名字："+MountManager.mountName[mountModel.getLevel()]+" 坐骑阶级："+mountModel.getLevel()+" 坐骑经验："+mountModel.getExperience());
        return stringBuffer.toString();
    }

    @Override
    public Attributes getMountAttributes(Long rId) {
        MountModel mountModel = mountManager.load(rId);
        Attributes attributes = new Attributes();
        attributes.setPowerBonus(0);
        attributes.setPower(mountModel.getLevel()*5);
        attributes.setBlood(mountModel.getLevel()*2);
        return attributes;
    }


    /**获取升阶要求(暂用)*/
    private int getUpgradeRequest(int level){
        return (level+1)*500;
    }
}
