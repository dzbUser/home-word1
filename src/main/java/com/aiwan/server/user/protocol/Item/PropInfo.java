package com.aiwan.server.user.protocol.Item;

/**
 * @author dengzebiao
 * @since 2019.6.12
 * 客户端道具项
 * */
public class PropInfo {
    /** 唯一id */
    private int id;

    /** 数量 */
    private int num;

    public static PropInfo valueOf(int id,int num){
        PropInfo propInfo = new PropInfo();
        propInfo.setId(id);
        propInfo.setNum(num);
        return propInfo;
    }
    public int getId() {
        return id;
    }

    public PropInfo setId(int id) {
        this.id = id;
        return this;
    }

    public int getNum() {
        return num;
    }

    public PropInfo setNum(int num) {
        this.num = num;
        return this;
    }
}
