package com.aiwan.server.user.role.buff.resource;

import com.aiwan.server.publicsystem.annotation.CellMapping;
import com.aiwan.server.publicsystem.annotation.Resource;
import com.aiwan.server.user.role.buff.effect.FightBuffType;

import java.util.HashMap;
import java.util.Map;

/**
 * buff效果资源
 *
 * @author dengzebiao
 * @since 2019.7.15
 */
@Resource("staticresource/effectResource.xls")
public class EffectResource {

    @CellMapping(name = "id")
    private int id;

    /**
     * 效果值
     */
    @CellMapping(name = "value")
    private String value;

    /**
     * buff类型
     */
    @CellMapping(name = "type")
    private int type;

    /**
     * 是否是周期buff
     */
    @CellMapping(name = "isPeriod")
    private int isPeriod;

    /**
     * 周期
     */
    @CellMapping(name = "period")
    private Long period;

    /**
     * 持续时间
     */
    @CellMapping(name = "duration")
    private long duration;

    /**
     * 效果id
     */
    @CellMapping(name = "uniqueId")
    private int uniqueId;

    /**
     * effect描述
     */
    @CellMapping(name = "dec")
    private String dec;

    /**
     * 值映射
     * */
    private Map<String, Object> valueMap = new HashMap<>();

    /**
     * 特殊参数解析值
     */
    private IFightBuffAnalysis valueParameter;

    /**
     * 战斗buff类型
     */
    private FightBuffType fightBuffType;

    public void init() {
        //初始化效果参数
        String[] parameters = value.split(" ");
        for (String parameter : parameters) {
            String[] parameterMap = parameter.split(":");
            valueMap.put(parameterMap[0], parameterMap[1]);
        }
        this.fightBuffType = FightBuffType.getEffectType(getType());
        Class<? extends IFightBuffAnalysis> fightBuffBeanClazz = this.fightBuffType.getAnaClazz();
        try {
            this.valueParameter = this.fightBuffType.getAnaClazz().newInstance();
            this.valueParameter.doParse(this);
        } catch (Exception e) {
            throw new IllegalArgumentException("生成Buff参数实力[" + fightBuffType.name() + "]错误");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsPeriod() {
        return isPeriod;
    }

    public void setIsPeriod(int isPeriod) {
        this.isPeriod = isPeriod;
    }

    public Long getPeriod() {
        return period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    public Map<String, Object> getValueMap() {
        return valueMap;
    }

    public IFightBuffAnalysis getValueParameter() {
        return valueParameter;
    }

    public FightBuffType getFightBuffType() {
        return fightBuffType;
    }
}
