package com.example.quartz2.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Data
@TableName("job_entity")
public class JobEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id **/
    private String id;

    /** 任务名称 **/
    private String jobName;

    /** 任务组名 **/
    private String jobGroup;

    /** 调用类名 **/
    private String beanName;

    private String methodName;

    @TableField(exist = false)
    private String methodParams;

    /** cron执行表达式 **/
    private String cronExpression;

    /** cron计划策略 **/
    @TableField(exist = false)
    private String misfirePolicy;

    /** 是否并发执行（0允许 1禁止） **/
//    @TableField(exist = false)
    private String concurrent;

    /** 任务状态（0正常 1暂停） **/
    private Integer status;

}
