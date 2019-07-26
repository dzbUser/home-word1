package com.aiwan.server.user.role.powerboard.service;

import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.powerboard.model.PowerRankBoard;
import com.aiwan.server.user.role.powerboard.model.RankInfo;
import com.aiwan.server.user.role.powerboard.protocol.RankBoardRoleMessage;
import com.aiwan.server.user.role.powerboard.protocol.SM_ViewRankBoard;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.StatusCode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 排行榜接口实现类
 *
 * @author dengzebiao
 * @since 2019.7.25
 */
@Service
public class RankService implements IRankService {

    /**
     * 查看排行榜
     */
    public void viewRankBoard(long rId) {
        List<RankBoardRoleMessage> list = new ArrayList<>();
        Iterator iter = GetBean.getPowerRankBoard().getRankInfoListMap().entrySet().iterator();
        int i = 0;
        while (iter.hasNext() && i < PowerRankBoard.getRankNum()) {
            Map.Entry<RankInfo, Long> entry = (Map.Entry<RankInfo, Long>) iter.next();
            RankInfo rankInfo = entry.getKey();
            list.add(RankBoardRoleMessage.valueOf(rankInfo.getrId(), rankInfo.getName(), rankInfo.getCombatPower()));
            i++;
        }
        SessionManager.sendMessageByRid(rId, StatusCode.VIEW_RANK_BOAED, SM_ViewRankBoard.valueOf(list));
    }

}
