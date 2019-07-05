package com.aiwan.server.ramcache.service.impl;

import com.aiwan.server.ramcache.IEntity;
import com.aiwan.server.ramcache.persist.Element;
import com.aiwan.server.ramcache.persist.EventType;
import com.aiwan.server.ramcache.persist.Persister;
import com.aiwan.server.ramcache.orm.Accessor;
import com.aiwan.server.ramcache.persist.TimingPersister;
import com.aiwan.server.ramcache.service.EntityCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 缓存业务类
 * @author dengzebiao
 * */
public class EntityCaheServiceImpl<PK extends Serializable & Comparable<PK>,T extends IEntity<PK>> implements EntityCacheService<PK,T> {
    private static final Logger logger = LoggerFactory.getLogger(EntityCaheServiceImpl.class);
    /**  初始化标志*/
    private boolean initailzation;
    /**  实体类型*/
    private Class<T> entityClz;
    /**存储器 */
    private Accessor accessor;
    /** 持久化缓存器*/
    private Persister persister;
    /** 实体缓存*/
    private Map<PK,T> cacheMap = new ConcurrentHashMap<PK,T>();
    /** 储存主键的队列*/
    ConcurrentLinkedQueue<PK> linkedQueue = new ConcurrentLinkedQueue<PK>();
    /**  缓存容量*/
    private int num = 0;
    /** 最大缓存容量 */
    private int maxmum;
    public void initailize(String name,Accessor accessor,Class<T> entityClz,int maxmum){
        logger.debug(name+"缓存创建");
        this.persister = new TimingPersister();
        this.persister.initailize(name,accessor);
        this.accessor = accessor;
        this.entityClz = entityClz;
        this.maxmum = maxmum;
        initailzation = true;
    }
    @Override
    public T load(PK id) {
        //若缓存中有
        T current = cacheMap.get(id);
        if (current != null){
            //序列化
            return current;
        }
        //若缓存中无
        current = accessor.load(entityClz,id);
        if (current == null){//持久层没有
            return null;
        }
        //若缓存容量小于最大容量,存到缓存并插入队列
        if (num<maxmum){
            cacheMap.put(id,current);
            linkedQueue.offer(id);
            num++;
            //反序列化
            current.unserialize();
            current.init();
            return current;
        }
        //从队列中取出最后一个，写回
        PK lasfId = linkedQueue.poll();
        cacheMap.remove(lasfId);
        linkedQueue.offer(id);
        cacheMap.put(id,current);
        current.init();
        return current;
    }

    @Override
    public void writeBack(PK id, T entity) {
        //序列化
        entity.serialize();
        Element element = new Element(EventType.SAVE,id,entity,entityClz);
        persister.put(element);
    }

    @Override
    public T remove(PK id) {
        T current = cacheMap.get(id);
        //从缓存移除
        cacheMap.remove(id);
        num--;
        //从队列中移除
        linkedQueue.remove(id);
        //从持久层中移除
        Element element = new Element(EventType.UPDATE,id,null,entityClz);
        persister.put(element);

        return current;

    }


    public boolean isInitailzation() {
        return initailzation;
    }

    public void setInitailzation(boolean initailzation) {
        this.initailzation = initailzation;
    }

    public Class<T> getEntityClz() {
        return entityClz;
    }

    public void setEntityClz(Class<T> entityClz) {
        this.entityClz = entityClz;
    }

    public Accessor getAccessor() {
        return accessor;
    }

    public void setAccessor(Accessor accessor) {
        this.accessor = accessor;
    }

    public Persister getPersister() {
        return persister;
    }

    public void setPersister(Persister persister) {
        this.persister = persister;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getMaxmum() {
        return maxmum;
    }

    public void setMaxmum(int maxmum) {
        this.maxmum = maxmum;
    }
}
