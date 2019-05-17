package com.aiwan.util;

import com.aiwan.netty.TaskDispatcher;
import com.aiwan.user.dao.UserDao;
import com.aiwan.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 单例资源获取工具类
 * */
@Component("getBean")
public class GetBean {
    private static UserService userService;
    private static UserDao userDao;
    private static TaskDispatcher taskDispatcher;

    public static TaskDispatcher getTaskDispatcher() {
        return GetBean.taskDispatcher;
    }

    @Autowired
    public  void setTaskDispatcher(TaskDispatcher taskDispatcher) {
        GetBean.taskDispatcher = taskDispatcher;
    }

    public static UserService getUserService() {
        return GetBean.userService;
    }
    @Autowired
    public  void setUserService(UserService userService) {
        GetBean.userService = userService;
    }

    public static UserDao getUserDao() {
        return GetBean.userDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        GetBean.userDao = userDao;
    }
}
