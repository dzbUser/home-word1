package com.aiwan.server.user.role.team.protocol;

import java.io.Serializable;
import java.util.List;

/**
 * 查看申请列表
 *
 * @author dengzebiao
 * @since 2019.7.18
 */
public class SM_ViewApplication implements Serializable {

    List<MemberMessage> list;

    public static SM_ViewApplication valueOf(List<MemberMessage> list) {
        SM_ViewApplication sm_viewApplication = new SM_ViewApplication();
        sm_viewApplication.setList(list);
        return sm_viewApplication;
    }

    public List<MemberMessage> getList() {
        return list;
    }

    public void setList(List<MemberMessage> list) {
        this.list = list;
    }
}
