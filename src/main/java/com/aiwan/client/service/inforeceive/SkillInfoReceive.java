package com.aiwan.client.service.inforeceive;

import com.aiwan.client.anno.InfoReceiveMethod;
import com.aiwan.client.anno.InfoReceiveObject;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.server.user.protocol.SM_PropList;
import com.aiwan.server.user.role.skill.protocol.SM_ViewLearnedSkill;
import com.aiwan.server.user.role.skill.protocol.element.SkillElement;
import com.aiwan.server.user.role.skill.resource.SkillLevelResource;
import com.aiwan.server.user.role.skill.resource.SkillResource;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dengzebiao
 * 技能模块信息接受类
 */
@InfoReceiveObject
public class SkillInfoReceive {

    private static Logger logger = LoggerFactory.getLogger(SkillInfoReceive.class);

    /**
     * 创建角色有接收信息
     */
    @InfoReceiveMethod(status = StatusCode.VIEWLEARNEDSKILL)
    public void viewLearnedSkill(SM_ViewLearnedSkill sm_viewLearnedSkill) {
        StringBuffer stringBuffer = new StringBuffer();
        //输出道具
        for (SkillElement skillElement : sm_viewLearnedSkill.getList()) {
            SkillResource skillResource = GetBean.getSkillManager().getSkillResourceBySkillId(skillElement.getSkillId());
            stringBuffer.append("名字:" + skillResource.getSkillName() + " 等级:" + skillElement.getLevel());
            SkillLevelResource skillLevelResource = GetBean.getSkillManager().getSkillLevelReById(skillElement.getSkillId() + "_" + skillElement.getLevel());
            stringBuffer.append(" 伤害:" + skillLevelResource.getSkillAttack() / 100 + "%\n");
            skillLevelResource = GetBean.getSkillManager().getSkillLevelReById(skillElement.getSkillId() + "_" + (skillElement.getLevel() + 1));
            if (skillElement.getLevel() < skillResource.getMaxLevel()) {
                stringBuffer.append("升级等级要求：" + skillLevelResource.getRoleLevelDemand() + " 经验消耗:" + skillLevelResource.getExperienceDemand() + "\n\n");
            } else {
                stringBuffer.append("技能达到最高等级\n\n");
            }
        }
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        gameInterface.printOtherMessage(stringBuffer.toString());
    }
}
