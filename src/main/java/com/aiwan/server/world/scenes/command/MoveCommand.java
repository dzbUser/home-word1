package com.aiwan.server.world.scenes.command;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneCommand;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.user.role.fight.pvpunit.BaseUnit;
import com.aiwan.server.world.scenes.model.Position;
import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.PromptCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 角色移动command
 *
 * @author dengzebiao
 */
public class MoveCommand extends AbstractSceneCommand {

    private Logger logger = LoggerFactory.getLogger(MoveCommand.class);

    /**
     * 移动目标
     */
    private Position target;

    /**
     * 移动角色
     */
    private Role role;


    @Override
    public void action() {
        BaseUnit baseUnit = GetBean.getMapManager().getSceneObject(role.getMap(), role.getSceneId()).getBaseUnit(role.getId());
        if (baseUnit.isDeath()) {
            //角色处于死亡状态
            logger.info("角色{}请求移动到({}.{})失败，角色处于死亡状态", role.getId(), target.getX(), target.getY());
            return;
        }
        Session session = SessionManager.getSessionByAccountId(role.getAccountId());
        if (GetBean.getMapManager().allowMove(target.getX(), target.getY(), getMapId())) {
            role.setX(target.getX());
            role.setY(target.getY());
            GetBean.getRoleManager().save(role);
            //获取角色战斗单位
            baseUnit.setPosition(Position.valueOf(target.getX(), target.getY()));
            //对所有在线用户发送地图信息
            GetBean.getMapManager().sendMessageToUsers(role.getMap(), role.getSceneId());
            logger.info("角色{}请求移动到({}.{})成功", role.getId(), target.getX(), target.getY());
        } else {
            session.sendPromptMessage(PromptCode.MOVEFAIL, "");
            logger.info("角色{}请求移动到({}.{})失败", role.getId(), target.getX(), target.getY());
        }
    }

    public MoveCommand(Position target, Role role) {
        super(role.getAccountId(), role.getMap());
        this.target = target;
        this.role = role;
        setTaskName("角色移动command");
    }

    public MoveCommand(Position target, Role role, int mapId) {
        super(role.getAccountId(), mapId, role.getMap());
        this.target = target;
        this.role = role;
        setTaskName("角色移动command");
    }

    @Override
    public String getTaskName() {
        return "MoveCommand";
    }
}
