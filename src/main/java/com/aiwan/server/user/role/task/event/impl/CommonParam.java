package com.aiwan.server.user.role.task.event.impl;

import com.aiwan.server.user.role.player.model.Role;
import com.aiwan.server.user.role.task.event.AbstractTaskParam;
import com.aiwan.server.user.role.task.process.TaskProgressType;

/**
 * 通用参数类，也就是没有出抽象类之外的参数的
 */
public class CommonParam extends AbstractTaskParam {
    public CommonParam(Role role, TaskProgressType taskProgressType) {
        super(role, taskProgressType);
    }
}
