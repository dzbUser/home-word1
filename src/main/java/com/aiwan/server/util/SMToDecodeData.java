package com.aiwan.server.util;

import com.aiwan.server.publicsystem.protocol.DecodeData;

/**
 * @author dengzebiao
 * DecodeData类转换工具类
 * */
public class SMToDecodeData {

    /** 组成协议发送类 */
    public static DecodeData shift(int type,Object object){
        DecodeData decodeData = new DecodeData();
        decodeData.setType(type);
        decodeData.setObject(object);
        return decodeData;
    }
}
