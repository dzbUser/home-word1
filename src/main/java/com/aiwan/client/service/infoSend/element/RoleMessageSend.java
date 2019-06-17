package com.aiwan.client.service.infoSend.element;

import com.aiwan.client.LoginUser;
import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.socket.ClientServerStart;
import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.scenes.protocol.CM_Move;
import com.aiwan.server.scenes.protocol.CM_Shift;
import com.aiwan.server.user.account.protocol.CM_CreateRole;
import com.aiwan.server.util.Protocol;
import com.aiwan.server.util.SMToDecodeData;

/**
 * 角色系统发送协议枚举类
 * @author dengzebiao
 * @since 2019.
 * */
public enum  RoleMessageSend {

    /** 角色信息发送 */
    CREATE(1){
        @Override
        public void messageSend(String message){
            String[] messages = message.split(" ");
            int job = Integer.parseInt(messages[0]);
            int sex = Integer.parseInt(messages[1]);
            if (job<0||job>1||sex<0||sex>1){
                System.out.println("您的输入不规格");
                return;
            }
            //获取性别和职业
            CM_CreateRole cm_createRole = new CM_CreateRole();
            cm_createRole.setAccountId(LoginUser.getUsername());
            cm_createRole.setJob(job);
            cm_createRole.setSex(sex);
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.CREATEROLE,cm_createRole));
        }
    },

    /** 角色移动 */
    MOVE(2){
        @Override
        public void messageSend(String message){
            //解析字符串含义
            String[] messages = message.split(" ");
            int x = Integer.parseInt(messages[0]);
            int y = Integer.parseInt(messages[1]);
            CM_Move move = new CM_Move();
            move.setCurrentX(LoginUser.getCurrentX());
            move.setCurrentY(LoginUser.getCurrentY());
            move.setTargetX(x);
            move.setTargetY(y);
            move.setUsername(LoginUser.getUsername());
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.MOVE,move));
        }
    },

    /** 角色地图跳转 */
    SHIFT(3){
        @Override
        public void messageSend(String message) {
            //解析字符串含义
            int map = Integer.parseInt(message);
            CM_Shift cm_shift = new CM_Shift();
            cm_shift.setMap(map);
            cm_shift.setUsername(LoginUser.getUsername());
            ClientServerStart.sendMessage(SMToDecodeData.shift(Protocol.SHIFT,cm_shift));
        }
    },
    ;

    RoleMessageSend(int num){
        this.num = num;
    }
    /** 所属位置 */
    private int num;
    /** 发送协议 */
    public void messageSend(String message){

    }

    /** 获取对应发送类 */
    public static RoleMessageSend getRoleMessageSend(int num){
        for (RoleMessageSend roleMessageSend:values()){
            if (roleMessageSend.getNum() == num){
                return roleMessageSend;
            }
        }
        throw new RuntimeException("匹配错误:" + num);
    }


    public int getNum() {
        return num;
    }

    public RoleMessageSend setNum(int num) {
        this.num = num;
        return this;
    }
}
