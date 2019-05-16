package com.aiwan.util;

import com.aiwan.publicsystem.DecodeData;

/**
 * DecodeData类转换工具类
 * */
public class DecodeDataShift {
    public static DecodeData shift(short type,Object object){
        DecodeData decodeData = new DecodeData();
        decodeData.setType(type);
        byte[] data = DeepClone.writeInto(object);
        decodeData.setLength(data.length);
        decodeData.setData(data);
        return decodeData;
    }
}
