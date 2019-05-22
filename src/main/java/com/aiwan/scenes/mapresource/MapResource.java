package com.aiwan.scenes.mapresource;

import com.aiwan.publicsystem.annotation.CellMapping;
import com.aiwan.publicsystem.protocol.DecodeData;
import com.aiwan.scenes.protocol.SM_Shift;
import com.aiwan.scenes.service.MapManager;
import com.aiwan.user.entity.User;
import com.aiwan.util.ConsequenceCode;
import com.aiwan.util.GetBean;
import com.aiwan.util.SMToDecodeData;

import java.util.HashMap;
import java.util.Map;

/**
 * 地图资源类
 * */
public class MapResource {
    //地图类型
    @CellMapping(name = "mapType")
    private Integer mapType;
    //地图名字
    @CellMapping(name = "name")
    private String name;
    //地图高度
    @CellMapping(name = "height")
    private Integer height;
    //地图宽度
    @CellMapping(name = "width")
    private Integer width;
    //地图初始x坐标
    @CellMapping(name = "originX")
    private Integer originX;
    //地图初始化y纵坐标
    @CellMapping(name = "originY")
    private Integer originY;
    //地图字符串
    @CellMapping(name = "mapString")
    private String mapString;
    //位置信息字符串
    @CellMapping(name = "positionString")
    private String positionString;

    //存地图数字涵义协议
    private Map<Integer, PositionMeaning> positionMeaningHashMap = new HashMap<>();
    //存地图数据
    private int[][] map;
    //存储在本地图中的用户
    private Map<String, User> userMap = new HashMap<>();
    //地图动态内容
    private String[][] mapMessage;

    //添加用户
    public void putUser(String username,User user){
        userMap.put(username,user);
    }
    //获取用户
    public User getUser(String username){
        return userMap.get(username);
    }
    //删除用户
    public void removeUser(String username){
        userMap.remove(username);
    }


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
        MapManager mapManager = GetBean.getMapManager();

        for (int i = 0;i < height;i++){
            for (int j = 0;j < width;j++){
                mapMessage[i][j] = positionMeaningHashMap.get(map[i][j]).getName();
            }
        }
    }




    //获取地图信息
    public String getMapContent(int x, int y){
        MapManager mapManager = GetBean.getMapManager();
        StringBuilder stringBuilder = new StringBuilder();
        //添加地图资源
        stringBuilder.append("您所在位子为"+name+"的("+x+","+y+")\n");

        //创建用户标志
        byte[][] userflag = new byte[height][width];
        //初始化用户标志
        for (int i = 0;i < height;i++) {
            for (int j = 0; j < width; j++) {
                userflag[i][j] =0;
            }
        }
        //添加用户标志
        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            User  user  = entry.getValue();
            userflag[user.getCurrentX()-1][user.getCurrentY()-1] = 1;
        }

        for (int i = 0;i < height;i++){
            for (int j = 0;j < width;j++){
                if (userflag[i][j] == 1)
                    stringBuilder.append("用户 ");
                else
                    stringBuilder.append(mapMessage[i][j]+" ");
            }
            stringBuilder.append("\n");
        }
        //生成地图信息
        return stringBuilder.toString();
    }

    //发送地图信息到本地图所有用户
    public void sendMessage(){
        DecodeData decodeData;
        User user;
        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            //获取本地图所有用户
            user = entry.getValue();
            String username = user.getUsername();
            String content = getMapContent(user.getCurrentX(),user.getCurrentY());
            //获取对应用户的channel
//            Channel channel = ChannelManager.getChannelByUsername(username);
            //创建跳转类
            SM_Shift sm_shift = new SM_Shift(user.getCurrentX(),user.getCurrentY(),user.getMap(),content);
            decodeData = SMToDecodeData.shift(ConsequenceCode.SHIFTSUCCESS,sm_shift);
//            channel.writeAndFlush(decodeData);
        }
    }

    //用户移动
    public void move(String username,int x,int y){
        User user = getUser(username);
        user.setCurrentY(x);
        user.setCurrentY(y);
        putUser(username,user);
    }

    /**
     * 判断是否可走
     * */
    public boolean allowMove(int x,int y){
        if (x>height||y>width)
            return false;
        if (positionMeaningHashMap.get(map[x-1][y-1]).getAllowMove() == 1){
            return true;
        }
        return false;
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

}
