package com.aiwan.server.user.backpack.protocol;

import com.aiwan.server.user.Item.PropInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 背包类 */
public class SM_Package {

    /** 道具id-》数量 */
    private List<PropInfo> list = new ArrayList<>(16);


    /**  添加道具*/
    public void putProp(PropInfo propInfo){
        list.add(propInfo);
    }

    public List<PropInfo> getList() {
        return list;
    }

    public SM_Package setList(List<PropInfo> list) {
        this.list = list;
        return this;
    }
}
