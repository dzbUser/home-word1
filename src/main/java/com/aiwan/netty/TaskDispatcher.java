package com.aiwan.netty;

import com.aiwan.publicsystem.protocol.DecodeData;
import com.aiwan.user.protocol.CM_UserMessage;
import com.aiwan.user.service.UserService;
import com.aiwan.scenes.protocol.CM_Move;
import com.aiwan.scenes.protocol.CM_Shift;
import com.aiwan.scenes.service.ScenesService;
import com.aiwan.util.ObjectToBytes;
import com.aiwan.util.Protocol;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * 任务分配器
 * */
@Component("taskDispatcher")
public class TaskDispatcher {

    private static Logger logger = LoggerFactory.getLogger(TaskDispatcher.class);
    private UserService userService;
    private ScenesService scenesService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Autowired
    public void setScenesService(ScenesService scenesService){
        this.scenesService = scenesService;
    }

    public DecodeData dispatcherTask(DecodeData decodeData, Channel channel){
        //开始分配任务
        DecodeData decodeData1 = null;
        switch (decodeData.getType()){
            case Protocol.LOGIN:{//分配到登录任务
                CM_UserMessage userMessage = (CM_UserMessage) ObjectToBytes.restore(decodeData.getData());
                userService.login(userMessage,channel);
                break;
            }
            case Protocol.REGIST:{//分配到注册任务
                CM_UserMessage userMessage = (CM_UserMessage) ObjectToBytes.restore(decodeData.getData());
                userService.registUser(userMessage,channel);
                break;
            }
            case Protocol.LOGOUT:{//分配到注销任务
                CM_UserMessage userMessage = (CM_UserMessage) ObjectToBytes.restore(decodeData.getData());
                userService.logout(userMessage);
                break;
            }
            case Protocol.MOVE:{//分配到角色移动任务
                CM_Move cm_move = (CM_Move) ObjectToBytes.restore(decodeData.getData());
                scenesService.move(cm_move);
                break;
            }
            case Protocol.SHIFT:{//分配到角色地图跳转任务
                CM_Shift cm_shift = (CM_Shift) ObjectToBytes.restore(decodeData.getData());
                scenesService.shift(cm_shift);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + decodeData.getType());
        }
        return decodeData1;
    }
}
