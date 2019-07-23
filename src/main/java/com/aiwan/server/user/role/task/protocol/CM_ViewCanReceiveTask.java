package com.aiwan.server.user.role.task.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * 查看可领取任务
 *
 * @author dengzebiao
 * @since 2019.7.23
 */
@ProtocolAnnotation(protocol = Protocol.VIEW_CANRECEIVE_TASK)
public class CM_ViewCanReceiveTask implements Serializable {
}
