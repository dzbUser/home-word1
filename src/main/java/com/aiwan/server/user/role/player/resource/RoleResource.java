package com.aiwan.server.user.role.player.resource;

import com.aiwan.server.publicsystem.annotation.CellMapping;
import com.aiwan.server.publicsystem.annotation.Resource;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.util.AttributeUtil;

import java.util.List;

/**
 * @author dengzebiao
 * @since 2019.6.10
 * 用来存职业，性别，装备，坐骑对应名字
 * */
@Resource("staticresource/role.xls")
public class RoleResource {


    /** 最大人物等级 */
    @CellMapping(name = "maxLevel")
    private int maxLevel;


    /** 人物属性对应信息 */
    @CellMapping(name = "roleAttribute")
    private String roleAttribute;

    /**
     * 技能栏最大技能数
     */
    @CellMapping(name = "maxSkillNum")
    private int maxSkillNum;

    /**
     * 初始地图
     */
    @CellMapping(name = "originMap")
    private int originMap;

    /**
     * 初始x坐标
     */
    @CellMapping(name = "originX")
    private int originX;

    /**
     * 初始y坐标
     */
    @CellMapping(name = "originY")
    private int originY;



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

    public int getMaxSkillNum() {
        return maxSkillNum;
    }

    public void setMaxSkillNum(int maxSkillNum) {
        this.maxSkillNum = maxSkillNum;
    }

    public int getOriginMap() {
        return originMap;
    }

    public void setOriginMap(int originMap) {
        this.originMap = originMap;
    }

    public int getOriginX() {
        return originX;
    }

    public void setOriginX(int originX) {
        this.originX = originX;
    }

    public int getOriginY() {
        return originY;
    }

    public void setOriginY(int originY) {
        this.originY = originY;
    }
}
