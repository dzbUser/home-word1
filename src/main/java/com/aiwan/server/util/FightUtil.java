package com.aiwan.server.util;

import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;

import java.util.Map;

/**
 * 战斗工具类
 *
 * @author dengzebiao
 * @since 2019.7.9
 */
public class FightUtil {

    /**
     * 计算最终伤害
     */
    public static long calculateFinalHurt(Map<AttributeType, AttributeElement> active, Map<AttributeType, AttributeElement> passive, int value) {
        return (active.get(AttributeType.ATTACK).getValue() * value - 2500 * passive.get(AttributeType.DEFENSE).getValue()) / 10000;
    }

    /**
     * 计算最终回复血量
     */
    public static long calculateCureBlood(Map<AttributeType, AttributeElement> active, int value) {
        return active.get(AttributeType.ATTACK).getValue() * value / 20000;
    }
}
