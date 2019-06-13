package com.aiwan.server.user.role.resource;

import com.aiwan.server.publicsystem.annotation.CellMapping;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.util.AttributeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.6.10
 * 用来存职业，性别，装备，坐骑对应名字
 * */
public class RoleResource {


    /** 最大人物等级 */
    @CellMapping(name = "maxLevel")
    private int maxLevel;


    /** 人物属性对应信息 */
    @CellMapping(name = "roleAttribute")
    private String roleAttribute;



    /** 属性类型初始化列表 */
    List<AttributeElement> list;

    /** 初始化 */
    public void init(){
        //初始化人物属性项
        list = AttributeUtil.getAttributeInitByString(roleAttribute);
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public RoleResource setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
        return this;
    }

    public String getRoleAttribute() {
        return roleAttribute;
    }

    public RoleResource setRoleAttribute(String roleAttribute) {
        this.roleAttribute = roleAttribute;
        return this;
    }

    public List<AttributeElement> getList() {
        return list;
    }

    public RoleResource setList(List<AttributeElement> list) {
        this.list = list;
        return this;
    }
}
