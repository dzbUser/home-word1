package com.aiwan.server.world.scenes.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.world.base.scene.AbstractScene;
import com.aiwan.server.world.base.scene.DungeonScene;
import com.aiwan.server.world.scenes.command.EnterMapCommand;
import com.aiwan.server.world.scenes.command.ChangeMapCommand;
import com.aiwan.server.world.scenes.command.MoveCommand;
import com.aiwan.server.user.role.fight.pvpunit.BaseUnit;
import com.aiwan.server.user.role.fight.pvpunit.RoleUnit;
import com.aiwan.server.world.scenes.mapresource.MapResource;
import com.aiwan.server.world.scenes.model.Position;
import com.aiwan.server.world.base.scene.UniqueScene;
import com.aiwan.server.world.scenes.protocol.SM_ViewAllUnitInMap;
import com.aiwan.server.world.scenes.protocol.UnitDetailMessage;
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
        //获取地图对象
        AbstractScene abstractScene = GetBean.getMapManager().getSceneObject(role.getMap(), role.getSceneId());
        GetBean.getSceneExecutorService().submit(new MoveCommand(Position.valueOf(x, y), role, abstractScene.getMapId()));
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
        AbstractScene abstractScene = mapManager.getSceneObject(role.getMap(), role.getSceneId());
        if (abstractScene == null) {
            logger.error("mapId:{}没有该地图对象", role.getMap());
            return;
        }
        abstractScene.setFighterAttribute(role);
    }

    @Override
    public void changeMap(Role role, int targetMapId, int targetSceneId) {
        if (role.isChangingMap()) {
            logger.info("角色:{}跳转到{}失败,正在进行地图跳转", role.getId(), targetMapId);
            return;
        }
        AbstractScene abstractScene = GetBean.getMapManager().getSceneObject(targetMapId, targetSceneId);
        if (abstractScene == null) {
            logger.error("找不到mapId为{}的地图资源", role.getId());
            SessionManager.sendPromptMessage(role.getId(), PromptCode.MAPNOEXIST, "");
            return;
        }
        if (!abstractScene.isCanEnter()) {
            //该资源不可进入
            logger.error("mapId为{}的跳转失败，该地图不可进入", role.getId());
            SessionManager.sendPromptMessage(role.getId(), PromptCode.MAP_NO_ENTER, "");
            return;
        }
        //设置正在地图跳转
        RoleUnit roleUnit = (RoleUnit) GetBean.getMapManager().getSceneObject(role.getMap(), role.getSceneId()).getBaseUnit(role.getId());

        if (roleUnit != null && roleUnit.isDeath()) {
            //角色处于死亡状态
            logger.info("角色:{}跳转到{}失败,角色处于死亡状态", role.getId(), targetMapId);
            return;
        }
        role.setChangingMap(true);
        if (abstractScene instanceof DungeonScene) {
            DungeonScene dungeonScene = (DungeonScene) abstractScene;
            dungeonScene.getHandler().quitDungeon(role);
        } else {
            leaveMap(role);
        }
        //进入map地图
        GetBean.getSceneExecutorService().submit(new EnterMapCommand(targetMapId, targetSceneId, role, roleUnit));
    }

    @Override
    public void leaveMap(Role role) {
        GetBean.getMapManager().removeFighterRole(role.getMap(), role.getSceneId(), role.getId());
        //给地图所有玩家发送最新地图信息
        GetBean.getMapManager().sendMessageToUsers(role.getMap(), role.getSceneId());
    }

    public void enterMap(Role role, AbstractScene abstractScene, RoleUnit roleUnit) {
        //获取地图资源
        MapResource mapResource = GetBean.getMapManager().getMapResource(abstractScene.getMapId());
        //初始化化角色坐标
        role.setMap(abstractScene.getMapId());
        role.setSceneId(abstractScene.getSceneId());
        role.setX(mapResource.getOriginX());
        role.setY(mapResource.getOriginY());
        //添加到地图资源中
        RoleUnit newFightRole = RoleUnit.valueOf(role, mapResource.getMapId());
        //传递cd、hp以及mp
        if (roleUnit != null) {
            newFightRole.transferStatus(roleUnit);
        }
        abstractScene.putBaseUnit(newFightRole);
        //写回
        GetBean.getRoleManager().save(role);
        //设置跳转结束
        role.setChangingMap(false);
        //给所有玩家发送消息
        GetBean.getMapManager().sendMessageToUsers(abstractScene.getMapId(), abstractScene.getSceneId());
    }

    @Override
    public void viewUnitInMap(int mapId, Session session) {
        //获取角色
        Role role = GetBean.getRoleManager().load(session.getrId());
        AbstractScene abstractScene = GetBean.getMapManager().getSceneObject(role.getMap(), role.getSceneId());
        if (abstractScene == null) {
            logger.error("没有改地图资源");
            return;
        }

        List<UnitDetailMessage> unitDetailMessages = new ArrayList<>();
        for (BaseUnit baseUnit : abstractScene.getBaseUnitMap().values()) {
            if (!baseUnit.isDeath()) {
                unitDetailMessages.add(UnitDetailMessage.valueOf(baseUnit.getId(), baseUnit.getName(), baseUnit.getPosition().getX(), baseUnit.getPosition().getY(), baseUnit.getHp(), baseUnit.getMp(), baseUnit.getLevel(), baseUnit.getFinalAttribute(), baseUnit.isMonster()));
            }
        }

        SM_ViewAllUnitInMap sm_viewAllUnitInMap = SM_ViewAllUnitInMap.valueOf(unitDetailMessages);
        session.messageSend(SMToDecodeData.shift(StatusCode.VIEW_ALLUNIT_INMAP, sm_viewAllUnitInMap));
    }

}
