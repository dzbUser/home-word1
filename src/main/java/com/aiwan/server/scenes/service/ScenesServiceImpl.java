package com.aiwan.server.scenes.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.publicsystem.protocol.SM_PromptMessage;
import com.aiwan.server.scenes.mapresource.MapResource;
import com.aiwan.server.user.account.model.User;
import com.aiwan.server.scenes.protocol.CM_Move;
import com.aiwan.server.scenes.protocol.CM_Shift;
import com.aiwan.server.scenes.protocol.SM_Move;
import com.aiwan.server.scenes.protocol.SM_Shift;
import com.aiwan.server.user.account.service.UserManager;
import com.aiwan.server.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


/**
 * @author dengzebiao
 * 场景业务逻辑类
 * */
@Scope("singleton")
@Service("scenesService")
public class ScenesServiceImpl implements ScenesService{

    Logger logger = LoggerFactory.getLogger(ScenesServiceImpl.class);

    private UserManager userManager;

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
    /**
     * 角色的移动
     * */
    @Override
    public void move(final CM_Move cm_move, final Session session) {
        logger.info(cm_move.getUsername()+"请求移动到"+cm_move.getTargetX()+","+cm_move.getTargetY());
        Object data;
        int type ;
        User user = session.getUser();
        if (GetBean.getMapManager().allowMove(cm_move.getTargetX(),cm_move.getTargetY(),user.getMap())) {
            userManager.save(user);
            user.setCurrentX(cm_move.getTargetX());
            user.setCurrentY(cm_move.getTargetY());
            SM_Move sm_move = SM_Move.valueOf(cm_move.getTargetX(), cm_move.getCurrentY(),1,GetBean.getMapManager().getMapContent(user.getCurrentX(),user.getCurrentY(),user.getMap()));
            data = sm_move;
            type = StatusCode.MOVESUCCESS;
            //对所有在线用户发送地图信息
            GetBean.getMapManager().sendMessageToUsers(user.getMap(),user.getAcountId());
            logger.info(cm_move.getUsername()+"移动成功");
        } else {
            data = "此处不可移动！";
            logger.info(cm_move.getUsername()+"移动失败");
            type = StatusCode.MESSAGE;
        }
        DecodeData decodeData = SMToDecodeData.shift((short) type,data);

        session.messageSend(decodeData);
    }

    /**
     * 地图跳转
     * */
    @Override
    public void shift(final CM_Shift cm_shift, final Session session) {
        logger.info(cm_shift.getUsername()+"请求地图跳转到"+cm_shift.getMap());
        User user = session.getUser();
        //是否有该地图，若无则放回无该地图
        if (GetBean.getMapManager().getMapResource(cm_shift.getMap()) == null){
            session.messageSend(SMToDecodeData.shift(StatusCode.MESSAGE, SM_PromptMessage.valueOf(PromptCode.MAPNOEXIST, "")));
            logger.info(cm_shift.getUsername()+"请求失败，原因：没有该地图");
            return;
        }
        //获取就地图位置
        int oldMap = user.getMap();
        //从旧地图中去除
        GetBean.getMapManager().removeUser(user.getMap(),user.getAcountId());
        MapResource mapResource = GetBean.getMapManager().getMapResource((int) cm_shift.getMap());
        user.setMap(cm_shift.getMap());
        user.setCurrentY(mapResource.getOriginY());
        user.setCurrentX(mapResource.getOriginX());
        //添加到地图资源中
        GetBean.getMapManager().putUser(user);
        //加入缓存
        userManager.save(user);
        String content = GetBean.getMapManager().getMapContent(user.getCurrentX(),user.getCurrentY(),user.getMap());
        SM_Shift sm_shift = SM_Shift.valueOf(mapResource.getOriginX(),mapResource.getOriginY(),cm_shift.getMap(),content);
        Object data = sm_shift;
        int type = StatusCode.SHIFTSUCCESS;
        DecodeData decodeData = SMToDecodeData.shift((short) type,data);
        session.messageSend(decodeData);
        //给所有玩家发送消息
        GetBean.getMapManager().sendMessageToUsers(user.getMap(),user.getAcountId());
        //如果与换到新的地图，需要给旧的地图发送改变消息
        if (user.getMap()!=oldMap){
            GetBean.getMapManager().sendMessageToUsers(oldMap,user.getAcountId());
        }
        logger.info("请求成功");
    }
}
