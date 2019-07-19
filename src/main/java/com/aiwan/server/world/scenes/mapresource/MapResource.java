package com.aiwan.server.world.scenes.mapresource;

import com.aiwan.server.publicsystem.annotation.CellMapping;
import com.aiwan.server.publicsystem.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengzebiao
 * 地图资源类
 * */
@Resource("staticresource/map.xls")
public class MapResource {
    /**地图类型*/
    @CellMapping(name = "mapId")
    private Integer mapId;

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

    /**
     * 是否为副本
     */
    @CellMapping(name = "isDungeon")
    private int isDungeon;

    /**
     * 副本类型（0.不是副本 1.经验副本 2.通关副本）
     */
    @CellMapping(name = "dungeonType")
    private int dungeonType;

    /**
     * 通关机制
     * 1.单人副本
     * 2.团队副本
     */
    @CellMapping(name = "isSingle")
    private int isSingle;

    /**
     * 关卡
     */
    @CellMapping(name = "checkpoint")
    private String checkpoint;

    /**
     * 结算奖励
     */
    @CellMapping(name = "settlementAward")
    private String settlementAward;

    @CellMapping(name = "duration")
    private Long duration;



    /**存地图数字涵义*/
    private Map<Integer, PositionMeaning> positionMeaningHashMap = new HashMap<Integer, PositionMeaning>();

    /**存地图数据*/
    private int[][] map;

    /**
     * 地图动态内容
     */
    private String[][] mapMessage;

    /**
     * 关卡列表（副本专有）
     */
    private Map<Integer, GateBean> gateBeanMap = new HashMap<>();

    /**
     * 结算（副本专有）
     */
    private SettlementBean settlementBean;

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
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mapMessage[i][j] = positionMeaningHashMap.get(map[i][j]).getName();
            }
        }

        //初始化关卡
        if (checkpoint != null) {
            String[] gateUnitString = checkpoint.split(" ");
            for (String unit : gateUnitString) {
                GateBean gateBean = new GateBean();
                gateBean.doParse(unit);
                gateBeanMap.put(gateBean.getGateNum(), gateBean);
            }
        }

        //初始化结算
        if (settlementAward != null) {
            this.settlementBean = new SettlementBean();
            this.settlementBean.doParse(settlementAward);
        }
    }


    public Integer getMapId() {
        return mapId;
    }

    public void setMapId(Integer mapId) {
        this.mapId = mapId;
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

    public int getIsDungeon() {
        return isDungeon;
    }

    public void setIsDungeon(int isDungeon) {
        this.isDungeon = isDungeon;
    }

    public int getDungeonType() {
        return dungeonType;
    }

    public void setDungeonType(int dungeonType) {
        this.dungeonType = dungeonType;
    }


    public int getIsSingle() {
        return isSingle;
    }

    public void setIsSingle(int isSingle) {
        this.isSingle = isSingle;
    }

    public String getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(String checkpoint) {
        this.checkpoint = checkpoint;
    }

    public String getSettlementAward() {
        return settlementAward;
    }

    public void setSettlementAward(String settlementAward) {
        this.settlementAward = settlementAward;
    }

    public Map<Integer, GateBean> getGateBeanMap() {
        return gateBeanMap;
    }

    public void setGateBeanMap(Map<Integer, GateBean> gateBeanMap) {
        this.gateBeanMap = gateBeanMap;
    }

    public SettlementBean getSettlementBean() {
        return settlementBean;
    }

    public void setSettlementBean(SettlementBean settlementBean) {
        this.settlementBean = settlementBean;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setPositionMeaningHashMap(Map<Integer, PositionMeaning> positionMeaningHashMap) {
        this.positionMeaningHashMap = positionMeaningHashMap;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public void setMapMessage(String[][] mapMessage) {
        this.mapMessage = mapMessage;
    }
}
