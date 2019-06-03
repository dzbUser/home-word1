package com.aiwan.server.util;

/**
 * @author dengzebiao
 * 状态编码
 * */
public class StatusCode {
    public final static int REGIST = 1;
    //登录成功
    public final static int LOGINSUCCESS = 2;
    //注册成功
    public final static int REGISTSUCCESS = 3;
    //登录失败
    public final static int LOGINFAIL = 4;
    //注册失败
    public final static int REGISTDAIL = 5;
    //注销用户成功
    public final static int LOGOUTSUCCESS = 6;
    //注销用户失败
    public final static int LOGOUTFAIL = 7;
    //移动成功
    public final static int MOVESUCCESS = 8;
    //移动失败
    public final static int MOVEFAIL = 9;
    //跳转成功
    public final static int SHIFTSUCCESS = 10;
    //跳转失败
    public final static int SHIFTFAIL = 11;
    //顶替下线
    public final static int INSIST = 12;
    //返回个人信息成功
    public final static int GETMESSAGESUCCESS = 13;
    //返回个人信息失败
    public final static int GETUSERMESSAGEFAIL = 14;
    //创建角色成功
    public final static int CREATEROLESUCESS = 15;
    //创建角色失败
    public final static int CREATEROLEFAIL = 16;
    //获取角色信息
    public final static int ROLEMESSAGE = 17;
    //未登录错误
    public final static int NOLOGIN = 18;
}
