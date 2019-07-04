package com.aiwan.server.scenes.model;

import com.aiwan.server.monster.model.Monster;
import com.aiwan.server.monster.resource.MonsterResource;
import com.aiwan.server.scenes.fight.model.pvpunit.FighterRole;
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


    /**
     * 存储角色
     */
    Map<Long, FighterRole> fighterRoleMap = new HashMap<>();

    /**
     * 怪物映射
     */
    Map<Long, Monster> monsterMap = new HashMap<>();

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }


    /**
     * 存角色
     */
    public void putFighterRole(Role role) {
        fighterRoleMap.put(role.getId(), FighterRole.valueOf(role));
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
                        Monster monster = new Monster(monsterResource.getResourceId(), position);
                        monsterMap.put(monster.getObjectId(), monster);
                        break;
                    }
                }
            }
        }

        logger.debug("场景{},怪物加载完毕", mapId);
    }

    public Map<Long, Monster> getMonsterMap() {
        return monsterMap;
    }

    public void setMonsterMap(Map<Long, Monster> monsterMap) {
        this.monsterMap = monsterMap;
    }
}

