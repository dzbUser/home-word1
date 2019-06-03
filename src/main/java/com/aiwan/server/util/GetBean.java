package com.aiwan.server.util;

import com.aiwan.server.ramcache.orm.Accessor;
import com.aiwan.server.netty.TaskDispatcher;
import com.aiwan.server.role.player.service.RoleService;
import com.aiwan.server.scenes.service.MapManager;
//import com.aiwan.server.user.dao.UserDao;
import com.aiwan.server.scenes.service.ScenesService;
import com.aiwan.server.user.service.UserManager;
import com.aiwan.server.user.service.UserService;
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
    private static RoleService roleService;

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
    public  void setRoleService(RoleService roleService) {
        GetBean.roleService = roleService;
    }

    public static RoleService getRoleService() {
        return GetBean.roleService;
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
