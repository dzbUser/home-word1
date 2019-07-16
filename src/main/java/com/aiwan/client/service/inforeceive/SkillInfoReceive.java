package com.aiwan.client.service.inforeceive;

import com.aiwan.client.anno.InfoReceiveMethod;
import com.aiwan.client.anno.InfoReceiveObject;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.server.user.role.skill.protocol.SM_ViewLearnedSkill;
import com.aiwan.server.user.role.skill.protocol.SM_ViewSkillBar;
import com.aiwan.server.user.role.skill.protocol.element.SkillElementMessage;
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
     * 查看所学技能
     */
    @InfoReceiveMethod(status = StatusCode.VIEWLEARNEDSKILL)
    public void viewLearnedSkill(SM_ViewLearnedSkill sm_viewLearnedSkill) {
        StringBuffer stringBuffer = new StringBuffer();
        //输出道具
        for (SkillElementMessage skillElementMessage : sm_viewLearnedSkill.getList()) {
            SkillResource skillResource = GetBean.getSkillManager().getSkillResourceBySkillId(skillElementMessage.getSkillId());
            stringBuffer.append("[" + skillResource.getSkillId() + "]");
            stringBuffer.append("名字:" + skillResource.getSkillName() + " 等级:" + skillElementMessage.getLevel());
            SkillLevelResource skillLevelResource = GetBean.getSkillManager().getSkillLevelReById(skillElementMessage.getSkillId(), skillElementMessage.getLevel());
            stringBuffer.append(" 伤害:" + skillLevelResource.getSkillAttack() / 100 + "%");
            stringBuffer.append(" 目标数:" + skillLevelResource.getNum() + "\n");
            skillLevelResource = GetBean.getSkillManager().getSkillLevelReById(skillElementMessage.getSkillId(), (skillElementMessage.getLevel() + 1));
            if (skillElementMessage.getLevel() < GetBean.getSkillManager().getMaxLevel(skillElementMessage.getSkillId())) {
                stringBuffer.append("升级等级要求：" + skillLevelResource.getRoleLevelDemand() + "\n\n");
            } else {
                stringBuffer.append("技能达到最高等级\n\n");
            }
        }
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        gameInterface.printOtherMessage(stringBuffer.toString());
    }

    /**
     * 查看技能栏
     */
    @InfoReceiveMethod(status = StatusCode.VIEWSKILLBAR)
    public void viewSkillBar(SM_ViewSkillBar sm_viewSkillBar) {
        StringBuffer stringBuffer = new StringBuffer();
        SkillElementMessage[] skillElementMessages = sm_viewSkillBar.getSkills();
        //输出道具
        for (int i = 0; i < skillElementMessages.length; i++) {
            if (skillElementMessages[i] == null) {
                stringBuffer.append("[" + i + "] 空\n\n");
            } else {
                SkillResource skillResource = GetBean.getSkillManager().getSkillResourceBySkillId(skillElementMessages[i].getSkillId());
                stringBuffer.append("[" + i + "] " + "名字:" + skillResource.getSkillName() + " 等级:" + skillElementMessages[i].getLevel());
                SkillLevelResource skillLevelResource = GetBean.getSkillManager().getSkillLevelReById(skillElementMessages[i].getSkillId(), skillElementMessages[i].getLevel());
                stringBuffer.append(" 伤害:" + skillLevelResource.getSkillAttack() / 100 + "%");
                stringBuffer.append(" 目标数:" + skillLevelResource.getNum() + "\n");
                skillLevelResource = GetBean.getSkillManager().getSkillLevelReById(skillElementMessages[i].getSkillId(), (skillElementMessages[i].getLevel() + 1));
                if (skillElementMessages[i].getLevel() < GetBean.getSkillManager().getMaxLevel(skillElementMessages[i].getSkillId())) {
                    stringBuffer.append("升级等级要求：" + skillLevelResource.getRoleLevelDemand() + "\n\n");
                } else {
                    stringBuffer.append("技能达到最高等级\n\n");
                }
            }
        }
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        gameInterface.printOtherMessage(stringBuffer.toString());
    }
}
