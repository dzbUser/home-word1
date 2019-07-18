package com.aiwan.server.user.role.team.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * 查看申请列表
 *
 * @author dengzebiao
 * @since 2019.7.18
 */
@ProtocolAnnotation(protocol = Protocol.VIEW_APPLCIATION)
public class CM_ViewApplication implements Serializable {
}
