package com.aiwan.server.user.role.buff.service;


import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.user.role.attributes.model.AttributeElement;
import com.aiwan.server.user.role.attributes.model.AttributeType;
import com.aiwan.server.user.role.buff.common.BuffOverCommand;
import com.aiwan.server.user.role.buff.entity.BuffElement;
import com.aiwan.server.user.role.buff.model.BuffModel;
import com.aiwan.server.user.role.buff.protocol.BuffMessage;
import com.aiwan.server.user.role.buff.protocol.SM_ViewBuff;
import com.aiwan.server.user.role.buff.resource.BuffResource;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.SMToDecodeData;
import com.aiwan.server.util.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 业务类
 *
 * @author dengzebiao
 * @since 2019.7.5
 */
@Service
public class BuffServiceImpl implements BuffService {
    Logger logger = LoggerFactory.getLogger(BuffService.class);
    @Autowired
    private BuffManager buffManager;

    @Override
    public void addBuff(Long rId, int buffId, Long delay) {
        /*
         * 1.查看是否有该buff静态资源
         * 2.是否已有buff，无则直接添加，return
         * 3.有则查看buff类型
         *   a.重置类型
         *   中断，重置，重新设置时间
         *   b.叠加类型
         *   获取剩余时间，叠加（>0）,重新定时（存库）
         * */

        BuffResource buffResource = buffManager.getBuffResourceByBuffId(buffId);
        if (buffResource == null) {
            logger.error("{}buff资源为空", buffId);
            //buff资源为空
            return;
        }

        //获取角色
        Role role = GetBean.getRoleManager().load(rId);
        BuffModel buffModel = buffManager.load(rId);
        if (!buffModel.isExistBuff(buffId)) {
            //不存在buff，直接添加buff,或结束时间
            Long overTime = System.currentTimeMillis() + delay;
            //添加定时command
            BuffOverCommand buffOverCommand = new BuffOverCommand(delay, role.getAccountId(), buffId, rId);
            GetBean.getAccountExecutorService().submit(buffOverCommand);
            //把buff添加到管理类
            buffManager.putCommand(rId, buffId, buffOverCommand);
            //写回
            buffModel.putBuff(buffId, overTime);
            buffManager.save(buffModel);
            //通知属性修改
            GetBean.getRoleService().updateAttributeModule("buff", getAttributeModule(rId), rId);
            logger.info("buffId:{},添加角色：{},新添加成功", buffId, rId);
            return;
        } else {
            //获取当前时间
            Long currentTime = System.currentTimeMillis();
            //中断command
            buffManager.interruptCommand(rId, buffId);
            //最终延迟时间
            Long finalDelay = delay;
            if (buffResource.getOverlayType() == 1) {
                logger.info("buffId:{},添加角色：{},重置添加", buffId, rId);
                //重置buff
                buffModel.resetTime(buffId, currentTime + delay);
            } else {
                //获取旧的结束时间
                logger.info("buffId:{},添加角色：{},叠加添加", buffId, rId);
                Long oldOverTime = buffModel.getOverTime(buffId);
                finalDelay = oldOverTime - currentTime + delay;
                buffModel.resetTime(buffId, currentTime + finalDelay);
            }
            //启动定时
            BuffOverCommand buffOverCommand = new BuffOverCommand(finalDelay, role.getAccountId(), buffId, rId);
            GetBean.getAccountExecutorService().submit(buffOverCommand);
            //存入Command
            buffManager.putCommand(rId, buffId, buffOverCommand);
            //写回
            buffManager.save(buffModel);
            //通知属性修改
            GetBean.getRoleService().updateAttributeModule("buff", getAttributeModule(rId), rId);
            logger.info("buffId:{},添加角色：{},添加成功", buffId, rId);
        }
    }

    @Override
    public void removeBuff(Long rId, int buffId) {
        BuffModel buffModel = buffManager.load(rId);
        if (buffModel.getBuff(buffId) != null) {
            logger.info("buffId:{},移除buff所属角色:{},没有该buff", buffId, rId);
            buffModel.removeBuff(buffId);
        }
        //移除对应的command
        buffManager.removerCommand(rId, buffId);
        //写回
        buffManager.save(buffModel);
        //通知属性变化
        GetBean.getRoleService().updateAttributeModule("buff", getAttributeModule(rId), rId);
        logger.info("buffId:{},移除buff所属角色:{},移除成功", buffId, rId);
    }

    @Override
    public Map<AttributeType, AttributeElement> getAttributeModule(Long rId) {
        /*
         * 1.便利buff属性，叠加属性
         * */
        BuffModel buffModel = buffManager.load(rId);
        return buffModel.getAttributeModule();
    }

    @Override
    public void viewBuff(Long rId, Session session) {
        //获取buff模型
        BuffModel buffModel = buffManager.load(rId);
        if (buffModel == null) {
            logger.error("角色id{}发送错误报", rId);
            return;
        }
        //获取buff映射
        Map<Integer, BuffElement> map = Collections.unmodifiableMap(buffModel.getBuffEntity().getBuffInfo().getMap());
        //创建存放列表
        List<BuffMessage> list = new ArrayList<>();
        for (BuffElement buffElement : map.values()) {
            BuffMessage buffMessage = BuffMessage.valueOf(buffElement.getBuffId(), buffElement.getOverTime());
            list.add(buffMessage);
        }
        SM_ViewBuff sm_viewBuff = SM_ViewBuff.valueOf(list);

        session.messageSend(SMToDecodeData.shift(StatusCode.VIEW_BUFF, sm_viewBuff));
    }

    @Override
    public void refreshCommand(Long rId, int buffId, Long delay) {
        Role role = GetBean.getRoleManager().load(rId);
        BuffOverCommand command = buffManager.getCommand(rId, buffId);
        if (command == null) {
            //重新设置定时器
            command = new BuffOverCommand(delay, role.getAccountId(), buffId, rId);
            GetBean.getAccountExecutorService().submit(command);
            buffManager.putCommand(rId, buffId, command);
        }
    }
}
