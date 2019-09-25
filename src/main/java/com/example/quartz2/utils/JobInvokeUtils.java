package com.example.quartz2.utils;

import com.example.quartz2.model.JobEntity;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
public class JobInvokeUtils {


    public static void invokeMethod(JobEntity jobEntity){
        String beanName = jobEntity.getBeanName();
        String methodName = jobEntity.getMethodName();
        invokeMethod(beanName,methodName);
    }

    public static void invokeMethod(String beanName,String methodName){
        Object bean = SpringUtils.getBean(beanName);
        if (bean == null) {
            log.error("任务名称 = [" + beanName + "]---------------未启动成功，请检查是否配置正确！！！");
            return;
        }
        Class<?> clazz = bean.getClass();
        Method method=null;
        try {
            method=clazz.getDeclaredMethod(methodName);
        }catch (NoSuchMethodException e){
            log.error("任务名称 = [" + beanName + "]---------------未启动成功，方法名["+methodName+"]设置错误！！！");
        }catch (Exception e){
            e.printStackTrace();
        }
        if(method!=null){
            try {
                method.invoke(bean);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        System.out.println("任务名称 = [" + beanName + "]----------启动成功");
    }
}
