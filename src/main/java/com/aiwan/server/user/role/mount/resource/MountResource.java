package com.aiwan.server.user.role.mount.resource;

import com.aiwan.server.publicsystem.annotation.CellMapping;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.util.AttributeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dengzebiao
 * @since 2019.6.13
 * 坐骑静态资源
 * */
public class MountResource {

    /** 属性 */
    @CellMapping(name = "attribute")
    private String attribute;

    /** 最大阶数 */
    @CellMapping(name = "maxLevel")
    private int maxLevel;

    /** 属性类型列表 */
    List<AttributeElement> list;

    /** 初始化 */
    public void init(){
        //初始化属性列表
        list = AttributeUtil.getAttributeInitByString(attribute);
    }
    public String getAttribute() {
        return attribute;
    }

    public MountResource setAttribute(String attribute) {
        this.attribute = attribute;
        return this;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public MountResource setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
        return this;
    }

    public List<AttributeElement> getList() {
        return list;
    }

    public MountResource setList(List<AttributeElement> list) {
        this.list = list;
        return this;
    }
}
