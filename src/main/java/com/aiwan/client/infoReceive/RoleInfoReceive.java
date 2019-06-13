package com.aiwan.client.infoReceive;

import com.aiwan.client.LoginUser;
import com.aiwan.client.anno.InfoReceiveMethod;
import com.aiwan.client.anno.InfoReceiveObject;
import com.aiwan.client.service.ClientResourseManager;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.player.protocol.SM_CreateRole;
import com.aiwan.server.user.role.player.protocol.SM_RoleMessage;
import com.aiwan.server.util.ObjectToBytes;
import com.aiwan.server.util.StatusCode;

import java.util.Map;

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

    /** 查看角色属性 */
    @InfoReceiveMethod(status = StatusCode.VIEWROLEMESSAGE)
    public void viewRoleMessage(SM_RoleMessage sm_roleMessage){
        //角色创建成功
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("职业:"+ ClientResourseManager.getContent("job",sm_roleMessage.getJob()));
        stringBuffer.append(" 性别:"+ ClientResourseManager.getContent("sex",sm_roleMessage.getSex()));
        stringBuffer.append(" 等级:"+ sm_roleMessage.getLevel());
        stringBuffer.append(" 经验:"+ sm_roleMessage.getExperience());
        stringBuffer.append(" 升级所需经验:"+ sm_roleMessage.getNeedExperience()+"\n");
        //输出属性
        for (Map.Entry<AttributeType, AttributeElement> elementEntry:sm_roleMessage.getMap().entrySet()){
            stringBuffer.append(elementEntry.getValue().toString()+" ");
        }

        System.out.println(stringBuffer.toString());
    }
}
