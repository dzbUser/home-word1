package com.aiwan.client.infoReceive;

import com.aiwan.client.LoginUser;
import com.aiwan.client.anno.InfoReceiveMethod;
import com.aiwan.client.anno.InfoReceiveObject;
import com.aiwan.server.user.role.player.protocol.SM_CreateRole;
import com.aiwan.server.util.ObjectToBytes;
import com.aiwan.server.util.StatusCode;

/**
 * 角色信息接收类
 * @author dengzebiao
 * @since 2019.6.11
 * */
@InfoReceiveObject
public class RoleInfoReceive {

    /** 创建角色有接收信息 */
    @InfoReceiveMethod(status = StatusCode.CREATEROLESUCESS)
    public void createRoleInfo(SM_CreateRole createRole){
        //角色创建成功
        LoginUser.setRoles(createRole.getRoles());
        System.out.println(createRole.getMessage());
    }
}
