package com.aiwan.server.user.role.fight.command;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneCommand;
import com.aiwan.server.user.role.skill.model.AbstractSkill;
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
    private AbstractSkill skill;


    public DoUserSkillCommand(String accountId, int mapId, long activeId, long targetId, AbstractSkill skill) {
        super(accountId, mapId);
        this.activeId = activeId;
        this.targetId = targetId;
        this.skill = skill;
    }

    @Override
    public void action() {
        GetBean.getFightService().doUserSkill(activeId, targetId, skill, getMapId());
    }
}
