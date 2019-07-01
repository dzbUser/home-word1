package com.aiwan.server.publicsystem.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dengzebiao
 * @since 2019.7.1
 * 静态资源注入注释
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Static {
    /**
     * 加载解析的方法
     *
     * @return
     */
    String initializeMethodName() default "";
}
