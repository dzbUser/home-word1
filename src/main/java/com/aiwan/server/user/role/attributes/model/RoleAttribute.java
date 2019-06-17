package com.aiwan.server.user.role.attributes.model;

import com.aiwan.server.util.AttributeUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.6.12
 * 人物属性汇总
 * */
public class RoleAttribute {
    /** 模块属性 */
    public Map<AttributesModule,Map<AttributeType,AttributeElement>> moduleMap = new HashMap<AttributesModule,Map<AttributeType,AttributeElement>>(16);

    /** 纯净属性 */
    public Map<AttributeType,AttributeElement> pureAttribute = new HashMap<AttributeType,AttributeElement>(16);

    /** 最终属性 */
    public Map<AttributeType,AttributeElement> finalAttribute = new HashMap<AttributeType,AttributeElement>(16);

    /** 构造函数 */
    public RoleAttribute(Long rId){
        //初始化
        for (AttributesModule module:AttributesModule.values()){
            moduleMap.put(module,module.getAttributes(rId));
        }
        //计算属性
        calculate();
    }

    /** 计算属性 */
    public void calculate(){
        //纯净属性清空
        for (Map.Entry<AttributeType,AttributeElement> entry:pureAttribute.entrySet()){
            entry.getValue().setValue(0);
        }

        //模块中的属性叠加到纯净属性中
        for (Map.Entry<AttributesModule,Map<AttributeType,AttributeElement>> entry:moduleMap.entrySet()){
            AttributeUtil.superimposed(pureAttribute,entry.getValue());
        }

        //计算最终属性
        for (Map.Entry<AttributeType,AttributeElement> entry:pureAttribute.entrySet()){
            //获取最终属性值
            long value = entry.getKey().calculate(entry.getValue(),pureAttribute);
            finalAttribute.put(entry.getKey(),AttributeElement.valueOf(entry.getKey(),value));
        }
    }

    /** 重新计算*/
    public void reCalculate(){
        calculate();
    }

    /** 模块更新 */
    public void updateModule(AttributesModule attributesModule,Map<AttributeType,AttributeElement> map){
        moduleMap.put(attributesModule,map);
        reCalculate();
    }

    public Map<AttributeType, AttributeElement> getFinalAttribute() {
        return finalAttribute;
    }

    public RoleAttribute setFinalAttribute(Map<AttributeType, AttributeElement> finalAttribute) {
        this.finalAttribute = finalAttribute;
        return this;
    }
}
