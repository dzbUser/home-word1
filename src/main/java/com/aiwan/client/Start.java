package com.aiwan.client;


import com.aiwan.publicsystem.DecodeData;
import com.aiwan.role.protocol.CM_UserMessage;
import com.aiwan.util.DeepClone;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Start {
    private static Logger logger = LoggerFactory.getLogger(Start.class);
    public static void main(String[] args) throws Exception {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Channel channel = ClientServer.connect();

                        }finally {
                            logger.debug("客户端启动结束");
                        }
                    }
                }
        ).start();
    }
}
