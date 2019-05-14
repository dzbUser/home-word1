package com.aiwan.dao;

import com.aiwan.model.User;
import com.aiwan.publicsystem.CM_UserMessage;

import java.util.List;

public interface UserDao {
    public void insert(User user);

    public void update(User user);

    public void delete(User user);

    public List<User> getUsers() ;

    public User getUserById(int uid) ;

    public User getUserByUsernameAndPassword(CM_UserMessage userMessage);
}
