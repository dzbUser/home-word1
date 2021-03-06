package com.aiwan.server.user.role.fight.pvpunit;

import com.aiwan.server.world.scenes.model.Position;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.attributes.model.ImmutableAttributeElement;
import com.aiwan.server.user.role.attributes.model.impl.FightAttributeModule;
import com.aiwan.server.user.role.attributes.model.impl.FightUnitAttribute;
import com.aiwan.server.user.role.buff.effect.AbstractFightBuff;
import com.aiwan.server.user.role.buff.effect.impl.AttributeFightBuff;
import com.aiwan.server.user.role.buff.resource.EffectResource;
import com.aiwan.server.user.role.buff.resource.bean.AttributeFightBuffBean;
import com.aiwan.server.util.GetBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 战斗基础单位
 *
 * @author dengzebiao
 * @since 2019.7.9
 */
public abstract class BaseUnit {

    Logger logger = LoggerFactory.getLogger(BaseUnit.class);

    /**
     * 唯一id
     */
    private Long id;

    /**
     * 血量
     */
    private long hp;

    /**
     * 魔法值
     */
    private long mp;

    /**
     * 位置
     */
    private Position position;

    /**
     * 是否死亡状态
     */
    private boolean isDeath = false;

    /**
     * 是否是怪物
     */
    private boolean isMonster;

    /**
     * 角色所属地图对象id
     */
    private int sceneId;

    /**
     * 角色所属地图对象资源id
     */
    private int mapId;

    /**
     * 名字
     */
    private String name;

    /**
     * 等级
     */
    private int level;

    public int getKey() {
        if (sceneId == 0) {
            return mapId;
        }
        return sceneId;
    }


    /**
     * 战斗单位属性
     */
    private FightUnitAttribute fightUnitAttribute = new FightUnitAttribute();


    /**
     * buff列表
     */
    private Map<Integer, AbstractFightBuff> buff = new HashMap<>();

    /**
     * 清除状态
     */
    public void resetStatus() {
        //重置状态
        setBuff(new HashMap<>(16));
        this.fightUnitAttribute.updateModule(FightAttributeModule.BUFF_MODULE, new HashMap<>(16));
        setHp(getMaxHp());
        setMp(getMaxMp());
        calculateFinalAttribute();
    }

    /**
     * 计算战斗最终属性
     */
    protected void calculateFinalAttribute() {
        fightUnitAttribute.calculate();
    }

    /**
     * 添加buff属性
     *
     * @param addAttribute 所添加的属性
     */
    private void putBuffAttribute(Map<AttributeType, AttributeElement> addAttribute) {
        fightUnitAttribute.putBuffAttribute(addAttribute, FightAttributeModule.BUFF_MODULE);
    }

    /**
     * 去除buff属性
     *
     * @param addAttribute 所去除的属性
     */
    public void removeBuffAttribute(Map<AttributeType, AttributeElement> addAttribute) {
        fightUnitAttribute.removeBuffAttribute(addAttribute, FightAttributeModule.BUFF_MODULE);
    }

    /**
     * 扣除血量
     */
    public void deduceHP(Long attackId, long hurt) {
        if (!isDeath()) {
            long finalHP = hp - hurt;
            finalHP = finalHP < 0 ? 0 : finalHP;
            setHp(finalHP);
            //状态发生改变，发送状态到客户端
            GetBean.getMapManager().synUnitStatusMessage(this);
            if (finalHP <= 0) {
                //战斗单位死亡，出发死亡机制
                death(attackId);
            }
        }
    }

    /**
     * 回复单位血量
     *
     * @param cure 回复血量
     */
    public void cureHp(long cure) {
        if (getHp() == getMaxHp()) {
            //已经满血
            return;
        }
        long finalHp = getHp() + cure;
        if (finalHp > getMaxHp()) {
            finalHp = getMaxHp();
        }
        setHp(finalHp);
        //状态发生改变，发送状态到客户端
        GetBean.getMapManager().synUnitStatusMessage(this);
    }

    public void putBuff(int uniqueId, AbstractFightBuff abstractFightBuff) {
        //添加buff
        buff.put(uniqueId, abstractFightBuff);
        //是属性buff
        if (abstractFightBuff.isAttributeBuff()) {
            //获取属性参数
            EffectResource effectResource = ((AttributeFightBuff) abstractFightBuff).getEffectResource();
            AttributeFightBuffBean attributeFightBuffBean = (AttributeFightBuffBean) effectResource.getValueParameter();
            putBuffAttribute(ImmutableAttributeElement.wrapper(attributeFightBuffBean.getAttributes()));
        }
    }

    /**
     * buff处理器
     *
     * @param now 当前时间
     */
    public void buffProcessor(Long now) {
        //便利buff容器
        for (Iterator<Map.Entry<Integer, AbstractFightBuff>> it = buff.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Integer, AbstractFightBuff> entry = it.next();
            if (entry.getValue().getWorkTime() <= now) {
                //生效
                if (!isDeath()) {
                    entry.getValue().doActive(this);
                    //重新计算生效时间
                    entry.getValue().setWorkTime(entry.getValue().getWorkTime() + entry.getValue().getPeriod());
                }
            }
            if (entry.getValue().getEndTime() <= now) {
                //结束，去除buff
                it.remove();
            }
        }
    }

    /**
     * 获取最大HP
     */
    public long getMaxHp() {
        return fightUnitAttribute.getFinalAttribute().get(AttributeType.BLOOD).getValue();
    }

    public long getMaxMp() {
        return fightUnitAttribute.getFinalAttribute().get(AttributeType.MAGIC).getValue();
    }

    /**
     * 出发死亡机制
     */
    protected abstract void death(Long attackId);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getHp() {
        return hp;
    }

    public void setHp(long hp) {
        this.hp = hp;
    }

    public long getMp() {
        return mp;
    }

    public void setMp(long mp) {
        this.mp = mp;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Map<AttributeType, AttributeElement> getFinalAttribute() {
        return fightUnitAttribute.getFinalAttribute();
    }


    public Map<Integer, AbstractFightBuff> getBuff() {
        return buff;
    }

    public void setBuff(Map<Integer, AbstractFightBuff> buff) {
        this.buff = buff;
    }

    public boolean isDeath() {
        return isDeath;
    }

    public void setDeath(boolean death) {
        isDeath = death;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public boolean isMonster() {
        return isMonster;
    }

    public void setMonster(boolean monster) {
        isMonster = monster;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public void setUnitAttribute(Map<AttributeType, AttributeElement> unitAttribute) {
        fightUnitAttribute.updateModule(FightAttributeModule.ROLE_MODULE, unitAttribute);
    }
}
