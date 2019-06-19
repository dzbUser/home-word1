package com.aiwan.server.publicsystem.Initialization;

import com.aiwan.server.prop.resource.EquipmentResource;
import com.aiwan.server.prop.resource.PropsResource;
import com.aiwan.server.prop.service.PropsManager;
import com.aiwan.server.util.ExcelUtil;
import com.aiwan.server.util.GetBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dengzebiao
 * @since 2019.6.4
 * 道具静态资源初始化
 * */
public class PropsInitialzation {

    static Logger logger = LoggerFactory.getLogger(PropsInitialzation.class);

    /**
     * 道具静态文件路径
     * 注意：此路径为绝对路径:"C:\\ideaProject\\homeword1\\src\\main\\resources\\prop\\prop.xls"
     * */
    private final static String FILEPATH = PropsInitialzation.class.getClassLoader().getResource("prop/prop.xls").getPath();

    /**
     * 装备静态文件路径
     * 注意：此路径为绝对路径:"C:\\ideaProject\\homeword1\\src\\main\\resources\\prop\\equipment.xls";
     * */
    private final static String FILEPATH1=PropsInitialzation.class.getClassLoader().getResource("prop/equipment.xls").getPath();

    public static void init(){
        propInit();
        equipmentInit();
    }
    /**道具初始化*/
    public static void propInit(){
        logger.debug("FILEPATH："+FILEPATH);
        logger.debug("道具静态资源初始化前");

        List<PropsResource> list = new ArrayList<PropsResource>();
        try {
            list.addAll(ExcelUtil.analysisExcelFile(FILEPATH, PropsResource.class));
            logger.debug("道具静态资源初始化debug：");
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        PropsManager propsManager = GetBean.getPropsManager();
//        logger.debug("道具静态资源初始化debug："+list.size());
        logger.debug(list+"");
        for (int i = 0;i < list.size();i++){
            propsManager.putProps(list.get(i));
        }
    }

    /** 装备静态资源初始化 */
    public static void equipmentInit(){
        logger.debug("FILEPATH1："+FILEPATH1);
        List<EquipmentResource> list = null;
        try {
            list = ExcelUtil.analysisExcelFile(FILEPATH1, EquipmentResource.class);
        } catch (IllegalAccessException e) {
            logger.error(e.getLocalizedMessage());
        } catch (InstantiationException e) {
            logger.error(e.getLocalizedMessage());
        }
        PropsManager propsManager = GetBean.getPropsManager();
        logger.debug("装备静态资源初始化debug："+list.size());
        for (int i = 0;i < list.size();i++){
            list.get(i).init();
            propsManager.putEquipment(list.get(i));
        }
    }

}
