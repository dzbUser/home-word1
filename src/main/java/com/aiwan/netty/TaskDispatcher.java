package com.aiwan.netty;

import com.aiwan.publicsystem.common.Session;
import com.aiwan.publicsystem.protocol.DecodeData;
import com.aiwan.publicsystem.service.ReflectionManager;
import com.aiwan.publicsystem.service.SessionManager;
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
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 * 任务分配器
 * */
@Component("taskDispatcher")
public class TaskDispatcher {

    private static Logger logger = LoggerFactory.getLogger(TaskDispatcher.class);
    //用户运行线程池
    private Executor userExecutor = Executors.newFixedThreadPool(10);
    //场景运行线程池
    private Executor secensExecutor = Executors.newFixedThreadPool(10);


    //任务分配
    public void dispatcherTask(DecodeData decodeData, final Channel channel){
        //获取协议对应的方法
        final Method method = ReflectionManager.getMethod(ReflectionManager.getProtocolClass(decodeData.getType()));

        //获取方法对应的bean
        //反序列化对象
        final Object obj = ObjectToBytes.restore(decodeData.getData());

        final Object bean = ReflectionManager.getBean(method);

        final Session session = SessionManager.getSessionByHashCode(channel.hashCode());
        if (bean instanceof UserService){//用户线程池
            userExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    ReflectionUtils.invokeMethod(method,bean,obj,session);
                }
            });
        }else {//场景线程池
            secensExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    ReflectionUtils.invokeMethod(method,bean,obj,session);
                }
            });
        }
    }
}
