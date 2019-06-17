package com.aiwan.client.service;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.6.13
 * 既然面管理类
 * */
public class InterfaceManager {

    /** 用户界面映射 */
    private static Map<String, JFrame> map = new HashMap<String, JFrame>(16);

    /** 导航栏 */
    private static JMenuBar navicationBar;

    public static JFrame getFrame(String name){
        return map.get(name);
    }

    public static void putFrame(String name,JFrame frame){
        map.put(name,frame);
    }

    public static JMenuBar getNavicationBar() {
        return navicationBar;
    }

    public static void setNavicationBar(JMenuBar navicationBar) {
        InterfaceManager.navicationBar = navicationBar;
    }

    public static Map<String, JFrame> getMap() {
        return map;
    }

    public static void setMap(Map<String, JFrame> map) {
        InterfaceManager.map = map;
    }
}
