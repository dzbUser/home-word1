package com.aiwan.scenes.service;

import com.aiwan.publicsystem.DecodeData;
import com.aiwan.role.entity.User;
import com.aiwan.role.service.UserServiceImpl;
import com.aiwan.scenes.Dao.ScenesDao;
import com.aiwan.scenes.MapReource.CityResource;
import com.aiwan.scenes.MapReource.FieldResource;
import com.aiwan.scenes.protocol.CM_Move;
import com.aiwan.scenes.protocol.CM_Shift;
import com.aiwan.scenes.protocol.SM_Move;
import com.aiwan.scenes.protocol.SM_Shift;
import com.aiwan.util.DecodeDataShift;
import com.aiwan.util.MapResourceProtocol;
import com.aiwan.util.Protocol;
import com.aiwan.util.UserCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * 场景业务逻辑类
 * */
@Scope("singleton")
@Component("scenesService")
public class ScenesServiceImpl implements ScenesService{
    Logger logger = LoggerFactory.getLogger(ScenesServiceImpl.class);
    private ScenesDao scenesDao;

    @Autowired
    public void setScenesDao(ScenesDao scenesDao){this.scenesDao = scenesDao;}
    /**
     * 角色的移动
     * */
    @Override
    public DecodeData move(CM_Move cm_move) {
        User user = UserCache.getUserByUsername(cm_move.getUsername());
        Object data = "移动失败！";
        short type = Protocol.MOVEFAIL;
        if (user == null){
            data  = "您还未登录！";
            type = Protocol.MOVEFAIL;
        }else{
            short map = user.getMap();
            if (map == MapResourceProtocol.CITY){
                if (CityResource.allowMove(cm_move.getTargetX(),cm_move.getTargetY())){//主城内移动
                    scenesDao.updateUserPosition(cm_move);
                    String content = "移动成功\n"+CityResource.MaptoMapMessage(cm_move.getTargetX(),cm_move.getTargetY());
                    SM_Move sm_move = new SM_Move(cm_move.getTargetX(),cm_move.getCurrentY(),content);
                    data = sm_move;
                    type = Protocol.MOVESUCCESS;
                }else {
                    data = "此处不可移动！";
                    type = Protocol.MOVEFAIL;
                }
            }else if (map == MapResourceProtocol.FIELD){//野外移动
                if (FieldResource.allowMove(cm_move.getTargetX(),cm_move.getTargetY())){//
                    scenesDao.updateUserPosition(cm_move);
                    String content = "移动成功\n"+FieldResource.MaptoMapMessage(cm_move.getTargetX(),cm_move.getTargetY());
                    SM_Move sm_move = new SM_Move(cm_move.getTargetX(),cm_move.getCurrentY(),content);
                    data = sm_move;
                    type = Protocol.MOVESUCCESS;
                }else {
                    data = "此处不可移动！";
                    type = Protocol.MOVEFAIL;
                }
            }
        }
        DecodeData decodeData = DecodeDataShift.shift(type,data);
        return decodeData;
    }

    /**
     * 地图跳转
     * */
    @Override
    public DecodeData shift(CM_Shift cm_shift) {
        User user = UserCache.getUserByUsername(cm_shift.getUsername());
        Object data = "跳转失败！";
        short type = Protocol.SHIFTFAIL;
        if (user == null){
            data  = "您还未登录！";
            type = Protocol.MOVEFAIL;
        }else {

            if (cm_shift.getMap() == MapResourceProtocol.CITY){//移动到主城
                scenesDao.updateMapPosition(cm_shift,CityResource.ORINGINX,CityResource.ORINGINY);
                String content = "跳转成功\n"+CityResource.MaptoMapMessage(CityResource.ORINGINX,CityResource.ORINGINY);
                SM_Shift sm_shift = new SM_Shift(CityResource.ORINGINX,CityResource.ORINGINY,MapResourceProtocol.CITY,content);
                data = sm_shift;
                type = Protocol.SHIFTSUCCESS;
            }else if (cm_shift.getMap() == MapResourceProtocol.FIELD){//移动到野外
                scenesDao.updateMapPosition(cm_shift,FieldResource.ORINGINX,FieldResource.ORINGINY);
                String content = "跳转成功\n"+FieldResource.MaptoMapMessage(FieldResource.ORINGINX,FieldResource.ORINGINY);
                SM_Shift sm_shift = new SM_Shift(FieldResource.ORINGINX,FieldResource.ORINGINY,MapResourceProtocol.FIELD,content);
                data = sm_shift;
                type = Protocol.SHIFTSUCCESS;
            }
        }
        return DecodeDataShift.shift(type,data);
    }
}
