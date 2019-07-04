package com.aiwan.server.util;

import com.aiwan.server.scenes.model.Position;

import java.util.Random;

/**
 * 怪物生成工具
 */
public class MonsterGenerateUtil {


    /**
     * 生成随机坐标
     */
    public static Position generaterRandomPosition(int width, int height) {
        //以当前时间为种子
        Random r = new Random();
        int x = r.nextInt() % width;
        int y = r.nextInt() % height;
        return Position.valueOf(x, y);
    }
}
