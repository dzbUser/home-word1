package com.aiwan.server.user.account.service;

import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.protocol.DecodeData;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.world.scenes.command.SignInMapCommand;
import com.aiwan.server.world.scenes.command.SignOutMapCommand;
import com.aiwan.server.user.account.model.User;
import com.aiwan.server.user.account.protocol.*;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 用户业务逻辑类
 * */
@Scope("singleton")
@Service("userService")
public class UserServiceImpl implements UserService {
    //新用户初始地址
    private final static int ORINGINMAP = 1;
    //新用户初始坐标
    private final static int ORINGINX = 1;
    private final static int ORINGINY = 3;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserManager userManager;

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }



    //用户登录
    @Override
    public void login(Session session, String accountId, String password) {
        /*
        * 1.查看缓存是否存在用户，若存在则说明用户已在线
        * 2.查看账号密码是否正确，若不正确则返回登录错误
        * 3.登录成功，加入缓存 a.username-session的映射
        * 4.查看是否有角色，若无角色返回角色注册提醒
        * */
        DecodeData decodeData;
        SM_UserMessage sm_userMessage = new SM_UserMessage();
        //~~~~~~~~~~第一步~``````
        Session session1 = SessionManager.getSessionByAccountId(accountId);
        if (session1 !=null){
            logger.info(accountId + "用户在线");
            sm_userMessage.setStatus(false);
            sm_userMessage.setOtherMessage("用户已在线，若想顶替，请选择高级登录");
            decodeData = SMToDecodeData.shift(StatusCode.LOGIN,sm_userMessage);
            session.messageSend(decodeData);
            return;
        }
        User user = userManager.getUserByAccountId(accountId);

        //账号或者密码错误~~~~~~~第二步~~~~~~~~~
        if (user == null || !user.getPassword().equals(password)) {
            logger.info(accountId + "登录失败");
            sm_userMessage.setStatus(false);
            sm_userMessage.setOtherMessage("账号或者密码错误");
            decodeData = SMToDecodeData.shift(StatusCode.LOGIN,sm_userMessage);
        }
        else {
            //~~~~~~~~~~~~第三步~~~~~~~~~~~~
            logger.debug(accountId + "用户登录成功");

            //加入session管理
            session.setUser(user);
            SessionManager.putSessionByUsername(accountId, session);
            sm_userMessage.setStatus(true);
            sm_userMessage.setAccountId(user.getAcountId());
            //~~~~~~~~~~~第四步~~~~~~~~~~~~~
            if (user.getRoleNum() == 0){
                sm_userMessage.setAccountId(user.getAcountId());
                sm_userMessage.setCreated(false);
                sm_userMessage.setOtherMessage("您还未创建角色，请创建您的角色");
            }else {
                //把用户添加到地图资源中
                Role role = GetBean.getRoleManager().load(user.getRoleId());
                session.setrId(role.getId());
                GetBean.getSceneExecutorService().submit(new SignInMapCommand(role));
                sm_userMessage.setCreated(true);
                sm_userMessage.setRoles(user.getUserBaseInfo().getRoles());
            }
            decodeData = SMToDecodeData.shift(StatusCode.LOGIN,sm_userMessage);
            logger.info(user.getAcountId()+"登录成功！");
        }
        session.messageSend(decodeData);
    }

    /**用户注册*/
    @Override
    public void registerUser(Session session, String accountId, String password, String hPassword) {
        User user = userManager.getUserByAccountId(accountId);
        //账号可用
        if (user == null) {
            logger.info(accountId + "注册新用户成功");
            userManager.register(accountId, password, hPassword, ORINGINMAP, ORINGINX, ORINGINY, 1);
            GetBean.getBackpackService().createBackpack(accountId);
            session.messageSend(SMToDecodeData.shift(StatusCode.REGISTER, SM_Register.valueOf(1, accountId)));
        }else {//账号已被注册
            logger.info(accountId + "用户已存在");
            session.messageSend(SMToDecodeData.shift(StatusCode.REGISTER, SM_Register.valueOf(0, accountId)));
        }
    }


    @Override
    //用户注销
    public void logout(String accountId, Session session) {
        /*
        * 1.删除缓存
        * 2.从地图资源中删除
        * */
        SessionManager.removeSessionByAccountId(accountId);
        logger.info(accountId + "注销成功！");
        String content = "注销用户成功！";
        DecodeData decodeData = SMToDecodeData.shift(StatusCode.LOGOUTSUCCESS,content);
        User user = userManager.getUserByAccountId(accountId);
        //保存用户数据
        userManager.save(user);
        //角色不会空
        if (user.getRoleId() != null) {
            //获取角色
            Role role = GetBean.getRoleManager().load(user.getRoleId());
            //把用户从地图资源中移除
            GetBean.getSceneExecutorService().submit(new SignOutMapCommand(role));
            //退出队伍
            GetBean.getTeamService().leaveTeam(role.getId());
        }
        //session移除用户信息
        session.setUser(null);
    }

    /**
     * 用户需顶替已登录用户登录
     * */
    @Override
    public void hLogin(String accountId, String hPassword, Session session) {

        SM_UserMessage sm_userMessage = new SM_UserMessage();
        DecodeData decodeData;
        User user = userManager.getUserByAccountId(accountId);
        //账号或者密码错误
        if (user == null || !user.getHpassword().equals(hPassword)) {
            logger.info(sm_userMessage.getAccountId() + "高级登录失败");
            sm_userMessage.setStatus(false);
            sm_userMessage.setOtherMessage("账号或者高级密码错误");
            decodeData = SMToDecodeData.shift(StatusCode.LOGIN,sm_userMessage);
            session.messageSend(decodeData);
            return;
        }

        //发送顶替下线信息
        Session session1 = SessionManager.getSessionByAccountId(accountId);
        if (session1!= null){
            session1.messageSend(SMToDecodeData.shift((short) StatusCode.INSIST,"您已被顶替下线！"));
        }


        //更改缓存
        session.setUser(user);
        SessionManager.putSessionByUsername(accountId, session);


        //是否创建新角色
        if (user.getRoleNum() == 0){
            sm_userMessage.setCreated(false);
            sm_userMessage.setOtherMessage("您还未创建角色，请创建您的角色");
        }else{
            //把用户添加到地图资源中
            Role role = GetBean.getRoleManager().load(user.getRoleId());
            GetBean.getSceneExecutorService().submit(new SignInMapCommand(role));
            sm_userMessage.setRoles(user.getUserBaseInfo().getRoles());
            session.setrId(role.getId());
        }

        sm_userMessage.setAccountId(user.getAcountId());
        sm_userMessage.setStatus(true);
        logger.info(accountId + "高级登录成功");
        decodeData = SMToDecodeData.shift(StatusCode.LOGIN,sm_userMessage);
        session.messageSend(decodeData);
    }


    /**
     * 创建角色
     * */
    @Override
    public void createRole(String accountId, String name, int job, int sex, final Session session) {
        logger.info(accountId + "创建角色");
        User user = session.getUser();
        if(user.getUserBaseInfo().getRoles().size() >= user.getMaxRole()){
            //查看角色量
            session.sendPromptMessage(PromptCode.ACHIEVEROLEMAXNUM, "");
            logger.info(accountId + "：用户角色已上限");
        }
        else {
            //转交给角色业务
            GetBean.getRoleService().create(session, accountId, name, job, sex);
        }
    }


    @Override
    public void deleteSave(String accountId) {
        //删除人物session缓存
        SessionManager.removeSessionByAccountId(accountId);
        logger.info(accountId+":删除人物缓存与保存信息");
        User user = userManager.getUserByAccountId(accountId);
        //保存用户数据
        userManager.save(user);
        //角色不会空
        if (user.getRoleId() != null) {
            //获取角色
            Role role = GetBean.getRoleManager().load(user.getRoleId());
            //把用户从地图资源中移除
            GetBean.getSceneExecutorService().submit(new SignOutMapCommand(role));
        }
    }

}
