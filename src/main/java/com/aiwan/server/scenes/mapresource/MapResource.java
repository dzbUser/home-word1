package com.aiwan.server.scenes.mapresource;

import com.aiwan.server.publicsystem.annotation.CellMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengzebiao
 * 地图资源类
 * */
public class MapResource {
    /**地图类型*/
    @CellMapping(name = "mapType")
    private Integer mapType;

    /**地图名字*/
    @CellMapping(name = "name")
    private String name;

    /**地图高度*/
    @CellMapping(name = "height")
    private Integer height;

    /**地图宽度*/
    @CellMapping(name = "width")
    private Integer width;

    /**地图初始x坐标*/
    @CellMapping(name = "originX")
    private Integer originX;

    /**地图初始化y纵坐标*/
    @CellMapping(name = "originY")
    private Integer originY;

    /**地图字符串*/
    @CellMapping(name = "mapString")
    private String mapString;

    /**位置信息字符串*/
    @CellMapping(name = "positionString")
    private String positionString;

    /**存地图数字涵义*/
    private Map<Integer, PositionMeaning> positionMeaningHashMap = new HashMap<Integer, PositionMeaning>();

    /**存地图数据*/
    private int[][] map;

    /**地图动态内容*/
    private String[][] mapMessage;



    public MapResource(){

    }
    public void init(){
        //初始化地图数据
        this.map = new int[height][width];
        String[] line = mapString.split(";");
        for (int i = 0;i<line.length;i++){
            String[] num = line[i].split(" ");
            for (int j = 0;j<num.length;j++){
                this.map[i][j] = Integer.parseInt(num[j]);
            }
        }

        //初始化位置信息
        line = positionString.split(";");
        for (int i = 0;i<line.length;i++){
            String[] element =  line[i].split(" ");
            PositionMeaning positionMeaning = new PositionMeaning();
            positionMeaning.setNum(Integer.parseInt(element[0]));
            positionMeaning.setName(element[1]);
            positionMeaning.setAllowMove(Integer.parseInt(element[2]));
            positionMeaningHashMap.put(positionMeaning.getNum(),positionMeaning);
        }
        //初始化动态资源
        initMapMessage();
    }

    //初始化动态资源
    private void initMapMessage(){
        this.mapMessage = new String[height][width];
        for (int i = 0;i < height;i++){
            for (int j = 0;j < width;j++){
                mapMessage[i][j] = positionMeaningHashMap.get(map[i][j]).getName();
            }
        }
    }



    public Integer getMapType() {
        return mapType;
    }

    public void setMapType(Integer mapType) {
        this.mapType = mapType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getOriginX() {
        return originX;
    }

    public void setOriginX(Integer originX) {
        this.originX = originX;
    }

    public Integer getOriginY() {
        return originY;
    }

    public void setOriginY(Integer originY) {
        this.originY = originY;
    }

    public String getMapString() {
        return mapString;
    }

    public void setMapString(String mapString) {
        this.mapString = mapString;
    }

    public String getPositionString() {
        return positionString;
    }

    public void setPositionString(String positionString) {
        this.positionString = positionString;
    }

    public String[][] getMapMessage() {
        return mapMessage;
    }


    public Map<Integer, PositionMeaning> getPositionMeaningHashMap() {
        return positionMeaningHashMap;
    }

    public int[][] getMap() {
        return map;
    }

}
