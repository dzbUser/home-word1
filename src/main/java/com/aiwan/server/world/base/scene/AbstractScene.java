package com.aiwan.server.world.base.scene;

import com.aiwan.server.base.executor.ICommand;
import com.aiwan.server.user.role.fight.pvpunit.BaseUnit;
import com.aiwan.server.user.role.fight.pvpunit.RoleUnit;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.world.scenes.mapresource.MapResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 场景抽象类
 *
 * @author dengzebiao
 * @since 2019.7.18
 */
public abstract class AbstractScene {
    Logger logger = LoggerFactory.getLogger(UniqueScene.class);

    protected static final AtomicInteger GLOBAL_SCENE_ID = new AtomicInteger(Integer.MAX_VALUE - 1);

    /**
     * 场景唯一id
     */
    private int sceneId;

    /**
     * 地图id
     */
    private int mapId;

    public int getKey() {
        if (sceneId == 0) {
            return mapId;
        }
        return sceneId;
    }

    /**
     * 地图内战斗单位
     */
    private Map<Long, BaseUnit> baseUnitMap = new HashMap<>();


    /**
     * 定时器容器
     */
    private Map<Class<? extends ICommand>, ICommand> commandMap = new HashMap<>();

    /**
     * 存入战斗单位
     */
    public void putBaseUnit(BaseUnit baseUnit) {
        baseUnitMap.put(baseUnit.getId(), baseUnit);
    }

    /**
     * 移除战斗单位
     */
    public void removeBaseUnit(Long id) {
        baseUnitMap.remove(id);
    }

    /**
     * 获取战斗单位
     */
    public BaseUnit getBaseUnit(Long id) {
        return baseUnitMap.get(id);
    }

    /**
     * 普通地图没用
     */
    public void monsterKillListener() {

    }

    public AbstractScene(int mapId) {
        //sceneId,默认为0
        this(mapId, 0);
    }

    public AbstractScene(int mapId, int sceneId) {
        this.mapId = mapId;
        this.sceneId = sceneId;
    }

    /**
     * 获取静态资源
     */
    public MapResource getResource() {
        return GetBean.getMapManager().getMapResource(mapId);
    }

    /**
     * 设置战斗对象属性
     */
    public void setFighterAttribute(Role role) {
        RoleUnit roleUnit = (RoleUnit) baseUnitMap.get(role.getId());
        if (roleUnit != null) {
            roleUnit.setRoleAttribute(role.getAttribute().getPureAttribute());
        }
    }

    /**
     * 寻找周围玩家
     *
     * @param list   加入list
     * @param target 目标单位
     * @param range  范围
     * @param num    数目 target
     * @param active 施法单位
     */
    public void findAroundUnit(BaseUnit active, List<BaseUnit> list, BaseUnit target, int range, int num) {
        for (BaseUnit baseUnit : baseUnitMap.values()) {
            //除去施法单位、施法选择单位、已死亡单位、队友
            if (baseUnit.getId().equals(active.getId()) || baseUnit.getId().equals(target.getId()) || baseUnit.isDeath() || GetBean.getTeamManager().isTeamMate(active.getId(), baseUnit.getId())) {
                //施法单位
                continue;
            }

            if (num > 0) {
                if (GetBean.getFightService().isDistanceSatisfy(target, baseUnit, range)) {
                    list.add(baseUnit);
                    num--;
                }
            } else {
                break;
            }
        }
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public Map<Long, BaseUnit> getBaseUnitMap() {
        return baseUnitMap;
    }

    public void setBaseUnitMap(Map<Long, BaseUnit> baseUnitMap) {
        this.baseUnitMap = baseUnitMap;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Map<Class<? extends ICommand>, ICommand> getCommandMap() {
        return commandMap;
    }

    public void setCommandMap(Map<Class<? extends ICommand>, ICommand> commandMap) {
        this.commandMap = commandMap;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }
}
