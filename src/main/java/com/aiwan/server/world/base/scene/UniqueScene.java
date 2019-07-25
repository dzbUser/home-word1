package com.aiwan.server.world.base.scene;

import com.aiwan.server.world.base.handler.ISceneHandler;
import com.aiwan.server.world.base.handler.impl.UniqueSceneHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dengzebiao
 * 唯一场景对象
 */
public class UniqueScene extends AbstractScene {
    Logger logger = LoggerFactory.getLogger(UniqueScene.class);

    public UniqueScene(int mapId) {
        super(mapId);
    }

    private UniqueSceneHandler handler;

    /**
     * 创建场景
     */
    public static UniqueScene valueOf(int mapId) {
        UniqueScene uniqueScene = new UniqueScene(mapId);
        return uniqueScene;
    }

    @Override
    public UniqueSceneHandler getHandler() {
        return handler;
    }

    public void setHandler(UniqueSceneHandler handler) {
        this.handler = handler;
    }
}

