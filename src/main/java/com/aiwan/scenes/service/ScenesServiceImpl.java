package com.aiwan.scenes.service;

import com.aiwan.publicsystem.protocol.DecodeData;
import com.aiwan.publicsystem.service.ChannelManager;
import com.aiwan.scenes.mapresource.MapResource;
import com.aiwan.user.entity.User;
import com.aiwan.scenes.Dao.ScenesDao;
import com.aiwan.scenes.protocol.CM_Move;
import com.aiwan.scenes.protocol.CM_Shift;
import com.aiwan.scenes.protocol.SM_Move;
import com.aiwan.scenes.protocol.SM_Shift;
import com.aiwan.util.*;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * 场景业务逻辑类
 * */
@Scope("singleton")
@Service("scenesService")
public class ScenesServiceImpl implements ScenesService{
    Logger logger = LoggerFactory.getLogger(ScenesServiceImpl.class);
    private ScenesDao scenesDao;

    @Autowired
    public void setScenesDao(ScenesDao scenesDao){this.scenesDao = scenesDao;}
    /**
     * 角色的移动
     * */
    @Override
    public void move(CM_Move cm_move,Channel channel) {
        User user = UserCache.getUserByUsername(cm_move.getUsername());
        user.setCurrentX(cm_move.getTargetX());
        user.setCurrentY(cm_move.getCurrentY());
        UserCache.putUserByUsername(user.getUsername(),user);
        Object data = "移动失败！";
        short type = ConsequenceCode.MOVEFAIL;
        if (user == null){
            data  = "您还未登录！";
            type = ConsequenceCode.MOVEFAIL;
        }else{
            int map = user.getMap();
            MapResource mapResource = GetBean.getMapManager().getMapResource((int) map);
            if (mapResource.allowMove(cm_move.getTargetX(),cm_move.getTargetY())){
                scenesDao.updateUserPosition(cm_move);
                mapResource.move(user.getUsername(),cm_move.getTargetX(),cm_move.getTargetY());
                String content = "移动成功\n"+mapResource.getMapContent(cm_move.getTargetX(),cm_move.getTargetY());
                SM_Move sm_move = new SM_Move(cm_move.getTargetX(),cm_move.getCurrentY(),content);
                data = sm_move;
                type = ConsequenceCode.MOVESUCCESS;
            }else{
                data = "此处不可移动！";
                type = ConsequenceCode.MOVEFAIL;
            }
        }
        DecodeData decodeData = SMToDecodeData.shift(type,data);
        if (channel != null){
            channel.writeAndFlush(decodeData);
        }
    }

    /**
     * 地图跳转
     * */
    @Override
    public void shift(CM_Shift cm_shift,Channel channel) {
        User user = UserCache.getUserByUsername(cm_shift.getUsername());
        Object data = "跳转失败！";
        short type = ConsequenceCode.SHIFTFAIL;
        if (user == null){
            data  = "您还未登录！";
            type = ConsequenceCode.SHIFTFAIL;
            DecodeData decodeData = SMToDecodeData.shift(type,data);
            channel.writeAndFlush(decodeData);
        }else {
            MapResource mapResource1 = GetBean.getMapManager().getMapResource(user.getMap());
            mapResource1.removeUser(user.getUsername());
            MapResource mapResource = GetBean.getMapManager().getMapResource((int) cm_shift.getMap());
            user.setMap(cm_shift.getMap());
            user.setCurrentY(mapResource.getOriginY());
            user.setCurrentX(mapResource.getOriginX());
            //添加到地图资源中
            mapResource.putUser(user.getUsername(),user);
            //加入缓存
            UserCache.putUserByUsername(user.getUsername(),user);
            scenesDao.updateMapPosition(cm_shift,mapResource.getOriginX(),mapResource.getOriginY());
            String content = "跳转成功\n"+ mapResource.getMapContent(mapResource.getOriginX(),mapResource.getOriginY());
            SM_Shift sm_shift = new SM_Shift(mapResource.getOriginX(),mapResource.getOriginY(),cm_shift.getMap(),content);
            data = sm_shift;
            type = ConsequenceCode.SHIFTSUCCESS;
            DecodeData decodeData = SMToDecodeData.shift(type,data);
//            mapResource.sendMessage();
//            mapResource1.sendMessage();
            channel.writeAndFlush(decodeData);
        }
    }
}
