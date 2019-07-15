package com.aiwan.server.user.role.skill.resource;

import com.aiwan.server.publicsystem.annotation.CellMapping;
import com.aiwan.server.publicsystem.annotation.Resource;
import com.aiwan.server.user.role.skill.impact.ImpactType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Resource("staticresource/skillLevel.xls")
public class SkillLevelResource {

    /**
     * 唯一id，技能id_等级
     */
    @CellMapping(name = "id")
    private String id;


    /**
     * 技能id
     */
    @CellMapping(name = "skillId")
    private int skillId;

    /**
     * 技能等级
     */
    @CellMapping(name = "skillLevel")
    private int skillLevel;

    /**
     * 技能攻击（暂时没用到）
     */
    @CellMapping(name = "skillAttack")
    private int skillAttack;

    /**
     * 技能升级人物等级需求
     */
    @CellMapping(name = "roleLevelDemand")
    private int roleLevelDemand;

    /**
     * 消耗魔法值
     */
    @CellMapping(name = "magicDemand")
    private int magicDemand;


    /**
     * 技能cd
     */
    @CellMapping(name = "cd")
    private int cd;

    /**
     * 攻击目标
     */
    @CellMapping(name = "num")
    private int num;

    /**
     * 释放距离
     */
    @CellMapping(name = "distance")
    private int distance;


    /**
     * 技能效果
     */
    @CellMapping(name = "impact")
    private String impact;


    /**
     * 技能效果执行顺序表
     */
    private List<ImpactAnalysis> impactList = new ArrayList<>();

    /**
     * 初始化
     */
    public void init() {
        if (impact.equals("")) {
            return;
        }
        //初始化技能效果
        String[] impactField = impact.split(" ");
        for (int i = 0; i < impactField.length; i++) {
            impactList.add(ImpactAnalysis.valueOf(impactField[i].split(":")));
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public int getSkillAttack() {
        return skillAttack;
    }

    public void setSkillAttack(int skillAttack) {
        this.skillAttack = skillAttack;
    }

    public int getRoleLevelDemand() {
        return roleLevelDemand;
    }

    public void setRoleLevelDemand(int roleLevelDemand) {
        this.roleLevelDemand = roleLevelDemand;
    }

    public int getMagicDemand() {
        return magicDemand;
    }

    public void setMagicDemand(int magicDemand) {
        this.magicDemand = magicDemand;
    }

    public int getCd() {
        return cd;
    }

    public void setCd(int cd) {
        this.cd = cd;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }


    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }


    public List<ImpactAnalysis> getImpactList() {
        return impactList;
    }

    public void setImpactList(List<ImpactAnalysis> impactList) {
        this.impactList = impactList;
    }
}
