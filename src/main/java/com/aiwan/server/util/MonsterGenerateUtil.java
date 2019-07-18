package com.aiwan.server.util;

import com.aiwan.server.world.scenes.model.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * 怪物生成工具
 */
public class MonsterGenerateUtil {

    private static Logger logger = LoggerFactory.getLogger(MonsterGenerateUtil.class);

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

    public static int getRandomNum(int length) {
        Random r = new Random();
        int num = r.nextInt() % length;
        return num >= 0 ? num : -num;
    }
}
