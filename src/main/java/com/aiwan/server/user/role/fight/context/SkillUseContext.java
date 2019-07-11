package com.aiwan.server.user.role.fight.context;

import java.util.HashMap;
import java.util.Map;

/**
 * 技能使用上下文
 *
 * @author dengzebiao
 * @since 2019.7.10
 */
public class SkillUseContext {

    /**
     * 中间参数
     */
    private Map<SkillUseContextEnum, Object> skillUseParameters = new HashMap<>();

    /**
     * 获取中间参数
     *
     * @param type 参数类型
     * @return 参数对象
     */
    public Object getParameter(SkillUseContextEnum type) {
        return skillUseParameters.get(type);
    }

    /**
     * 存入中间参数
     *
     * @param type 参数类型
     * @param obj  参数对象
     */
    public void putParameter(SkillUseContextEnum type, Object obj) {
        skillUseParameters.put(type, obj);
    }
}
