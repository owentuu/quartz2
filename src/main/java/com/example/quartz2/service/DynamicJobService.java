//package com.example.quartz2.service;
//
//import com.example.quartz2.config.AbstractQuartzJob;
//import com.example.quartz2.config.QuartzDisallowConcurrentExecution;
//import com.example.quartz2.mapper.JobEntityMapper;
//import com.example.quartz2.model.JobEntity;
//import org.quartz.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class DynamicJobService {
//
//    @Autowired
//    private JobEntityMapper mapper;
//    //通过Id获取Job
//    public JobEntity getJobEntityById(Integer id) {
//        return mapper.selectById(id);
//    }
//    //从数据库中加载获取到所有Job
//    public List<JobEntity> loadJobs() {
//        List<JobEntity> list = mapper.findAll();
//        return list;
//    }
//    //获取JobDataMap.(Job参数对象)
//    public JobDataMap getJobDataMap(JobEntity job) {
//        JobDataMap map = new JobDataMap();
////        map.put("name", job.getJobName());
////        map.put("group", job.getJobGroup());
////        map.put("cronExpression", job.getCronExpression());
////        map.put("parameter", job.getMethodParams());
////        map.put("status", job.getStatus());
//        map.put("prop",job);
//        return map;
//    }
//    //获取JobDetail,JobDetail是任务的定义,而Job是任务的执行逻辑,JobDetail里会引用一个Job Class来定义
//    public JobDetail geJobDetail(JobKey jobKey, String description, JobDataMap map) {
//        return JobBuilder.newJob(QuartzDisallowConcurrentExecution.class)
//                .withIdentity(jobKey)
//                .withDescription(description)
//                .setJobData(map)
//                .storeDurably()
//                .build();
//    }
//    //获取Trigger (Job的触发器,执行规则)
//    public Trigger getTrigger(JobEntity job) {
//        return TriggerBuilder.newTrigger()
//                .withIdentity(job.getJobName(), job.getJobGroup())
//                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression()))
//                .build();
//    }
//    //获取JobKey,包含Name和Group
//    public JobKey getJobKey(JobEntity job) {
//        return JobKey.jobKey(job.getJobName(), job.getJobGroup());
//    }
//}
