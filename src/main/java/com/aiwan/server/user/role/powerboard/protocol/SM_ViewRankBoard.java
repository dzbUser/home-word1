package com.aiwan.server.user.role.powerboard.protocol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 查看排行榜返回协议类
 *
 * @author dengzebiao
 * @since 2019.7.25
 */
public class SM_ViewRankBoard implements Serializable {

    private List<RankBoardRoleMessage> list = new ArrayList<>();

    public static SM_ViewRankBoard valueOf(List<RankBoardRoleMessage> list) {
        SM_ViewRankBoard sm_viewRankBoard = new SM_ViewRankBoard();
        sm_viewRankBoard.setList(list);
        return sm_viewRankBoard;
    }

    public List<RankBoardRoleMessage> getList() {
        return list;
    }

    public void setList(List<RankBoardRoleMessage> list) {
        this.list = list;
    }
}
