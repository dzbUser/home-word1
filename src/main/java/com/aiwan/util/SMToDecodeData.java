package com.aiwan.util;

import com.aiwan.publicsystem.protocol.DecodeData;

/**
 * DecodeData类转换工具类
 * */
public class SMToDecodeData {
    public static DecodeData shift(short type,Object object){
        DecodeData decodeData = new DecodeData();
        decodeData.setType(type);
        byte[] data = ObjectToBytes.writeInto(object);
        decodeData.setLength(data.length);
        decodeData.setData(data);
        return decodeData;
    }
}