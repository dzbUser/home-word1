package com.aiwan.server.user.role.mount.service.impl;

import com.aiwan.server.prop.model.AbstractProps;
import com.aiwan.server.prop.model.PropsType;
import com.aiwan.server.prop.model.impl.MountDan;
import com.aiwan.server.prop.resource.PropsResource;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.backpack.model.Backpack;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.mount.model.MountModel;
import com.aiwan.server.user.role.mount.protocol.CM_MountUpgrade;
import com.aiwan.server.user.role.mount.protocol.CM_ViewMount;
import com.aiwan.server.user.role.mount.protocol.SM_ViewMount;
import com.aiwan.server.user.role.mount.service.MountManager;
import com.aiwan.server.user.role.mount.service.MountService;
import com.aiwan.server.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * 坐骑业务类
 * @author dengzebiao
 * */
@Service
public class MountServiceImpl implements MountService {
    /**
     * 升阶丹id
     */
    private final static int MOUNTDANID = 2;
    Logger logger = LoggerFactory.getLogger(MountServiceImpl.class);

    @Autowired
    private MountManager mountManager;

    @Override
    public void createMount(Long rId) {
        mountManager.createMount(rId);
    }

    @Override
    public void addExperience(Long rId, int experienceNum) {
        logger.info(rId+"角色请求坐骑提升");
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
        GetBean.getRoleService().putAttributeModule("mount",getAttributes(rId),rId);
    }

    @Override
    public void viewMount(CM_ViewMount cm_viewMount, Session session) {

        logger.info(cm_viewMount.getAccountId()+":查看坐骑信息");
        //获取坐骑模型
        MountModel mountModel = mountManager.load(cm_viewMount.getrId());
        //创建坐骑返回数据
        SM_ViewMount sm_viewMount = SM_ViewMount.valueOf(mountModel.getLevel(),mountModel.getExperience(),getUpgradeRequest(mountModel.getLevel()),getAttributes(cm_viewMount.getrId()));
        session.messageSend(SMToDecodeData.shift(StatusCode.VIEWMOUNT, sm_viewMount));
    }

    @Override
    public void mountUpgrade(CM_MountUpgrade cm_mountUpgrade, Session session) {
        //获取坐骑模型
        MountModel mountModel = mountManager.load(cm_mountUpgrade.getrId());
        //判断是否达到做高级
        int level = mountModel.getLevel();
        if (level >= mountManager.getMountResource().getMaxLevel()) {
            //以达到最高等级
            logger.info(cm_mountUpgrade.getAccountId() + "角色请求坐骑提升失败，以达到最大等级");
            session.sendPromptMessage(PromptCode.MOUNTACHIEVEMAXLEVEL, "");
            return;
        }
        //获取背包
        Backpack backpack = GetBean.getBackPackManager().load(cm_mountUpgrade.getAccountId());
        //获取坐骑升阶丹静态资源
        PropsResource propsResource = GetBean.getPropsManager().getPropsResource(cm_mountUpgrade.getResourceId());
        if (backpack.deductionByResourceIdInNum(cm_mountUpgrade.getResourceId(), cm_mountUpgrade.getNum())) {
            addExperience(cm_mountUpgrade.getrId(), propsResource.getEffect());
            session.sendPromptMessage(PromptCode.PROMOTESUCCESS, "");
            //写回
            GetBean.getBackPackManager().writeBack(backpack);
            return;
        }
        //扣除未成功
        session.sendPromptMessage(PromptCode.NOMOUNTDAN, "");
    }


    /**获取升阶要求(暂用)*/
    private int getUpgradeRequest(int level){
        return (level+1)*500;
    }

    /** 返回坐骑属性 */
    @Override
    public Map<AttributeType, AttributeElement> getAttributes(Long rId) {
        //获取坐骑
        MountModel mountModel = mountManager.load(rId);
        //获取坐骑初始化属性
        List<AttributeElement> list = mountManager.getMountResource().getList();
        //创建属性列表
        Map<AttributeType, AttributeElement> map = AttributeUtil.getMapAttributeWithLevel(list,mountModel.getLevel());
        return map;
    }


}
