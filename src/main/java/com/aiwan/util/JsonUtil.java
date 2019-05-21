package com.aiwan.util;

import net.sf.json.JSONObject;


//json工具
public class JsonUtil {
    public static String objectToJson(Object object){
        JSONObject jsonObject = JSONObject.fromObject(object);
        return jsonObject.toString();
    }

    public static Object jsonToObject(String str){
        JSONObject jsonObject = new JSONObject().fromObject(str);
        Object object = JSONObject.toBean(jsonObject,Object.class);
        return object;
    }
}
