package com.aiwan.client.service.inforeceive;

import com.aiwan.client.anno.InfoReceiveMethod;
import com.aiwan.client.anno.InfoReceiveObject;
import com.aiwan.client.service.ClientResourceManager;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.swing.clientinterface.GameInterface;
import com.aiwan.server.user.role.team.protocol.*;
import com.aiwan.server.util.StatusCode;

/**
 * 组队信息
 *
 * @author dengzebiao
 * @since 2019.7.18
 */
@InfoReceiveObject
public class TeamReceive {

    /**
     * 查看所有申请者
     */
    @InfoReceiveMethod(status = StatusCode.VIEW_APPLICATION)
    public void viewApplication(SM_ViewApplication sm_viewApplication) {
        StringBuffer stringBuffer = new StringBuffer();
        //输出道具
        for (MemberMessage memberMessage : sm_viewApplication.getList()) {
            stringBuffer.append("申请者唯一ID:" + memberMessage.getrId());
            stringBuffer.append(" 申请者名字:" + memberMessage.getName() + "\n");
            stringBuffer.append(" 申请者等级:" + memberMessage.getLevel());
            stringBuffer.append(" 职业:" + ClientResourceManager.getContent("job", memberMessage.getJob()) + "\n\n");
        }
        stringBuffer.append("\n");
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        gameInterface.printOtherMessage(stringBuffer.toString());
    }

    /**
     * 查看队伍成员
     */
    @InfoReceiveMethod(status = StatusCode.VIEW_MEMBERS)
    public void viewMembers(SM_ViewMemberInTeam sm_viewMemberInTeam) {
        StringBuffer stringBuffer = new StringBuffer();
        //输出道具
        for (MemberMessage memberMessage : sm_viewMemberInTeam.getList()) {
            stringBuffer.append("成员-》唯一ID:" + memberMessage.getrId());
            stringBuffer.append(" 名字:" + memberMessage.getName());
            if (memberMessage.getrId() == sm_viewMemberInTeam.getLeaderId()) {
                stringBuffer.append("(队长)");
            }
            stringBuffer.append("\n等级:" + memberMessage.getLevel());
            stringBuffer.append(" 职业:" + ClientResourceManager.getContent("job", memberMessage.getJob()) + "\n\n");
        }
        stringBuffer.append("\n");
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        gameInterface.printOtherMessage(stringBuffer.toString());
    }

    /**
     * 查看所有队伍
     */
    @InfoReceiveMethod(status = StatusCode.VIEW_ALL_TEAM)
    public void viewAllTeam(SM_ViewAllTeam sm_viewAllTeam) {
        StringBuffer stringBuffer = new StringBuffer();
        //输出道具
        for (TeamMessage teamMessage : sm_viewAllTeam.getList()) {
            stringBuffer.append("唯一ID:" + teamMessage.getId());
            stringBuffer.append(" 队长名字:" + teamMessage.getName());
            stringBuffer.append(" 队伍人数:" + teamMessage.getNum() + "/5\n");
        }
        stringBuffer.append("\n");
        GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
        gameInterface.printOtherMessage(stringBuffer.toString());
    }
}
