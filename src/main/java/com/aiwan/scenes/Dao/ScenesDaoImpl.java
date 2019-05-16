package com.aiwan.scenes.Dao;

import com.aiwan.role.service.UserServiceImpl;
import com.aiwan.scenes.protocol.CM_Move;
import com.aiwan.scenes.protocol.CM_Shift;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Scope("singleton")
@Component("scenesDao")
public class ScenesDaoImpl extends HibernateDaoSupport implements ScenesDao{

    @Resource
    public void setMySessionFactory(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void updateUserPosition(CM_Move cm_move) {
        String hql="update User u set u.currentX="+cm_move.getTargetX()+",u.currentY="+cm_move.getTargetY()+" where u.username='"+cm_move.getUsername()+"'";
        getHibernateTemplate().bulkUpdate(hql);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void updateMapPosition(CM_Shift cm_shift, short x, short y) {
        String hql="update User u set u.currentX="+x+",u.currentY="+y+",u.map="+cm_shift.getMap()+" where u.username='"+cm_shift.getUsername()+"'";
        getHibernateTemplate().bulkUpdate(hql);
    }
}
