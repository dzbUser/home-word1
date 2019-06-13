package com.aiwan.server.user.protocol;

import com.aiwan.server.user.protocol.Item.PropInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dengzebiao
 * @since 2019.9.17
 * 道具查看类
 * */
public class SM_PropList {

    /** 道具id-》数量 */
    private List<PropInfo> list = new ArrayList<>(16);


    /**  添加道具*/
    public void putProp(PropInfo propInfo){
        list.add(propInfo);
    }

    public List<PropInfo> getList() {
        return list;
    }

    public SM_PropList setList(List<PropInfo> list) {
        this.list = list;
        return this;
    }
}
