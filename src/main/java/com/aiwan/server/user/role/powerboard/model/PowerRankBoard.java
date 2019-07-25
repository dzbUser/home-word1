package com.aiwan.server.user.role.powerboard.model;

import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 战力排行榜
 *
 * @author dengzebiao
 * @since 2019.7.25
 */
@Component
public class PowerRankBoard {

    private final static Logger logger = LoggerFactory.getLogger(PowerRankBoard.class);
    /**
     * 排行榜数目
     */
    private final static int RANK_NUM = 3;

    private CopyOnWriteArrayList<RankInfo> rankInfoList;

    public void init() {
        rankInfoList = GetBean.getRoleManager().getRoleSortByCombat(RANK_NUM);
    }

    /**
     * 更新排行榜
     *
     * @param role
     */
    public void update(Role role) {
        boolean inRank = false;
        for (int i = 0; i < RANK_NUM; i++) {
            if (rankInfoList.get(i).getrId() == role.getId()) {
                inRank = true;
                //更新战力
                if (rankInfoList.get(i).getCombatPower() < role.getCombatPower()) {
                    rankInfoList.get(i).setCombatPower(role.getCombatPower());
                    RankInfo rankInfo = rankInfoList.get(i);
                    //往前更新
                    int j = i - 1;
                    for (; j >= 0; j--) {
                        if (rankInfoList.get(j).getCombatPower() < rankInfo.getCombatPower()) {
                            rankInfoList.set(j + 1, rankInfoList.get(j));
                        } else {
                            break;
                        }
                    }
                    rankInfoList.set(j + 1, rankInfo);
                } else {
                    rankInfoList.get(i).setCombatPower(role.getCombatPower());
                    RankInfo rankInfo = rankInfoList.get(i);
                    //往后更新
                    int j = i + 1;
                    for (; j < RANK_NUM; j++) {
                        if (rankInfoList.get(j).getCombatPower() > rankInfo.getCombatPower()) {
                            rankInfoList.set(j - 1, rankInfoList.get(j));
                        } else {
                            break;
                        }
                    }
                    rankInfoList.set(j - 1, rankInfo);
                }
                //找到变化，退出循环
                break;
            }
        }

        if (!inRank) {
            //向前更新
            if (rankInfoList.get(RANK_NUM - 1).getCombatPower() < role.getCombatPower()) {
                RankInfo info = RankInfo.valueOf(role.getId(), role.getCombatPower());
                rankInfoList.set(RANK_NUM - 1, info);
                int i = RANK_NUM - 2;
                for (; i >= 0; i--) {
                    if (rankInfoList.get(i).getCombatPower() < info.getCombatPower()) {
                        rankInfoList.set(i + 1, rankInfoList.get(i));
                    } else {
                        break;
                    }
                }
                rankInfoList.set(i + 1, info);
            }
        }
        logger.debug("{}战力更新", role.getId());
    }

    public static int getRankNum() {
        return RANK_NUM;
    }

    public List<RankInfo> getRankInfoList() {
        return rankInfoList;
    }

    public void setRankInfoList(CopyOnWriteArrayList<RankInfo> rankInfoList) {
        this.rankInfoList = rankInfoList;
    }
}
