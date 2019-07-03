package com.aiwan.server.scenes.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.scenes.mapresource.MapResource;
import com.aiwan.server.scenes.protocol.SM_Shift;
import com.aiwan.server.user.account.model.User;
import com.aiwan.server.user.account.service.UserManager;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


/**
 * @author dengzebiao
 * 场景业务逻辑类
 * */
@Scope("singleton")
@Service("scenesService")
public class ScenesServiceImpl implements ScenesService{

    private Logger logger = LoggerFactory.getLogger(ScenesServiceImpl.class);

    private UserManager userManager;

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
    /**
     * 角色的移动
     * */
    @Override
    public void move(String accountId, int x, int y, final Session session) {
        logger.info("{}请求移动到({}.{})", accountId, x, y);
        User user = session.getUser();
        Role role = GetBean.getRoleManager().load(user.getRoleId());
        if (GetBean.getMapManager().allowMove(x, y, user.getMap())) {
            role.setX(x);
            role.setY(y);
            GetBean.getRoleManager().save(role);
            //加入地图资源
            GetBean.getMapManager().putRole(role);
            //对所有在线用户发送地图信息
            GetBean.getMapManager().sendMessageToUsers(role.getMap());
            logger.info("{}请求移动到({}.{})成功", accountId, x, y);
        } else {
            session.sendPromptMessage(PromptCode.MOVEFAIL, "");
            logger.info("{}请求移动到({}.{})失败", accountId, x, y);
        }
    }

    /**
     * 地图跳转
     * */
    @Override
    public void shift(Long rId, int map, final Session session) {
        logger.info("角色{}请求地图跳转到{}", rId, map);
        //是否有该地图，若无则放回无该地图
        if (GetBean.getMapManager().getMapResource(map) == null) {
            session.sendPromptMessage(PromptCode.MAPNOEXIST, "");
            logger.info("{}请求失败，原因：没有该地图", rId);
            return;
        }

        //获取角色
        Role role = GetBean.getRoleManager().load(rId);
        //获取就地图
        int oldMap = role.getMap();

        //从旧地图中去除
        GetBean.getMapManager().removeRole(role.getMap(), rId);
        MapResource mapResource = GetBean.getMapManager().getMapResource(map);

        //初始化化角色坐标
        role.setMap(map);
        role.setX(mapResource.getOriginX());
        role.setY(mapResource.getOriginY());
        //添加到地图资源中
        GetBean.getMapManager().putRole(role);
        //写回
        GetBean.getRoleManager().save(role);
        //给所有玩家发送消息
        GetBean.getMapManager().sendMessageToUsers(role.getMap());
        //如果与换到新的地图，需要给旧的地图发送改变消息
        if (map != oldMap) {
            GetBean.getMapManager().sendMessageToUsers(oldMap);
        }
        logger.info("请求成功");
    }

    @Override
    public void moveUserPosition(String accountId, int x, int y) {

//        //改缓存
//        User user = userManager.getUserByAccountId(accountId);
//        user.setCurrentX(x);
//        user.setCurrentY(y);
//        userManager.save(user);
//
//        //改地图内的用户
//        GetBean.getMapManager().putRole(user);
//        GetBean.getMapManager().sendMessageToUsers(user.getMap(), user.getAcountId());
//        logger.info("{}请求移动到({}.{})成功", accountId, x, y);
    }
}
