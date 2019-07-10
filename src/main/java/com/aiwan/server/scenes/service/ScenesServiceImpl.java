package com.aiwan.server.scenes.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.scenes.command.EnterMapCommand;
import com.aiwan.server.scenes.command.ChangeMapCommand;
import com.aiwan.server.scenes.command.MoveCommand;
import com.aiwan.server.scenes.fight.model.pvpunit.BaseUnit;
import com.aiwan.server.scenes.fight.model.pvpunit.FighterRole;
import com.aiwan.server.scenes.model.Position;
import com.aiwan.server.scenes.model.SceneObject;
import com.aiwan.server.scenes.protocol.SM_ViewAllUnitInMap;
import com.aiwan.server.scenes.protocol.UnitDetailMessage;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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
        if (role == null) {
            logger.info("角色{}请求地图跳转到{}失败,没有该角色", rId, map);
            return;
        }
        //脱离地图
        GetBean.getSceneExecutorService().submit(new ChangeMapCommand(role, map));
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

    @Override
    public void changeMap(Role role, int targetMapId) {
        if (role.isChangingMap()) {
            logger.info("角色:{},正在进行地图跳转", role.getId());
            return;
        }
        //设置正在地图跳转
        role.setChangingMap(true);
        FighterRole fighterRole = (FighterRole) GetBean.getMapManager().getSceneObject(role.getMap()).getBaseUnit(role.getId());
        leaveMap(role);
        //进入map地图
        GetBean.getSceneExecutorService().submit(new EnterMapCommand(targetMapId, role, fighterRole));
    }

    @Override
    public void leaveMap(Role role) {
        //脱离地图
        GetBean.getMapManager().removeFighterRole(role.getMap(), role.getId());
        //给地图所有玩家发送最新地图信息
        GetBean.getMapManager().sendMessageToUsers(role.getMap());
    }

    @Override
    public void viewUnitInMap(int mapId, Session session) {
        SceneObject sceneObject = GetBean.getMapManager().getSceneObject(mapId);
        if (sceneObject == null) {
            logger.error("没有改地图资源");
            return;
        }

        List<UnitDetailMessage> unitDetailMessages = new ArrayList<>();
        for (BaseUnit baseUnit : sceneObject.getBaseUnitMap().values()) {
            unitDetailMessages.add(UnitDetailMessage.valueOf(baseUnit.getId(), baseUnit.getName(), baseUnit.getPosition().getX(), baseUnit.getPosition().getY(), baseUnit.getHp(), baseUnit.getMp(), baseUnit.getLevel(), baseUnit.getFinalAttribute(), baseUnit.isMonster()));
        }

        SM_ViewAllUnitInMap sm_viewAllUnitInMap = SM_ViewAllUnitInMap.valueOf(unitDetailMessages);
        session.messageSend(SMToDecodeData.shift(StatusCode.VIEW_ALLUNIT_INMAP, sm_viewAllUnitInMap));
    }

}
