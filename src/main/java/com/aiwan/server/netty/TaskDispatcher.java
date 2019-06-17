package com.aiwan.server.netty;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.publicsystem.service.ReflectionManager;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.publicsystem.service.ThreadPoolManager;
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


    /** 人物分配 */
    public void dispatcherTask(final DecodeData decodeData, final Channel channel){

        final Method method = ReflectionManager.getMethod(ReflectionManager.getProtocolClass(decodeData.getType()));

        //获取方法对应的bean

        final Object bean = ReflectionManager.getBean(method);

        final Session session = SessionManager.getSessionByHashCode(channel.hashCode());
        /** 用户已登录 */
        if (session.getUser()!=null){
            ThreadPoolManager.executeUserThread(session.getUser().getAcountId(), new Runnable() {
                @Override
                public void run() {
                    ReflectionUtils.invokeMethod(method,bean,decodeData.getObject(),session);
                }
            });
        }else {
            ThreadPoolManager.excuteOtherThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            ReflectionUtils.invokeMethod(method,bean,decodeData.getObject(),session);
                        }
                    }
            );
        }
    }
}
