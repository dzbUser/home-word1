package com.aiwan.client.swing;

import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.swing.clientinterface.GameInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author dengzebiao
 * @since 2019.6.17
 * 导航栏
 * */
public class NavigationBar extends JPanel {

    /**
     * 人物模块
     */
    public static final int ROLE = 1;

    /** 背包模块 */
    public static final int PACK = 2;

    /** 坐骑模块 */
    public static final int MOUNT = 3;
    /**
     * 技能模块
     */
    public static final int SKILL = 4;

    /**
     * 战斗模块
     */
    public static final int FIGHT = 5;

    /**
     * 组队模块
     */
    public static final int TEAM = 6;

    /**
     * 副本模块
     */
    public static final int DUNGEON = 7;

    /**
     * 任务模块
     */
    public static final int TASK = 8;

    /** 只是当前所属模块 */
    public int num;

    /** 角色系统提示信息 */
    private static final String ROLEMESSAGE =
            "地图：1.主城 2.野外\n"+
                    "1.创建角色 （指令说明: 0:战士 1:法师；  0:男 1:女；  如:输入 0 0 则为战士+男； 角色名字:不可超过10个字）\n" +
            "2.移动（指令说明: 需指定坐标如：4 4，移动到坐标(4,4)）\n" +
            "3.地图跳转(指令说明: 如:1，跳转到主城 ) \n" +
            "4.查看角色信息(指令不用输入) \n" +
                    "5.查看装备(指令不用输入)\n" +
                    "6.卸装备（指令输入装备位置数）\n" +
                    "7.查看Buff (指令不用输入)\n" +
                    "8.查看战力排行榜";


    /** 背包系统提示信息 */
    private static final String PACKMESSAGE =
            "1.添加道具 （指令：输入道具的id 数量）\n"+
            "2.查看背包 （指令：不用输入）\n" +
                    "3.道具使用 （指令：背包位置 数量）\n"
                    + "4.道具丢弃 （指令：输入背包位置 数量\n" +
                    "5.装备使用 （指令：输入背包位置）";


    /**
     * 坐骑系统提示信息
     */
    private static final String MOUNTMESSAGE =
            "1.查看坐骑信息 （指令：不用输入）\n"+
            "2.坐骑提升 （指令：不用输入）\n";

    private static final String SKILLMESSAGE =
            "0.查看所有技能\n" +
                    "1.学习技能 （指令：输入技能id）\n" +
                    "2.查看所学技能 (指令：无需输入)\n" +
                    "3.技能升级 (指令：输入技能id)\n" +
                    "4.移动技能位置 （指令：技能id 位置）\n" +
                    "5.查看技能栏";

    private static final String FIGHTMESSAGE =
            "0.查看地图所有战斗单位\n" +
                    "1.使用技能 （指令：技能位置 唯一id）\n" +
                    "2.查看身上buff";

    private static final String TEAMMESSAGE =
            "0.创建队伍\n" +
                    "1.查看所有队伍\n" +
                    "2.离开队伍\n" +
                    "3.申请加入队伍（指令 输出队伍唯一id）\n" +
                    "4.查看申请队列\n" +
                    "5.允许申请者加入队伍（指令 输入申请者id）\n" +
                    "6.查看队伍成员\n" +
                    "7.踢出 （输入想要踢出的队员id）\n" +
                    "8.发出入队邀请(输入想要邀请的角色id) \n" +
                    "9.接受入队邀请（输入接受邀请的队伍id）";

    private static final String DUNGEONMESSAGE =
            "提示：3.烤猪城（单人） 4.科赞岛（团队)\n" +
                    "0.创建副本（输入创建副本的id）\n";

    private static final String TASKMESSAGE =
            "0.查看可以接取的任务（指令：无需输入）\n" +
                    "1.接受任务（指令：输入任务id）\n" +
                    "2.查看进行中的任务（指令：无需输入）\n" +
                    "3.完成任务（指令：输入任务id）\n";


    private static Logger logger = LoggerFactory.getLogger(NavigationBar.class);

    public NavigationBar(){
        this.setBounds(0, 0, 1500, 50);
        add(createItem("角色系统",ROLEMESSAGE,50,0,ROLE));
        add(createItem("背包系统",PACKMESSAGE,100,0,PACK));
        add(createItem("坐骑系统",MOUNTMESSAGE,150, 0, MOUNT));
        add(createItem("技能系统", SKILLMESSAGE, 200, 0, SKILL));
        add(createItem("战斗系统", FIGHTMESSAGE, 250, 0, FIGHT));
        add(createItem("组队系统", TEAMMESSAGE, 300, 0, TEAM));
        add(createItem("副本系统", DUNGEONMESSAGE, 350, 0, DUNGEON));
        add(createItem("任务系统", TASKMESSAGE, 400, 0, TASK));
        num = ROLE;
    }

    /**
     * 创建菜单项
     * @param name 菜单名
     * */
    private JButton createItem(final String name, final String message, int x, int y, final int num){
        JButton jButton = new JButton(name);
        jButton.setBounds(x,y,40,40);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setNum(num);
                GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
                gameInterface.printPromptMessage(message);
            }
        });
        return jButton;
    }

    public int getNum() {
        return num;
    }

    public NavigationBar setNum(int num) {
        this.num = num;
        return this;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        NavigationBar.logger = logger;
    }

    /**
     * 获取默认消息提示
     */
    public static String getDefaultMessage() {
        return ROLEMESSAGE;
    }
}
