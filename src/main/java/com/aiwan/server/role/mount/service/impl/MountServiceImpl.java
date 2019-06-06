package com.aiwan.server.role.mount.service.impl;

import com.aiwan.server.role.mount.model.MountModel;
import com.aiwan.server.role.mount.service.MountManager;
import com.aiwan.server.role.mount.service.MountService;
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
            totalExperience = totalExperience - getUpgradeRequest(level);
            level = level+1;
        }
        mountModel.setLevel(level);
        mountModel.setExperience(totalExperience);
        mountManager.writeBack(mountModel);
    }

    @Override
    public String viewMount(Long rId) {
        StringBuffer stringBuffer =new StringBuffer();
        MountModel mountModel = mountManager.load(rId);
        stringBuffer.append("坐骑名字："+MountManager.mountName[mountModel.getLevel()]+" 坐骑阶级："+mountModel.getLevel()+" 坐骑经验："+mountModel.getExperience());
        return stringBuffer.toString();
    }


    /**获取升阶要求(暂用)*/
    private int getUpgradeRequest(int level){
        return (level+1)*500;
    }
}
