package com.aiwan.server.user.role.powerboard.model;

import com.aiwan.server.user.role.player.entity.RoleEnt;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

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

    /**
     * 排行榜最大数目
     */
    private final static int MAX_NUM = 10;

    private ConcurrentSkipListMap<RankInfo, Long> rankInfoListMap = new ConcurrentSkipListMap<>();

    private Map<Long, RankInfo> rankInfoMap = new HashMap<>();

    public void init() {
        List<RoleEnt> list = GetBean.getRoleManager().getRoleSortByCombat(RANK_NUM);
        for (RoleEnt roleEnt : list) {
            RankInfo rankInfo = RankInfo.valueOf(roleEnt.getId(), roleEnt.getCombatPower(), roleEnt.getUpdateTime(), roleEnt.getName());
            rankInfoMap.put(rankInfo.getrId(), rankInfo);
            rankInfoListMap.put(rankInfo, rankInfo.getrId());
        }
    }

    /**
     * 更新排行榜
     *
     * @param role
     */
    public void update(Role role) {
        RankInfo rankInfo = rankInfoMap.get(role.getId());
        if (rankInfo != null) {
            rankInfoListMap.remove(rankInfo);
        }
        //获取当前时间
        rankInfo = RankInfo.valueOf(role.getId(), role.getCombatPower(), role.getUpdateTime(), role.getName());
        rankInfoMap.put(rankInfo.getrId(), rankInfo);
        rankInfoListMap.put(rankInfo, rankInfo.getrId());
        if (rankInfoListMap.size() > MAX_NUM) {
            //当前容器中的数量大于排行榜容器最大数目
            Map.Entry<RankInfo, Long> entry = rankInfoListMap.pollLastEntry();
            rankInfoMap.remove(entry.getValue());
        }
        logger.debug("{}战力更新", role.getId());
    }

    public static int getRankNum() {
        return RANK_NUM;
    }

    public ConcurrentSkipListMap<RankInfo, Long> getRankInfoListMap() {
        return rankInfoListMap;
    }

    public void setRankInfoListMap(ConcurrentSkipListMap<RankInfo, Long> rankInfoListMap) {
        this.rankInfoListMap = rankInfoListMap;
    }
}
