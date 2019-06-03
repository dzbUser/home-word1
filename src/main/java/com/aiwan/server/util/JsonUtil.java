package com.aiwan.server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
/**
 * @author dengzebiao
 * json转化工具
 * */
public class JsonUtil {
    private static ObjectMapper mapper;
    static Logger  logger = LoggerFactory.getLogger(JsonUtil.class);

    static {
        mapper = new ObjectMapper();
        // 转换为格式化的json
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // 如果json中有新增的字段并且是实体类类中不存在的，不报错
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 对象转二进制
     * */
    public static byte[] object2Bytes(Object object) {
        try {
            return mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            logger.error("JsonProcessingException",e);
        }
        return null;
    }

    /**
     * 二进制转对象
     * */
    public static Object bytes2Object(byte[] bytes,Class clz) {
        Object object = null;
        try {
            return mapper.readValue(bytes,clz);
        } catch (IOException e) {
            logger.error("IOException",e);
        }
        return null;
    }
}
