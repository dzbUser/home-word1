package com.aiwan.server.user.role.powerboard.service;

import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.powerboard.model.RankInfo;
import com.aiwan.server.user.role.powerboard.protocol.RankBoardRoleMessage;
import com.aiwan.server.user.role.powerboard.protocol.SM_ViewRankBoard;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.StatusCode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        for (RankInfo rankInfo : GetBean.getPowerRankBoard().getRankInfoList()) {
            Role role = GetBean.getRoleManager().load(rankInfo.getrId());
            list.add(RankBoardRoleMessage.valueOf(role.getId(), role.getName(), rankInfo.getCombatPower()));
        }
        SessionManager.sendMessageByRid(rId, StatusCode.VIEW_RANK_BOAED, SM_ViewRankBoard.valueOf(list));
    }

}
