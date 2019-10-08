package com.example.quartz2.config;


import com.example.quartz2.model.JobEntity;
import com.example.quartz2.utils.JobInvokeUtils;
import org.quartz.JobExecutionContext;

/**
 * 允许并发执行
 */
public class QuartzJobExecution  extends AbstractQuartzJob{
    /**
     * 子类去实现
     *
     * @param jobExecutionContext
     * @param jobEntity
     */
    @Override
    protected void doExecute(JobExecutionContext jobExecutionContext, JobEntity jobEntity) throws Exception {
        JobInvokeUtils.invokeMethod(jobEntity);
    }
}
