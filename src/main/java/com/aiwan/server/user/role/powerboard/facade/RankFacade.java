package com.aiwan.server.user.role.powerboard.facade;

import com.aiwan.server.base.event.anno.ReceiverAnno;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.powerboard.RankRefreshEvent;
import com.aiwan.server.user.role.powerboard.protocol.CM_ViewRankBoard;
import com.aiwan.server.util.GetBean;
import org.springframework.stereotype.Controller;

/**
 * 排行榜门面
 *
 * @author dengzebiao
 * @since 2019.7.25
 */
@Controller
public class RankFacade {

    /**
     * 接受排行榜更新事件
     *
     * @param event
     */
    @ReceiverAnno
    public void rankRefresh(RankRefreshEvent event) {
        GetBean.getPowerRankBoard().update(event.getRole());
    }

    /**
     * 查看排行榜
     *
     * @param cm_viewRankBoard
     * @param session
     */
    public void viewRankBoard(CM_ViewRankBoard cm_viewRankBoard, Session session) {
        GetBean.getRankService().viewRankBoard(session.getrId());
    }

}
