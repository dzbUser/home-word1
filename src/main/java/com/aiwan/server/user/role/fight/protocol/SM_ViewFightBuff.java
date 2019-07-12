package com.aiwan.server.user.role.fight.protocol;

import com.aiwan.server.user.role.buff.protocol.SM_ViewBuff;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 战斗buff查看协议
 *
 * @author dengzebiao
 * @since 2019.7.12
 */
public class SM_ViewFightBuff implements Serializable {

    List<FightBuffMessage> list = new ArrayList<>();

    public static SM_ViewFightBuff valueOf(List<FightBuffMessage> list) {
        SM_ViewFightBuff sm_viewFightBuff = new SM_ViewFightBuff();
        sm_viewFightBuff.setList(list);
        return sm_viewFightBuff;
    }

    public List<FightBuffMessage> getList() {
        return list;
    }

    public void setList(List<FightBuffMessage> list) {
        this.list = list;
    }
}
