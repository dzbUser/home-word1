package com.aiwan.dao;

import com.aiwan.model.User;
import com.aiwan.publicsystem.CM_UserMessage;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("userDao")
public class UserDaoImpl extends HibernateDaoSupport implements UserDao{
    @Resource
    public void setMySessionFactory(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

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
    public User getUserByUsernameAndPassword(CM_UserMessage userMessage) {
        List users = getHibernateTemplate().find("from User where username = ? and password = ?", userMessage.getUsername(),userMessage.getPassword());
        return (User) users.get(0);
    }
}
