package com.aiwan.server.scenes.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.protocol.DecodeData;
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
    public void move(Session session, CM_Move cm_move) {
        Object data;
        int type ;
        User user = session.getUser();
        int map = user.getMap();
        if (GetBean.getMapManager().allowMove(cm_move.getTargetX(),cm_move.getTargetY(),user.getMap())) {
            userManager.save(user);
            user.setCurrentX(cm_move.getTargetX());
            user.setCurrentY(cm_move.getTargetY());
            String content = "移动成功\n" + GetBean.getMapManager().getMapContent(user.getCurrentX(),user.getCurrentY(),user.getMap());
            SM_Move sm_move = new SM_Move(cm_move.getTargetX(), cm_move.getCurrentY(), content);
            data = sm_move;
            type = StatusCode.MOVESUCCESS;
        } else {
            data = "此处不可移动！";
            type = StatusCode.MOVEFAIL;
        }
        DecodeData decodeData = SMToDecodeData.shift((short) type,data);
        session.messageSend(decodeData);
    }

    /**
     * 地图跳转
     * */
    @Override
    public void shift(Session session,CM_Shift cm_shift) {
        User user = session.getUser();
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
        String content = "跳转成功\n"+ GetBean.getMapManager().getMapContent(user.getCurrentX(),user.getCurrentY(),user.getMap());
        SM_Shift sm_shift = new SM_Shift(mapResource.getOriginX(),mapResource.getOriginY(),cm_shift.getMap(),content);
        Object data = sm_shift;
        int type = StatusCode.SHIFTSUCCESS;
        DecodeData decodeData = SMToDecodeData.shift((short) type,data);
        session.messageSend(decodeData);
    }
}