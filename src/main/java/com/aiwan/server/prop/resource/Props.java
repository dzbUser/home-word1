package com.aiwan.server.prop.resource;

/**
 * @author dengzebiap
 * @since 2019.6.3
 * 道具静态初始化类
 * */
public class Props {

    //id
    private int id;
    //道具类型
    private int type;
    //道具名字
    private String name;
    //道具介绍
    private String introduce;
    //道具是否可叠加
    private int overlay;

    public int getId() {
        return id;
    }

    public Props setId(int id) {
        this.id = id;
        return this;
    }

    public int getType() {
        return type;
    }

    public Props setType(int type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public Props setName(String name) {
        this.name = name;
        return this;
    }

    public String getIntroduce() {
        return introduce;
    }

    public Props setIntroduce(String introduce) {
        this.introduce = introduce;
        return this;
    }

    public int getOverlay() {
        return overlay;
    }

    public Props setOverlay(int overlay) {
        this.overlay = overlay;
        return this;
    }
}
