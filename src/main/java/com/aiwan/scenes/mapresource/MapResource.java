package com.aiwan.scenes.mapresource;

import com.aiwan.scenes.service.MapManager;
import com.aiwan.util.GetBean;

import java.util.Map;

/**
 * 地图资源类
 * */
public class MapResource {
    //地图类型
    private int mapType;
    //地图名字
    private String name;
    //地图
    private int[][] map;
    //地图长度
    private int height;
    //地图宽度
    private int width;
    //初始横坐标
    private int originX;
    //初始纵坐标
    private int originY;

    public MapResource(int mapType, String name, int[][] map,int height,int width,int originX,int originY) {
        this.mapType = mapType;
        this.name = name;
        this.map = map;
        this.height = height;
        this.width = width;
        this.originX = originX;
        this.originY = originY;
    }

    public int getMapType() {
        return mapType;
    }

    public void setMapType(int mapType) {
        this.mapType = mapType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getOriginX() {
        return originX;
    }

    public void setOriginX(int originX) {
        this.originX = originX;
    }

    public int getOriginY() {
        return originY;
    }

    public void setOriginY(int originY) {
        this.originY = originY;
    }

    public String getMapContent(int x, int y){
        MapManager mapManager = GetBean.getMapManager();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("您所在位子为"+name+"的("+x+","+y+")\n");
        for (int i = 0;i < height;i++){
            for (int j = 0;j < width;j++){
                if (i+1 == x&&j+1 == y){
                    stringBuilder.append("用户 ");
                }else {
                    stringBuilder.append(mapManager.getmapProtocol(map[i][j])+" ");
                }

            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();

    }

    public boolean allowMove(int x,int y){
        MapManager mapManager = GetBean.getMapManager();
        if (mapManager.getPositionMeaning(map[x-1][y-1]).getAllowMove() == 1){
            return true;
        }
        return false;
    }
}
