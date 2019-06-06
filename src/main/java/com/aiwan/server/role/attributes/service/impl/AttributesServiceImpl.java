package com.aiwan.server.role.attributes.service.impl;

import com.aiwan.server.role.attributes.model.Attributes;
import com.aiwan.server.role.attributes.model.RoleAttributes;
import com.aiwan.server.role.attributes.service.AttributesManager;
import com.aiwan.server.role.attributes.service.AttributesService;
import com.aiwan.server.util.GetBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttributesServiceImpl implements AttributesService {
    @Autowired
    private  AttributesManager attributesManager;

    @Override
    public String viewRoleAttributes(Long rId) {
        //获取属性模型
        RoleAttributes  roleAttributes = attributesManager.getRoleAttributes(rId);
        StringBuffer stringBuffer = new StringBuffer();
        if (roleAttributes != null){
            //获取人物属性
            Attributes attributes = roleAttributes.getTotalAttributes();
            stringBuffer.append("攻击力："+attributes.getPower()+" 血量："+attributes.getBlood()+" 攻击力加成："+attributes.getPowerBonus());
        }
        return stringBuffer.toString();
    }

    @Override
    public void putRoleAttributes(Long rId) {
        RoleAttributes roleAttributes = new RoleAttributes();
        //配置各种属性
        roleAttributes.setEquipAttributes(GetBean.getEquipmentService().getEquipAttribute(rId));
        roleAttributes.setBaseAttributes(GetBean.getRoleService().getBaseAttribute(rId));
        roleAttributes.setMountAttribute(GetBean.getMountService().getMountAttributes(rId));
        attributesManager.putRoleAttributes(rId,roleAttributes);
    }

    @Override
    public void removeRoleAttributes(Long rId) {
        attributesManager.removeRoleAttributes(rId);
    }
}
