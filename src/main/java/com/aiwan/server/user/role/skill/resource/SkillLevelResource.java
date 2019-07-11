package com.aiwan.server.user.role.skill.resource;

import com.aiwan.server.publicsystem.annotation.CellMapping;
import com.aiwan.server.publicsystem.annotation.Resource;
import com.aiwan.server.user.role.skill.impact.ImpactType;

import java.util.HashMap;
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
     * 技能攻击万分比
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
     * 升级所需经验
     */
    @CellMapping(name = "experienceDemand")
    private int experienceDemand;

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
     * buffid
     */
    @CellMapping(name = "buffId")
    private int buffId;

    /**
     * 技能效果
     */
    @CellMapping(name = "impact")
    private String impact;

    /**
     * 技能效果列表,对应效果类型id，效果的值
     */
    private Map<ImpactType, Integer> impactMap = new HashMap<>();

    /**
     * 技能效果列表,对应效果类型id，效果的类型注入
     */
    private Map<Integer, ImpactType> impactTypeMap = new HashMap<>();

    /**
     * 初始化
     */
    public void init() {
        if (impact.equals("empty") || impact.equals("")) {
            return;
        }
        String[] impactField = impact.split(" ");
        for (int i = 0; i < impactField.length; i++) {
            String[] impactValue = impactField[i].split(":");
            ImpactType impactType = ImpactType.getImpactType(Integer.parseInt(impactValue[0]));
            impactMap.put(impactType, Integer.parseInt(impactValue[1]));
            impactTypeMap.put(Integer.parseInt(impactValue[0]), impactType);
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

    public int getExperienceDemand() {
        return experienceDemand;
    }

    public void setExperienceDemand(int experienceDemand) {
        this.experienceDemand = experienceDemand;
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

    public int getBuffId() {
        return buffId;
    }

    public void setBuffId(int buffId) {
        this.buffId = buffId;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public Map<ImpactType, Integer> getImpactMap() {
        return impactMap;
    }

    public void setImpactMap(Map<ImpactType, Integer> impactMap) {
        this.impactMap = impactMap;
    }

    public Map<Integer, ImpactType> getImpactTypeMap() {
        return impactTypeMap;
    }

    public void setImpactTypeMap(Map<Integer, ImpactType> impactTypeMap) {
        this.impactTypeMap = impactTypeMap;
    }
}
