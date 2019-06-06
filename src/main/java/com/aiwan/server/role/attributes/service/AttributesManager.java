package com.aiwan.server.role.attributes.service;

import com.aiwan.server.role.attributes.model.RoleAttributes;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dengzebiao
 * @since 2019.6.6
 * 属性模型类
 * */
@Service
public class AttributesManager {
    /** 人物属性映射 */
    private Map<Long, RoleAttributes> roleAttributesMap = new ConcurrentHashMap<>();

    /** 获取人物属性 */
    public RoleAttributes getRoleAttributes(Long rId){
        return roleAttributesMap.get(rId);
    }

    /** 插入人物属性 */
    public void putRoleAttributes(Long rId,RoleAttributes roleAttributes){
        roleAttributesMap.put(rId,roleAttributes);
    }

    /** 删除人物属性 */
    public void removeRoleAttributes(Long rId){
        roleAttributesMap.remove(rId);
    }

}
