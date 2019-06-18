package com.aiwan.server.util;

/**
 * @author dengzebiao
 * 状态编码
 * */
public class StatusCode {
    //登录成功
    public final static int LOGIN = 1;
    //登录失败
    public final static int LOGINFAIL = 2;
    //注销用户成功
    public final static int LOGOUTSUCCESS = 3;
    //移动成功
    public final static int MOVESUCCESS = 4;
    //跳转成功
    public final static int SHIFTSUCCESS = 5;
    //跳转失败
    public final static int SHIFTFAIL = 6;
    //顶替下线
    public final static int INSIST = 7;
    //查看道具列表
    public final static  int VIEWPROPLIST = 8;
    //创建角色成功
    public final static int CREATEROLESUCESS = 9;
    //Message;
    public final static int MESSAGE = 10;
    /** 查看坐骑详细 */
    public final static int VIEWMOUNT = 11;
    /** 查看角色信息 */
    public final static int VIEWROLEMESSAGE = 12;
    /** 注销返回协议码 */
    public final static int REGISTER = 13;
    /** 用户提示信息返回 */
    public final static int MAPMESSAGE = 14;
    /** 查看用户装备 */
    public final static int VIEWEQUIP = 15;
}
