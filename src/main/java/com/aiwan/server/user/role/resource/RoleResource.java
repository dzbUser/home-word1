package com.aiwan.server.user.role.resource;

import com.aiwan.server.publicsystem.annotation.CellMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.6.10
 * 用来存职业，性别，装备，坐骑对应名字
 * */
public class RoleResource {
    /** 职业对应信息 */
    @CellMapping(name = "job")
    private String job;

    /** 性别对应信息 */
    @CellMapping(name = "sex")
    private String sex;

    /** 装备对应信息 */
    @CellMapping(name = "equipment")
    private String equipment;

    /** 坐骑对应信息 */
    @CellMapping(name = "mountName")
    private String mountName;

    /** 最大人物等级 */
    @CellMapping(name = "maxLevel")
    private int maxLevel;

    /** 最大坐骑等级 */
    @CellMapping(name = "maxMountLevel")
    private int maxMountLevel;

    /** 人物属性对应信息 */
    @CellMapping(name = "roleAttribute")
    private String roleAttribute;

    /** 坐骑属性对应信息 */
    @CellMapping(name = "mountAttribute")
    private String mountAttribute;

    /** 职业名转换类 */
    private Map<Integer,String> jobs = new HashMap<>();

    /** 装备名转换类 */
    private Map<Integer,String> equipments = new HashMap<>();

    /** 性别名转换类 */
    private Map<Integer,String> sexs = new HashMap<>();

    /** 坐骑转换类 */
    private Map<Integer,String> mountNames = new HashMap<>();

    /** 人物属性转换数组 */
    private String[] roleAttributes;

    /** 坐骑属性转换数组 */
    private String[] mountAttributes;

    /** 初始化 */
    public void init(){
        //初始化
        initSex();initEquip();initJob();initMount();
        //初始化属性
        roleAttributes = roleAttribute.split(" ");
        mountAttributes = mountAttribute.split(" ");
    }

    /** 职业初始化 */
    private void initJob(){
        String[] jobArray = job.split(" ");
        for (String element:jobArray){
            String[] jobMap = element.split(":");
            jobs.put(Integer.parseInt(jobMap[0]),jobMap[1]);
        }
    }

    /** 性别初始化 */
    private void initSex(){
        String[] sexArray = sex.split(" ");
        for (String element:sexArray){
            String[] sexMap = element.split(":");
            sexs.put(Integer.parseInt(sexMap[0]),sexMap[1]);
        }
    }

    /** 装备初始化 */
    private void initEquip(){
        String[] equipArray = equipment.split(" ");
        for (String element:equipArray){
            String[] equipMap = element.split(":");
            equipments.put(Integer.parseInt(equipMap[0]),equipMap[1]);
        }
    }

    /** 坐骑初始化 */
    private void initMount(){
        String[] mountArray = mountName.split(" ");
        for (String element:mountArray){
            String[] mountMap = element.split(":");
            mountNames.put(Integer.parseInt(mountMap[0]),mountMap[1]);
        }
    }

    public String getJob() {
        return job;
    }

    public RoleResource setJob(String job) {
        this.job = job;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public RoleResource setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getEquipment() {
        return equipment;
    }

    public RoleResource setEquipment(String equipment) {
        this.equipment = equipment;
        return this;
    }

    public String getMountName() {
        return mountName;
    }

    public RoleResource setMountName(String mountName) {
        this.mountName = mountName;
        return this;
    }

    public Map<Integer, String> getJobs() {
        return jobs;
    }

    public RoleResource setJobs(Map<Integer, String> jobs) {
        this.jobs = jobs;
        return this;
    }

    public Map<Integer, String> getEquipments() {
        return equipments;
    }

    public RoleResource setEquipments(Map<Integer, String> equipments) {
        this.equipments = equipments;
        return this;
    }

    public Map<Integer, String> getSexs() {
        return sexs;
    }

    public RoleResource setSexs(Map<Integer, String> sexs) {
        this.sexs = sexs;
        return this;
    }

    public Map<Integer, String> getMountNames() {
        return mountNames;
    }

    public RoleResource setMountNames(Map<Integer, String> mountNames) {
        this.mountNames = mountNames;
        return this;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public RoleResource setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
        return this;
    }

    public int getMaxMountLevel() {
        return maxMountLevel;
    }

    public RoleResource setMaxMountLevel(int maxMountLevel) {
        this.maxMountLevel = maxMountLevel;
        return this;
    }

    public String[] getRoleAttributes() {
        return roleAttributes;
    }

    public RoleResource setRoleAttributes(String[] roleAttributes) {
        this.roleAttributes = roleAttributes;
        return this;
    }

    public String[] getMountAttributes() {
        return mountAttributes;
    }

    public RoleResource setMountAttributes(String[] mountAttributes) {
        this.mountAttributes = mountAttributes;
        return this;
    }
}
