package com.aiwan.server.util;

import com.aiwan.server.base.event.core.EventBusManager;
import com.aiwan.server.base.executor.ScheduleService;
import com.aiwan.server.base.executor.account.IAccountExecutorService;
import com.aiwan.server.base.executor.scene.ISceneExecutorService;
import com.aiwan.server.monster.service.MonsterManager;
import com.aiwan.server.prop.service.PropsManager;
import com.aiwan.server.ramcache.orm.Accessor;
import com.aiwan.server.netty.TaskDispatcher;
import com.aiwan.server.user.backpack.service.BackPackManager;
import com.aiwan.server.user.backpack.service.BackResourceManager;
import com.aiwan.server.user.role.buff.service.BuffManager;
import com.aiwan.server.user.role.buff.service.BuffService;
import com.aiwan.server.user.role.equipment.service.EquipmentManager;
import com.aiwan.server.user.role.equipment.service.EquipmentService;
import com.aiwan.server.user.role.fight.service.IFightService;
import com.aiwan.server.user.role.mount.service.MountManager;
import com.aiwan.server.user.role.mount.service.MountService;
import com.aiwan.server.user.role.player.service.RoleManager;
import com.aiwan.server.user.role.player.service.RoleService;
import com.aiwan.server.user.role.player.service.RoleResourceManager;
import com.aiwan.server.user.role.powerboard.model.PowerRankBoard;
import com.aiwan.server.user.role.powerboard.service.IRankService;
import com.aiwan.server.user.role.task.service.ITaskService;
import com.aiwan.server.user.role.task.service.TaskManager;
import com.aiwan.server.world.dungeon.service.DungeonService;
import com.aiwan.server.world.scenes.service.MapManager;
import com.aiwan.server.world.scenes.service.ScenesService;
import com.aiwan.server.user.account.service.UserManager;
import com.aiwan.server.user.account.service.UserService;
import com.aiwan.server.user.backpack.service.BackpackService;
import com.aiwan.server.user.role.skill.service.SkillManager;
import com.aiwan.server.user.role.skill.service.SkillService;
import com.aiwan.server.user.role.team.manager.TeamManager;
import com.aiwan.server.user.role.team.service.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 单例资源获取工具类
 * */
@Component("getBean")
public class GetBean {
    private static UserService userService;
    private static TaskDispatcher taskDispatcher;
    private static MapManager mapManager;
    private static Accessor accessor;
    private static UserManager userManager;
    private static ScenesService scenesService;
    private static RoleService roleService;
    private static PropsManager propsManager;
    private static BackpackService backpackService;
    private static EquipmentService equipmentService;
    private static MountService mountService;
    private static RoleResourceManager roleResourceManager;
    private static MountManager mountManager;
    private static RoleManager roleManager;
    private static BackPackManager backPackManager;
    private static BackResourceManager backResourceManager;

    /**
     * 技能管理类
     */
    private static SkillManager skillManager;

    /**
     * 技能业务
     */
    private static SkillService skillService;

    /**
     * 用户线程业务分配类
     */
    private static IAccountExecutorService accountExecutorService;

    /**
     * 场景线程业务分配类
     */
    private static ISceneExecutorService sceneExecutorService;

    /**
     * 怪物管理类
     */
    private static MonsterManager monsterManager;

    /**
     * buff业务类
     */
    private static BuffService buffService;

    /**
     * 获取buff管理
     */
    private static BuffManager buffManager;

    /**
     * 定时线程
     */
    private static ScheduleService scheduleService;

    /**
     * 获取战斗业务类
     */
    private static IFightService fightService;

    /**
     * 获取队伍业务
     */
    private static ITeamService teamService;

    /**
     * 获取队伍管理类
     */
    private static TeamManager teamManager;

    /**
     * 副本类
     */
    private static DungeonService dungeonService;

    /**
     * 事件事务管理类
     */
    private static EventBusManager eventBusManager;

    /**
     * 任务管理类
     */
    private static TaskManager taskManager;

    /**
     * 任务业务类
     */
    private static ITaskService taskService;

    /**
     * 装备管理类
     */
    private static EquipmentManager equipmentManager;

    /**
     * 战力计算公式
     */
    private static CombatPowerFormula combatPowerFormula;

    /**
     * 战力排行榜
     */
    private static PowerRankBoard powerRankBoard;

    /**
     * 查看排行榜
     */
    private static IRankService rankService;

    public static IRankService getRankService() {
        return rankService;
    }

    @Autowired
    public void setRankService(IRankService rankService) {
        GetBean.rankService = rankService;
    }

    public static PowerRankBoard getPowerRankBoard() {
        return powerRankBoard;
    }

    @Autowired
    public void setPowerRankBoard(PowerRankBoard powerRankBoard) {
        GetBean.powerRankBoard = powerRankBoard;
    }

    public static CombatPowerFormula getCombatPowerFormula() {
        return combatPowerFormula;
    }

    @Autowired
    public void setCombatPowerFormula(CombatPowerFormula combatPowerFormula) {
        GetBean.combatPowerFormula = combatPowerFormula;
    }

    public static EquipmentManager getEquipmentManager() {
        return equipmentManager;
    }

    @Autowired
    public void setEquipmentManager(EquipmentManager equipmentManager) {
        GetBean.equipmentManager = equipmentManager;
    }

    public static ITaskService getTaskService() {
        return taskService;
    }

    @Autowired
    public void setTaskService(ITaskService taskService) {
        GetBean.taskService = taskService;
    }

    public static TaskManager getTaskManager() {
        return taskManager;
    }

    @Autowired
    public void setTaskManager(TaskManager taskManager) {
        GetBean.taskManager = taskManager;
    }

    public static EventBusManager getEventBusManager() {
        return eventBusManager;
    }

    @Autowired
    public void setEventBusManager(EventBusManager eventBusManager) {
        GetBean.eventBusManager = eventBusManager;
    }

    public static DungeonService getDungeonService() {
        return dungeonService;
    }

    @Autowired
    public void setDungeonService(DungeonService dungeonService) {
        GetBean.dungeonService = dungeonService;
    }

    public static TeamManager getTeamManager() {
        return teamManager;
    }

    @Autowired
    public void setTeamManager(TeamManager teamManager) {
        GetBean.teamManager = teamManager;
    }

    public static ITeamService getTeamService() {
        return teamService;
    }

    @Autowired
    public void setTeamService(ITeamService teamService) {
        GetBean.teamService = teamService;
    }

    public static IFightService getFightService() {
        return fightService;
    }

    @Autowired
    public void setFightService(IFightService fightService) {
        GetBean.fightService = fightService;
    }

    public static ScheduleService getScheduleService() {
        return scheduleService;
    }

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        GetBean.scheduleService = scheduleService;
    }

    public static BuffManager getBuffManager() {
        return buffManager;
    }

    @Autowired
    public void setBuffManager(BuffManager buffManager) {
        GetBean.buffManager = buffManager;
    }

    public static BuffService getBuffService() {
        return buffService;
    }

    @Autowired
    public void setBuffService(BuffService buffService) {
        GetBean.buffService = buffService;
    }

    public static MonsterManager getMonsterManager() {
        return monsterManager;
    }

    @Autowired
    public void setMonsterManager(MonsterManager monsterManager) {
        GetBean.monsterManager = monsterManager;
    }

    public static ISceneExecutorService getSceneExecutorService() {
        return sceneExecutorService;
    }

    @Autowired
    public void setSceneExecutorService(ISceneExecutorService sceneExecutorService) {
        GetBean.sceneExecutorService = sceneExecutorService;
    }

    public static IAccountExecutorService getAccountExecutorService() {
        return accountExecutorService;
    }

    @Autowired
    public void setAccountExecutorService(IAccountExecutorService accountExecutorService) {
        GetBean.accountExecutorService = accountExecutorService;
    }

    public static SkillService getSkillService() {
        return skillService;
    }

    @Autowired
    public void setSkillService(SkillService skillService) {
        GetBean.skillService = skillService;
    }

    public static SkillManager getSkillManager() {
        return skillManager;
    }

    @Autowired
    public void setSkillManager(SkillManager skillManager) {
        GetBean.skillManager = skillManager;
    }

    public static BackResourceManager getBackResourceManager() {
        return backResourceManager;
    }

    @Autowired
    public void setBackResourceManager(BackResourceManager backResourceManager) {
        GetBean.backResourceManager = backResourceManager;
    }

    public static BackPackManager getBackPackManager() {
        return backPackManager;
    }

    @Autowired
    public void setBackPackManager(BackPackManager backPackManager) {
        GetBean.backPackManager = backPackManager;
    }

    public static RoleManager getRoleManager() {
        return roleManager;
    }

    @Autowired
    public void setRoleManager(RoleManager roleManager) {
        GetBean.roleManager = roleManager;
    }

    public static MountManager getMountManager() {
        return mountManager;
    }

    @Autowired
    public void setMountManager(MountManager mountManager) {
        GetBean.mountManager = mountManager;
    }

    public static RoleResourceManager getRoleResourceManager() {
        return roleResourceManager;
    }

    @Autowired
    public void setRoleResourceManager(RoleResourceManager roleResourceManager) {
        GetBean.roleResourceManager = roleResourceManager;
    }



    public static MountService getMountService() {
        return mountService;
    }

    @Autowired
    public void setMountService(MountService mountService) {
        GetBean.mountService = mountService;
    }

    public static BackpackService getBackpackService() {
        return backpackService;
    }

    public static EquipmentService getEquipmentService() {
        return equipmentService;
    }

    @Autowired
    public void setEquipmentService(EquipmentService equipmentService) {
        GetBean.equipmentService = equipmentService;
    }

    @Autowired
    public void setBackpackService(BackpackService backpackService) {
        GetBean.backpackService = backpackService;
    }

    public static TaskDispatcher getTaskDispatcher() {
        return GetBean.taskDispatcher;
    }

    @Autowired
    public  void setTaskDispatcher(TaskDispatcher taskDispatcher) {
        GetBean.taskDispatcher = taskDispatcher;
    }

    public static ScenesService getScenesService() {
        return scenesService;
    }

    @Autowired
    public  void setScenesService(ScenesService scenesService) {
        GetBean.scenesService = scenesService;
    }

    public static UserService getUserService() {
        return GetBean.userService;
    }

    @Autowired
    public  void setRoleService(RoleService roleService) {
        GetBean.roleService = roleService;
    }

    public static RoleService getRoleService() {
        return GetBean.roleService;
    }

    @Autowired
    public  void setUserService(UserService userService) {
        GetBean.userService = userService;
    }

    public static MapManager getMapManager() {
        return mapManager;
    }
    @Autowired
    public void setMapManager(MapManager mapManager) {
        GetBean.mapManager = mapManager;
    }

    public static PropsManager getPropsManager() {
        return propsManager;
    }

    @Autowired
    public void setPropsManager(PropsManager propsManager) {
        GetBean.propsManager = propsManager;
    }

    public static Accessor getAccessor() {
        return GetBean.accessor;
    }

    @Autowired
    public  void setAccessor(Accessor accessor) {
        GetBean.accessor = accessor;
    }

    public static UserManager getUserManager() {
        return GetBean.userManager;
    }
    @Autowired
    public  void setUserManager(UserManager userManager) {
        GetBean.userManager = userManager;
    }

}
