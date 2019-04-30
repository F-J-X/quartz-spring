package com.ff.spring.quartzspring.quartzJob;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public abstract class BaseJob implements Job {
    protected final Logger logger= LoggerFactory.getLogger(BaseJob.class);
    // 执行业务逻辑
    public abstract void run();

    @Override
    public void execute(JobExecutionContext context) {
        try{
            JobDataMap jobDataMap = context.getMergedJobDataMap();
            if(jobDataMap.get("jobRunType")!=null&&"sync".equals(jobDataMap.get("jobRunType"))){
                synchronized (context.getJobDetail().getJobClass()){
                    logger.info("任务开始执行--------------");
                    run();
                    logger.info("任务执行结束----------------");
                }
            }else{
                logger.info("任务开始执行--------------");
                run();
                logger.info("任务执行结束----------------");
            }
        }catch(Exception t){
            logger.error("任务执行失败", t);
            t.printStackTrace();
        }
    }

}
