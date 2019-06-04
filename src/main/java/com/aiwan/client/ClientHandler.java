package com.aiwan.client;

import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.role.player.protocol.SM_CreateRole;
import com.aiwan.server.user.account.protocol.SM_UserMessage;
import com.aiwan.server.scenes.protocol.SM_Move;
import com.aiwan.server.scenes.protocol.SM_Shift;
import com.aiwan.server.util.StatusCode;
import com.aiwan.server.util.ObjectToBytes;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
/**
 *
 * 处理器
 * */
public class ClientHandler extends SimpleChannelInboundHandler<DecodeData> {

//    Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DecodeData decodeData) throws Exception {
        Channel channel = ctx.channel();
        //加入缓存
        if (decodeData.getType() == StatusCode.GETUSERMESSAGEFAIL||decodeData.getType() == StatusCode.LOGINFAIL ||decodeData.getType() == StatusCode.REGISTDAIL||decodeData.getType() == StatusCode.REGISTSUCCESS||decodeData.getType() == StatusCode.MOVEFAIL||decodeData.getType()== StatusCode.SHIFTFAIL||decodeData.getType() == StatusCode.CREATEROLEFAIL||decodeData.getType() == StatusCode.NOLOGIN || decodeData.getType() == StatusCode.ROLEMESSAGE){
            String content = (String) ObjectToBytes.restore(decodeData.getData());
           System.out.println(content);
        }
        else if (decodeData.getType() == StatusCode.LOGINSUCCESS){
            SM_UserMessage userMessage = (SM_UserMessage) ObjectToBytes.restore(decodeData.getData());
            LoginUser.setUsername (userMessage.getUsername());
            LoginUser.setCurrentX(userMessage.getCurrentX());
            LoginUser.setCurrentY(userMessage.getCurrentY());
            LoginUser.setMap(userMessage.getMap());
            LoginUser.setMapMessage(userMessage.getMapMessage());
            if (!userMessage.isCreated()){
                System.out.println(userMessage.getOtherMessage());
            }else {
                LoginUser.setRoles(userMessage.getRoles());
                System.out.println(userMessage.getUsername()+"登录成功");
                System.out.println(userMessage.getMapMessage());
            }
        }else if(decodeData.getType() == StatusCode.LOGOUTSUCCESS){
            String content = (String) ObjectToBytes.restore(decodeData.getData());
            LoginUser.setUsername("");
            LoginUser.setRoles(null);
            System.out.println(content);
        }else if (decodeData.getType() == StatusCode.MOVESUCCESS){
            SM_Move sm_move= (SM_Move) ObjectToBytes.restore(decodeData.getData());
            LoginUser.setCurrentY(sm_move.getTargetY());
            LoginUser.setCurrentX(sm_move.getTargetX());
            LoginUser.setMapMessage(sm_move.getMapMessage());
            System.out.println(sm_move.getMapMessage());
        }else if(decodeData.getType() == StatusCode.SHIFTSUCCESS){
            SM_Shift sm_shift = (SM_Shift) ObjectToBytes.restore(decodeData.getData());
            LoginUser.setCurrentY(sm_shift.getTargetY());
            LoginUser.setCurrentX(sm_shift.getTargetX());
            LoginUser.setMap(sm_shift.getMap());
            LoginUser.setMapMessage(sm_shift.getMapMessage());
            System.out.println(sm_shift.getMapMessage());
        }if (decodeData.getType() == StatusCode.INSIST){
            String content = (String) ObjectToBytes.restore(decodeData.getData());
            LoginUser.setUsername("");
            System.out.println(content);
        }else if (decodeData.getType() == StatusCode.GETMESSAGESUCCESS){
            SM_UserMessage sm_userMessage = (SM_UserMessage) ObjectToBytes.restore(decodeData.getData());
            System.out.println(sm_userMessage.getMapMessage());
        }else if (decodeData.getType() == StatusCode.CREATEROLESUCESS){
            //角色创建成功
            SM_CreateRole sm_createRole = (SM_CreateRole) ObjectToBytes.restore(decodeData.getData());
            LoginUser.setRoles(sm_createRole.getRoles());
            System.out.println(sm_createRole.getMessage());
        }
        System.out.println("请输入1.登录 2.注册 3.注销  4.进入角色系统 5.角色移动 6.地图跳转 7.高级登录 8获取用户信息 9.退出游戏");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(null != cause) cause.printStackTrace();
        if(null != ctx) ctx.close();
    }
}
