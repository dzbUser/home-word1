package com.aiwan.publicsystem.Initialization;

import com.aiwan.scenes.mapresource.MapResource;
import com.aiwan.scenes.mapresource.PositionMeaning;
import com.aiwan.scenes.service.MapManager;
import com.aiwan.util.GetBean;

import java.io.*;

/**
 * 地图资源初始化类
 * */
public class MapInitialization {
    private final static String FILEPATH="src/main/resources/map";
    private final static String PROTOCOLPATH = "src/main/resources/protocol/mapProtocol.txt";
    public static void init() throws IOException {
        /*
        * 1)获取MapManager类
        * 2)把Map资源按规定转换为MapResource，存到MapManager中
        * 3)把map协议资源村到MapManager中
        * */
        MapManager mapManager = GetBean.getMapManager();// 1)

        //存协议
        InputStreamReader reader = new InputStreamReader(new FileInputStream(new File(PROTOCOLPATH)),"UTF-8");
        BufferedReader bfreader=new BufferedReader(reader);
        String line;
        while((line=bfreader.readLine())!=null) {//包含该行内容的字符串，不包含任何行终止符，如果已到达流末尾，则返回 null
            String[] data = line.split(" ");
            //创建位置含义
            PositionMeaning positionMeaning = new PositionMeaning(Integer.parseInt(data[0]),data[1],Integer.parseInt(data[2]));
            mapManager.putMapProtocol(Integer.parseInt(data[0]),positionMeaning);
        }

        //存地图资源
        File file = new File(FILEPATH);
        if (file.isDirectory()){
            String[] files = file.list();
            for (int i=0;i<files.length;i++){
                File readfile = new File(FILEPATH + "\\" + files[i]);
                MapResource mapResource = getMapFromFile(readfile);
                mapManager.putMapResource(mapResource);
            }
        }


    }

    //从文件从读取地图资源
    public static MapResource getMapFromFile(File file) throws IOException {
        /*
        * 1.读类型
        * 2.读长与宽
        * 3.创建数组
        * 4.初始化初始地点
        * 4.读取地图名字
        * 5.存数组
        * */
        //读类型
        InputStreamReader reader= null;
        try {
            reader = new InputStreamReader(new FileInputStream(file),"UTF-8");
            BufferedReader bfreader=new BufferedReader(reader);
            String line;
            //1获取地图类型
            line = bfreader.readLine();
            Integer mapType = Integer.parseInt(line);
            //获取地图长宽
            line = bfreader.readLine();
            String[] size = line.split(" ");
            int height = Integer.parseInt(size[0]);
            int width = Integer.parseInt(size[1]);
            //创建二维数组
            int[][] data = new int[height][width];
            //获取初始地点
            String origin  = bfreader.readLine();
            String[] origins = origin.split(" ");
            int originX = Integer.parseInt(origins[0]);
            int originY = Integer.parseInt(origins[1]);
            //读取地图名字
            String name = bfreader.readLine();
            //读取二维数组
            for (int i = 0;i<height;i++){
                line = bfreader.readLine();
                String[] num  = line.split(" ");
                for (int j = 0;j<width;j++){
                    data[i][j] = Integer.parseInt(num[j]);
                }
            }
            return new MapResource(mapType,name,data,height,width,originX,originY);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            reader.close();
        }
        return null;
    }


}
