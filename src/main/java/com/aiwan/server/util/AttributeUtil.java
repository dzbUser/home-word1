package com.aiwan.server.util;

import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;

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
            //若为百分比值,或者该属性没有被影响
            return value;
        }
        //初始化百分比
        int rate = 0;
        for (AttributeType type:element.getAttributeType().getBeEffectAttribute()){
            //叠加的属性在角色属性列表中不存在
            if (pureAttribute.get(type) == null){
                continue;
            }
            //叠加百分比
            rate += pureAttribute.get(type).getValue();
        }
        return value+rate*value/100;
    }
}
