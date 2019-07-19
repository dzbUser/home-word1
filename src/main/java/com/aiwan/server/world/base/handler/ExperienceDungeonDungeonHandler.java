package com.aiwan.server.world.base.handler;

import com.aiwan.server.util.GetBean;
import com.aiwan.server.world.scenes.mapresource.GateBean;

/**
 * 经验副本处理器
 *
 * @author dengzebiao
 * @since 2019.7.18
 */
public class ExperienceDungeonDungeonHandler extends AbstractDungeonHandler {


    /**
     * 总击杀数
     */
    private int totalKillNum;


    /**
     * 关卡监听器
     */
    @Override
    public void checkpointListener() {
        /*
        0.发放关卡奖励
        1.判断是否是最后一个关卡
        2.若不是，则刷下个关卡的怪
        3.若是，则判断是否是不通关副本，若是则再次刷最后关卡的怪，若不是则结算
         */
        //添加杀怪数
        setTotalKillNum(getTotalKillNum() + 1);
        setKillMonsterNum(getKillMonsterNum() + 1);
        //获取关卡
        GateBean gateBean = getDungeonScene().getResource().getGateBeanMap().get(getCheckpointNum());
        //判断怪物是否全部死亡
        if (gateBean.getMonsterNum() == getKillMonsterNum()) {
            //清楚所有怪物尸体
            clearAllMonster();
            setKillMonsterNum(0);
            //判断是否有下一关卡的怪
            GateBean nextGateBean = getDungeonScene().getResource().getGateBeanMap().get(getCheckpointNum() + 1);
            if (nextGateBean != null) {
                //还有下一关
                setCheckpointNum(getCheckpointNum() + 1);
                generateMonster(nextGateBean.getMonsterId(), nextGateBean.getMonsterNum());
            } else {
                //没有下一关
                generateMonster(gateBean.getMonsterId(), gateBean.getMonsterNum());
            }
        }
    }

    @Override
    public void init() {
        /*
        初始化副本
        1.关卡是为1
        2.初始化怪物
         */
        this.totalKillNum = 0;
        super.init();
    }


    /**
     * 结算
     */
    @Override
    public void settlement() {
        /*
        1.发送奖励给所有队员
         */
        existDungeon();
        //删除副本
        GetBean.getMapManager().removeSceObject(getDungeonScene().getKey());
    }

    public int getTotalKillNum() {
        return totalKillNum;
    }

    public void setTotalKillNum(int totalKillNum) {
        this.totalKillNum = totalKillNum;
    }
}
