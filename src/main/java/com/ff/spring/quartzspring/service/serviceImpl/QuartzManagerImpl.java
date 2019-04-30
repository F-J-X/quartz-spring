package com.ff.spring.quartzspring.service.serviceImpl;

import com.ff.spring.quartzspring.pojo.Quartz;
import com.ff.spring.quartzspring.web.QuartzController;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class QuartzManagerImpl {
    protected final Logger logger= LoggerFactory.getLogger(QuartzManagerImpl.class);
    @Autowired
    @Qualifier("scheduler")
    private Scheduler scheduler;

    /**
     * 根据class name获取class对象
     *
     */
    private Class<? extends Job> getClassByPath(String jobClassPath) throws Exception {
        Class<? extends Job> clazz;
        try {
            clazz = (Class<? extends Job>) Class.forName(jobClassPath);
        } catch (Exception e) {

            throw new SchedulerException("任务类加载失败！!");
        }
        return clazz;
    }

    /**
     * 根据class name获取class对象
     *
     */
    private void setMapMsg(Map<String,Object> msgMap,String status, String msg)  {
        msgMap.put("status", status);
        msgMap.put("msg", msg);
        logger.info(msg);
    }

    /**
     * 新建一个任务
     *
     */
    public Map<String,Object> addJob(Quartz quartz)   {
        Map<String, Object> msgMap = new HashMap<>();
        if (!CronExpression.isValidExpression(quartz.getCronExpression())) {
            setMapMsg(msgMap,"fail", "表达式格式不正确");
            return msgMap;   //表达式格式不正确
        }
        try {
            Class<? extends Job> jobClass = getClassByPath(quartz.getJobClassPath());
            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(quartz.getJobName(), quartz.getJobGroup()).build();
            //表达式调度构建器  加上 withMisfireHandlingInstructionDoNothing防止启动就运行
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartz.getCronExpression()).withMisfireHandlingInstructionDoNothing();
            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(quartz.getJobName(), quartz.getJobGroup())
                    .withSchedule(scheduleBuilder).build();
            //传递参数
            if (quartz.getInvokeParam() != null && !"".equals(quartz.getInvokeParam())) {
                trigger.getJobDataMap().put("invokeParam", quartz.getInvokeParam());
            }
            if (quartz.getJobRunType() != null && !"".equals(quartz.getJobRunType())) {
                trigger.getJobDataMap().put("invokeParam", quartz.getInvokeParam());
            }
            scheduler.scheduleJob(jobDetail, trigger);
        }catch (Exception e) {
            setMapMsg(msgMap,"fail", "创建定时任务失败");
            e.printStackTrace();
        }
        return msgMap;
    }



    //暂停所有任务
    public void pauseAllJob()  {
        try {
            scheduler.pauseAll();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    //暂停某个任务
    public Map<String,Object> pauseJob(String jobName, String jobGroup)  {
        Map<String, Object> msgMap = new HashMap<>();
        JobKey jobKey = new JobKey(jobName, jobGroup);
        JobDetail jobDetail = null;
        try {
            jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail == null) {
                setMapMsg(msgMap,"fail", "暂停定时任务失败");
            }else {
                scheduler.pauseJob(jobKey);
                setMapMsg(msgMap,"success", "暂停定时任务成功");
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            setMapMsg(msgMap,"fail", "暂停定时任务失败");
        }
       return msgMap;

    }

    //恢复所有任务
    public void resumeAllJob()  {
        try {
            scheduler.resumeAll();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    // 恢复某个任务
    public Map<String, Object> resumeJob(String jobName, String jobGroup)  {
        Map<String, Object> msgMap = new HashMap<>();
        JobKey jobKey = new JobKey(jobName, jobGroup);
        JobDetail jobDetail = null;
        try {
            jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail == null) {
                setMapMsg(msgMap,"fail", "恢复定时任务失败");
            }else {
                scheduler.resumeJob(jobKey);
                setMapMsg(msgMap,"success", "恢复定时任务成功");
            }
        } catch (SchedulerException e) {
            setMapMsg(msgMap,"fail", "恢复定时任务失败");
            e.printStackTrace();
        }
        return msgMap;
    }

    //删除某个任务
    public Map<String, Object>  deleteJob(Quartz quartz)  {
        Map<String, Object> msgMap = new HashMap<>();
        JobKey jobKey = new JobKey(quartz.getJobName(), quartz.getJobGroup());
        JobDetail jobDetail = null;
        try {
            jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail == null || !scheduler.checkExists(jobKey)) {
                setMapMsg(msgMap,"fail", "删除定时任务失败");
            }else {
                scheduler.deleteJob(jobKey);
                setMapMsg(msgMap,"success", "删除定时任务成功");
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return msgMap;
    }

    //修改任务
    public  Map<String, Object>  modifyJob(Quartz quartz)  {
        Map<String, Object> msgMap = new HashMap<>();
        // 先删除之前的再添加一个新的
        Quartz beforeQuartz = quartz;
        msgMap = deleteJob(beforeQuartz);
        if("success".equals(msgMap.get("status"))){
            msgMap = addJob(quartz);
        }
        return msgMap;
    }
}
