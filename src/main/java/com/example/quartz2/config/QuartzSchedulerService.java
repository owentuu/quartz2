package com.example.quartz2.config;

import com.example.quartz2.mapper.JobEntityMapper;
import com.example.quartz2.model.JobEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Configuration
public class QuartzSchedulerService {

    @Autowired
    private JobEntityMapper mapper;

    @Autowired
    private Scheduler scheduler;

    private final  String TEST_CRON="0 0/1 * * * ?";


    @PostConstruct
    public void init() {

        List<JobEntity> jobEntities = mapper.findAll();
        for (JobEntity job : jobEntities) {
            try {
                //防止因为数据问题重复创建
                if(checkJobExists(job))
                {
                    deleteJob(job);
                }
                createSchedule(job);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
        //这一块可以从数据库中查
//        for (int i=1;i<=1;i++)
//        {
//            SysQuartzJob job=new SysQuartzJob();
//            job.setId("332182389491109888");
//            job.setJobName("v2Task2");
//            job.setJobGroup("SYSTEM");
//            job.setCronExpression("*/6 * * * * ?");
//            //并发执行
//            job.setConcurrent("0");
//            //0启用
//            job.setStatus(1);
//            //执行的job类
//            job.setInvokeTarget("v2Task.runTask2(1,2l,'asa',true,2D)");
//            try {
//                //防止因为数据问题重复创建
//                if(checkJobExists(job))
//                {
//                    deleteJob(job);
//                }
//                createSchedule(job);
//	        } catch (SchedulerException e) {
//	            e.printStackTrace();
//	        }
//        }

        start();
    }


    /**
     * 启动定时器
     */
    public void start(){
        try {
            scheduler.start();
        }catch (SchedulerException e){
            e.printStackTrace();
            log.error("启动定时器失败");
        }
    }

    /**
     * 立即执行一个定时任务
     */
    public void run(JobEntity jobEntity) throws SchedulerException{
        JobDataMap map=new JobDataMap();
        map.put(ScheduleConstants.TASK_PROPERTIES,map);
        scheduler.triggerJob(getJobKey(jobEntity),map);
    }

    /**
     * 删除一个定时任务
     * @param jobEntity
     * @return
     */
    public boolean deleteJob(JobEntity jobEntity){
        boolean flag=false;
        try {
            scheduler.deleteJob(getJobKey(jobEntity));
            flag=true;
        }catch (SchedulerException e){
            e.printStackTrace();
            log.error("删除调度任务失败");
        }
        return flag;
    }

    /**
     * 暂停一个定时任务
     * @param jobEntity
     * @return
     */
    public boolean pauseJob(JobEntity jobEntity){
        boolean flag=false;
        try {
            scheduler.pauseJob(getJobKey(jobEntity));
            flag=true;
        }catch (SchedulerException e){
            e.printStackTrace();
            log.error("删除调度任务失败"+e);
        }
        return flag;
    }

    /**
     *  继续执行定时任务
     * @param job
     * @return
     */
    public boolean resumeJob(JobEntity job) {
        boolean flag = false;
        try {
            //JobKey定义了job的名称和组别
//            JobKey jobKey = JobKey.jobKey(ScheduleConstants.TASK_CLASS_NAME+job.getId(), job.getJobGroup());
            JobKey jobKey = getJobKey(job);
            if (jobKey != null) {
                //继续任务
                scheduler.resumeJob(jobKey);
                flag = true;
            }
        } catch (SchedulerException e) {
           log.error("继续调度任务异常:" + e);
        } catch (Exception e) {
            log.error("继续调度任务异常:" + e);
        }
        return flag;
    }

    /**
     * 检查job是存在
     * @param jobEntity
     * @return
     */
    public boolean checkJobExists(JobEntity jobEntity){
        boolean flag=false;
        try {
            flag=scheduler.checkExists(getJobKey(jobEntity));
        }catch (SchedulerException e){
            flag=false;
        }
        return flag;
    }

    /**
     * 修改定时任务
     * @param job
     * @return
     * @throws Exception
     */
    public boolean modifyJob(JobEntity job) {

        try {
            //先删除
            if(checkJobExists(job))
            {
                deleteJob(job);
            }
            createSchedule(job);
        } catch (Exception e) {
            e.printStackTrace();
            return  false;
        }
        return  true;
    }


    /**
     * 获取jobKey
     * @param jobEntity
     * @return
     */
    public JobKey getJobKey(JobEntity jobEntity){
        return  JobKey.jobKey(jobEntity.getJobName(),jobEntity.getJobGroup());
    }


    /**
     * 创建一个定时任务
     */
    public void createSchedule(JobEntity jobEntity) throws SchedulerException{
        if(!checkJobExists(jobEntity)){
            //new job 传入定义的job执行者 是否允许并发
            JobDetail jobDetail = JobBuilder.newJob("0".equals(jobEntity.getConcurrent())?QuartzJobExecution.class:QuartzDisallowConcurrentExecution.class).withIdentity(getJobKey(jobEntity)).build();
            //cron
            String cron = jobEntity.getCronExpression();
            CronScheduleBuilder cronScheduleBuilder =StringUtils.isNotBlank(cron)?CronScheduleBuilder.cronSchedule(cron):CronScheduleBuilder.cronSchedule(TEST_CRON);

            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(jobEntity.getJobName(),jobEntity.getJobGroup())
                    .withSchedule(cronScheduleBuilder.withMisfireHandlingInstructionDoNothing()).build();
            //放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, jobEntity);
            scheduler.scheduleJob(jobDetail, cronTrigger);

            //如果这个工作的状态为1
            if (null!=jobEntity.getStatus()&&jobEntity.getStatus().equals(1))
            {
                pauseJob(jobEntity);
            }
        }
    }
}
