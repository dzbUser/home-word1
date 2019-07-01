package com.aiwan.server.prop.service;

import com.aiwan.server.prop.resource.PropsResource;
import com.aiwan.server.publicsystem.annotation.Manager;
import com.aiwan.server.publicsystem.annotation.Static;
import com.aiwan.server.util.ExcelUtil;
import com.aiwan.server.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dengzebiao
 * @since 2019.6.3
 * 静态资源管理类
 * */
@Manager
public class PropsManager {
    static Logger logger = LoggerFactory.getLogger(PropsManager.class);
    public final static int EQUIP  = 3;
    public final static int Prop = 1;
    public final static int DEVELOP = 2;

    /** 道具静态资源映射 */
    @Static(initializeMethodName = "initializeProp")
    private Map<Integer, PropsResource> propsMap = new ConcurrentHashMap<Integer, PropsResource>();

    /**
     * 获取某种道具
     * */
    public PropsResource getPropsResource(Integer id) {
        return propsMap.get(id);
    }

    /**
     * 保存某类道具
     * */
    public void putProps(PropsResource propsResource){
        propsMap.put(propsResource.getId(), propsResource);
    }

    /**
     * 道具资源初始化
     */
    private void initializeProp() {
        //获取资源路径
        String path = ResourceUtil.getResourcePath(PropsResource.class);
        logger.debug("FILEPATH：{}", path);
        logger.debug("道具静态资源初始化前");
        List<PropsResource> list = new ArrayList<PropsResource>();
        try {
            list.addAll(ExcelUtil.analysisWithRelativePath(path, PropsResource.class));
            logger.debug("道具静态资源初始化debug：");
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        logger.debug("道具静态资源初始化debug：" + list.size());
        for (int i = 0; i < list.size(); i++) {
            list.get(i).init();
            this.putProps(list.get(i));
        }
    }
}
