package com.aiwan.server.scenes.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.scenes.command.EnterMapCommand;
import com.aiwan.server.scenes.command.LeaveMapCommand;
import com.aiwan.server.scenes.command.MoveCommand;
import com.aiwan.server.scenes.mapresource.MapResource;
import com.aiwan.server.scenes.model.Position;
import com.aiwan.server.scenes.model.SceneObject;
import com.aiwan.server.scenes.protocol.SM_Shift;
import com.aiwan.server.user.account.model.User;
import com.aiwan.server.user.account.service.UserManager;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * @author dengzebiao
 * 场景业务逻辑类
 * */
@Scope("singleton")
@Service("scenesService")
public class ScenesServiceImpl implements ScenesService{

    private Logger logger = LoggerFactory.getLogger(ScenesServiceImpl.class);

    @Autowired
    private MapManager mapManager;

    /**
     * 角色的移动
     * */
    @Override
    public void move(Long rId, int x, int y, final Session session) {
        logger.info("角色{}请求移动到({}.{})", rId, x, y);
        Role role = GetBean.getRoleManager().load(rId);
        GetBean.getSceneExecutorService().submit(new MoveCommand(Position.valueOf(x, y), role));
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
        //脱离地图
        GetBean.getSceneExecutorService().submit(new LeaveMapCommand(role));
        //进入map地图
        GetBean.getSceneExecutorService().submit(new EnterMapCommand(map, role));
        logger.info("请求成功");
    }

    @Override
    public void updateSceneAttribute(final Role role) {
        //获取场景
        SceneObject sceneObject = mapManager.getSceneObject(role.getMap());
        if (sceneObject == null) {
            logger.error("mapId:{}没有该地图对象", role.getMap());
            return;
        }
        sceneObject.setFighterAttribute(role);
    }

}
