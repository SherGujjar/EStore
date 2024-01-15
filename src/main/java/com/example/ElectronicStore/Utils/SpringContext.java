package com.example.ElectronicStore.Utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;

    private static final Map<Class<?>, Object> secondaryContext = new HashMap();
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setContext(context);
    }

    public static <T> T getBean(Class<T> beanClass) {
            return context.getBean(beanClass);
    }

    private static synchronized void setContext(ApplicationContext c) {
        context = c;
    }
}
