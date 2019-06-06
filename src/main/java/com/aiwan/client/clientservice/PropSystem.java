package com.aiwan.client.clientservice;

import com.aiwan.client.LoginUser;
import com.aiwan.server.prop.protocol.CM_PropUse;
import com.aiwan.server.prop.resource.Props;
import com.aiwan.server.role.equipment.CM_ViewEquipBar;
import com.aiwan.server.user.backpack.protocol.CM_ObtainProp;
import com.aiwan.server.user.backpack.protocol.CM_ViewBackpack;
import com.aiwan.server.util.Protocol;
import com.aiwan.server.util.SMToDecodeData;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author dengzebiao
 * @since 2019.6.5
 * 道具管理系统
 * */
public class PropSystem {
    public static void entrance(Channel channel){
        Scanner scanner = new Scanner(System.in);
        System.out.println("您已进入背包系统 1.添加道具 2.查看背包 3.使用道具 4.查看装备栏");
        int num = scanner.nextInt();
        if (num == 1){
            addProp(scanner,channel);
        }
        else if (num == 2){
            CM_ViewBackpack cm_viewBackpack = new CM_ViewBackpack();
            cm_viewBackpack.setAccountId(LoginUser.getUsername());
            channel.writeAndFlush(SMToDecodeData.shift(Protocol.VIEWBACKPACK,cm_viewBackpack));
        }
        else if (num ==3){
            propUse(scanner,channel);
        }
        else if(num == 4){
            viewEquipBar(channel);
        }
    }

    /** 添加道具 */
    public static void addProp(Scanner scanner,Channel channel){
        System.out.println("请输入您要添加的道具的id 数量");
        int id = scanner.nextInt();
        int num = scanner.nextInt();
        CM_ObtainProp cm_obtainProp = new CM_ObtainProp();
        cm_obtainProp.setAccountId(LoginUser.getUsername());
        cm_obtainProp.setId(id);
        cm_obtainProp.setNum(num);
        channel.writeAndFlush(SMToDecodeData.shift(Protocol.OBTAINPROP,cm_obtainProp));
    }

    /** 道具使用 */
    public static void propUse(Scanner scanner,Channel channel){
        System.out.println("请输入您要使用的道具的id");
        int id = scanner.nextInt();
        CM_PropUse cm_propUse = new CM_PropUse();
        cm_propUse.setAccountId(LoginUser.getUsername());
        cm_propUse.setpId(id);
        cm_propUse.setrId(LoginUser.getRoles().get(0));
        channel.writeAndFlush(SMToDecodeData.shift(Protocol.PROPUSER,cm_propUse));
    }

    /** 查看装备栏 */
    public static void viewEquipBar(Channel channel){
        CM_ViewEquipBar cm_viewEquipBar = new CM_ViewEquipBar();
        cm_viewEquipBar.setAccountId(LoginUser.getUsername());
        cm_viewEquipBar.setrId(LoginUser.getRoles().get(0));
        channel.writeAndFlush(SMToDecodeData.shift(Protocol.VIEWQEUIP,cm_viewEquipBar));
    }
}
