package com.aiwan.server.util;

import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 战力计算公式
 *
 * @author dengzebiao
 * @since 2019.7.25
 */
@Component
public class CombatPowerFormula {

    private final int EVERY_ATTACK_POWER = 300;

    private final int EVERY_BLOOD_POWER = 100;

    private final int EVERY_DEFENCE_POWER = 100;

    public long calculateCombatPower(Map<AttributeType, AttributeElement> attributeMap) {
        long attackPower = attributeMap.get(AttributeType.ATTACK).getValue() * EVERY_ATTACK_POWER;
        long bloodPower = attributeMap.get(AttributeType.BLOOD).getValue() * EVERY_BLOOD_POWER;
        long defencePower = attributeMap.get(AttributeType.DEFENSE).getValue() * EVERY_DEFENCE_POWER;
        return attackPower + bloodPower + defencePower;
    }
}
