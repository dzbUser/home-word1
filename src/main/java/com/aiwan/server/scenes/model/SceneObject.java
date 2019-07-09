package com.aiwan.server.scenes.model;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneRateCommand;
import com.aiwan.server.monster.resource.MonsterResource;
import com.aiwan.server.scenes.fight.model.pvpunit.BaseUnit;
import com.aiwan.server.scenes.fight.model.pvpunit.FighterRole;
import com.aiwan.server.scenes.fight.model.pvpunit.MonsterUnit;
import com.aiwan.server.scenes.mapresource.MapResource;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.MonsterGenerateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dengzebiao
 * 场景对象
 */
public class SceneObject {
    Logger logger = LoggerFactory.getLogger(SceneObject.class);

    /**
     * 地图id
     */
    private int mapId;

    private Map<Long, BaseUnit> baseUnitMap = new HashMap<>();

    private AbstractSceneRateCommand abstractSceneRateCommand;

    /**
     * 存入战斗单位
     * */
    public void putBaseUnit(BaseUnit baseUnit) {
        baseUnitMap.put(baseUnit.getId(), baseUnit);
    }

    /**
     * 移除战斗单位
     * */
    public void removeBaseUnit(Long id) {
        baseUnitMap.remove(id);
    }

    /**
     * 获取战斗单位
     * */
    public BaseUnit getBaseUnit(Long id) {
        return baseUnitMap.get(id);
    }



    /**
     * 创建场景
     */
    public static SceneObject valueOf(int mapId) {
        SceneObject sceneObject = new SceneObject();
        sceneObject.setMapId(mapId);
        return sceneObject;
    }

    /**
     * 获取静态资源
     */
    public MapResource getResource() {
        return GetBean.getMapManager().getMapResource(mapId);
    }

    /**
     * 初始化
     */
    public void init() {
        generateMonster();
    }

    /**
     * 生成怪物
     */
    private void generateMonster() {
        /*
         * 1.获取该地图的怪物
         * 2.获取数量，循环生成
         * 3.获取随机坐标
         * 4.生成怪物
         * */
        List<MonsterResource> monsterResources = GetBean.getMonsterManager().getMonsterList(getMapId());
        if (monsterResources == null) {
            return;
        }
        for (MonsterResource monsterResource : monsterResources) {
            for (int i = 0; i < monsterResource.getNum(); i++) {
                //获取随机坐标
                while (true) {
                    Position position = MonsterGenerateUtil.generaterRandomPosition(getResource().getWidth(), getResource().getHeight());
                    if (GetBean.getMapManager().allowMove(position.getX(), position.getY(), getMapId())) {
                        MonsterUnit monster = MonsterUnit.valueOf(monsterResource, position);
                        baseUnitMap.put(monster.getId(), monster);
                        break;
                    }
                }
            }
        }

        logger.debug("场景{},怪物加载完毕", mapId);
    }


    /**
     * 设置战斗对象属性
     */
    public void setFighterAttribute(Role role) {
        FighterRole fighterRole = (FighterRole) baseUnitMap.get(role.getId());
        if (fighterRole != null) {
            fighterRole.setRoleAttribute(role.getAttribute().getPureAttribute());
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

    public AbstractSceneRateCommand getAbstractSceneRateCommand() {
        return abstractSceneRateCommand;
    }

    public void setAbstractSceneRateCommand(AbstractSceneRateCommand abstractSceneRateCommand) {
        this.abstractSceneRateCommand = abstractSceneRateCommand;
    }
}

