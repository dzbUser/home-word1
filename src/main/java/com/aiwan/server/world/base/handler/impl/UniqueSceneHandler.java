package com.aiwan.server.world.base.handler.impl;

import com.aiwan.server.monster.resource.MonsterResource;
import com.aiwan.server.user.role.fight.pvpunit.MonsterUnit;
import com.aiwan.server.user.role.fight.pvpunit.RoleUnit;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.MonsterGenerateUtil;
import com.aiwan.server.world.base.handler.ISceneHandler;
import com.aiwan.server.world.base.scene.UniqueScene;
import com.aiwan.server.world.scenes.command.MonsterRateReviveCommand;
import com.aiwan.server.world.scenes.command.SceneRateCommand;
import com.aiwan.server.world.scenes.model.Position;

import java.util.List;


/**
 * 普通地图处理器
 *
 * @author dengzebiao
 * @since 2019.7.25
 */
public class UniqueSceneHandler implements ISceneHandler {

    private UniqueScene uniqueScene;

    @Override
    public void init() {
        generateMonster();
        //初始化buff
        long now = System.currentTimeMillis();
        SceneRateCommand sceneRateCommand = new SceneRateCommand(null, uniqueScene.getMapId(), 0, 1000);
        MonsterRateReviveCommand monsterRateReviveCommand = new MonsterRateReviveCommand(null, uniqueScene.getMapId(), 0, 5000);
        GetBean.getSceneExecutorService().submit(sceneRateCommand);
        GetBean.getSceneExecutorService().submit(monsterRateReviveCommand);
        uniqueScene.getCommandMap().put(SceneRateCommand.class, sceneRateCommand);
        uniqueScene.getCommandMap().put(MonsterRateReviveCommand.class, monsterRateReviveCommand);
        uniqueScene.setCanEnter(true);
    }

    @Override
    public void enterDungeon(Role role, RoleUnit roleUnit) {
        GetBean.getScenesService().enterMap(role, uniqueScene, roleUnit);
    }

    @Override
    public void quitDungeon(Role role) {
        GetBean.getScenesService().leaveMap(role);
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
        List<MonsterResource> monsterResources = GetBean.getMonsterManager().getMonsterList(uniqueScene.getMapId());
        if (monsterResources == null) {
            return;
        }
        //循环创建怪物
        for (MonsterResource monsterResource : monsterResources) {
            for (int i = 0; i < monsterResource.getNum(); i++) {
                //获取随机坐标
                while (true) {
                    Position position = MonsterGenerateUtil.generaterRandomPosition(uniqueScene.getResource().getWidth(), uniqueScene.getResource().getHeight());
                    //判断不是阻挡点
                    if (GetBean.getMapManager().allowMove(position.getX(), position.getY(), uniqueScene.getMapId())) {
                        MonsterUnit monster = MonsterUnit.valueOf(monsterResource, position, uniqueScene.getSceneId(), uniqueScene.getMapId());
                        uniqueScene.getBaseUnitMap().put(monster.getId(), monster);
                        break;
                    }
                }
            }
        }
    }

    public UniqueScene getUniqueScene() {
        return uniqueScene;
    }

    public void setUniqueScene(UniqueScene uniqueScene) {
        this.uniqueScene = uniqueScene;
    }
}
