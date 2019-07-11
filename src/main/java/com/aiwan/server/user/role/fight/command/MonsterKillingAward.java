package com.aiwan.server.user.role.fight.command;

import com.aiwan.server.base.executor.account.impl.AbstractAccountCommand;
import com.aiwan.server.monster.resource.MonsterResource;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;

import java.util.Map;

/**
 * 怪物击杀奖励
 *
 * @author dengzebiao
 * @since 2019.7.11
 */
public class MonsterKillingAward extends AbstractAccountCommand {

    /**
     * 角色id
     */
    private long rId;

    /**
     * 怪物id
     */
    private int monsterResourceId;

    public MonsterKillingAward(String accountId, long rId, int monsterResourceId) {
        super(accountId);
        this.monsterResourceId = monsterResourceId;
        this.rId = rId;
    }

    @Override
    public void active() {
        Role role = GetBean.getRoleManager().load(rId);
        MonsterResource monsterResource = GetBean.getMonsterManager().getResourceById(monsterResourceId);
        //添加经验
        GetBean.getRoleService().addExperience(rId, monsterResource.getExperience());
        for (Map.Entry<Integer, Integer> entry : monsterResource.getDropMap().entrySet()) {

        }
    }
}
