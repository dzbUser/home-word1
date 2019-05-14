package com.aiwan.model;

import javax.persistence.*;

@Entity
@Table(name = "usertable")
public class User {
    private int uid;
    private String username;
    private String password;
    private short map;
    private short currentX;
    private short currentY;

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
    public short getMap() {
        return map;
    }

    public void setMap(short map) {
        this.map = map;
    }

    @Column(name = "currentX",nullable = true,length = 4)
    public short getCurrentX() {
        return currentX;
    }

    public void setCurrentX(short currentX) {
        this.currentX = currentX;
    }

    @Column(name = "currentY",nullable = true,length = 4)
    public short getCurrentY() {
        return currentY;
    }

    public void setCurrentY(short currentY) {
        this.currentY = currentY;
    }
}
