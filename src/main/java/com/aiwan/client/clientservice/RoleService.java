package com.aiwan.client.clientservice;

import com.aiwan.client.LoginUser;
import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.role.player.protocol.CM_RoleMessage;
import com.aiwan.server.user.protocol.CM_CreateRole;
import com.aiwan.server.util.Protocol;
import com.aiwan.server.util.SMToDecodeData;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author dzb
 * 客户端角色业务
 * */
public class RoleService {
    public static void roleSystem(Channel channel){
        System.out.println("进图角色系统，请选择您要的功能 1.创建角色 2.查看角色信息 3.退出系统");
        Scanner scanner = new Scanner(System.in);
        int num  = scanner.nextInt();
        if (num ==1){
            createRole(channel);
        }
        else if (num == 2){
            getRoleMessage(channel);
        }

    }
    /**
     * 创建角色
     * */
    public static void createRole(Channel channel){
        //获取客户端屏幕的写入
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入职业：0.战士 1.法师;输入性别：0.女 1.男");
        int job = scanner.nextInt();
        int sex = scanner.nextInt();
        if (job<0||job>1||sex<0||sex>1){
            System.out.println("您的输入不规格");
            return;
        }
        CM_CreateRole cm_createRole = new CM_CreateRole();
        cm_createRole.setAccountId(LoginUser.getUsername());
        cm_createRole.setJob(job);
        cm_createRole.setSex(sex);
        DecodeData decodeData = SMToDecodeData.shift(Protocol.CREATEROLE,cm_createRole);
        channel.writeAndFlush(decodeData);
    }

    /**
     * 获取角色信息
     * */
    public static void getRoleMessage(Channel channel){
        //获取客户端屏幕的写入
        Scanner scanner = new Scanner(System.in);
        CM_RoleMessage cm_roleMessage = new CM_RoleMessage();
        if (LoginUser.getRoles().size() <= 0){
            System.out.println("您还未创建角色");
            return;
        }
        cm_roleMessage.setAccountId(LoginUser.getUsername());
        cm_roleMessage.setrId(LoginUser.getRoles().get(0));
        channel.writeAndFlush(SMToDecodeData.shift(Protocol.GETROLEMESSAGE,cm_roleMessage));
    }
}
