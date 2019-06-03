package com.aiwan.server.scenes.mapresource;

//存储地图二维数组内数字的含义
public class PositionMeaning {
    //二维数组中的数字
    private int num;
    //名字
    private String name;
    //是否可移动,1可移动，0不可移动
    private int allowMove;

    public PositionMeaning(int num, String name, int allowMove) {
        this.num = num;
        this.name = name;
        this.allowMove = allowMove;
    }

    public PositionMeaning(){}

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAllowMove() {
        return allowMove;
    }

    public void setAllowMove(int allowMove) {
        this.allowMove = allowMove;
    }
}
