package com.aiwan.client;

import com.aiwan.publicsystem.protocol.DecodeData;
import com.aiwan.user.protocol.SM_UserMessage;
import com.aiwan.scenes.protocol.SM_Move;
import com.aiwan.scenes.protocol.SM_Shift;
import com.aiwan.util.ConsequenceCode;
import com.aiwan.util.ObjectToBytes;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
/**
 *
 * 处理器
 * */
public class ClientHandler extends SimpleChannelInboundHandler<DecodeData> {

//    Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    //存登录成功username;
    private static String username;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DecodeData decodeData) throws Exception {
        Channel channel = ctx.channel();
        //加入缓存
        if (decodeData.getType() == ConsequenceCode.LOGINFAIL ||decodeData.getType() == ConsequenceCode.REGISTDAIL||decodeData.getType() == ConsequenceCode.REGISTSUCCESS||decodeData.getType() == ConsequenceCode.MOVEFAIL||decodeData.getType()==ConsequenceCode.SHIFTFAIL){
            String content = (String) ObjectToBytes.restore(decodeData.getData());
           System.out.println(content);
        }
        else if (decodeData.getType() == ConsequenceCode.LOGINSUCCESS){
            SM_UserMessage userMessage = (SM_UserMessage) ObjectToBytes.restore(decodeData.getData());
            LoginUser.setUsername (userMessage.getUsername());
            LoginUser.setCurrentX(userMessage.getCurrentX());
            LoginUser.setCurrentY(userMessage.getCurrentY());
            LoginUser.setMap(userMessage.getMap());
            LoginUser.setMapMessage(userMessage.getMapMessage());
            System.out.println(userMessage.getUsername()+"登录成功");
            System.out.println(userMessage.getMapMessage());

        }else if(decodeData.getType() == ConsequenceCode.LOGOUTSUCCESS){
            String content = (String) ObjectToBytes.restore(decodeData.getData());
            LoginUser.setUsername("");
            System.out.println(content);
        }else if (decodeData.getType() == ConsequenceCode.MOVESUCCESS){
            SM_Move sm_move= (SM_Move) ObjectToBytes.restore(decodeData.getData());
            LoginUser.setCurrentY(sm_move.getTargetY());
            LoginUser.setCurrentX(sm_move.getTargetX());
            LoginUser.setMapMessage(sm_move.getMapMessage());
            System.out.println(sm_move.getMapMessage());
        }else if(decodeData.getType() == ConsequenceCode.SHIFTSUCCESS){
            SM_Shift sm_shift = (SM_Shift) ObjectToBytes.restore(decodeData.getData());
            LoginUser.setCurrentY(sm_shift.getTargetY());
            LoginUser.setCurrentX(sm_shift.getTargetX());
            LoginUser.setMap(sm_shift.getMap());
            LoginUser.setMapMessage(sm_shift.getMapMessage());
            System.out.println(sm_shift.getMapMessage());
        }if (decodeData.getType() == ConsequenceCode.INSIST){
            String content = (String) ObjectToBytes.restore(decodeData.getData());
            LoginUser.setUsername("");
            System.out.println(content);
        }
//        channel.writeAndFlush(decodeData);
        System.out.println("请输入1.登录 2.注册 3.注销 4.角色移动 5.地图跳转 6 高级登录 7.退出游戏");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        logger.debug("channelReadComplete");
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(null != cause) cause.printStackTrace();
        if(null != ctx) ctx.close();
    }
}
