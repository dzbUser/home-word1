package com.aiwan.util;

import com.aiwan.common.ramcache.orm.Accessor;
import com.aiwan.netty.TaskDispatcher;
import com.aiwan.scenes.service.MapManager;
//import com.aiwan.user.dao.UserDao;
import com.aiwan.scenes.service.ScenesService;
import com.aiwan.user.service.UserManager;
import com.aiwan.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 单例资源获取工具类
 * */
@Component("getBean")
public class GetBean {
    private static UserService userService;
//    private static UserDao userDao;
    private static TaskDispatcher taskDispatcher;
    private static MapManager mapManager;
    private static Accessor accessor;
    private static UserManager userManager;
    private static ScenesService scenesService;

    public static TaskDispatcher getTaskDispatcher() {
        return GetBean.taskDispatcher;
    }

    @Autowired
    public  void setTaskDispatcher(TaskDispatcher taskDispatcher) {
        GetBean.taskDispatcher = taskDispatcher;
    }

    public static ScenesService getScenesService() {
        return scenesService;
    }

    @Autowired
    public  void setScenesService(ScenesService scenesService) {
        GetBean.scenesService = scenesService;
    }

    public static UserService getUserService() {
        return GetBean.userService;
    }
    @Autowired
    public  void setUserService(UserService userService) {
        GetBean.userService = userService;
    }

//    public static UserDao getUserDao() {
//        return GetBean.userDao;
//    }
//
//    @Autowired
//    public void setUserDao(UserDao userDao) {
//        GetBean.userDao = userDao;
//    }

    public static MapManager getMapManager() {
        return mapManager;
    }
    @Autowired
    public void setMapManager(MapManager mapManager) {
        GetBean.mapManager = mapManager;
    }

    public static Accessor getAccessor() {
        return GetBean.accessor;
    }

    @Autowired
    public  void setAccessor(Accessor accessor) {
        GetBean.accessor = accessor;
    }

    public static UserManager getUserManager() {
        return GetBean.userManager;
    }
    @Autowired
    public  void setUserManager(UserManager userManager) {
        GetBean.userManager = userManager;
    }

}
