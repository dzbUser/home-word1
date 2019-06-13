package com.aiwan.server.user.role.mount.protocol;

import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;

import java.io.Serializable;
import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.6.13
 * 查看坐骑详细
 * */
public class SM_ViewMount implements Serializable {

    /** 坐骑阶数 */
    private int level;

    /** 坐骑经验 */
    private int experience;

    /** 升级所需经验 */
    private int needExperience;

    /** 坐骑属性 */
    private Map<AttributeType, AttributeElement> Attributes;

    public static SM_ViewMount valueOf(int level,int experience,int needExperience,Map<AttributeType, AttributeElement> attributes){
        SM_ViewMount sm_viewMount = new SM_ViewMount();
        sm_viewMount.setAttributes(attributes);
        sm_viewMount.setExperience(experience);
        sm_viewMount.setLevel(level);
        sm_viewMount.setNeedExperience(needExperience);
        return sm_viewMount;
    }
    public int getLevel() {
        return level;
    }

    public SM_ViewMount setLevel(int level) {
        this.level = level;
        return this;
    }

    public int getExperience() {
        return experience;
    }

    public SM_ViewMount setExperience(int experience) {
        this.experience = experience;
        return this;
    }

    public Map<AttributeType, AttributeElement> getAttributes() {
        return Attributes;
    }

    public SM_ViewMount setAttributes(Map<AttributeType, AttributeElement> attributes) {
        Attributes = attributes;
        return this;
    }

    public int getNeedExperience() {
        return needExperience;
    }

    public SM_ViewMount setNeedExperience(int needExperience) {
        this.needExperience = needExperience;
        return this;
    }
}
