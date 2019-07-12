package com.aiwan.server.user.role.buff.service;

import com.aiwan.server.publicsystem.annotation.Manager;
import com.aiwan.server.publicsystem.annotation.Static;
import com.aiwan.server.ramcache.service.impl.EntityCaheServiceImpl;
import com.aiwan.server.user.role.buff.common.BuffOverCommand;
import com.aiwan.server.user.role.buff.entity.BuffEntity;
import com.aiwan.server.user.role.buff.model.BuffModel;
import com.aiwan.server.user.role.buff.resource.BuffResource;
import com.aiwan.server.user.role.buff.resource.EffectResource;
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
     * 效果buff资源id与效果buff资源
     */
    @Static(initializeMethodName = "initEffectResource")
    private Map<Integer, EffectResource> effectResourceMap = new HashMap<>();

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
            logger.debug("buf静态资源初始化debug：");
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        for (BuffResource buffResource : list) {
            buffResource.init();
            buffResourceMap.put(buffResource.getId(), buffResource);
        }
        logger.debug("buf静态资源初始化后");
    }

    private void initEffectResource() {
        //获取资源路径
        String path = ResourceUtil.getResourcePath(EffectResource.class);
        logger.debug("FILEPATH：{}", path);
        logger.debug("EffectResource静态资源初始化前");
        List<EffectResource> list = new ArrayList<EffectResource>();
        try {
            list.addAll(ExcelUtil.analysisWithRelativePath(path, EffectResource.class));
            logger.debug("EffectResource静态资源初始化debug：{}", list.size());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        for (EffectResource effectResource : list) {
            effectResourceMap.put(effectResource.getId(), effectResource);
        }
        logger.debug("EffectResource静态资源初始化后");
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
     * 中断角色rid的所有buff的command
     *
     * @param rId
     */
    public void interruptCommand(Long rId) {
        Map<Integer, BuffOverCommand> map = this.buffCommandMap.get(rId);
        if (map != null) {
            for (BuffOverCommand buffOverCommand : map.values()) {
                buffOverCommand.cancel();
            }
            this.buffCommandMap.remove(rId);
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

    /**
     * 获取buff效果资源
     *
     * @param id 效果资源id
     * @return buff效果资源
     */
    public EffectResource getEffectResource(int id) {
        return effectResourceMap.get(id);
    }

}
