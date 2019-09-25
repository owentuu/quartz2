package com.example.quartz2.config;

import com.example.quartz2.model.JobEntity;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Slf4j
public abstract class AbstractQuartzJob implements Job {

    /**
     * 线程本地变量
     */
    private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        JobEntity jobEntity=new JobEntity();
        BeanUtils.copyProperties(context.getMergedJobDataMap().get("prop"),jobEntity);
        try
        {
            before(context, jobEntity);
            if (jobEntity != null)
            {
                doExecute(context, jobEntity);
            }
            after(context, jobEntity, null);
        }
        catch (Exception e)
        {
            log.error("任务执行异常  - ：", e);
            after(context, jobEntity, e);
        }
    }

    /**
     * 执行前
     *
     * @param context 工作执行上下文对象
     * @param sysJob 系统计划任务
     */
    protected void before(JobExecutionContext context, JobEntity sysJob)
    {
        threadLocal.set(new Date());
    }

    /**
     * 执行后
     *
     * @param context 工作执行上下文对象
     * @param sysJob 系统计划任务
     */
    protected void after(JobExecutionContext context, JobEntity sysJob, Exception e)
    {
        Date startTime = threadLocal.get();
        threadLocal.remove();

//        final SysQuartzJobLog sysJobLog = new SysQuartzJobLog();
//        sysJobLog.setJobName(sysJob.getJobName());
//        sysJobLog.setJobGroup(sysJob.getJobGroup());
//        sysJobLog.setInvokeTarget(sysJob.getInvokeTarget());
//        sysJobLog.setStartTime(startTime);
//        sysJobLog.setEndTime(new Date());
//        long runMs = sysJobLog.getEndTime().getTime() - sysJobLog.getStartTime().getTime();
//        sysJobLog.setJobMessage(sysJobLog.getJobName() + " 总共耗时：" + runMs + "毫秒");
//        if (e != null)
//        {
//            sysJobLog.setStatus(ScheduleConstants.FAIL_STATUS);
//            String errorMsg = StringUtils.substring(ExceptionUtil.getMessage(e), 0, 2000);
//            sysJobLog.setExceptionInfo(errorMsg);
//        }
//        else
//        {
//            sysJobLog.setStatus(ScheduleConstants.SUCCESS_STATUS);
//        }
//        //  这里获取service然后插入库中
//        SpringUtils.getBean(SysQuartzJobLogService.class).insertSelective(sysJobLog);
    }

    /**
     * 子类去实现
     * @param jobExecutionContext
     * @param jobEntity
     */
    protected abstract void doExecute(JobExecutionContext jobExecutionContext, JobEntity jobEntity) throws Exception;
}
