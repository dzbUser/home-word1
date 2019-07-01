package com.aiwan.client.init;

import com.aiwan.client.Resource.ClientViewResource;
import com.aiwan.client.service.ClientResourceManager;
import com.aiwan.server.util.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dengzebiao
 * @since 2019.6.13
 * 客户端资源初始化类
 * */
public class ClientResourceInit {

    static Logger logger = LoggerFactory.getLogger(ClientResourceInit.class);

    /** 客户端资源静态文件路径
     * 注意：此路径为绝对路径
     * 绝对路径："C:\\ideaProject\\homeword1\\src\\main\\resources\\client\\clientResource.xls";
     * */
    private final static String FILEPATH = "staticresource/clientResource.xls";


    public static void init(){
        logger.debug("FILEPATH：" + FILEPATH);
        logger.debug("初始化客户端查看资源");
        List<ClientViewResource> list = new ArrayList<ClientViewResource>();
        try {
            list.addAll(ExcelUtil.analysisWithRelativePath(FILEPATH, ClientViewResource.class));
        } catch (IllegalAccessException e) {
            logger.error(e.getLocalizedMessage());
        } catch (InstantiationException e) {
            logger.error(e.getLocalizedMessage());
        }

        for (ClientViewResource clientViewResource:list){
            //模块类型为空
            if (ClientResourceManager.getResourceMap().get(clientViewResource.getType()) == null) {
                Map<Integer,ClientViewResource> map = new HashMap<Integer,ClientViewResource>(16);
                ClientResourceManager.getResourceMap().put(clientViewResource.getType(), map);
            }
            ClientResourceManager.getResourceMap().get(clientViewResource.getType()).put(clientViewResource.getId(), clientViewResource);
        }

    }
}
