package com.aiwan.server.scenes.protocol;

import java.io.Serializable;
import java.util.List;

/**
 * 返回所有战斗单位信息
 *
 * @author dengzebiao
 * @since 2019.7.10
 */
public class SM_ViewAllUnitInMap implements Serializable {

    private List<UnitDetailMessage> unitDetailMessages;

    public static SM_ViewAllUnitInMap valueOf(List<UnitDetailMessage> unitDetailMessages) {
        SM_ViewAllUnitInMap sm_viewAllUnitInMap = new SM_ViewAllUnitInMap();
        sm_viewAllUnitInMap.setUnitDetailMessages(unitDetailMessages);
        return sm_viewAllUnitInMap;
    }

    public List<UnitDetailMessage> getUnitDetailMessages() {
        return unitDetailMessages;
    }

    public void setUnitDetailMessages(List<UnitDetailMessage> unitDetailMessages) {
        this.unitDetailMessages = unitDetailMessages;
    }
}
