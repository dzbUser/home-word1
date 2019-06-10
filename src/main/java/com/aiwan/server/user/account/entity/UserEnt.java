package com.aiwan.server.user.account.entity;

import com.aiwan.server.ramcache.IEntity;
import com.aiwan.server.ramcache.anno.Cache;
import com.aiwan.server.user.account.model.User;
import com.aiwan.server.user.account.model.UserBaseInfo;
import com.aiwan.server.util.JsonUtil;

import javax.persistence.*;


/**
 * @author dengzebiao
 * 用户实体类
 * */
@Cache(maxmum = 300)
@Entity()
@Table(name = "usertable")
public class UserEnt implements IEntity<String> {
    @Id
    @Column(name = "accountId", nullable = false,length = 50)
    private String acountId;
    @Column(name = "password", nullable = false,length = 50)
    private String password;
    @Column(name = "map",nullable = true,length = 4)
    private int map;
    @Column(name = "currentX",nullable = true,length = 4)
    private int currentX;
    @Column(name = "currentY",nullable = true,length = 4)
    private int currentY;
    @Column(name = "hpassword", nullable = false,length = 50)
    private String hpassword;
    @Column(nullable = false)
    private int maxRole;

    /**
     * 用户角色记录
     * */
    @Lob
    @Column()
    private byte[] userBaseData;

    @Transient
    private User user;
    @Transient
    private UserBaseInfo userBaseInfo;

    @Override
    public String getId() {
        return acountId;
    }

    @Override
    public boolean serialize() {
        userBaseData = JsonUtil.object2Bytes(userBaseInfo);
        return true;
    }

    @Override
    public boolean unserialize() {
        userBaseInfo = (UserBaseInfo) JsonUtil.bytes2Object(userBaseData,UserBaseInfo.class);
        return true;
    }

    @Override
    public void init() {

    }

    public String getAcountId() {
        return acountId;
    }

    public void setAcountId(String acountId) {
        this.acountId = acountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMap() {
        return map;
    }

    public void setMap(int map) {
        this.map = map;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public String getHpassword() {
        return hpassword;
    }

    public void setHpassword(String hpassword) {
        this.hpassword = hpassword;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public int getMaxRole() {
        return maxRole;
    }

    public void setMaxRole(int maxRole) {
        this.maxRole = maxRole;
    }

    public UserBaseInfo getUserBaseInfo() {
        return userBaseInfo;
    }

    public void setUserBaseInfo(UserBaseInfo userBaseInfo) {
        this.userBaseInfo = userBaseInfo;
    }

    public void putRole(Long role){
        userBaseInfo.getRoles().add(role);
    }
}
