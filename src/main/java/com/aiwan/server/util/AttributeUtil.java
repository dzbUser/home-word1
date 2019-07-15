package com.aiwan.server.util;

import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.6.12
 * 属性计算工具类
 * */
public class AttributeUtil {

    /** 把vice属性叠加到main属性中 */
    public static void superimposed(Map<AttributeType, AttributeElement> mainAttributes, Map<AttributeType, AttributeElement> viceAttributes) {
        for (Map.Entry<AttributeType, AttributeElement> entry:viceAttributes.entrySet()){
            if (mainAttributes.get(entry.getKey()) == null){
                //主属性中没有副属性的属性项
                mainAttributes.put(entry.getKey(),AttributeElement.valueOf(entry.getKey(),0));
            }
            //属性叠加
            mainAttributes.get(entry.getKey()).setValue(viceAttributes.get(entry.getKey()).getValue()+mainAttributes.get(entry.getKey()).getValue());
        }
    }

    /** 计算最终属性项 */
    public static long calculateItem(AttributeElement element, Map<AttributeType, AttributeElement> pureAttribute) {
        long value = element.getValue();
        if (element.getAttributeType().isRateAttribute()||element.getAttributeType().getBeEffectAttribute() == null){
            //若为万分比值,或者该属性没有被影响
            return value;
        }
        //初始化万分比
        int rate = 0;
        for (AttributeType type:element.getAttributeType().getBeEffectAttribute()){
            //叠加的属性在角色属性列表中不存在
            if (pureAttribute.get(type) == null){
                continue;
            }
            //叠加万分比比
            rate += pureAttribute.get(type).getValue();
        }
        return value + (rate * value) / 10000;
    }

    /** 字符串转化为初始属性 */
    public static List<AttributeElement> getAttributeInitByString(String attributeString){
        List<AttributeElement> list = new ArrayList<AttributeElement>();
        String[] attributeStrings = attributeString.split(" ");
        for (String element:attributeStrings){
            //遍历所有属性项
            String[] strings = element.split(":");
            AttributeType attributeType = AttributeType.getType(Integer.parseInt(strings[0]));
            AttributeElement attributeElement = AttributeElement.valueOf(attributeType,Integer.parseInt(strings[1]));
            list.add(attributeElement);
        }
        return list;
    }

    /** 把基础初始属性按照等级计算的最终属性
     * value*level
     * */
    public static Map<AttributeType, AttributeElement> getMapAttributeWithLevel(List<AttributeElement> list,int level){
        Map<AttributeType, AttributeElement> map = new HashMap<AttributeType, AttributeElement>();
        for (AttributeElement element:list){
            //属性项垴村
            AttributeElement attributeElement = AttributeElement.valueOf(element.getAttributeType(),element.getValue()*level);
            map.put(attributeElement.getAttributeType(),attributeElement);
        }
        return map;
    }

    /**
     * copy属性到新的容器
     */
    public static Map<AttributeType, AttributeElement> getCopyAttributeMap(Map<AttributeType, AttributeElement> attributeElementMap) {
        Map<AttributeType, AttributeElement> newMap = new HashMap<>();
        for (AttributeElement attributeElement : attributeElementMap.values()) {
            newMap.put(attributeElement.getAttributeType(), attributeElement.cloneAttribute());
        }
        return newMap;
    }
}
