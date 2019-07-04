package com.aiwan.server.scenes.service;

import com.aiwan.server.publicsystem.common.Session;

/**
 * @author dengzebiao
 * 场景的移动和跳转
 * */
public interface ScenesService {
    /** 角色移动 */
    void move(Long rId, int x, int y, final Session session);

    /** 角色地图跳转 */
    void shift(Long rId, int map, final Session session);

}
