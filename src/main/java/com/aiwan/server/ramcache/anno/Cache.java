package com.aiwan.server.ramcache.anno;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author dengzebiao
 * 用户注解缓存的大小
 * */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Scope("prototype")
public @interface Cache {
    int maxmum()default 0;
}
