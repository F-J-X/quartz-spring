package com.ff.spring.quartzspring.quartzJob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestJobTask  extends BaseJob{
    private static final Logger logger = LoggerFactory.getLogger(TestJobTask.class);

    @Override
    public void run() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 日期时间转字符串
        logger.info("TestJobTask具体逻辑开始"+formatter.format(new Date()));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("TestJobTask具体逻辑结束"+formatter.format(new Date()));
    }
}
