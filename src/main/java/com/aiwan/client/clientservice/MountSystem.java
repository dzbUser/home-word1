package com.aiwan.client.clientservice;

import com.aiwan.client.LoginUser;
import com.aiwan.server.role.mount.protocol.CM_ViewMount;
import com.aiwan.server.util.Protocol;
import com.aiwan.server.util.SMToDecodeData;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author dengzebiao
 * @since 2019.6.6
 * 坐骑系统
 * */
public class MountSystem {
    public static void entrance(Channel channel){
        Scanner scanner = new Scanner(System.in);
        System.out.println("您已进入坐骑系统 1.查看坐骑信息");
        int num = scanner.nextInt();
        if (num == 1){
            viewMessage(channel);
        }
    }

    public static void viewMessage(Channel channel){
        CM_ViewMount cm_viewMount = new CM_ViewMount();
        cm_viewMount.setAccountId(LoginUser.getUsername());
        cm_viewMount.setrId(LoginUser.getRoles().get(0));
        channel.writeAndFlush(SMToDecodeData.shift(Protocol.VIEWMOUNT,cm_viewMount));
    }
}
