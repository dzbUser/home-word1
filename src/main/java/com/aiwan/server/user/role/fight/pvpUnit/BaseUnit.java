package com.aiwan.server.user.role.fight.pvpUnit;

import com.aiwan.server.scenes.model.Position;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.buff.effect.AbstractFightBuff;
import com.aiwan.server.user.role.buff.effect.impl.AttributeFightBuff;
import com.aiwan.server.user.role.buff.resource.EffectResource;
import com.aiwan.server.user.role.buff.resource.bean.AttributeFightBuffBean;
import com.aiwan.server.util.AttributeUtil;
import com.aiwan.server.util.GetBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * PvP基础单位
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
     * 角色所属地图
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

    /**
     * 最终属性
     */
    private Map<AttributeType, AttributeElement> finalAttribute = new HashMap<>();

    /**
     * 战斗单位属性
     */
    private Map<AttributeType, AttributeElement> unitAttribute = new HashMap<>();


    /**
     * buff属性
     */
    private Map<AttributeType, AttributeElement> buffAttribute = new HashMap<>();


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
        this.buffAttribute = new HashMap<>(16);
        setHp(getMaxHp());
        setMp(getMaxMp());
        calculateFinalAttribute();
    }

    /**
     * 计算战斗最终属性
     */
    protected void calculateFinalAttribute() {
        //深拷贝单位属性
        Map<AttributeType, AttributeElement> pureAttribute = AttributeUtil.getCopyAttributeMap(unitAttribute);
        //计算buff属性
        for (Map.Entry<AttributeType, AttributeElement> entry : buffAttribute.entrySet()) {
            AttributeElement pureUnit = pureAttribute.get(entry.getKey());
            if (pureUnit == null) {
                pureAttribute.put(entry.getKey(), AttributeElement.valueOf(entry.getKey(), entry.getValue().getValue()));
            } else {
                pureUnit.setValue(entry.getValue().getValue() + pureUnit.getValue());
            }
        }
        //计算最终属性
        finalAttribute = new HashMap<>(16);
        for (Map.Entry<AttributeType, AttributeElement> entry : pureAttribute.entrySet()) {
            //获取最终属性值
            long value = entry.getKey().calculate(entry.getValue(), pureAttribute);
            getFinalAttribute().put(entry.getKey(), AttributeElement.valueOf(entry.getKey(), value));
        }
        logger.debug("计算成功");
    }

    /**
     * 添加buff属性
     *
     * @param addAttribute 所添加的属性
     */
    public void putBuffAttribute(Map<AttributeType, AttributeElement> addAttribute) {
        for (Map.Entry<AttributeType, AttributeElement> entry : addAttribute.entrySet()) {
            AttributeElement unit = buffAttribute.get(entry.getKey());
            if (unit == null) {
                buffAttribute.put(entry.getKey(), AttributeElement.valueOf(entry.getKey(), entry.getValue().getValue()));
            } else {
                unit.setValue(entry.getValue().getValue() + unit.getValue());
            }
        }
        calculateFinalAttribute();
    }

    /**
     * 去除buff属性
     *
     * @param addAttribute 所去除的属性
     */
    public void removeBuffAttribute(Map<AttributeType, AttributeElement> addAttribute) {
        for (Map.Entry<AttributeType, AttributeElement> entry : addAttribute.entrySet()) {
            AttributeElement unit = buffAttribute.get(entry.getKey());
            if (unit == null) {
                logger.error("角色{}，去除属性：{}错误", getId(), entry.getKey().getDesc());
            } else {
                unit.setValue(unit.getValue() - entry.getValue().getValue());
                if (unit.getValue() == 0) {
                    buffAttribute.remove(entry.getKey());
                }
            }
        }
        calculateFinalAttribute();
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
            EffectResource effectResource = ((AttributeFightBuff) abstractFightBuff).getEffectResource();
            AttributeFightBuffBean attributeFightBuffBean = (AttributeFightBuffBean) effectResource.getValueParameter();
            putBuffAttribute(attributeFightBuffBean.getAttributes());
        }
    }

    /**
     * buff处理器
     *
     * @param now 当前时间
     */
    public void buffProcessor(Long now) {
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
        return finalAttribute.get(AttributeType.BLOOD).getValue();
    }

    public long getMaxMp() {
        return finalAttribute.get(AttributeType.MAGIC).getValue();
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
        return finalAttribute;
    }

    public void setFinalAttribute(Map<AttributeType, AttributeElement> finalAttribute) {
        this.finalAttribute = finalAttribute;
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

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
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

    public Map<AttributeType, AttributeElement> getUnitAttribute() {
        return unitAttribute;
    }

    public void setUnitAttribute(Map<AttributeType, AttributeElement> unitAttribute) {
        this.unitAttribute = unitAttribute;
    }
}
