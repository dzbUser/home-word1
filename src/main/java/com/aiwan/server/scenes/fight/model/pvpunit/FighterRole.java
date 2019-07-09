package com.aiwan.server.scenes.fight.model.pvpunit;

import com.aiwan.server.scenes.mapresource.MapResource;
import com.aiwan.server.scenes.model.Position;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.skill.model.AbstractSkill;
import com.aiwan.server.util.GetBean;
import java.util.HashMap;
import java.util.Map;

/**
 * 战斗成员
 *
 * @author dengzebiao
 */
public class FighterRole extends BaseUnit {

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
        for (Map.Entry<AttributeType, AttributeElement> entry : role.getAttribute().getFinalAttribute().entrySet()) {
            pureAttributeMap.put(entry.getKey(), AttributeElement.valueOf(entry.getValue().getAttributeType(), entry.getValue().getValue()));
        }
        fighterRole.setRoleAttribute(pureAttributeMap);
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
    public boolean isCanUseSkill(AbstractSkill skill) {

        //获取cd时间
        Long cd = skillCD.get(skill.getSkillId());
        //获取当前时间
        Long now = System.currentTimeMillis();
        if (cd != null && now < cd) {
            //存入cd到的时间
            return false;
        }
        if (getMp() < skill.getSkillLevelResouece().getMagicDemand()) {
            //魔法值不够
            return false;
        }
        skillCD.put(skill.getSkillId(), now + skill.getSkillLevelResouece().getCd());
        setMp(getMp() - skill.getSkillLevelResouece().getMagicDemand());
        return true;
    }


    /**
     * 触发死亡机制
     */
    @Override
    protected void death(Long attackId) {
        setDeath(true);
        //清除状态
        skillCD = new HashMap<>();
        setBuff(new HashMap<>());
        //复活点复活,暂时为立即服务
        // TODO: 2019/7/9 修改为规定时间后复活
        MapResource mapResource = GetBean.getMapManager().getMapResource(getMapId());
        //修改角色位置
        Role role = GetBean.getRoleManager().load(getId());
        role.setX(mapResource.getOriginX());
        role.setY(mapResource.getOriginY());
        GetBean.getRoleManager().save(role);
        setPosition(Position.valueOf(mapResource.getOriginX(), mapResource.getOriginY()));
        setDeath(false);
    }

    /**
     * 重置技能状态
     */
    public void reset(FighterRole fighterRole) {
        // TODO: 2019/7/9  空指针不报错
        setSkillCD(fighterRole.getSkillCD());
    }

    public Map<AttributeType, AttributeElement> getRoleAttribute() {
        return rolePureAttribute;
    }

    public void setRoleAttribute(Map<AttributeType, AttributeElement> roleAttribute) {
        this.rolePureAttribute = roleAttribute;
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

}
