package com.aiwan.server.ramcache.orm.impl;

import com.aiwan.server.ramcache.IEntity;
import com.aiwan.server.ramcache.orm.Accessor;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Scope("singleton")
@Repository("accessor")
public class HibernateAccessor extends HibernateDaoSupport implements Accessor {
    @Resource
    public void setMySessionFactory(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Transactional(readOnly=true)
    @Override
    public <PK extends Serializable, T extends IEntity> T load(Class<T> clz, PK id) {
        return getHibernateTemplate().get(clz,id);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public <PK extends Serializable, T extends IEntity> PK sava(Class<T> clz, T entity) {
        return (PK) getHibernateTemplate().save(entity);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public <PK extends Serializable, T extends IEntity> void remove(Class<T> clz, PK id) {
        T entity = load(clz,id);
        if (entity ==null){
            return;
        }
        getHibernateTemplate().delete(entity);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public <PK extends Serializable, T extends IEntity> void update(Class<T> clz, T entity) {
        getHibernateTemplate().update(entity);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public <PK extends Serializable, T extends IEntity> void savaOrUpdate(Class<T> clz, T entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public <PK extends Serializable, T extends IEntity> void batchSava(final List<T> entitys) {
        getHibernateTemplate().execute(new HibernateCallback<Integer>() {

            @Override
            public Integer doInHibernate(Session session) throws HibernateException {
                int size = entitys.size();
                session.beginTransaction();
                for (int i = 0;i<size;i++){
                    T entity = entitys.get(i);
                    session.save(entity);
                    if ((i+1)%5 == 0){
                        session.flush();;
                        session.clear();
                    }
                }
                session.flush();
                session.clear();
                return size;
            }
        });
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public <PK extends Serializable, T extends IEntity> void batchUpdate(final List<T> entitys) {
        getHibernateTemplate().execute(new HibernateCallback<Integer>() {

            @Override
            public Integer doInHibernate(Session session) throws HibernateException {
                int size = entitys.size();
                session.beginTransaction();
                for (int i = 0;i<size;i++){
                    T entity = entitys.get(i);
                    session.update(entity);
                    if ((i+1)%5 == 0){
                        session.flush();;
                        session.clear();
                    }
                }
                session.flush();
                session.clear();
                return size;
            }
        });
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void bulkUpdate(final String queryname) {
        getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) throws HibernateException {
                Query namedQuery = session.getNamedQuery(queryname);
                String queryString = namedQuery.getQueryString();
                return getHibernateTemplate().bulkUpdate(queryString);
            }
        });
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void bulkUpdate(final String queryname, final Object value) {
        getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) throws HibernateException {
                Query namedQuery = session.getNamedQuery(queryname);
                String queryString = namedQuery.getQueryString();
                return getHibernateTemplate().bulkUpdate(queryString,value);
            }
        });
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void bulkUpdate(final String queryname, final Object... params) {
        getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) throws HibernateException {
                Query namedQuery = session.getNamedQuery(queryname);
                String queryString = namedQuery.getQueryString();
                return getHibernateTemplate().bulkUpdate(queryString,params);
            }
        });
    }

    @Transactional(readOnly=true)
    @Override
    public <PK extends Serializable, T extends IEntity> List<T> find(final String query) {
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            @Override
            public List<T> doInHibernate(Session session) throws HibernateException {
                Query namedQuery = session.getNamedQuery(query);
                String queryString = namedQuery.getQueryString();
                return (List<T>) getHibernateTemplate().find(queryString);
            }
        });
    }

    @Transactional(readOnly=true)
    @Override
    public <PK extends Serializable, T extends IEntity> List<T> find(final String query, final Object value) {
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            @Override
            public List<T> doInHibernate(Session session) throws HibernateException {
                Query namedQuery = session.getNamedQuery(query);
                String queryString = namedQuery.getQueryString();
                return (List<T>) getHibernateTemplate().find(queryString,value);
            }
        });
    }

    @Transactional(readOnly=true)
    @Override
    public <PK extends Serializable, T extends IEntity> List<T> find(final String query, final Object... params) {
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            @Override
            public List<T> doInHibernate(Session session) throws HibernateException {
                Query namedQuery = session.getNamedQuery(query);
                String queryString = namedQuery.getQueryString();
                return (List<T>) getHibernateTemplate().find(queryString,params);
            }
        });
    }
}
