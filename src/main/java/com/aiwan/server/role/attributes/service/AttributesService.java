package com.aiwan.server.role.attributes.service;

/**
 * @author dengzebiao
 * @since 2019.6.6
 * 人物属性业务逻辑接口 */
public interface AttributesService {
    /** 查看人物属性 */
    String viewRoleAttributes(Long rId);

    /** 加入人物属性到管理映射 */
    void putRoleAttributes(Long rId);

    /** 从管理映射中删除人物属性 */
    void removeRoleAttributes(Long rId);
}
