package com.aiwan.server.netty;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.publicsystem.service.ReflectionManager;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.publicsystem.service.ThreadPoolManager;
import com.aiwan.server.user.account.protocol.CM_HLogin;
import com.aiwan.server.user.account.protocol.CM_Login;
import com.aiwan.server.user.account.protocol.CM_Registered;
import com.aiwan.server.util.GetBean;
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
        //判断时属于什么协议
        if (decodeData.getObject() instanceof CM_Login){
            //普通登录协议类
            CM_Login cm_login = (CM_Login)decodeData.getObject();
            GetBean.getAccountExecutorService().addTask(cm_login.getUsername(), new Runnable() {
                @Override
                public void run() {
                    ReflectionUtils.invokeMethod(method,bean,decodeData.getObject(),session);
                }
            });
        }else if (decodeData.getObject() instanceof CM_Registered){
            //注册协议类
            CM_Registered cm_registered = (CM_Registered) decodeData.getObject();
            GetBean.getAccountExecutorService().addTask(cm_registered.getUsername(), new Runnable() {
                @Override
                public void run() {
                    ReflectionUtils.invokeMethod(method,bean,decodeData.getObject(),session);
                }
            });
        }
        else if (decodeData.getObject() instanceof CM_HLogin){
            //高级登录协议
            CM_HLogin cm_hLogin = (CM_HLogin) decodeData.getObject();
            GetBean.getAccountExecutorService().addTask(cm_hLogin.getUsername(), new Runnable() {
                @Override
                public void run() {
                    ReflectionUtils.invokeMethod(method,bean,decodeData.getObject(),session);
                }
            });
        }
        //用户已登录
        else if (session.getUser()!=null){
            GetBean.getAccountExecutorService().addTask(session.getUser().getAcountId(), new Runnable() {
                @Override
                public void run() {
                    ReflectionUtils.invokeMethod(method,bean,decodeData.getObject(),session);
                }
            });
        }else {
            logger.info("错误包");
        }
    }
}
