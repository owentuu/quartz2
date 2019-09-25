package com.example.quartz2.task;

import org.springframework.stereotype.Component;

/**  任务驻留在内存 **/
@Component
public class HelloTask {

    private String methodParam;

    public void run(){
        System.out.println("hello");
    }
}
