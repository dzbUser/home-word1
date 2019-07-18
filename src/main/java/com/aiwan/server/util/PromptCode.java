package com.aiwan.server.util;

import java.security.PublicKey;

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

    /**
     * 角色姓名长度过长
     */
    public final static int NAME_EXCEED_MAXLENGTH = 34;

    /**
     * Buff丹药一次只能使用一个
     */
    public final static int USER_ONLY_ONE = 35;

    /**
     * 施法错误，位置超出技能槽范围
     */
    public final static int EXCEED_SKILLBAR = 36;

    /**
     * 施法错误，该位置没有技能
     */
    public final static int NOSKILL_INSKILLBAR = 37;

    /**
     * prompt	38	施法错误，找不到施法单位
     */
    public final static int NOFIND_MAGICUNIT = 38;

    /**
     * prompt	39	施法错误，找不到施法目标或者施法目标已死亡
     */
    public final static int NOFIND_MAGICAIM = 39;

    /**
     * prompt	40	施法错误，超出施法范围
     */
    public final static int EXCEED_MAGICRANGE = 40;

    /**
     * prompt	41	施法错误，不具备施法条件
     */
    public final static int BAN_USE_SKILL = 41;

    /**
     * prompt	42	施法成功
     */
    public final static int USE_SKILL_SUCCESS = 42;

    /**
     * prompt	43	您已死亡，将在复活点复活
     */
    public final static int ROLE_DEATH = 43;

    /**
     * 施法目标已经死亡
     */
    public final static int CASTING_TARGET_DEATH = 44;

    /**
     * 您已经在队伍中
     */
    public final static int IN_TEAM = 45;

    /**
     * 队伍创建成功
     */
    public final static int CREATE_TEAM_SUCCESS = 46;


    /**
     * 没有该队伍
     */
    public final static int NO_TEAM = 47;

    /**
     * 已经在队伍中
     */
    public final static int HAVE_IN_TEAM = 48;

    /**
     * 队伍已满
     */
    public final static int TEAM_FULL = 49;

    /**
     * 申请加入队伍成功
     */
    public final static int APPLY_JOIN_SUCCESS = 50;

    /**
     * 未在队伍中
     */
    public final static int NO_IN_TEAM = 51;

    /**
     * 离开队伍成功
     */
    public final static int LEAVE_SUCCESS = 52;

    /**
     * 队长接到通知
     */
    public final static int MEMBER_LEAVE_TEAM = 53;

    /**
     * 不是队长
     */
    public final static int NO_TEAM_LEADER = 54;

    /**
     * 加入成功
     */
    public final static int JOIN_TEAM_SUCCESS = 55;

    /**
     * 不可以把自己踢出队伍
     */
    public final static int NO_KICK_OUT_YOUSELF = 56;

    /**
     * 踢出对象不在队伍中
     */
    public final static int NO_IN_YOURTEAM = 57;

    /**
     * 踢出成功
     */
    public final static int KICK_OUT_SUCCESS = 58;

    /**
     * 通知成为队长
     */
    public final static int BECAME_LEADER = 59;


//
//    /**
//     * 申请加入队伍成功
//     */
//    public final static int APPLY_JOIN_SUCCESS = 50;


}
