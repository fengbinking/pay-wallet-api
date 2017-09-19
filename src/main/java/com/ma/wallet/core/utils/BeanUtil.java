package com.ma.wallet.core.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 */
@Component
public class BeanUtil implements ApplicationContextAware {

    public void setApplicationContext(ApplicationContext applicationContext) {
        BeanUtil.applicationContext = applicationContext;
    }

    public BeanUtil getInstance() {
        return new BeanUtil();
    }

    private static ApplicationContext applicationContext = null;

    public static ApplicationContext getAppContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return getAppContext().getBean(name);
    }

    public static <T> T getBean(Class<T> t) {
        return getAppContext().getBean(t);
    }

}