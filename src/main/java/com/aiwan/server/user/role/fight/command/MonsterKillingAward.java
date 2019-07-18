package com.aiwan.server.user.role.fight.command;

import com.aiwan.server.base.executor.account.impl.AbstractAccountCommand;
import com.aiwan.server.monster.resource.DropBean;
import com.aiwan.server.monster.resource.MonsterResource;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.MonsterGenerateUtil;

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
    private Role role;

    /**
     * 怪物id
     */
    private int monsterResourceId;

    public MonsterKillingAward(String accountId, Role role, int monsterResourceId) {
        super(accountId);
        this.monsterResourceId = monsterResourceId;
        this.role = role;
        setTaskName("怪物击杀奖励");
    }

    @Override
    public void active() {
        MonsterResource monsterResource = GetBean.getMonsterManager().getResourceById(monsterResourceId);
        //添加经验
        GetBean.getRoleService().addExperience(role.getId(), monsterResource.getExperience());
        if (monsterResource.getDropList().size() == 0) {
            //没有掉落物
            return;
        }
        //获取drop随机
        DropBean dropBean = monsterResource.getDropList().get(MonsterGenerateUtil.getRandomNum(monsterResource.getDropList().size()));
        //添加物品
        Session session = SessionManager.getSessionByAccountId(getAccountId());
        GetBean.getBackpackService().addPropToBack(getAccountId(), dropBean.getPropId(), dropBean.getNum(), session);
    }
}
