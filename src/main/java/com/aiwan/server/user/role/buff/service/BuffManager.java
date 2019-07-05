package com.aiwan.server.user.role.buff.service;

import com.aiwan.server.publicsystem.annotation.Manager;
import com.aiwan.server.publicsystem.annotation.Static;
import com.aiwan.server.ramcache.service.impl.EntityCaheServiceImpl;
import com.aiwan.server.user.role.buff.common.BuffOverCommand;
import com.aiwan.server.user.role.buff.entity.BuffEntity;
import com.aiwan.server.user.role.buff.model.BuffModel;
import com.aiwan.server.user.role.buff.resource.BuffResource;
import com.aiwan.server.util.ExcelUtil;
import com.aiwan.server.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * buff管理类
 *
 * @author dengzebiao
 */
@Manager
public class BuffManager {

    Logger logger = LoggerFactory.getLogger(BuffManager.class);
    /**
     * buff映射
     */
    @Static(initializeMethodName = "initBuffResource")
    private Map<Integer, BuffResource> buffResourceMap = new HashMap<>();

    /**
     * 维护角色的buff command
     */
    private Map<Long, Map<Integer, BuffOverCommand>> buffCommandMap = new HashMap<>();

    /**
     * 缓存
     */
    private EntityCaheServiceImpl<Long, BuffEntity> cache;

    /**
     * 获取模型
     */
    public BuffModel load(Long rId) {
        BuffEntity buffEntity = cache.load(rId);
        BuffModel buffModel = new BuffModel();
        buffModel.setBuffEntity(buffEntity);
        return buffModel;
    }

    /**
     * 写回
     */
    public void save(BuffModel buffModel) {
        BuffEntity buffEntity = buffModel.getBuffEntity();
        cache.writeBack(buffEntity.getId(), buffEntity);
    }

    /**
     * 创建技能栏
     */
    public void create(Long rId) {
        BuffEntity buffEntity = new BuffEntity();
        buffEntity.setrId(rId);
        cache.writeBack(rId, buffEntity);
    }

    /**
     * 获取对应buff资源
     */
    public BuffResource getBuffResourceByBuffId(int buffId) {
        return buffResourceMap.get(buffId);
    }

    private void initBuffResource() {
        //获取资源路径
        String path = ResourceUtil.getResourcePath(BuffResource.class);
        logger.debug("FILEPATH：{}", path);
        logger.debug("buf静态资源初始化前");
        List<BuffResource> list = new ArrayList<BuffResource>();
        try {
            list.addAll(ExcelUtil.analysisWithRelativePath(path, BuffResource.class));
            logger.debug("道具静态资源初始化debug：");
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        logger.debug("道具静态资源初始化debug：" + list.size());
        for (BuffResource buffResource : list) {
            buffResource.init();
            buffResourceMap.put(buffResource.getId(), buffResource);
        }
        logger.debug("buf静态资源初始化后");
    }

    /**
     * 获取buffcommand
     */
    public BuffOverCommand getCommand(Long rId, int buffId) {
        Map<Integer, BuffOverCommand> map = buffCommandMap.get(rId);
        if (map == null) {
            return null;
        }
        return map.get(buffId);
    }

    /**
     * 存入command
     */
    public void putCommand(Long rId, int buffId, BuffOverCommand command) {
        Map<Integer, BuffOverCommand> map = buffCommandMap.get(rId);
        if (map == null) {
            map = new HashMap<>();
            buffCommandMap.put(rId, map);
        }
        map.put(buffId, command);
    }

    /**
     * 中断command
     */
    public void interruptCommand(Long rId, int buffId) {
        BuffOverCommand buffOverCommand = getCommand(rId, buffId);
        if (buffOverCommand != null) {
            buffOverCommand.cancel();
        }
    }

    /**
     * 移除command
     */
    public void removerCommand(Long rId, int buffId) {
        Map<Integer, BuffOverCommand> map = buffCommandMap.get(rId);
        if (map != null) {
            map.remove(buffId);
        }
    }



}
