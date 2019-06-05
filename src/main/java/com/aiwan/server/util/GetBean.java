package com.aiwan.server.util;

import com.aiwan.server.prop.service.PropService;
import com.aiwan.server.prop.service.PropsManager;
import com.aiwan.server.ramcache.orm.Accessor;
import com.aiwan.server.netty.TaskDispatcher;
import com.aiwan.server.role.player.service.RoleService;
import com.aiwan.server.scenes.service.MapManager;
//import com.aiwan.server.user.dao.UserDao;
import com.aiwan.server.scenes.service.ScenesService;
import com.aiwan.server.user.account.service.UserManager;
import com.aiwan.server.user.account.service.UserService;
import com.aiwan.server.user.backpack.service.BackpackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 单例资源获取工具类
 * */
@Component("getBean")
public class GetBean {
    private static UserService userService;
    private static TaskDispatcher taskDispatcher;
    private static MapManager mapManager;
    private static Accessor accessor;
    private static UserManager userManager;
    private static ScenesService scenesService;
    private static RoleService roleService;
    private static PropsManager propsManager;
    private static BackpackService backpackService;
    private static PropService propService;

    public static PropService getPropService() {
        return propService;
    }

    @Autowired
    public void setPropService(PropService propService) {
        GetBean.propService = propService;
    }

    public static BackpackService getBackpackService() {
        return backpackService;
    }

    @Autowired
    public void setBackpackService(BackpackService backpackService) {
        GetBean.backpackService = backpackService;
    }

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

    public static PropsManager getPropsManager() {
        return propsManager;
    }

    @Autowired
    public void setPropsManager(PropsManager propsManager) {
        GetBean.propsManager = propsManager;
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
