package com.aiwan.user.entity;

import javax.persistence.*;
/**
 * 用户表实体类
 * */
@Entity
@Table(name = "usertable")
public class User {
    private int uid;
    private String username;
    private String password;
    private int map;
    private int currentX;
    private int currentY;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", unique = true, nullable = false)
    private int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }

    @Column(name = "username", nullable = false,length = 50)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password",nullable = false,length = 50)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "map",nullable = true,length = 4)
    public int getMap() {
        return map;
    }

    public void setMap(int map) {
        this.map = map;
    }

    @Column(name = "currentX",nullable = true,length = 4)
    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    @Column(name = "currentY",nullable = true,length = 4)
    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }
}
