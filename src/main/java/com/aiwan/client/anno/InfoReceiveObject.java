package com.aiwan.client.anno;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 客户端信息接收类自定义注解
 * @author dengzebiao
 * @since 2019.6.11
 * */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Scope("prototype")
public @interface InfoReceiveObject {

}
