package com.aiwan.client.swing;

import com.aiwan.client.service.InterfaceManager;
import com.aiwan.client.swing.clientInterface.GameInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.6.17
 * 导航栏
 * */
public class NavigationBar extends JPanel {

    public static final int ROLE = 1;

    /** 只是当前所属模块 */
    private int num;

    /** 角色系统提示信息 */
    public static  final String ROLEMESSAGE = "地图：1.主城 2.野外\n"+
            "1.创建角色 （0：战士 1：法师；  0：男 1：女  如：输入 0 0 则为战士+男 ）\n" +
            "2.移动（需指定坐标如：1 4 4，移动到坐标(4,4)）\n" +
            "3.地图跳转(如:2 1，跳转到主城 ) 4查看角色信息 5.查看装备";

    private static Logger logger = LoggerFactory.getLogger(NavigationBar.class);

    public NavigationBar(){
        this.setBounds(0,0,600,50);
        add(createItem("角色系统",ROLEMESSAGE,"roleSystem",50,0,ROLE));
        num = ROLE;
//        add(createItem("背包系统",100,0));
//        add(createItem("坐骑系统",150,0));
//        add(createItem("退出游戏",200,0));
    }

    /**
     * 创建菜单项
     * @param name 菜单名
     * */
    public JButton createItem(final String name, String message, final String interfaceName, int x, int y,int num){
        JButton jButton = new JButton(name);
        jButton.setBounds(x,y,40,40);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setNum(num);
                GameInterface gameInterface = (GameInterface) InterfaceManager.getFrame("game");
                gameInterface.printPromptMessage(ROLEMESSAGE);
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
}
