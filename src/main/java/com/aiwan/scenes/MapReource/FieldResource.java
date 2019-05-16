package com.aiwan.scenes.MapReource;

import com.aiwan.model.Monster;

import java.util.ArrayList;
import java.util.List;

public class FieldResource {
    public final static int LENGTH = 5;
    public final static short ORINGINX =1;
    public final static short ORINGINY = 3;
    public static short[][] map= {
            {0,2,1,3,0},
            {0,1,1,1,0},
            {0,1,4,1,0},
            {0,1,1,1,0},
            {0,0,0,0,0}
    };

    public static List<Monster> monsterList = new ArrayList<>();

    static {
        int num = 1;
        for(int i = 0;i < LENGTH;i++)
            for (int j = 0;j < LENGTH;j++){
                if (map[i][j] == 2){
                    Monster monster = new Monster("鹿灵"+num,(short) (i+1),(short)(j+1));
                    num++;
                    monsterList.add(monster);
                }
                else if (map[i][j] == 3){
                    Monster monster = new Monster("树灵"+num,(short) (i+1),(short)(j+1));
                    num++;
                    monsterList.add(monster);
                }
                else if (map[i][j] == 4){
                    Monster monster = new Monster("树王"+num,(short) (i+1),(short)(j+1));
                    num++;
                    monsterList.add(monster);
                }
            }
    }

    public static String MaptoMapMessage(short userX,short userY){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("您所在位子为野外的("+userX+","+userY+")\n");
        for(int i = 0;i < LENGTH;i++) {
            for (int j = 0; j < LENGTH; j++) {
                if(map[i][j] == 0)
                    stringBuilder.append("树   ");
                else if (i+1 == userX&&j+1 == userY){
                    stringBuilder.append("用户 ");
                }
                else if(map[i][j] == 1)
                    stringBuilder.append("草地 ");
                else if (map[i][j] == 2)
                    stringBuilder.append("鹿灵 ");
                else if (map[i][j] == 3)
                    stringBuilder.append("树灵 ");
                else if(map[i][j] == 4)
                    stringBuilder.append("树王 ");
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
