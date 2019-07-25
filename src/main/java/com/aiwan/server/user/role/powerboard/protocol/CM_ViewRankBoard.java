package com.aiwan.server.user.role.powerboard.protocol;

import com.aiwan.server.publicsystem.annotation.ProtocolAnnotation;
import com.aiwan.server.util.Protocol;

import java.io.Serializable;

/**
 * 查看排行榜
 *
 * @author dengzebiao
 * @since 2019.7.25
 */
@ProtocolAnnotation(protocol = Protocol.VIEW_RANKBOARD)
public class CM_ViewRankBoard implements Serializable {
}
