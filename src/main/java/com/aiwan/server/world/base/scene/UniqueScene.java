package com.aiwan.server.world.base.scene;

import com.aiwan.server.monster.resource.MonsterResource;
import com.aiwan.server.world.scenes.command.SceneRateCommand;
import com.aiwan.server.user.role.fight.pvpunit.MonsterUnit;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.MonsterGenerateUtil;
import com.aiwan.server.world.scenes.model.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author dengzebiao
 * 唯一场景对象
 */
public class UniqueScene extends AbstractScene {
    Logger logger = LoggerFactory.getLogger(UniqueScene.class);

    public UniqueScene(int mapId) {
        super(mapId);
    }


    /**
     * 创建场景
     */
    public static UniqueScene valueOf(int mapId) {
        UniqueScene uniqueScene = new UniqueScene(mapId);
        return uniqueScene;
    }

    /**
     * 初始化
     */
    public void init() {
        generateMonster();
        //初始化buff
        long now = System.currentTimeMillis();
        SceneRateCommand sceneRateCommand = new SceneRateCommand(null, getMapId(), 0, 1000, now + 5000, 5000);
        GetBean.getSceneExecutorService().submit(sceneRateCommand);
        getCommandMap().put(SceneRateCommand.class, sceneRateCommand);
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
        //获取属于该地图的怪物
        List<MonsterResource> monsterResources = GetBean.getMonsterManager().getMonsterList(getMapId());
        if (monsterResources == null) {
            return;
        }
        //循环创建怪物
        for (MonsterResource monsterResource : monsterResources) {
            for (int i = 0; i < monsterResource.getNum(); i++) {
                //获取随机坐标
                while (true) {
                    Position position = MonsterGenerateUtil.generaterRandomPosition(getResource().getWidth(), getResource().getHeight());
                    //判断不是阻挡点
                    if (GetBean.getMapManager().allowMove(position.getX(), position.getY(), getMapId())) {
                        MonsterUnit monster = MonsterUnit.valueOf(monsterResource, position, getSceneId(), getMapId());
                        getBaseUnitMap().put(monster.getId(), monster);
                        break;
                    }
                }
            }
        }

        logger.debug("场景{},怪物加载完毕", getMapId());
    }
}

