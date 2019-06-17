package com.aiwan.client.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author dengzebiao
 * @since 2019.6.17
 * 获取bean
 * */
public class GetBean {

    /**  spring全文*/
    private static ClassPathXmlApplicationContext applicationContext;

    public static ClassPathXmlApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ClassPathXmlApplicationContext applicationContext) {
        GetBean.applicationContext = applicationContext;
    }

    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }
}
