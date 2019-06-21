package com.aiwan.server.publicsystem.Initialization;

import com.aiwan.server.user.role.mount.resource.MountResource;
import com.aiwan.server.user.role.player.resource.RoleResource;
import com.aiwan.server.user.role.player.service.impl.RoleResourceManager;
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
    private final static String FILEPATH = "staticresource/role.xls";

    /** 坐骑静态文件路径 */
    private final static String MOUNTFILEPATH = "staticresource/mount.xls";
    public static void init(){
        List<RoleResource> roleList = null;
        List<MountResource> mountList = null;
        try {
            //资源读取，角色资源以及坐骑资源
            roleList = ExcelUtil.analysisWithRelativePath(FILEPATH, RoleResource.class);
            mountList = ExcelUtil.analysisWithRelativePath(MOUNTFILEPATH, MountResource.class);
        } catch (IllegalAccessException e) {
            logger.error(e.getLocalizedMessage());
        } catch (InstantiationException e) {
            logger.error(e.getLocalizedMessage());
        }
        RoleResourceManager roleResourceManager = GetBean.getRoleResourceManager();
        logger.debug("人物静态资源初始化debug："+roleList.size());
        if (roleList.size() == 0){
            logger.error("人物静态资源初始化错误"+roleList.size());
            return;
        }
        //初始化
        roleList.get(0).init();
        mountList.get(0).init();
        roleResourceManager.setRoleResource(roleList.get(0));
        GetBean.getMountManager().setMountResource(mountList.get(0));
    }
}
