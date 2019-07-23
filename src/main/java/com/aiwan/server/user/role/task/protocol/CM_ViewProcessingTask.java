package com.aiwan.server.user.role.task.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * 查看进行中的任务
 *
 * @author dengzebiao
 * @since 2019.7.22
 */
@ProtocolAnnotation(protocol = Protocol.VIEW_PROCESSING_TASK)
public class CM_ViewProcessingTask implements Serializable {

}
