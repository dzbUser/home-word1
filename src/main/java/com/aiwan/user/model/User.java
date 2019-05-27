package com.aiwan.user.model;

import com.aiwan.user.entity.UserEnt;

import java.util.List;

public class User {
    private UserEnt userEnt;

    public User(UserEnt userEnt){
        this.userEnt = userEnt;
    }

    public String getAcountId() {
        return userEnt.getAcountId();
    }


    public String getPassword() {
        return userEnt.getPassword();
    }

    public void setPassword(String password) {
        userEnt.setPassword(password);
    }

    public int getMap() {
        return userEnt.getMap();
    }

    public void setMap(int map) {
        userEnt.setMap(map);
    }

    public int getCurrentX() {
        return userEnt.getCurrentX();
    }

    public void setCurrentX(int currentX) {
        userEnt.setCurrentX(currentX);
    }

    public int getCurrentY() {
        return userEnt.getCurrentY();
    }

    public void setCurrentY(int currentY) {
        userEnt.setCurrentY(currentY);
    }

    public String getHpassword() {
        return userEnt.getHpassword();
    }

    public void setHpassword(String hpassword) {
        userEnt.setHpassword(hpassword);
    }

    public UserEnt getUserEnt() {
        return userEnt;
    }

    public void setUserEnt(UserEnt userEnt) {
        this.userEnt = userEnt;
    }

    public int getMaxRole() {
        return userEnt.getMaxRole();
    }

    public UserBaseInfo getUserBaseInfo() {
        return userEnt.getUserBaseInfo();
    }

    public void setUserBaseInfo(UserBaseInfo userBaseInfo) {
        userEnt.setUserBaseInfo(userBaseInfo);
    }

    public void setMaxRole(int maxRole) {
        userEnt.setMaxRole(maxRole);
    }
}
