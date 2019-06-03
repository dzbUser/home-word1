package com.aiwan.server.ramcache.persist;

import com.aiwan.server.ramcache.orm.Accessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * @author dengzebiao
 * 持久化消费者队列
 * */
public class TimingConsumer implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(TimingPersister.class);
    /** 消费者线程名字*/
    private String name;
    /** 持久层存储器*/
    private Accessor accessor;
    /**实体持久化缓存*/
    private TimingPersister ower;
    /** 状态 */
    private volatile boolean stoped;
    /** 当前消费者自身 */
    private Thread me;
    @Override
    public void run() {
        while (!stoped){
            synchronized (me){
                Collection<Element> elements = ower.clearElements();
                persist(elements);
                try {
                    me.wait(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public TimingConsumer(String name,Accessor accessor,TimingPersister ower){
        this.name = name;
        this.accessor = accessor;
        this.ower = ower;
        this.me = new Thread(this,"持久化["+name+"：定时]");
        me.setDaemon(true);
        me.start();
    }

    /**
     * 持久化
     * */
    private void persist(Collection<Element> elements){
        for (Element element:elements){
            Class clz = element.getClz();
            switch (element.getType()){
                case SAVE:
                    if (element.getEntity().serialize()){
                        accessor.savaOrUpdate(clz,element.getEntity());
                    }else {
                        logger.warn("");
                    }
                    break;
                case REMOVE:
                    accessor.remove(clz,element.getId());
                    break;
                case UPDATE:
                    if (element.getEntity().serialize()){
                        accessor.savaOrUpdate(clz,element.getEntity());
                    }
                    break;
            }
            //remove
            ower.removeUpdating(element.getIdentity());
        }
    }

    void stop(){
        stoped = true;
    }
}
