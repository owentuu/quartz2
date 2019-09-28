package com.example.quartz2.task;

import org.springframework.stereotype.Component;

@Component
public class TestTask {


    private String methodParam;

    public void run(){
        System.out.println("test");
    }
}
