package com.aiwan.server.scenes.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.player.model.Role;

import java.util.Map;

/**
 * @author dengzebiao
 * 场景的移动和跳转
 * */
public interface ScenesService {
    /** 角色移动 */
    void move(Long rId, int x, int y, final Session session);

    /** 角色地图跳转 */
    void shift(Long rId, int map, final Session session);

    /**
     * 更新场景资源
     */
    void updateSceneAttribute(Role role);

}
