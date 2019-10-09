package com.example.quartz2.controller;

import com.example.quartz2.config.QuartzSchedulerService;
import com.example.quartz2.mapper.JobEntityMapper;
import com.example.quartz2.model.JobEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/quartz2")
@RestController
@Slf4j
public class QuartzController {

    @Autowired
    private QuartzSchedulerService quartzSchedulerService;
    @Autowired
    private JobEntityMapper jobEntityMapper;


    @GetMapping("/getList")
    public Object getJobList(){
        List<JobEntity> all = jobEntityMapper.findAll();
        return all;
    }

    @GetMapping("/getById")
    public Object getJob(@RequestParam String id){
        JobEntity jobEntity = jobEntityMapper.selectById(id);
        return jobEntity;
    }

    @GetMapping("/pause")
    public Object pauseJob(@RequestParam String id){

        JobEntity jobEntity = jobEntityMapper.selectById(id);
        if(jobEntity!=null){
            quartzSchedulerService.pauseJob(jobEntity);
            jobEntity.setStatus(1);
            jobEntityMapper.updateById(jobEntity);
            return "暂停任务"+jobEntity;
        }
        return "暂停失败"+id;
    }

    @GetMapping("/resume")
    public Object resumeJob(@RequestParam String id){
        JobEntity jobEntity = jobEntityMapper.selectById(id);
        if(jobEntity!=null){
            quartzSchedulerService.resumeJob(jobEntity);
            jobEntity.setStatus(0);
            jobEntityMapper.updateById(jobEntity);
            return "重启任务"+jobEntity;
        }
        return "重启失败"+id;
    }

}
