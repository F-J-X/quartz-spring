package com.ff.spring.quartzspring.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Quartz {

    private Integer quartzId;
    /**
     * 任务名
     */
    private String jobName;

    private String jobGroup;

    /**
     * 任务类的路径
     */
    private String jobClassPath;
    /**
     * cron表达式
     */
    private String cronExpression;

    /**
     * 执行方式 同步sync 异步async
     */
    private String jobRunType;
    /**
     * 参数
     */
    private String invokeParam;

    /**
     * 是否启动定时
     */
    private Integer isRun;

    /**
     * 启动时间
     */
    private Date startTime;

    /**
     * 运行中的状态
     */
    private String triggerState;

    /**
     * 运行中的状态名
     */
    private String triggerStateName;

    /**
     * 描述
     */
    private String description;
}
