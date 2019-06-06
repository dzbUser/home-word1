package com.aiwan.server.role.mount.service;

import com.aiwan.server.role.attributes.model.Attributes;

/**
 * @author dengzebiao
 * @since 2019.6.6
 * 业务逻辑接口类
 * */
public interface MountService {
    /** 创建坐骑 */
    public void createMount(Long rId);

    /** 经验增加 */
    public void addExperience(Long rId,int experienceNum);

    /** 查看坐骑 */
    public String viewMount(Long rId);

    /** 获取坐骑属性 */
    Attributes getMountAttributes(Long rId);
}
