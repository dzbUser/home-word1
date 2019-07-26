package com.aiwan.server.user.role.attributes.model.impl;

import com.aiwan.server.user.role.attributes.model.AttributeContainer;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.powerboard.PowerChangeEvent;
import com.aiwan.server.util.GetBean;

/**
 * @author dengzebiao
 * @since 2019.6.12
 * 人物属性汇总
 */
public class RoleAttribute extends AttributeContainer {

    private long rId;

    /**
     * 构造函数
     */
    public RoleAttribute(Long rId) {
        //初始化
        for (RoleAttributesModule module : RoleAttributesModule.values()) {
            getModuleMap().put(module, module.getAttributes(rId));
        }
        setrId(rId);
        //计算属性
        calculate();
    }

    @Override
    public void calculate() {
        super.calculate();
        //计算战斗力
        Role role = GetBean.getRoleManager().load(rId);
        if (role != null) {
            role.setCombatPower(GetBean.getCombatPowerFormula().calculateCombatPower(getFinalAttribute()));
        }
        //写会
        GetBean.getRoleManager().save(role);
        //抛出战力更新事件
        GetBean.getEventBusManager().asynSubmit(new PowerChangeEvent(role));
    }

    public long getrId() {
        return rId;
    }

    public void setrId(long rId) {
        this.rId = rId;
    }
}
