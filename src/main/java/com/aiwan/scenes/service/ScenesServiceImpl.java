package com.aiwan.scenes.service;

import com.aiwan.publicsystem.common.Session;
import com.aiwan.publicsystem.protocol.DecodeData;
import com.aiwan.publicsystem.service.SessionManager;
import com.aiwan.scenes.mapresource.MapResource;
import com.aiwan.user.model.User;
import com.aiwan.scenes.protocol.CM_Move;
import com.aiwan.scenes.protocol.CM_Shift;
import com.aiwan.scenes.protocol.SM_Move;
import com.aiwan.scenes.protocol.SM_Shift;
import com.aiwan.user.service.UserManager;
import com.aiwan.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
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
    public void move(Session session,CM_Move cm_move) {
        Object data;
        int type ;
        User user = session.getUser();
        int map = user.getMap();
        MapResource mapResource = GetBean.getMapManager().getMapResource((int) map);
        if (mapResource.allowMove(cm_move.getTargetX(), cm_move.getTargetY())) {
            userManager.sava(user);
            user.setCurrentX(cm_move.getTargetX());
            user.setCurrentY(cm_move.getTargetY());
            String content = "移动成功\n" + mapResource.getMapContent(cm_move.getTargetX(), cm_move.getTargetY());
            SM_Move sm_move = new SM_Move(cm_move.getTargetX(), cm_move.getCurrentY(), content);
            data = sm_move;
            type = ConsequenceCode.MOVESUCCESS;
        } else {
            data = "此处不可移动！";
            type = ConsequenceCode.MOVEFAIL;
        }
        DecodeData decodeData = SMToDecodeData.shift((short) type,data);
        session.getChannel().writeAndFlush(decodeData);
    }

    /**
     * 地图跳转
     * */
    @Override
    public void shift(Session session,CM_Shift cm_shift) {
        User user = session.getUser();
        MapResource mapResource1 = GetBean.getMapManager().getMapResource(user.getMap());
        mapResource1.removeUser(user.getAcountId());
        MapResource mapResource = GetBean.getMapManager().getMapResource((int) cm_shift.getMap());
        user.setMap(cm_shift.getMap());
        user.setCurrentY(mapResource.getOriginY());
        user.setCurrentX(mapResource.getOriginX());
        //添加到地图资源中
        mapResource.putUser(user.getAcountId(),user);
        //加入缓存

        userManager.sava(user);
        String content = "跳转成功\n"+ mapResource.getMapContent(mapResource.getOriginX(),mapResource.getOriginY());
        SM_Shift sm_shift = new SM_Shift(mapResource.getOriginX(),mapResource.getOriginY(),cm_shift.getMap(),content);
        Object data = sm_shift;
        int type = ConsequenceCode.SHIFTSUCCESS;
        DecodeData decodeData = SMToDecodeData.shift((short) type,data);
        session.getChannel().writeAndFlush(decodeData);
    }
}
