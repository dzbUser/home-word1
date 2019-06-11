package com.aiwan.server.prop.resource;

import com.aiwan.server.publicsystem.annotation.CellMapping;

/**
 * @author dengzebiap
 * @since 2019.6.3
 * 道具静态初始化类
 * */
public class Props {

    /** 道具id */
    @CellMapping(name = "id")
    private int id;

    /** 道具类型 */
    @CellMapping(name = "type")
    private int type;

    /** 道具名字 */
    @CellMapping(name = "name")
    private String name;

    /** 道具介绍 */
    @CellMapping(name = "introduce")
    private String introduce;

    /** 道具是否可叠加 */
    @CellMapping(name = "overlay")
    private int overlay;

    /** 是否可以使用 */
    @CellMapping(name = "use")
    private int use;

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

    public int getUse() {
        return use;
    }

    public Props setUse(int use) {
        this.use = use;
        return this;
    }
}
