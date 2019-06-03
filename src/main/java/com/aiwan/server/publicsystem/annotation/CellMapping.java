package com.aiwan.server.publicsystem.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解，用于定位静态地图资源成员
 * */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
//@Documented
//@Component
//@Scope("prototype")
public @interface CellMapping {
    String name();
}
