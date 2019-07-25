package com.aiwan.server.world.dungeon.service;

import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.team.model.TeamModel;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.PromptCode;
import com.aiwan.server.world.base.handler.DungeonType;
import com.aiwan.server.world.base.handler.impl.ExperienceDungeonHandler;
import com.aiwan.server.world.base.handler.impl.KozanIslandDungeonHandler;
import com.aiwan.server.world.base.scene.DungeonScene;
import com.aiwan.server.world.scenes.command.ChangeMapCommand;
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
        logger.info("{}请求进入副本,mapId:{}", rId, mapId);
        if (mapResource == null) {
            logger.error("{}创建副本{}错误，找不到地图资源", rId, mapId);
            SessionManager.sendPromptMessage(rId, PromptCode.MAPNOEXIST, "");
            return;
        }
        if (mapResource.getIsDungeon() == 0) {
            logger.error("{}创建副本{}错误，该地图资源不是副本资源", rId, mapId);
            return;
        }
        DungeonType.getDungeonCreator(mapResource.getDungeonType()).create(rId, mapResource);
    }

    /**
     * 创建单人副本
     *
     * @param rId         角色id
     * @param mapResource 地图资源
     */
    public void createExperienceDungeon(long rId, MapResource mapResource) {
        //判断是否在队伍中，否则创建队伍
        if (GetBean.getTeamManager().isInTeam(rId)) {
            //创建副本错误
            logger.error("{}创建单人副本{}错误，角色在队伍中", rId, mapResource.getMapId());
            SessionManager.sendPromptMessage(rId, PromptCode.IN_TEAM, "");
            return;
        }
        //开始创建副本
        DungeonScene dungeonScene = new DungeonScene(mapResource.getMapId());
        //创建副本处理器
        Role role = GetBean.getRoleManager().load(rId);
        ExperienceDungeonHandler experienceDungeonHandler = new ExperienceDungeonHandler(role);
        //副本与处理器的绑定
        dungeonScene.setHandler(experienceDungeonHandler);
        experienceDungeonHandler.setDungeonScene(dungeonScene);
        //把副本添加到地图对象资类中
        GetBean.getMapManager().putSceObject(dungeonScene);
        //初始化副本
        experienceDungeonHandler.init();
        //拥有者切到副本
        GetBean.getSceneExecutorService().submit(new ChangeMapCommand(role, dungeonScene.getMapId(), dungeonScene.getSceneId()));
    }

    /**
     * 创建团队副本
     *
     * @param rId         角色id
     * @param mapResource 地图资源
     */
    public void createKozanIslandDungeon(long rId, MapResource mapResource) {
        //判断是否在队伍中，否则创建队伍
        if (!GetBean.getTeamManager().isInTeam(rId)) {
            //创建副本错误
            logger.error("{}创建团队副本{}错误，角色没在在队伍中", rId, mapResource.getMapId());
            SessionManager.sendPromptMessage(rId, PromptCode.NO_IN_TEAM, "");
            return;
        }
        //获取队伍
        TeamModel teamModel = GetBean.getTeamManager().getTeam(GetBean.getTeamManager().getTeamIdByRid(rId));
        if (teamModel.getLeaderId() != rId) {
            //创建副本错误
            logger.error("{}创建团队副本{}错误，角色不是队长", rId, mapResource.getMapId());
            SessionManager.sendPromptMessage(rId, PromptCode.NO_TEAM_LEADER, "");
            return;
        }
        //开始构建副本
        DungeonScene dungeonScene = new DungeonScene(mapResource.getMapId());
        //创建副本处理器
        KozanIslandDungeonHandler kozanIslandDungeonHandler = new KozanIslandDungeonHandler(teamModel);
        //副本与处理器的相互绑定
        dungeonScene.setHandler(kozanIslandDungeonHandler);
        kozanIslandDungeonHandler.setDungeonScene(dungeonScene);
        //把副本添加到地图对象资类中
        GetBean.getMapManager().putSceObject(dungeonScene);
        //初始化副本
        kozanIslandDungeonHandler.init();
        //队伍成员切到地图
        for (Role role : teamModel.getTeamList()) {
            GetBean.getSceneExecutorService().submit(new ChangeMapCommand(role, dungeonScene.getMapId(), dungeonScene.getSceneId()));
        }
    }
}
