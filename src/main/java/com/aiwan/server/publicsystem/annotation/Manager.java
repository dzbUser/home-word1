package com.aiwan.server.publicsystem.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author dengzebiao
 * @since 2019.7.1
 * 表示Manager类
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Manager {
}
