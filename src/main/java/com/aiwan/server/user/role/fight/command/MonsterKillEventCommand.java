package com.aiwan.server.user.role.fight.command;

import com.aiwan.server.base.executor.account.impl.AbstractAccountCommand;
import com.aiwan.server.base.event.event.impl.MonsterKillEvent;
import com.aiwan.server.user.role.fight.pvpunit.MonsterUnit;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;

/**
 * 抛出杀怪事件到用户线程
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
public class MonsterKillEventCommand extends AbstractAccountCommand {

    private Role role;

    private MonsterUnit monsterUnit;

    public MonsterKillEventCommand(Role role, MonsterUnit monsterUnit) {
        super(role.getAccountId());
        this.role = role;
        this.monsterUnit = monsterUnit;
    }

    @Override
    public void active() {
        //抛出条件
        MonsterKillEvent monsterKillEvent = MonsterKillEvent.valueOf(role, monsterUnit.getResourceId());
        GetBean.getEventBusManager().synSubmit(monsterKillEvent);
    }

    @Override
    public String getTaskName() {
        return "MonsterKillEventCommand";
    }
}
