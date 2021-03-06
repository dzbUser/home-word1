package com.aiwan.server.monster.resource;

/**
 * 掉落资源的参数类
 *
 * @author dengzebiao
 * @since 2019.7.15
 */
public class DropBean {

    /**
     * 道具id
     */
    private int propId;

    /**
     * 道具数目
     */
    private int num;

    public static DropBean valueOf(int propId, int num) {
        DropBean dropBean = new DropBean();
        dropBean.setNum(num);
        dropBean.setPropId(propId);
        return dropBean;
    }

    public int getPropId() {
        return propId;
    }

    public void setPropId(int propId) {
        this.propId = propId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
