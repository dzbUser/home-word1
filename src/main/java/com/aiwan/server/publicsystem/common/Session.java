package com.aiwan.server.publicsystem.common;

import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.publicsystem.protocol.SM_PromptMessage;
import com.aiwan.server.user.account.model.User;
import com.aiwan.server.util.PromptCode;
import com.aiwan.server.util.SMToDecodeData;
import com.aiwan.server.util.StatusCode;
import io.netty.channel.Channel;

/**
 * @author dengzebiao
 * 用户session
 * */
public class Session {
    private Channel channel;
    private User user;
    /**
     * 角色id
     */
    private long rId;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void messageSend(DecodeData decodeData){
        channel.writeAndFlush(decodeData);
    }

    /**
     * 发送提示信息
     */
    public void sendPromptMessage(int code, String message) {
        messageSend(SMToDecodeData.shift(StatusCode.MESSAGE, SM_PromptMessage.valueOf(code, message)));
    }

    public long getrId() {
        return rId;
    }

    public void setrId(long rId) {
        this.rId = rId;
    }
}
