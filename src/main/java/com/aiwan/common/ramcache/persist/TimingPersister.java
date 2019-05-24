package com.aiwan.common.ramcache.persist;

import com.aiwan.common.ramcache.IEntity;
import com.aiwan.common.ramcache.orm.Accessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class TimingPersister implements Persister{
    private static final Logger logger = LoggerFactory.getLogger(TimingPersister.class);
    /**  等待更新的信息缓存*/
    private ConcurrentHashMap<String,Element> elements = new ConcurrentHashMap<>();
    /** 正在更新的信息缓存 */
    private HashMap<String,IEntity> updating = new HashMap<>();
    /**初始化标志*/
    private boolean initailize;
    /**消费线程*/
    private TimingConsumer consumer;
    /** 是否停止*/
    private volatile boolean stop;
    @Override
    public void initailize(String name,Accessor accessor) {
        this.consumer = new TimingConsumer(name,accessor,this);
        initailize = true;
    }

    @Override
    public <T extends IEntity> T get(String id) {
        Element element = elements.get(id);
        return element != null? (T)element.getEntity():(T)updating.get(id);
    }

    @Override
    public void put(Element element) {
        if (element == null)
            return;
        if (stop){

        }
        String id = element.getIdentity();
        elements.put(id,element);
    }

    @Override
    public void shutdown() {
        stop = true;
        consumer.stop();
    }

    public void removeUpdating(String id){
        updating.remove(id);
    }

    Collection<Element> clearElements(){
        Collection<Element> vlues = elements.values();
        ArrayList<Element>  arrayList = new ArrayList<>(vlues);
        for (Element element:vlues){
            if (element.getEntity()!=null){
                updating.put(element.getIdentity(),element.getEntity());
            }
        }
        elements.clear();
        return arrayList;
    }
}
