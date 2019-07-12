package com.aiwan.server.user.role.fight.pvpUnit;

import com.aiwan.server.monster.resource.MonsterResource;
import com.aiwan.server.scenes.model.Position;
import com.aiwan.server.user.role.fight.command.MonsterKillingAward;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.IDUtil;

import java.util.Collections;
import java.util.Map;

/**
 * 怪物战斗单位
 *
 * @author dengzebiao
 * @since 2019.7.9
 */
public class MonsterUnit extends BaseUnit {

    /**
     * 怪物资源id
     */
    private int resourceId;


    public static MonsterUnit valueOf(MonsterResource monsterResource, Position position) {
        MonsterUnit monsterUnit = new MonsterUnit();
        monsterUnit.getFinalAttribute().putAll(monsterResource.getAttributeMap());
        monsterUnit.setPosition(position);
        monsterUnit.setId(IDUtil.getId());
        monsterUnit.setHp(monsterUnit.getMaxHp());
        monsterUnit.setLevel(monsterResource.getLevel());
        monsterUnit.setName(monsterResource.getName());
        monsterUnit.setMonster(true);
        monsterUnit.setResourceId(monsterResource.getResourceId());
        return monsterUnit;
    }

    /**
     * 获取静态资源
     */
    public MonsterResource getResource() {
        return GetBean.getMonsterManager().getResourceById(resourceId);
    }


    @Override
    protected void death(Long attackId) {
        setDeath(true);
        //为攻击者添加道具
        Role role = GetBean.getRoleManager().load(attackId);
        //为角色添加击杀奖励
        GetBean.getAccountExecutorService().submit(new MonsterKillingAward(role.getAccountId(), role, getResourceId()));
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
