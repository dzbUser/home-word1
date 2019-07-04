package com.aiwan.server.scenes.fight.model.pvpunit;

import com.aiwan.server.scenes.model.Position;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.player.model.Role;

import java.util.HashMap;
import java.util.Map;

/**
 * 战斗成员
 *
 * @author dengzebiao
 */
public class
FighterRole {

    /**
     * 角色基础信息
     */
    private RoleBaseMessage roleBaseMessage;

    /**
     * 角色纯净属性
     */
    private Map<AttributeType, AttributeElement> roleAttribute;

    /**
     * 创建对象
     */
    public static FighterRole valueOf(Role role) {
        FighterRole fighterRole = new FighterRole();
        //初始化角色基础信息
        fighterRole.setRoleBaseMessage(RoleBaseMessage.valueOf(role.getAccountId(), role.getId(), Position.valueOf(role.getX(), role.getY()), role.getName()));
        Map<AttributeType, AttributeElement> pureAttributeMap = new HashMap<>();
        //复制属性
        for (Map.Entry<AttributeType, AttributeElement> entry : role.getAttribute().getFinalAttribute().entrySet()) {
            pureAttributeMap.put(entry.getKey(), AttributeElement.valueOf(entry.getValue().getAttributeType(), entry.getValue().getValue()));
        }
        fighterRole.setRoleAttribute(pureAttributeMap);
        return fighterRole;
    }


    public Map<AttributeType, AttributeElement> getRoleAttribute() {
        return roleAttribute;
    }

    public void setRoleAttribute(Map<AttributeType, AttributeElement> roleAttribute) {
        this.roleAttribute = roleAttribute;
    }

    public RoleBaseMessage getRoleBaseMessage() {
        return roleBaseMessage;
    }

    public void setRoleBaseMessage(RoleBaseMessage roleBaseMessage) {
        this.roleBaseMessage = roleBaseMessage;
    }
}
