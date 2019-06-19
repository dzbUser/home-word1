package com.aiwan.server.prop.model;

import com.aiwan.server.prop.model.impl.Equipment;
import com.aiwan.server.prop.resource.PropsResource;
import com.aiwan.server.util.GetBean;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


/**
 * @author dengzebiao
 * @since 2019.6.19
 * 道具抽象类
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS)
public abstract class AbstractProps {
    /**
     * 道具资源唯一id
     */
    protected int id;

    /**
     * 数量
     */
    protected int num;


    /**
     * 道具使用
     */
    public abstract boolean propUser(String accountId, Long rId);

    /**
     * 初始化
     */
    public void init(int id) {
        this.id = id;
        this.num = 1;
    }

    @JsonIgnore
    public void init(PropsResource propsResource) {
        this.id = propsResource.getId();
        this.num = 1;
    }

    /**
     * 是否为装备类型
     */
    @JsonIgnore
    public boolean isEquipment() {
        return this instanceof Equipment;
    }

    /**
     * 获取道具类型
     */
    @JsonIgnore
    public PropsResource getPropsResource() {
        return GetBean.getPropsManager().getPropsResource(getId());
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

}
