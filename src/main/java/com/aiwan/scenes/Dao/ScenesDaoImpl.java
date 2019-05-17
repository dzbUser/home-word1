package com.aiwan.scenes.Dao;

import com.aiwan.scenes.protocol.CM_Move;
import com.aiwan.scenes.protocol.CM_Shift;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 场景持久层操作类
 * */
@Scope("singleton")
@Component("scenesDao")
public class ScenesDaoImpl extends HibernateDaoSupport implements ScenesDao{

    @Resource
    public void setMySessionFactory(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    //修改用户坐标
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void updateUserPosition(CM_Move cm_move) {
        String hql="update User u set u.currentX="+cm_move.getTargetX()+",u.currentY="+cm_move.getTargetY()+" where u.username='"+cm_move.getUsername()+"'";
        getHibernateTemplate().bulkUpdate(hql);
    }

    //修改用户地图，初始化坐标
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void updateMapPosition(CM_Shift cm_shift, int x, int y) {
        String hql="update User u set u.currentX="+x+",u.currentY="+y+",u.map="+cm_shift.getMap()+" where u.username='"+cm_shift.getUsername()+"'";
        getHibernateTemplate().bulkUpdate(hql);
    }
}
