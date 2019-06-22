package com.aiwan.server.user.role.mount.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * @author dengzebiao
 * @since 2019.6.11
 * 使用坐骑升阶丹
 * */
@ProtocolAnnotation(protocol = Protocol.MOUNTUPGRADE)
public class CM_MountUpgrade implements Serializable {
    /** 用户账号 */
    private String accountId;

    /** 角色id */
    private Long rId;

    /**
     * 坐骑丹资源id
     */
    private int resourceId;

    /**
     * 消耗数量
     */
    private int num;

    public String getAccountId() {
        return accountId;
    }

    public CM_MountUpgrade setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public Long getrId() {
        return rId;
    }

    public CM_MountUpgrade setrId(Long rId) {
        this.rId = rId;
        return this;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
