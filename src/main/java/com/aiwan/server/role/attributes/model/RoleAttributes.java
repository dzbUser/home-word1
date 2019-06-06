package com.aiwan.server.role.attributes.model;

/**
 * @author dengzebiao
 * @since 2019.6.6
 *  角色属性
 * */
public class RoleAttributes {
    /** 装备属性 */
    private Attributes equipAttributes;

    /** 人物基本属性 */
    private Attributes baseAttributes;

    /** 养成系统属性 */
    private Attributes mountAttribute;

    public Attributes getEquipAttributes() {
        return equipAttributes;
    }

    public RoleAttributes setEquipAttributes(Attributes equipAttributes) {
        this.equipAttributes = equipAttributes;
        return this;
    }

    public Attributes getBaseAttributes() {
        return baseAttributes;
    }

    public RoleAttributes setBaseAttributes(Attributes baseAttributes) {
        this.baseAttributes = baseAttributes;
        return this;
    }

    public Attributes getMountAttribute() {
        return mountAttribute;
    }

    public RoleAttributes setMountAttribute(Attributes mountAttribute) {
        this.mountAttribute = mountAttribute;
        return this;
    }

    /** 获取人物属性值 */
    public Attributes getTotalAttributes(){
        Attributes attributes = new Attributes();
        //设置属性值
        attributes.setPower(equipAttributes.getPower()+baseAttributes.getPower()+mountAttribute.getPower());
        attributes.setBlood(equipAttributes.getBlood()+baseAttributes.getBlood()+mountAttribute.getBlood());
        attributes.setPowerBonus(equipAttributes.getPowerBonus()+baseAttributes.getPowerBonus()+mountAttribute.getPowerBonus());
        return attributes;
    }
}
