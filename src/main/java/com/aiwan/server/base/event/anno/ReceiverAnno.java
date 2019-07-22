package com.aiwan.server.base.event.anno;

import java.lang.annotation.*;

/**
 * 事件接收器注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.METHOD})
public @interface ReceiverAnno {

}
