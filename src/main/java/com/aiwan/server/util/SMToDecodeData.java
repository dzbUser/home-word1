package com.aiwan.server.util;

import com.aiwan.server.publicsystem.protocol.DecodeData;

/**
 * @author dengzebiao
 * DecodeData类转换工具类
 * */
public class SMToDecodeData {
    public static DecodeData shift(int type,Object object){
        DecodeData decodeData = new DecodeData();
        decodeData.setType(type);
        byte[] data = ObjectToBytes.writeInto(object);
        decodeData.setLength(data.length);
        decodeData.setData(data);
        return decodeData;
    }
}
