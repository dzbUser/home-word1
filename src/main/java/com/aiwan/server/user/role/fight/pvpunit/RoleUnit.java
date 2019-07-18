package com.aiwan.server.user.role.fight.pvpunit;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneDelayCommand;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.world.scenes.model.Position;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.fight.command.RoleReviveCommand;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.skill.model.Skill;
import com.aiwan.server.util.AttributeUtil;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.PromptCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 角色战斗成员
 *
 * @author dengzebiao
 */
public class RoleUnit extends BaseUnit {

    /**
     * 复活时间
     */
    private final static long REVIVE_TIME = 5000L;

    Logger logger = LoggerFactory.getLogger(RoleUnit.class);

    /**
     * 角色基础信息
     */
    private String accountId;


    /**
     * cd列表
     */
    private Map<Integer, Long> skillCD = new HashMap<>();

    /**
     * 复活命令
     */
    private AbstractSceneDelayCommand reviveCommand;

    /**
     * 创建对象
     */
    public static RoleUnit valueOf(Role role) {
        RoleUnit roleUnit = new RoleUnit();
        //初始化角色基础信息
        roleUnit.setId(role.getId());
        //设置基础信息
        roleUnit.setAccountId(role.getAccountId());
        roleUnit.setLevel(role.getLevel());
        roleUnit.setName(role.getName());
        roleUnit.setMonster(false);
        //设置位置信息
        roleUnit.setPosition(Position.valueOf(role.getX(), role.getY()));
        roleUnit.setMapId(role.getMap());
        //复制用户属性
        roleUnit.setRoleAttribute(role.getAttribute().getPureAttribute());
        roleUnit.setHp(roleUnit.getMaxHp());
        roleUnit.setMp(roleUnit.getMaxMp());
        return roleUnit;
    }


    /**
     * 是否可以释放技能
     */
    public boolean isCanUseSkill(Skill skill) {

        //获取cd时间
        Long cd = skillCD.get(skill.getSkillId());
        //获取当前时间
        Long now = System.currentTimeMillis();
        if (cd != null && now < cd) {
            //处于cd状态
            logger.debug("角色{}施法技能{}失败，处于CD状态", getId(), skill.getSkillId());
            return false;
        }
        if (getMp() < skill.getSkillLevelResource().getMagicDemand()) {
            //魔法值不够
            logger.debug("角色{}施法技能{}失败，魔法值不够", getId(), skill.getSkillId());
            return false;
        }
        skillCD.put(skill.getSkillId(), now + skill.getSkillLevelResource().getCd());
        setMp(getMp() - skill.getSkillLevelResource().getMagicDemand());
        return true;
    }


    /**
     * 触发死亡机制
     */
    @Override
    protected void death(Long attackId) {
        setDeath(true);
        //复活点复活,暂时为立即复活，发送提示
        GetBean.getMapManager().sendMessageToUsers(getMapId());
        SessionManager.sendPromptMessage(this.getAccountId(), PromptCode.ROLE_DEATH,"");
        GetBean.getSceneExecutorService().submit(new RoleReviveCommand(REVIVE_TIME, getAccountId(), this.getMapId(),this));
    }

    /**
     * 转移状态,用于地图跳转时
     */
    public void transferStatus(RoleUnit roleUnit) {
        setSkillCD(roleUnit.getSkillCD());
        setHp(roleUnit.getHp());
        setMp(roleUnit.getMp());
    }

    /**
     * 根据角色重置状态（用于角色升级）
     *
     * @param role 角色
     */
    public void resetStatus(Role role) {
        setLevel(role.getLevel());
        setHp(getMaxHp());
        setMp(getMaxMp());
    }

    /**
     * 重置角色状态（用于复活时的重置）
     */
    @Override
    public void resetStatus() {
        //重置状态
        super.resetStatus();
        skillCD = new HashMap<>(16);
    }

    /**
     * 设置角色属性
     *
     * @param roleAttribute 角色属性
     */
    public void setRoleAttribute(Map<AttributeType, AttributeElement> roleAttribute) {
        //设置属性（复制）
        setUnitAttribute(AttributeUtil.getCopyAttributeMap(roleAttribute));
        //重新计算最终属性
        calculateFinalAttribute();
    }


    public Map<Integer, Long> getSkillCD() {
        return skillCD;
    }

    public void setSkillCD(Map<Integer, Long> skillCD) {
        this.skillCD = skillCD;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public static long getReviveTime() {
        return REVIVE_TIME;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }


    public AbstractSceneDelayCommand getReviveCommand() {
        return reviveCommand;
    }

    public void setReviveCommand(AbstractSceneDelayCommand reviveCommand) {
        this.reviveCommand = reviveCommand;
    }
}
