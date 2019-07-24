package com.aiwan.server.world.dungeon.service;

import com.aiwan.server.world.scenes.mapresource.MapResource;

/**
 * 副本业务接口
 *
 * @author dengzebiao
 * @since 2019.7.19
 */
public interface IDungeonService {

    /**
     * 创建副本
     *
     * @param rId   角色id
     * @param mapId 地图id
     */
    void createDungeon(long rId, int mapId);

    /**
     * 创建单人副本
     *
     * @param rId         角色id
     * @param mapResource 地图资源
     */
    void createExperienceDungeon(long rId, MapResource mapResource);

    /**
     * 创建团队副本
     *
     * @param rId         角色id
     * @param mapResource 地图资源
     */
    void createKozanIslandDungeon(long rId, MapResource mapResource);
}
