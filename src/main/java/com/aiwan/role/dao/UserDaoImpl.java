package com.aiwan.role.dao;

import com.aiwan.role.entity.User;
import com.aiwan.role.protocol.CM_UserMessage;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Scope
@Repository("userDao")
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {
    @Resource
    public void setMySessionFactory(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void insert(User user) {
        getHibernateTemplate().save(user);
    }

    public void update(User user) {
        getHibernateTemplate().update(user);
    }

    public void delete(User user) {
        getHibernateTemplate().delete(user);
    }
    public List<User> getUsers() {
        //from 类名（Article），注意大小写
        return (List<User>) getHibernateTemplate().find("from User");
    }

    public User getUserById(int uid) {
        List users = getHibernateTemplate().find("from User where uid = ?", uid);
        return (User) users.get(0);
    }

    @Override
    @Transactional(readOnly=true)
    public User getUserByUsernameAndPassword(CM_UserMessage userMessage) {
        List users = getHibernateTemplate().find("from User where username = ? and password = ?", userMessage.getUsername(),userMessage.getPassword());
        if (users.size()==0){
            return null;
        }
        return (User) users.get(0);
    }

    @Override
    @Transactional(readOnly=true)
    public User getUserByUsername(CM_UserMessage userMessage) {
        List users = getHibernateTemplate().find("from User where username = ?", userMessage.getUsername());
        if(users.size() == 0){
            return null;
        }
        return (User) users.get(0);
    }
}
