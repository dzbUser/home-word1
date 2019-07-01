package com.aiwan.server.util;

/**
 * 提示信息编码
 */
public class PromptCode {

    /**
     * 您的背包是空的
     */
    public final static int BACKEMPTY = 0;

    /*** 不可以使用*/
    public final static int UNAVAILABLE = 1;


    /***  使用成功*/
    public final static int USERSUCCESS = 3;

    /***  您的等级未达到装备要求等级*/
    public final static int NOREQUIREMENTINLEVEL = 4;

    /***  没有该道具*/
    public final static int NOPROP = 5;

    /***  添加成功*/
    public final static int ADDSUCCESS = 6;

    /***  您的角色已达到最高级*/
    public final static int ROLEACHIEVEMAXLEVEL = 7;

    /***  您的坐骑已经是最高级*/
    public final static int MOUNTACHIEVEMAXLEVEL = 8;

    /***  提升成功*/
    public final static int PROMOTESUCCESS = 9;

    /***  没有该地图*/
    public final static int MAPNOEXIST = 10;

    /***  您角色数已上限*/
    public final static int ACHIEVEROLEMAXNUM = 11;

    /**
     * 背包已满
     */
    public final static int BACKFULL = 12;

    /**
     * 你背包中没有升阶丹
     */
    public final static int NOMOUNTDAN = 13;

    /**
     * 您该位置没有装备
     */
    public final static int EQUIPEMPTY = 14;

    /**
     * 卸装备成功
     */
    public final static int UNLOADEQUIPSUCCESS = 15;

    /**
     * 没有该位置的装备
     */
    public final static int UNLOADEQUIPFAIL = 16;


    /**
     * 该背包位置为空
     */
    public final static int EMPTYINBACK = 18;

    /**
     * 此处不可移动
     */
    public final static int MOVEFAIL = 19;

    /**
     * 丢弃成功
     */
    public final static int DROPSUCCESS = 20;

    /**
     * 丢弃失败
     */
    public final static int DROPFAIL = 21;

    /**
     * 该位置的道具的数量不足
     */
    public final static int PROPNUMINSUFFICIENT = 22;

    /**
     * 该道具不是装备
     */
    public final static int NOEQUIPMENT = 23;

    /**
     * 您所输入的背包位置不符合规范
     */
    public final static int INREGUARPOSITION = 24;

    /**
     * 没有该技能
     */
    public final static int NOSKILL = 25;

    /**
     * 您的等级未达到要求
     */
    public final static int NOTREACHEDLEVELDEMAND = 26;

    /**
     * 您已学过该技能
     */
    public final static int HAVALEARN = 27;

    /**
     * 学习技能成功
     */
    public final static int LEARNSKILLSUCCESS = 28;

    /**
     * 您还未学习该技能
     */
    public final static int NOTLEARNSKILL = 29;

    /**
     * 您未达到升级技能的条件
     */
    public final static int NOTREARCHDEMAND = 30;

    /**
     * 达到最高等级
     */
    public final static int REACHMAXLEVEL = 31;

    /**
     * 技能升级成功
     */
    public final static int UPGRADESKILLSUCE = 32;

    /**
     * 技能移动成功
     */
    public final static int MOVESKILLSUCCESS = 33;
}
