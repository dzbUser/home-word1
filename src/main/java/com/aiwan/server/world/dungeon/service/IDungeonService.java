package com.aiwan.server.world.dungeon.service;

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
}
