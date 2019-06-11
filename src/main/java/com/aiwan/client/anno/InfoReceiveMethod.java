package com.aiwan.client.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 客户端信息接收方法注解
 * @author dengzebiao
 * @since 2019.6.11
 * */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InfoReceiveMethod {
    /** 状态码 */
    int status();
}
