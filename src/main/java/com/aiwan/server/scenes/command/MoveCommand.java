package com.aiwan.server.scenes.command;

import com.aiwan.server.base.executor.scene.impl.AbstractSceneCommand;

/**
 * 角色移动command
 *
 * @author dengzebiao
 */
public class MoveCommand extends AbstractSceneCommand {

    private int targetX;

    private int targetY;


    @Override
    public void action() {

    }

    public MoveCommand(int targetX, int targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
    }
}
