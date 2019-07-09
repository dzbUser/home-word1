package com.aiwan.server.scenes.model;

import com.aiwan.server.monster.model.Monster;
import com.aiwan.server.monster.resource.MonsterResource;
import com.aiwan.server.scenes.fight.model.pvpunit.BaseUnit;
import com.aiwan.server.scenes.fight.model.pvpunit.FighterRole;
import com.aiwan.server.scenes.fight.model.pvpunit.MonsterUnit;
import com.aiwan.server.scenes.mapresource.MapResource;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
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


//    /**
//     * 地图动态单位
//     * */
//    Map<Long, BaseUnit> baseUnitMap = new HashMap<>();
    /**
     * 存储角色
     */
    Map<Long, FighterRole> fighterRoleMap = new HashMap<>();

    /**
     * 怪物映射
     */
    Map<Long, MonsterUnit> monsterMap = new HashMap<>();


    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }


    /**
     * 存角色
     */
    public void putFighterRole(FighterRole fighterRole) {
        fighterRoleMap.put(fighterRole.getId(), fighterRole);
    }

    /**
     * 移除角色
     */
    public void removeFighterRole(Long rId) {
        fighterRoleMap.remove(rId);
    }

    /**
     * 获取角色
     */
    public FighterRole getFighterRole(Long rId) {
        return fighterRoleMap.get(rId);
    }

    /**
     * 获取怪物
     */
    public MonsterUnit getMonster(Long id) {
        return monsterMap.get(id);
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

    public Map<Long, FighterRole> getFighterRoleMap() {
        return fighterRoleMap;
    }

    public void setFighterRoleMap(Map<Long, FighterRole> fighterRoleMap) {
        this.fighterRoleMap = fighterRoleMap;
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
                        monsterMap.put(monster.getId(), monster);
                        break;
                    }
                }
            }
        }

        logger.debug("场景{},怪物加载完毕", mapId);
    }

    public Map<Long, MonsterUnit> getMonsterMap() {
        return monsterMap;
    }

    public void setMonsterMap(Map<Long, MonsterUnit> monsterMap) {
        this.monsterMap = monsterMap;
    }

    /**
     * 设置战斗对象属性
     */
    public void setFighterAttribute(Role role) {
        FighterRole fighterRole = fighterRoleMap.get(role.getId());
        if (fighterRole != null) {
            fighterRole.setRoleAttribute(role.getAttribute().getPureAttribute());
        }
    }
}

