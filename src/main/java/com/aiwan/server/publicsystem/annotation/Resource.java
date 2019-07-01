package com.aiwan.server.publicsystem.annotation;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author dengzebiao
 * 静态资源对象声明注释
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Scope("prototype")
public @interface Resource {

    /**
     * 资源位置
     */
    String value() default "";
}
