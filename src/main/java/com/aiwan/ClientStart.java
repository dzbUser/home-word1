package com.aiwan;

import com.aiwan.publicsystem.CM_Move;
import com.aiwan.publicsystem.CM_UserMessage;
import com.aiwan.publicsystem.DecodeData;
import com.aiwan.util.DeepClone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class ClientStart {
    private Logger logger = LoggerFactory.getLogger(ClientStart.class);

    String host = "localhost";
    int port = 8001;
    public void testLongConn() throws Exception {
        logger.debug("start");
        final Socket socket = new Socket();
        socket.connect(new InetSocketAddress(host, port));
        Scanner scanner = new Scanner(System.in);
        new Thread(() -> {
            while (true) {
                try {
                    byte[] input = new byte[1024];
                    int readByte = socket.getInputStream().read(input);
//                    DecodeData decodeData = (DecodeData) DeepClone.restore(input);
//                    String content = new String(decodeData.getData());
//                    logger.debug(readByte+"");
//                    LiveMessage msg = (LiveMessage) DeepClone.restore(input);

                    logger.debug("readByte " + readByte+"  msg:"+new String(input));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        int code;
        while (true) {
            code = scanner.nextInt();
            logger.debug("input code:" + code);
            if (code == 0) {
                break;
            } else if (code == 1) {
//                DecodeData decodeData = new DecodeData();
//                decodeData.setType(DecodeData.LOGIN);
                CM_UserMessage userMessage = new CM_UserMessage();
                userMessage.setUsername("testone");
                userMessage.setPassword("1");
                byte[] data  = DeepClone.writeInto(userMessage);
//                decodeData.setLength(data.length);
//                decodeData.setData(data);
//                byte[] content = DeepClone.writeInto(decodeData);
                ByteBuffer byteBuffer = ByteBuffer.allocate(data.length+6);
                byteBuffer.putShort(DecodeData.LOGIN);
                byteBuffer.putInt(data.length);
                byteBuffer.put(data);
                socket.getOutputStream().write(byteBuffer.array());
                logger.debug("write heart finish!");
            } else if (code == 2) {
//                CM_Move move = new CM_Move();
//                move.setCurrentX((short) 1);
//                move.setCurrentY((short) 2);
//                move.setTargetX((short) 3);
//                move.setTargetY((short) 4);
//                byte[] content = DeepClone.writeInto(move);
//                ByteBuffer byteBuffer = ByteBuffer.allocate(content.length + 5);
//                byteBuffer.put((byte) 2);
//                byteBuffer.putInt(content.length);
//                byteBuffer.put(content);
//                socket.getOutputStream().write(byteBuffer.array());
                logger.debug("write content finish!");
            }
        }
        socket.close();
    }

    // 因为Junit不支持用户输入,所以用main的方式来执行用例
    public static void main(String[] args) throws Exception {
        new ClientStart().testLongConn();
    }
}
