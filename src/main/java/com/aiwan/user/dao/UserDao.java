package com.aiwan.user.dao;

import com.aiwan.user.entity.User;
import com.aiwan.user.protocol.CM_UserMessage;

import java.util.List;

public interface UserDao {
    public void insert(User user);

    public void update(User user);

    public void delete(User user);

    public List<User> getUsers() ;

    public User getUserById(int uid) ;

    public User getUserByUsernameAndPassword(CM_UserMessage userMessage);

    public User getUserByUsername(CM_UserMessage userMessage);
}
