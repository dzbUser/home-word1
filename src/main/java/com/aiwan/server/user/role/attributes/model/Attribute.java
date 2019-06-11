package com.aiwan.server.user.role.attributes.model;

import com.aiwan.server.user.role.player.service.impl.RoleServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dengzebiao
 * @since 2019.6.10
 * 人物属性
 * */
public class Attribute {
    Logger logger = LoggerFactory.getLogger(Attribute.class);

    /** 各个模块属性 */
    private Map<String,AttributeModule> attributeModuleMap = new ConcurrentHashMap<>();

    /** 人物属性 */
    private Map<String,AttributeItem> attributeItemMap = new ConcurrentHashMap<>();

    /** 插入属性模块 */
    public void putAttributeModule(String name,AttributeModule attributeModule){
        //是否有旧的模块
        AttributeModule oldModule = attributeModuleMap.get(name);
        if (oldModule == null){
            //没有旧的模块，不用减去旧的属性
            attributeModuleMap.put(name,attributeModule);
        }else {
            //有旧的属性，需减去旧的属性
            Map<String, AttributeItem> map = oldModule.getAttributeItemMap();
            for (Map.Entry<String, AttributeItem> entry:map.entrySet()){
                AttributeItem attributeItem = attributeItemMap.get(entry.getKey());
                logger.debug("attribute:"+attributeItem.getName()+" value:"+attributeItem.getValue());
                logger.debug("entry:attribute:"+entry.getKey()+" value:"+entry.getValue().getValue());
                attributeItem.setValue(attributeItem.getValue()-entry.getValue().getValue());
                logger.debug("attribute:"+attributeItem.getName()+" value:"+attributeItem.getValue());
            }
        }
        //添加新属性
        for (Map.Entry<String, AttributeItem> entry:attributeModule.getAttributeItemMap().entrySet()){
            //获取旧的属性项
            AttributeItem oldItem = attributeItemMap.get(entry.getKey());
            if (oldItem == null)
            {
                //没有属性项.创建属性项
                oldItem = new AttributeItem();
                oldItem.setName(entry.getKey());
                oldItem.setValue(entry.getValue().getValue());
                attributeItemMap.put(entry.getKey(),oldItem);
            }else {
                //有属性项
                oldItem.setValue(oldItem.getValue()+entry.getValue().getValue());
            }
        }
        attributeModuleMap.put(name,attributeModule);
    }

    public Map<String, AttributeItem> getAttributeItemMap() {
        //暂用
        attributeItemMap.get("攻击力").setOverValue(attributeItemMap.get("攻击力").getValue()+attributeItemMap.get("攻击力").getValue()*attributeItemMap.get("攻击加成").getValue()/100);
        return attributeItemMap;
    }

    public Attribute setAttributeItemMap(Map<String, AttributeItem> attributeItemMap) {
        this.attributeItemMap = attributeItemMap;
        return this;
    }
}