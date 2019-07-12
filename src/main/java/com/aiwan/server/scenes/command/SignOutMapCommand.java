package com.aiwan.server.scenes.command;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneCommand;
import com.aiwan.server.user.role.buff.common.BuffOverCommand;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 离开地图指令
 *
 * @author dengzebiao
 * @since 2019.7.5
 */
public class SignOutMapCommand extends AbstractSceneCommand {

    private Logger logger = LoggerFactory.getLogger(SignOutMapCommand.class);


    /**
     * 移动角色
     */
    private Role role;


    @Override
    public void action() {
        //去除地图缓存
        GetBean.getScenesService().leaveMap(role);
        //删除buff定时器
        GetBean.getBuffManager().interruptCommand(role.getId());
    }

    public SignOutMapCommand(Role role) {
        super(role.getAccountId(), role.getMap());
        this.role = role;
    }
}