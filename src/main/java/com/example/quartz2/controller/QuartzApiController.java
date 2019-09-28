//package com.example.quartz2.controller;
//
//import com.example.quartz2.model.JobEntity;
//import com.example.quartz2.service.DynamicJobService;
//import lombok.extern.slf4j.Slf4j;
//import org.quartz.*;
//import org.quartz.impl.matchers.GroupMatcher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.PostConstruct;
//import java.util.Set;
//
///**
// * @author 94492
// * @date 2019-9-20
// */
//@Slf4j
//@RestController
//@RequestMapping("/quartz")
//public class QuartzApiController {
////
//    @Autowired
//    private Scheduler scheduler;
//
//    @Autowired
//    DynamicJobService service;
//
//    //初始化启动所有的Job
//    @PostConstruct
//    public void initialize() {
//        try {
//            reStartAllJobs();
//            log.info("INIT SUCCESS");
//        } catch (SchedulerException e) {
//            log.info("INIT EXCEPTION : " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 重新启动所有的job
//     */
//    private void reStartAllJobs() throws SchedulerException {
//        synchronized (this) {                                                         //只允许一个线程进入操作
//            Set<JobKey> set = scheduler.getJobKeys(GroupMatcher.anyGroup());
//            scheduler.pauseJobs(GroupMatcher.anyGroup());                               //暂停所有JOB
//            for (JobKey jobKey : set) {                                                 //删除从数据库中注册的所有JOB
//                scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
//                scheduler.deleteJob(jobKey);
//            }
//            for (JobEntity job : service.loadJobs()) {                               //从数据库中注册的所有JOB
//                log.info("Job register name : {} , group : {} , cron : {}", job.getJobName(), job.getJobGroup(), job.getCronExpression());
//                JobDataMap map = service.getJobDataMap(job);
//                JobKey jobKey = service.getJobKey(job);
//                JobDetail jobDetail = service.geJobDetail(jobKey, "", map);
//                scheduler.scheduleJob(jobDetail,service.getTrigger(job));
////                if (job.getStatus().equals("OPEN")) scheduler.scheduleJob(jobDetail, service.getTrigger(job));
////                else
////                    log.info("Job jump name : {} , Because {} status is {}", job.getName(), job.getName(), job.getStatus());
//            }
//        }
//    }
//
//
//}
