package com.aiwan.client.swing;

import com.aiwan.client.service.InterfaceManager;
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

    public static final int ROLE = 0;
    /** 只是当前所属模块 */
    private int num;

    private static Logger logger = LoggerFactory.getLogger(NavigationBar.class);

    public NavigationBar(){
        this.setBounds(0,0,400,50);
        add(createItem("角色系统","roleSystem",50,0,ROLE));
//        add(createItem("背包系统",100,0));
//        add(createItem("坐骑系统",150,0));
//        add(createItem("退出游戏",200,0));
    }

    /**
     * 创建菜单项
     * @param name 菜单名
     * */
    public JButton createItem(final String name, final String interfaceName, int x, int y,int num){
        JButton jButton = new JButton(name);
        jButton.setBounds(x,y,40,40);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setNum(num);
                System.out.println(num);
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
