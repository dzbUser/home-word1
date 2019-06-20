package com.aiwan.server.user.role.equipment.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * @author dengzebiao
 * @since 2019.6.20
 * 卸下装备协议类
 */
@ProtocolAnnotation(protocol = Protocol.UNLOADEQUIP)
public class CM_UnloadingEquipment implements Serializable {

    /**
     * 账号id
     */
    private String accountId;

    /**
     * 角色id
     */
    private Long rid;

    /**
     * 装备位置
     */
    private int position;

    /**
     * 创建对象
     */
    public static CM_UnloadingEquipment valueOf(String accountId, Long rid, int position) {
        CM_UnloadingEquipment cm_unloadingEquipment = new CM_UnloadingEquipment();
        cm_unloadingEquipment.setAccountId(accountId);
        cm_unloadingEquipment.setRid(rid);
        cm_unloadingEquipment.setPosition(position);
        return cm_unloadingEquipment;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
