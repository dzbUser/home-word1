package com.aiwan.server.world.dungeon.facade;


import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.world.dungeon.protocol.CM_CreateDungeon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

/**
 * 副本门面
 */
@Controller
public class DungeonFacade {
    Logger logger = LoggerFactory.getLogger(DungeonFacade.class);

    /**
     * 创建并进入副本
     */
    public void createAndEnterDungeon(CM_CreateDungeon cm_createDungeon, Session session) {
        if (session.getrId() == null) {
            logger.error("错误包");
            return;
        }
        GetBean.getDungeonService().createDungeon(session.getrId(), cm_createDungeon.getMapId());
    }
}
