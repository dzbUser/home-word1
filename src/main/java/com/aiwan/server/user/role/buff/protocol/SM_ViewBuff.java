package com.aiwan.server.user.role.buff.protocol;

import java.io.Serializable;
import java.util.List;

/**
 * 返回玩家buff信息
 *
 * @author dengzebiao
 * @since 2019.7.5
 */
public class SM_ViewBuff implements Serializable {

    /**
     * buff信息列表
     */
    private List<BuffMessage> list;

    public List<BuffMessage> getList() {
        return list;
    }

    public void setList(List<BuffMessage> list) {
        this.list = list;
    }

    public static SM_ViewBuff valueOf(List<BuffMessage> list) {
        SM_ViewBuff sm_viewBuff = new SM_ViewBuff();
        sm_viewBuff.setList(list);
        return sm_viewBuff;
    }
}
