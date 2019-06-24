package com.aiwan.server.user.backpack.model;

import com.aiwan.server.prop.model.AbstractProps;


/**
 * @author dengzebia
 * @since 2019.6.4
 * 背包详细信息
 * */
public class BackpackInfo {


    /** 背包数据 */
    private AbstractProps[] abstractProps;


    public BackpackInfo(int maxNum) {
        //初始化数组
        this.abstractProps = new AbstractProps[maxNum];
    }

    public BackpackInfo() {

    }

    public AbstractProps[] getAbstractProps() {
        return abstractProps;
    }

    public void setAbstractProps(AbstractProps[] abstractProps) {
        this.abstractProps = abstractProps;
    }
}
