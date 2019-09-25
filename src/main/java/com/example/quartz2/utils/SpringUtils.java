package com.example.quartz2.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils implements ApplicationContextAware {

    public static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext=applicationContext;
    }

    public static Object getBean(String beanName) {

        Object bean =null;
        if(applicationContext==null) return null;
        try {
            String name = getBeanName(beanName);
            bean = applicationContext.getBean( name);
        }finally {
            return bean;
        }
    }

    private static  String getBeanName(String name)
    {
        String beanName = StringUtils.substringBefore(name, "(");
        if(beanName.contains(".")){
            return firstLowerCase(StringUtils.substringAfterLast(beanName, "."));
        }
        return firstLowerCase(beanName);
    }

    /**
     * 首字母小写
     *
     * @param name
     * @return
     */
    private static String firstLowerCase(String name) {
        name = name.substring(0, 1).toLowerCase() + name.substring(1);
        return name;

    }
}
