package com.aiwan.server.user.role.attributes.model;

import com.aiwan.server.user.role.attributes.model.impl.FightAttributeModule;
import com.aiwan.server.user.role.attributes.model.impl.RoleAttributesModule;
import com.aiwan.server.util.AttributeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.Attribute;
import java.util.HashMap;
import java.util.Map;

/**
 * 属性容器
 *
 * @author dengzebiao
 * @since 2019.7.15
 */
public class AttributeContainer {

    Logger logger = LoggerFactory.getLogger(AttributeContainer.class);

    /**
     * 模块属性
     */
    private Map<AttributeId, Map<AttributeType, AttributeElement>> moduleMap = new HashMap<>(16);

    /**
     * 纯净属性
     */
    private Map<AttributeType, AttributeElement> pureAttribute = new HashMap<>(16);

    /**
     * 最终属性
     */
    private Map<AttributeType, AttributeElement> finalAttribute = new HashMap<>(16);

    /**
     * 计算属性
     */
    public void calculate() {
        //纯净属性清空
        for (Map.Entry<AttributeType, AttributeElement> entry : pureAttribute.entrySet()) {
            entry.getValue().setValue(0);
        }

        //模块中的属性叠加到纯净属性中
        for (Map.Entry<AttributeId, Map<AttributeType, AttributeElement>> entry : moduleMap.entrySet()) {
            AttributeUtil.superimposed(pureAttribute, entry.getValue());
        }

        //计算最终属性
        for (Map.Entry<AttributeType, AttributeElement> entry : pureAttribute.entrySet()) {
            //获取最终属性值
            long value = entry.getKey().calculate(entry.getValue(), pureAttribute);
            finalAttribute.put(entry.getKey(), AttributeElement.valueOf(entry.getKey(), value));
        }

    }

    /**
     * 重新计算
     */
    public void reCalculate() {
        calculate();
    }

    /**
     * 模块更新
     */
    public void updateModule(RoleAttributesModule roleAttributesModule, Map<AttributeType, AttributeElement> map) {
        moduleMap.put(roleAttributesModule, map);
        reCalculate();
    }

    /**
     * 根据属性id获取对应模块的属性
     */
    public Map<AttributeType, AttributeElement> getAttributesById(AttributeId attributeId) {
        return moduleMap.get(attributeId);
    }

    /**
     * 更具模块id加入属性
     */

    public void updateModule(AttributeId attributeId, Map<AttributeType, AttributeElement> map) {
        moduleMap.put(attributeId, map);
        reCalculate();
    }

    public Map<AttributeType, AttributeElement> getFinalAttribute() {
        return finalAttribute;
    }

    public Map<AttributeType, AttributeElement> getPureAttribute() {
        return pureAttribute;
    }

    public void setFinalAttribute(Map<AttributeType, AttributeElement> finalAttribute) {
        this.finalAttribute = finalAttribute;
    }


    /**
     * 加入属性
     *
     * @param addAttribute 添加属性
     */
    public void putBuffAttribute(Map<AttributeType, AttributeElement> addAttribute, AttributeId attributeId) {
        for (Map.Entry<AttributeType, AttributeElement> entry : addAttribute.entrySet()) {
            AttributeElement unit = getAttributesById(attributeId).get(entry.getKey());
            if (unit == null) {
                getAttributesById(attributeId).put(entry.getKey(), AttributeElement.valueOf(entry.getKey(), entry.getValue().getValue()));
            } else {
                unit.setValue(entry.getValue().getValue() + unit.getValue());
            }
        }
        reCalculate();
    }

    /**
     * 去除buff属性
     *
     * @param addAttribute 去除的属性
     */
    public void removeBuffAttribute(Map<AttributeType, AttributeElement> addAttribute, AttributeId attributeId) {
        for (Map.Entry<AttributeType, AttributeElement> entry : addAttribute.entrySet()) {
            AttributeElement unit = getAttributesById(attributeId).get(entry.getKey());
            if (unit == null) {
                logger.error("模块{}去除属性：{}错误", attributeId.getName(), entry.getKey().getDesc());
            } else {
                unit.setValue(unit.getValue() - entry.getValue().getValue());
                if (unit.getValue() == 0) {
                    getAttributesById(attributeId).remove(entry.getKey());
                }
            }
        }
        reCalculate();
    }

    public Map<AttributeId, Map<AttributeType, AttributeElement>> getModuleMap() {
        return moduleMap;
    }

    public void setModuleMap(Map<AttributeId, Map<AttributeType, AttributeElement>> moduleMap) {
        this.moduleMap = moduleMap;
    }
}
