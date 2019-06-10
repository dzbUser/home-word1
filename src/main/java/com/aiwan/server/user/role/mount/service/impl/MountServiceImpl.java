package com.aiwan.server.user.role.mount.service.impl;

import com.aiwan.server.user.role.attributes.model.*;
import com.aiwan.server.user.role.mount.model.MountModel;
import com.aiwan.server.user.role.mount.service.MountManager;
import com.aiwan.server.user.role.mount.service.MountService;
import com.aiwan.server.util.GetBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MountServiceImpl implements MountService {
    @Autowired
    private MountManager mountManager;

    @Override
    public void createMount(Long rId) {
        mountManager.createMount(rId);
    }

    @Override
    public int addExperience(Long rId,int experienceNum) {
        //获取等级，计算升阶经验，循环升阶
        MountModel mountModel = mountManager.load(rId);
        int level = mountModel.getLevel();
        if (level >= GetBean.getRoleResourceManager().getMaxMountLevel()){
            //以达到最高等级
            return 0;
        }
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
        GetBean.getRoleService().putAttributeModule("mount",getMountAttributes(rId),rId);
        return 1;
    }
    @Override
    public String viewMount(Long rId) {
        StringBuffer stringBuffer =new StringBuffer();
        //获取坐骑模型
        MountModel mountModel = mountManager.load(rId);
        stringBuffer.append("坐骑名字："+GetBean.getRoleResourceManager().getMount(mountModel.getLevel())+" 坐骑阶级："+mountModel.getLevel()+" 坐骑经验："+mountModel.getExperience()
                +"\n");
        //获取坐骑属性
        AttributeModule attributeModule = getMountAttributes(rId);
        for (Map.Entry<String,AttributeItem> entry:attributeModule.getAttributeItemMap().entrySet()){
            stringBuffer.append(entry.getKey()+":"+entry.getValue().getValue()+" ");
        }
        return stringBuffer.toString();
    }

    @Override
    public AttributeModule getMountAttributes(Long rId) {
        MountModel mountModel = mountManager.load(rId);
        AttributeModule attributeModule = new AttributeModule();
        //获取人物基本属性
        String[] mountAttribute = GetBean.getRoleResourceManager().getRoleResource().getMountAttributes();
        for (String element:mountAttribute){
            //遍历添加基本属性
            AttributeItem attributeItem = new AttributeItem();
            attributeItem.setName(element);
            attributeItem.setValue(mountModel.getLevel()*5);
            attributeModule.putAttributeItem(attributeItem);
        }
        return attributeModule;
    }


    /**获取升阶要求(暂用)*/
    private int getUpgradeRequest(int level){
        return (level+1)*500;
    }
}
