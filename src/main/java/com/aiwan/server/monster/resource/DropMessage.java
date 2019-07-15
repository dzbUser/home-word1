package com.aiwan.server.monster.resource;

/**
 * 掉落资源的参数类
 *
 * @author dengzebiao
 * @since 2019.7.15
 */
public class DropMessage {

    /**
     * 道具id
     */
    private int propId;

    /**
     * 道具数目
     */
    private int num;

    public static DropMessage valueOf(int propId, int num) {
        DropMessage dropMessage = new DropMessage();
        dropMessage.setNum(num);
        dropMessage.setPropId(propId);
        return dropMessage;
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
