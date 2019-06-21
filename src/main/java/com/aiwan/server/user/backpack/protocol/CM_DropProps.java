package com.aiwan.server.user.backpack.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * 丢弃道具协议
 *
 * @author dengzebiao
 * @since 2019.6.21
 */
@ProtocolAnnotation(protocol = Protocol.DROPPROP)
public class CM_DropProps implements Serializable {
    /**
     * 用户账号
     */
    private String accountId;

    /**
     * 背包位置
     */
    private int position;

    /**
     * 丢弃数量
     */
    private int num;

    /**
     * 创建对象
     */
    public static CM_DropProps valueOf(String accountId, int position, int num) {
        CM_DropProps cm_dropProps = new CM_DropProps();
        cm_dropProps.setAccountId(accountId);
        cm_dropProps.setNum(num);
        cm_dropProps.setPosition(position);
        return cm_dropProps;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
