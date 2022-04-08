package com.kaan.kalaha.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext ctx = null;

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        ctx = context;
    }

    public static String getProperty(String key) {
        return getEnvironment().getProperty(key);
    }

    public static String getPropertyOrDefault(String key, String defaultValue) {
        return getEnvironment().getProperty(key, defaultValue);
    }

    public static Environment getEnvironment() {
        return ctx.getEnvironment();
    }

}
