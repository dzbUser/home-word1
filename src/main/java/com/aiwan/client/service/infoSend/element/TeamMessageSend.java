package com.aiwan.client.service.infoSend.element;

import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.socket.ClientServerStart;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.client.util.Verification;
import com.aiwan.server.user.role.team.protocol.*;
import com.aiwan.server.util.Protocol;
import com.aiwan.server.util.SMToDecodeData;

/**
 * 组队协议发送类
 *
 * @author dengzebiao
 * @since 2019.7.17
 */
public enum TeamMessageSend {

    /**
     * 创建队伍
     */
    CREATE_TEAM(0) {
        @Override
        public void messageSend(String message) {
            CM_CreateTeam cm_createTeam = new CM_CreateTeam();
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.CREATE_TEAM, cm_createTeam));
        }
    },

    /**
     * 查看所有队伍
     */
    VIEW_ALL_TEAM(1) {
        @Override
        public void messageSend(String message) {
            CM_ViewAllTeam cm_viewAllTeam = new CM_ViewAllTeam();
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.VIEW_ALL_TEAM, cm_viewAllTeam));
        }
    },
    /**
     * 离开队伍
     */
    LEAVE_TEAM(2) {
        @Override
        public void messageSend(String message) {
            CM_LeaveTeam cm_viewAllTeam = new CM_LeaveTeam();
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.LEAVE_TEAM, cm_viewAllTeam));
        }
    },

    /**
     * 申请加入队伍
     */
    APPLY_JOIN(3) {
        @Override
        public void messageSend(String message) {
            GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
            if (!verify(message)) {
                //校验指令正确性
                gameInterface.printOtherMessage("您的输入不规范");
                return;
            }
            long teamId = Long.parseLong(message);
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.APPLYJOIN, CM_ApplyJoin.valueOf(teamId)));
        }

        @Override
        public boolean verify(String message) {
            if (!Verification.canParseNum(message)) {
                return false;
            }
            return true;
        }
    },

    /**
     * 查看申请列表
     */
    VIEW_APPLICATION(4) {
        @Override
        public void messageSend(String message) {
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.VIEW_APPLCIATION, new CM_ViewApplication()));
        }

    },
    /**
     * 允许加入
     */
    ALLOW_JOIN(5) {
        @Override
        public void messageSend(String message) {
            GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
            if (!verify(message)) {
                //校验指令正确性
                gameInterface.printOtherMessage("您的输入不规范");
                return;
            }
            long allowId = Long.parseLong(message);
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.ALLOW_JOIN, CM_AllowJoin.valueOf(allowId)));
        }

        @Override
        public boolean verify(String message) {
            if (!Verification.canParseNum(message)) {
                return false;
            }
            return true;
        }
    },
    /**
     * 查看队伍成员
     */
    VIEW_MEMBERS(6) {
        @Override
        public void messageSend(String message) {
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.VIEW_MEMBERS, new CM_ViewMembers()));
        }

    },
    /**
     * 踢出队伍
     */
    KICK_OUT_MEMBER(7) {
        @Override
        public void messageSend(String message) {
            GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
            if (!verify(message)) {
                //校验指令正确性
                gameInterface.printOtherMessage("您的输入不规范");
                return;
            }
            long kickOutRid = Long.parseLong(message);
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.KICK_OUT_TEAM, CM_KickOutTeam.valueOf(kickOutRid)));
        }

        @Override
        public boolean verify(String message) {
            if (!Verification.canParseNum(message)) {
                return false;
            }
            return true;
        }

    },
    ;

    TeamMessageSend(int num) {
        this.num = num;
    }

    /**
     * 所属位置
     */
    private int num;

    /**
     * 发送协议
     */
    public void messageSend(String message) {

    }

    /**
     * 获取对应发送类
     */
    public static TeamMessageSend getMessageSend(int num) {
        for (TeamMessageSend teamMessageSend : values()) {
            if (teamMessageSend.getNum() == num) {
                return teamMessageSend;
            }
        }
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        gameInterface.printOtherMessage("没有此功能选项");
        return null;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    /**
     * 验证指令是否符合格式
     */
    public boolean verify(String message) {
        return true;
    }
}
