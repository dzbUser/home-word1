package com.aiwan.client;


import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端启动类
 * */
public class Start {
    public static void main(String[] args) throws Exception {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Channel channel = ClientServer.connect();

                        }finally {
                        }
                    }
                }
        ).start();
    }
}
