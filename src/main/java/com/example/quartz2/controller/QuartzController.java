package com.example.quartz2.controller;

import com.example.quartz2.config.QuartzSchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/quartz2")
@RestController
@Slf4j
public class QuartzController {

    @Autowired
    private QuartzSchedulerService quartzSchedulerService;


}
