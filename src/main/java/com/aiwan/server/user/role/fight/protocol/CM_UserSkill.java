package com.aiwan.server.user.role.fight.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * 实用技能协议类
 *
 * @author dengzebiao
 * @since 2019.7.9
 */
@ProtocolAnnotation(protocol = Protocol.USESKILL)
public class CM_UserSkill implements Serializable {

    /**
     * 使用角色
     */
    private Long rId;

    /**
     * 使用位置
     */
    private int barPosition;

    /**
     * 施法选择
     * 1.怪物
     * 2.角色
     */
    private int aimType;

    /**
     * 目标id
     */
    private Long targetId;

    public static CM_UserSkill valueOf(Long rId, Long targetId, int usePosition, int castingType) {
        CM_UserSkill cm_userSkill = new CM_UserSkill();
        cm_userSkill.setrId(rId);
        cm_userSkill.setBarPosition(usePosition);
        cm_userSkill.setTargetId(targetId);
        cm_userSkill.setAimType(castingType);
        return cm_userSkill;
    }

    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }

    public int getBarPosition() {
        return barPosition;
    }

    public void setBarPosition(int barPosition) {
        this.barPosition = barPosition;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public int getAimType() {
        return aimType;
    }

    public void setAimType(int aimType) {
        this.aimType = aimType;
    }
}
