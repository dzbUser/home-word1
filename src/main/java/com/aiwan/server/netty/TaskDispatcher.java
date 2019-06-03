package com.aiwan.server.netty;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.publicsystem.service.ReflectionManager;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.publicsystem.service.ThreadPoolManager;
import com.aiwan.server.util.ObjectToBytes;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author dengzebiao
 * 任务分配器
 * */
@Component("taskDispatcher")
public class TaskDispatcher {

    private static Logger logger = LoggerFactory.getLogger(TaskDispatcher.class);


    //任务分配
    public void dispatcherTask(DecodeData decodeData, final Channel channel){
        //获取协议对应的方法
        final Method method = ReflectionManager.getMethod(ReflectionManager.getProtocolClass(decodeData.getType()));

        //获取方法对应的bean
        //反序列化对象
        final Object obj = ObjectToBytes.restore(decodeData.getData());

        final Object bean = ReflectionManager.getBean(method);

        final Session session = SessionManager.getSessionByHashCode(channel.hashCode());
        ThreadPoolManager.start("user", new Runnable() {
            @Override
            public void run() {
                ReflectionUtils.invokeMethod(method,bean,obj,session);
            }
        });
    }
}
