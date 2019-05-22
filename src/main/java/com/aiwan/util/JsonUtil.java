package com.aiwan.util;

import net.sf.json.JSONObject;


//json工具
public class JsonUtil {
    public static String objectToJson(Object object){
        JSONObject jsonObject = JSONObject.fromObject(object);
        return jsonObject.toString();
    }

    public static Object jsonToObject(String str,Class clazz){
        JSONObject jsonObject = new JSONObject().fromObject(str);
        Object object = JSONObject.toBean(jsonObject,clazz);
        return object;
    }
}
