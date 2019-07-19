package com.aiwan.server.user.role.fight.command;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneCommand;
import com.aiwan.server.user.role.skill.model.Skill;
import com.aiwan.server.util.GetBean;

/**
 * 技能真正施法命令
 *
 * @author dengzebiao
 * @since 2019.7.9
 */
public class DoUserSkillCommand extends AbstractSceneCommand {

    /**
     * 施法id
     */
    private long activeId;

    /**
     * 目标id
     */
    private long targetId;

    /**
     * 技能
     */
    private Skill skill;


    public DoUserSkillCommand(String accountId, int mapId, int sceneId, long activeId, long targetId, Skill skill) {
        super(accountId, mapId, sceneId);
        this.activeId = activeId;
        this.targetId = targetId;
        this.skill = skill;
        setTaskName("技能真正施法命令");
    }

    @Override
    public void action() {
        GetBean.getFightService().doUserSkill(activeId, targetId, skill, getMapId(), getSceneId());
    }
}
