package com.aiwan.server.user.backpack.model;

import com.aiwan.server.prop.model.AbstractProps;
import com.aiwan.server.prop.model.PropsType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
        //初始化所有元素0,0代表为空
        for (int i = 0; i < abstractProps.length; i++) {
            AbstractProps props = PropsType.EMPTY.createProp();
            props.init(0);
            this.abstractProps[i] = props;
        }
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
