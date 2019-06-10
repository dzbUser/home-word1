package com.aiwan.server.publicsystem.Initialization;

import com.aiwan.server.user.role.resource.RoleResource;
import com.aiwan.server.user.role.service.RoleResourceManager;
import com.aiwan.server.util.ExcelUtil;
import com.aiwan.server.util.GetBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author dengzebia
 * @since 2019.6.10
 * 人物资源初始化
 * */
public class RoleResourceInit {
    static Logger logger = LoggerFactory.getLogger(RoleResourceInit.class);

    /** 人物静态文件路径 */
    private final static String FILEPATH="src/main/resources/role/role.xls";

    public static void init(){
        List<RoleResource> list = null;
        try {
            list = ExcelUtil.analysisExcelFile(FILEPATH, RoleResource.class);
        } catch (IllegalAccessException e) {
            logger.error(e.getLocalizedMessage());
        } catch (InstantiationException e) {
            logger.error(e.getLocalizedMessage());
        }
        RoleResourceManager roleResourceManager = GetBean.getRoleResourceManager();
        logger.debug("人物静态资源初始化debug："+list.size());
        if (list.size() == 0){
            logger.error("人物静态资源初始化错误"+list.size());
            return;
        }
        list.get(0).init();
        roleResourceManager.setRoleResource(list.get(0));

    }
}
