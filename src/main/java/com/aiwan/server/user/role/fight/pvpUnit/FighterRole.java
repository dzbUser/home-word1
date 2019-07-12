package com.aiwan.server.user.role.fight.pvpUnit;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneDelayCommand;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.scenes.mapresource.MapResource;
import com.aiwan.server.scenes.model.Position;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.fight.command.RoleReviveCommand;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.skill.model.Skill;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.PromptCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 战斗成员
 *
 * @author dengzebiao
 */
public class FighterRole extends BaseUnit {

    /**
     * 复活时间
     */
    private final static long REVIVE_TIME = 5000L;

    Logger logger = LoggerFactory.getLogger(FighterRole.class);

    /**
     * 角色基础信息
     */
    private String accountId;


    /**
     * 角色纯净属性
     */
    private Map<AttributeType, AttributeElement> rolePureAttribute;

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
    public static FighterRole valueOf(Role role) {
        FighterRole fighterRole = new FighterRole();
        //初始化角色基础信息
        fighterRole.setId(role.getId());
        //设置基础信息
        fighterRole.setAccountId(role.getAccountId());
        fighterRole.setLevel(role.getLevel());
        fighterRole.setName(role.getName());
        fighterRole.setMonster(false);
        //设置位置信息
        fighterRole.setPosition(Position.valueOf(role.getX(), role.getY()));
        fighterRole.setMapId(role.getMap());
        Map<AttributeType, AttributeElement> pureAttributeMap = new HashMap<>();
        //复制用户属性
        fighterRole.setRoleAttribute(role.getAttribute().getFinalAttribute());
        fighterRole.setHp(fighterRole.getMaxHp());
        fighterRole.setMp(fighterRole.getMaxMp());
        return fighterRole;
    }

    /**
     * 计算战斗最终属性
     */
    private void calculateFinalAttribute() {
        //计算最终属性
        for (Map.Entry<AttributeType, AttributeElement> entry : rolePureAttribute.entrySet()) {
            //获取最终属性值
            long value = entry.getKey().calculate(entry.getValue(), rolePureAttribute);
            getFinalAttribute().put(entry.getKey(), AttributeElement.valueOf(entry.getKey(), value));
        }
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
            //存入cd到的时间
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
        SessionManager.sendPromptMessage(this.getAccountId(), PromptCode.ROLE_DEATH,"");
        GetBean.getSceneExecutorService().submit(new RoleReviveCommand(REVIVE_TIME, getAccountId(), this.getMapId(),this));
    }

    /**
     * 转移状态
     */
    public void transferStatus(FighterRole fighterRole) {
        // TODO: 2019/7/9  空指针不报错
        setSkillCD(fighterRole.getSkillCD());
        setHp(fighterRole.getHp());
        setMp(fighterRole.getMp());
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
     * 重置角色状态
     */
    public void resetStatus() {
        //重置状态
        skillCD = new HashMap<>();
        setBuff(new HashMap<>());
        setHp(getMaxHp());
        setMp(getMaxMp());
    }

    public Map<AttributeType, AttributeElement> getRoleAttribute() {
        return rolePureAttribute;
    }

    public void setRoleAttribute(Map<AttributeType, AttributeElement> roleAttribute) {
        this.rolePureAttribute = new HashMap<>(16);
        this.rolePureAttribute.putAll(roleAttribute);
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

    public Map<AttributeType, AttributeElement> getRolePureAttribute() {
        return rolePureAttribute;
    }

    public void setRolePureAttribute(Map<AttributeType, AttributeElement> rolePureAttribute) {
        this.rolePureAttribute = rolePureAttribute;
    }

    public AbstractSceneDelayCommand getReviveCommand() {
        return reviveCommand;
    }

    public void setReviveCommand(AbstractSceneDelayCommand reviveCommand) {
        this.reviveCommand = reviveCommand;
    }
}
