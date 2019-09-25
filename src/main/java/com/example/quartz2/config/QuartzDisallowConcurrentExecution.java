package com.example.quartz2.config;

import com.example.quartz2.model.JobEntity;
import com.example.quartz2.utils.JobInvokeUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（禁止并发执行）
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {
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
