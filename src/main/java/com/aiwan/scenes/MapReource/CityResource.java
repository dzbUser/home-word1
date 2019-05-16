package com.aiwan.scenes.MapReource;

import com.aiwan.model.NPC;

import java.util.ArrayList;
import java.util.List;

/**
 * 主城地图静态资源类
 * */
public class CityResource {
    public final static int LENGTH = 5;
    public final static short ORINGINX =1;
    public final static short ORINGINY = 3;
    public static short[][] map= {
            {0,2,1,2,0},
            {0,1,1,1,0},
            {0,1,3,1,0},
            {0,1,1,1,0},
            {0,0,0,0,0}
    };

    public static List<NPC> npcs = new ArrayList<>();

    static {
        int num = 1;
        for(int i = 0;i < LENGTH;i++)
            for (int j = 0;j < LENGTH;j++){
                if (map[i][j] == 2){
                    NPC npc = new NPC("士兵"+num,(short) (i+1),(short)(j+1));
                    num++;
                    npcs.add(npc);
                }
            }
    }

    public static String MaptoMapMessage(short userX,short userY){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("您所在位子为主城的("+userX+","+userY+")\n");
        for(int i = 0;i < LENGTH;i++) {
            for (int j = 0; j < LENGTH; j++) {
                if(map[i][j] == 0)
                    stringBuilder.append("墙   ");
                else if (i+1 == userX&&j+1 == userY){
                    stringBuilder.append("用户 ");
                }
                else if(map[i][j] == 1)
                    stringBuilder.append("地板 ");
                else if (map[i][j] == 2)
                    stringBuilder.append("士兵 ");
                else if (map[i][j] == 3)
                    stringBuilder.append("营地 ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public static boolean allowMove(short targetX,short targetY){
        if (targetX<0||targetY<0||targetX>5||targetY>5)
            return false;
        else if (map[targetX-1][targetY-1] ==1)
            return true;
        return false;
    }
}
