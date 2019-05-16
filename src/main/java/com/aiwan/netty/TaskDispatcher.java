package com.aiwan.netty;

import com.aiwan.publicsystem.DecodeData;
import com.aiwan.role.protocol.CM_UserMessage;
import com.aiwan.role.service.UserService;
import com.aiwan.scenes.protocol.CM_Move;
import com.aiwan.scenes.protocol.CM_Shift;
import com.aiwan.scenes.service.ScenesService;
import com.aiwan.util.DeepClone;
import com.aiwan.util.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 任务分配器
 * type = 1.注册、2.登录、3.移动（move）、4.跳转（shift）
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

    public DecodeData dispatcherTask(DecodeData decodeData){
        //开始分配任务
        DecodeData decodeData1 = null;
        switch (decodeData.getType()){
            case Protocol.LOGIN:{
                CM_UserMessage userMessage = (CM_UserMessage) DeepClone.restore(decodeData.getData());
                decodeData1 =userService.login(userMessage);
                break;
            }
            case Protocol.REGIST:{
                CM_UserMessage userMessage = (CM_UserMessage) DeepClone.restore(decodeData.getData());
                decodeData1 = userService.registUser(userMessage);
                break;
            }
            case Protocol.LOGOUT:{
                CM_UserMessage userMessage = (CM_UserMessage) DeepClone.restore(decodeData.getData());
                decodeData1 = userService.logout(userMessage);
                break;
            }
            case Protocol.MOVE:{
                CM_Move cm_move = (CM_Move) DeepClone.restore(decodeData.getData());
                decodeData1 = scenesService.move(cm_move);
                break;
            }
            case Protocol.SHIFT:{
                CM_Shift cm_shift = (CM_Shift) DeepClone.restore(decodeData.getData());
                decodeData1 = scenesService.shift(cm_shift);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + decodeData.getType());
        }
        return decodeData1;
    }
}
