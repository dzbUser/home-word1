package com.aiwan.server.user.role.buff.resource.bean;

import com.aiwan.server.user.role.buff.resource.EffectResource;
import com.aiwan.server.user.role.buff.resource.IFightBuffAnalysis;

/**
 * 战斗治疗buff参数
 *
 * @author dengzebiao
 * @since 2019.7.15
 */
public class CureFightBuffBean implements IFightBuffAnalysis {

    private int cure;

    @Override
    public void doParse(EffectResource effectResource) {
        cure = Integer.parseInt((String) effectResource.getValueMap().get("cure"));
    }

    public int getCure() {
        return cure;
    }
}
