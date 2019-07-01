package com.aiwan.server.user.role.skill.service;

import com.aiwan.server.publicsystem.annotation.Manager;
import com.aiwan.server.publicsystem.annotation.Static;
import com.aiwan.server.ramcache.service.impl.EntityCaheServiceImpl;
import com.aiwan.server.user.backpack.entity.BackpackEnt;
import com.aiwan.server.user.role.skill.entity.SkillEntity;
import com.aiwan.server.user.role.skill.model.Skill;
import com.aiwan.server.user.role.skill.model.SkillInfo;
import com.aiwan.server.user.role.skill.model.SkillModel;
import com.aiwan.server.user.role.skill.resource.SkillLevelResource;
import com.aiwan.server.user.role.skill.resource.SkillResource;
import com.aiwan.server.util.ExcelUtil;
import com.aiwan.server.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 技能管理类
 *
 * @author dengzebiao
 * @since 2019.7.1
 */
@Manager
public class SkillManager {
    private static Logger logger = LoggerFactory.getLogger(SkillManager.class);

    /**
     * 技能静态资源
     */
    @Static(initializeMethodName = "initSkillResource")
    private Map<Integer, SkillResource> skillResourceMap = new HashMap<Integer, SkillResource>();

    /**
     * 等级技能静态资源
     */
    @Static(initializeMethodName = "initSkillLevelResource")
    private Map<String, SkillLevelResource> skillLevelResourceMap = new HashMap<String, SkillLevelResource>();

    /**
     * 缓存
     */
    private EntityCaheServiceImpl<Long, SkillEntity> cache;

    /**
     * 获取技能模型
     */
    public SkillModel load(Long rId) {
        SkillEntity skillEntity = cache.load(rId);
        SkillModel skillModel = new SkillModel();
        skillModel.setSkillEntity(skillEntity);
        return skillModel;
    }

    /**
     * 写回
     */
    public void save(SkillModel skillModel) {
        SkillEntity skillEntity = skillModel.getSkillEntity();
        cache.writeBack(skillEntity.getRId(), skillEntity);
    }

    /**
     * 创建技能栏
     */
    public void create(Long rId) {
        SkillEntity skillEntity = new SkillEntity();
        SkillInfo skillInfo = new SkillInfo(skillEntity.getMaxSkillBarNum());
        skillEntity.setSkillInfo(skillInfo);
        skillEntity.setRId(rId);
        cache.writeBack(rId, skillEntity);
    }

    /**
     * 静态技能初始化
     */
    private void initSkillResource() {
        //获取地图资源静态路径
        String path = ResourceUtil.getResourcePath(SkillResource.class);
        //加载资源
        logger.debug("静态技能初始化");
        List<SkillResource> list = null;
        try {
            list = ExcelUtil.analysisWithRelativePath(path, SkillResource.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("加载地图错误{" + SkillResource.class.getName() + "}");
        }
        for (int i = 0; i < list.size(); i++) {
            skillResourceMap.put(list.get(i).getSkillId(), list.get(i));
        }
    }

    /**
     * 加载技能等级静态资源
     */
    private void initSkillLevelResource() {
        //获取地图资源静态路径
        String path = ResourceUtil.getResourcePath(SkillLevelResource.class);
        //加载资源
        logger.debug("加载技能等级静态资源");
        List<SkillLevelResource> list = null;
        try {
            list = ExcelUtil.analysisWithRelativePath(path, SkillLevelResource.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("加载地图错误{" + SkillLevelResource.class.getName() + "}");
        }
        for (int i = 0; i < list.size(); i++) {
            skillLevelResourceMap.put(list.get(i).getId(), list.get(i));
        }
    }

    /**
     * 获取静态技能资源
     *
     * @param skillId 技能id
     */
    public SkillResource getSkillResourceBySkillId(int skillId) {
        return skillResourceMap.get(skillId);
    }

    /**
     * 获取等级资源
     *
     * @param id 技能id_等级
     */
    public SkillLevelResource getSkillLevelReById(String id) {
        return skillLevelResourceMap.get(id);
    }
}
