package com.aiwan.server.user.role.skill.resource;

import com.aiwan.server.user.role.skill.impact.ImpactType;

/**
 * 技能效果静态资源解析后存入的数据类
 *
 * @author dengzebiao
 * @since 2019.7.12
 */
public class ImpactAnalysis {

    /**
     * 技能效果类型
     */
    private ImpactType impactType;

    /**
     * 万分比参数值
     */
    private int value;

    /**
     * effectId
     */
    private int effectId;

    public static ImpactAnalysis valueOf(String[] parameters) {
        ImpactAnalysis impactAnalysis = new ImpactAnalysis();
        ImpactType impactType = ImpactType.getImpactType(Integer.parseInt(parameters[0]));
        impactAnalysis.setImpactType(impactType);
        if (!parameters[1].equals("")) {
            impactAnalysis.setValue(Integer.parseInt(parameters[1]));
        }
        if (!parameters[2].equals("")) {
            impactAnalysis.setEffectId(Integer.parseInt(parameters[2]));
        }
        return impactAnalysis;
    }

    public ImpactType getImpactType() {
        return impactType;
    }

    public void setImpactType(ImpactType impactType) {
        this.impactType = impactType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getEffectId() {
        return effectId;
    }

    public void setEffectId(int effectId) {
        this.effectId = effectId;
    }
}
