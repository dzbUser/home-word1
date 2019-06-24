package com.aiwan.client.swing.start;

import com.aiwan.client.socket.ClientServerStart;
import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.util.Protocol;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * 发送心跳
 *
 * @author dengzebiao
 * @since 2019.6.24
 */
public class SendHeard extends TimerTask {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(SendHeard.class);
    /**
     * 发送通道
     */
    private Channel channel;

    public static SendHeard valueOf(Channel channel) {
        SendHeard sendHeard = new SendHeard();
        sendHeard.setChannel(channel);
        return sendHeard;
    }

    @Override
    public void run() {
        try {
            final DecodeData decodeData = new DecodeData();
            decodeData.setType(Protocol.HEART);
            decodeData.setObject(null);
            channel.writeAndFlush(decodeData);
        } finally {
        }
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
