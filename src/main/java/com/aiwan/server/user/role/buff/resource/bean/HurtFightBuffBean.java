package com.aiwan.server.user.role.buff.resource.bean;

import com.aiwan.server.user.role.buff.resource.EffectResource;
import com.aiwan.server.user.role.buff.resource.IFightBuffAnalysis;

/**
 * 攻击buff参数bean
 *
 * @author dengzebiao
 * @since 2019.7.15
 */
public class HurtFightBuffBean implements IFightBuffAnalysis {

    /**
     * 攻击力
     */
    int attack;


    public int getAttack() {
        return attack;
    }

    @Override
    public void doParse(EffectResource effectResource) {
        attack = Integer.parseInt((String) effectResource.getValueMap().get("attack"));
    }
}
