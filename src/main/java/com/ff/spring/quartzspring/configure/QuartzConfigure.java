package com.ff.spring.quartzspring.configure;

import com.ff.spring.quartzspring.dao.QuartzDao;
import com.ff.spring.quartzspring.pojo.Quartz;
import com.ff.spring.quartzspring.service.serviceImpl.QuartzManagerImpl;
import com.ff.spring.quartzspring.web.QuartzController;
import org.quartz.Scheduler;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class QuartzConfigure {
    protected final Logger logger= LoggerFactory.getLogger(QuartzController.class);
    @Autowired
    private JobFactory jobFactory;
    @Autowired
    private QuartzManagerImpl quartzManagerImpl;

    @PostConstruct
    public void initQuartz() {

        logger.info("启动自动开启的任务------------");
        /*Quartz quartz = new Quartz();
        quartz.setJobName("aac1");
        quartz.setJobGroup("bbbc1");
        quartz.setJobRunType("sync");
        quartz.setCronExpression("0/3 * * * * ?");
        quartz.setJobClassPath("com.ff.spring.quartzspring.quartzJob.TestJobTask");
        quartzManagerImpl.addJob(quartz);*/
       /* Quartz quartz1 = new Quartz();
        quartz1.setJobName("aa1");
        quartz1.setJobGroup("bbb1");
        quartz1.setCronExpression("0/41 * * * * ?");
        quartz1.setJobClassPath("com.ff.spring.quartzspring.quartzJob.TestJobTask");
        quartzManagerImpl.addJob(quartz1);*/
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {

        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setQuartzProperties(quartzProperties().getObject());
        //使用数据源，自定义数据源
        factory.setJobFactory(jobFactory);
        factory.setWaitForJobsToCompleteOnShutdown(true);//这样当spring关闭时，会等待所有已经启动的quartz job结束后spring才能完全shutdown。
        factory.setOverwriteExistingJobs(false);
        factory.setStartupDelay(1);
        return factory;
    }

    /*
     * 通过SchedulerFactoryBean获取Scheduler的实例
     */
    @Bean(name="scheduler")
    public Scheduler scheduler() throws IOException {
        return schedulerFactoryBean().getScheduler();
    }

    /**
     * 设置quartz属性
     */
    public PropertiesFactoryBean quartzProperties() throws IOException {
        //获取配置属性
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        //在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        //创建SchedulerFactoryBean

        return propertiesFactoryBean;
    }
}
