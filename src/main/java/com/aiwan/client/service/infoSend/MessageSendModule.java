package com.aiwan.client.service.infoSend;

import com.aiwan.client.service.infoSend.element.*;

/**
 * 信息发送模块枚举类
 * @author dengzebiao
 * @since 2019.6.17
 * */
public enum  MessageSendModule {

    /** 角色信息发送模块 */
    ROLE_MODULE(1){
        @Override
        public void  sendMessage(String message,int num){
            RoleMessageSend roleMessageSend = RoleMessageSend.getRoleMessageSend(num);
            if (roleMessageSend == null){
                return;
            }
            RoleMessageSend.getRoleMessageSend(num).messageSend(message);
        }
    },
    /** 背包发送模块 */
    PACK_MODULE(2){
        @Override
        public void  sendMessage(String message,int num){
            BackMessageSend backMessageSend = BackMessageSend.getPackMessageSend(num);
            if (backMessageSend == null) {
                return;
            }
            BackMessageSend.getPackMessageSend(num).messageSend(message);
        }
    },
    /** 背包发送模块 */
    MOUNT_MODULE(3){
        @Override
        public void  sendMessage(String message, int num){
            MountMessageSend mountMessageSend = MountMessageSend.getMountMessageSend(num);
            if (mountMessageSend == null){
                return;
            }
            MountMessageSend.getMountMessageSend(num).messageSend(message);
        }
    },
    /**
     * 技能发送模块
     */
    SKILL_MODULE(4) {
        @Override
        public void sendMessage(String message, int num) {
            SkillMessageSend skillMessageSend = SkillMessageSend.getMessageSend(num);
            if (skillMessageSend == null) {
                return;
            }
            skillMessageSend.messageSend(message);
        }
    },
    /**
     * 怪物模块
     */
    FIGHT_MODULE(5) {
        @Override
        public void sendMessage(String message, int num) {
            FightMessageSend fightMessageSend = FightMessageSend.getMonsterMessageSend(num);
            if (fightMessageSend == null) {
                return;
            }
            fightMessageSend.messageSend(message);
        }
    },
    TEAM_MODULE(6) {
        @Override
        public void sendMessage(String message, int num) {
            TeamMessageSend teamMessageSend = TeamMessageSend.getMessageSend(num);
            if (teamMessageSend == null) {
                return;
            }
            teamMessageSend.messageSend(message);
        }
    },
    DUNGEON_MODULE(7) {
        @Override
        public void sendMessage(String message, int num) {
            DungeonMessageSend messageSend = DungeonMessageSend.getMessageSend(num);
            if (messageSend == null) {
                return;
            }
            messageSend.messageSend(message);
        }
    },
    Task_MODULE(8) {
        @Override
        public void sendMessage(String message, int num) {
            TaskMessageSend messageSend = TaskMessageSend.getMessageSend(num);
            if (messageSend == null) {
                return;
            }
            messageSend.messageSend(message);
        }
    },
    ;

    MessageSendModule(int num){
        this.num = num;
    }

    /** 模块位置 */
    private int num;

    /** 信息发送 */
    public void  sendMessage(String message,int num){

    }
    public int getNum() {
        return num;
    }

    /** 获取对应类型 */
    public MessageSendModule setNum(int num) {
        this.num = num;
        return this;
    }
    public static MessageSendModule getMessageModule(int num){
        for (MessageSendModule module:values()){
            if (module.getNum() == num){
                return module;
            }
        }
        throw new RuntimeException("匹配错误:" + num);
    }

}
