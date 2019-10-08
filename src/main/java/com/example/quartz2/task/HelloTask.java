package com.example.quartz2.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**  任务驻留在内存 **/
@Slf4j
@Component
public class HelloTask {

    private String methodParam;

    public void run(){
        log.info("This is a HelloTask");
    }
}
