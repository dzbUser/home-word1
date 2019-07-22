package com.aiwan.server.world.dungeon.service;

import com.aiwan.server.user.role.team.model.TeamModel;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.world.base.handler.AbstractDungeonHandler;
import com.aiwan.server.world.base.handler.DungeonHandlerType;
import com.aiwan.server.world.base.scene.AbstractScene;
import com.aiwan.server.world.base.scene.DungeonScene;
import com.aiwan.server.world.scenes.mapresource.MapResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 副本业务实现类
 *
 * @author dengzebiao
 * @since 2019.7.19
 */
@Service
public class DungeonService implements IDungeonService {

    Logger logger = LoggerFactory.getLogger(DungeonService.class);

    @Override
    public void createDungeon(long rId, int mapId) {
        MapResource mapResource = GetBean.getMapManager().getMapResource(mapId);
        if (mapResource == null) {
            logger.error("{}创建副本{}错误，找不到地图资源", rId, mapId);
            return;
        }
        if (mapResource.getIsDungeon() == 0) {
            logger.error("{}创建副本{}错误，该地图资源不是副本资源", rId, mapId);
            return;
        }
        if (mapResource.getIsSingle() == 1) {
            //创建单人副本
            createSingleDungeon(rId, mapResource);
        } else {
            //创建团队副本
            createTeamDungeon(rId, mapResource);
        }
    }

    /**
     * 创建单人副本
     *
     * @param rId         角色id
     * @param mapResource 地图资源
     */
    private void createSingleDungeon(long rId, MapResource mapResource) {
        //判断是否在队伍中，否则创建队伍
        if (GetBean.getTeamManager().isInTeamOrCreate(rId)) {
            //创建副本错误
            logger.error("{}创建单人副本{}错误，角色在队伍中", rId, mapResource.getMapId());
            return;
        }
        //开始创建副本
        DungeonScene dungeonScene = new DungeonScene(mapResource.getMapId());
        //创建副本处理器
        AbstractDungeonHandler handler = DungeonHandlerType.getHandler(mapResource.getDungeonType()).creator();
        dungeonScene.setHandler(handler);
        //把玩家队伍添加到副本
        dungeonScene.setTeamModel(GetBean.getTeamManager().getTeam(GetBean.getTeamManager().getTeamIdByRid(rId)));
        handler.setDungeonScene(dungeonScene);
        //把副本添加到地图对象资类中
        GetBean.getMapManager().putSceObject(dungeonScene);
        //初始化副本
        handler.init();
    }

    /**
     * 创建团队副本
     *
     * @param rId         角色id
     * @param mapResource 地图资源
     */
    private void createTeamDungeon(long rId, MapResource mapResource) {
        //判断是否在队伍中，否则创建队伍
        if (!GetBean.getTeamManager().isInTeam(rId)) {
            //创建副本错误
            logger.error("{}创建团队副本{}错误，角色没在在队伍中", rId, mapResource.getMapId());
            return;
        }
        if (GetBean.getTeamManager().getTeam(GetBean.getTeamManager().getTeamIdByRid(rId)).getLeaderId() != rId) {
            //创建副本错误
            logger.error("{}创建团队副本{}错误，角色不是队长", rId, mapResource.getMapId());
            return;
        }
        //开始创建副本
        DungeonScene dungeonScene = new DungeonScene(mapResource.getMapId());
        //创建副本处理器
        AbstractDungeonHandler handler = DungeonHandlerType.getHandler(mapResource.getDungeonType()).creator();
        dungeonScene.setHandler(handler);
        //把玩家队伍添加到副本
        dungeonScene.setTeamModel(GetBean.getTeamManager().getTeam(GetBean.getTeamManager().getTeamIdByRid(rId)));
        handler.setDungeonScene(dungeonScene);
        //把副本添加到地图对象资类中
        GetBean.getMapManager().putSceObject(dungeonScene);
        //初始化副本
        handler.init();
    }
}
