package com.example.quartz2.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestTask {


    private String methodParam;

    public void run(){
        log.info("This is a TestTask");
    }
}
