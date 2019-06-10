package com.aiwan.server.user.role.mount.service;

import com.aiwan.server.user.role.attributes.model.AttributeModule;

/**
 * @author dengzebiao
 * @since 2019.6.6
 * 业务逻辑接口类
 * */
public interface MountService {
    /** 创建坐骑 */
    public void createMount(Long rId);

    /** 经验增加 */
    public int addExperience(Long rId,int experienceNum);

    /** 查看坐骑 */
    public String viewMount(Long rId);

    /** 获取坐骑属性 */
    AttributeModule getMountAttributes(Long rId);
}
