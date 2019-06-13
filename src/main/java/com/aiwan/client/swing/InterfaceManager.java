package com.aiwan.client.swing;

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
    private static Map<String, JFrame> map = new HashMap<>(16);

    public static JFrame getFrame(String name){
        return map.get(name);
    }

    public static void putFrame(String name,JFrame frame){
        map.put(name,frame);
    }
}
