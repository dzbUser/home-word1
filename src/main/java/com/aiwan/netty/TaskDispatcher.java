package com.aiwan.netty;

import com.aiwan.publicsystem.CM_UserMessage;
import com.aiwan.publicsystem.DecodeData;
import com.aiwan.service.UserService;
import com.aiwan.util.DeepClone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * 任务分配器
 * type = 1.注册、2.登录、3.移动（move）、4.跳转（shift）
 * */
@Component("taskDispatcher")
public class TaskDispatcher {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    public DecodeData dispatcherTask(DecodeData decodeData){
        //开始分配任务
        DecodeData decodeData1 = null;
        switch (decodeData.getType()){
            case DecodeData.LOGIN:{
                CM_UserMessage userMessage = (CM_UserMessage) DeepClone.restore(decodeData.getData());
                decodeData1 =userService.login(userMessage);
                break;
            }
            case DecodeData.REGIST:{
                break;
            }

            case DecodeData.MOVE:{
                break;
            }

            case DecodeData.SHIFT:{
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + decodeData.getType());
        }
        return decodeData1;
    }
}
