package com.ff.spring.quartzspring.web;

import com.ff.spring.quartzspring.dao.QuartzDao;
import com.ff.spring.quartzspring.pojo.Quartz;
import com.ff.spring.quartzspring.quartzJob.BaseJob;
import com.ff.spring.quartzspring.service.serviceImpl.QuartzManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;

@Controller
public class QuartzController {
    protected final Logger logger= LoggerFactory.getLogger(QuartzController.class);
    @Autowired
    private QuartzManagerImpl quartzManagerImpl;
    @Autowired
    private QuartzDao quartzDao;
    @PostConstruct
    public void initQuartz() {
        Quartz quartz = quartzDao.loadById("1");
        //logger.info();
        String b = "a";
    }

    //添加一个job
    @RequestMapping(value="/addJob",method= RequestMethod.POST)
    public String addjob(@RequestBody Quartz quartz) throws Exception {
        quartzManagerImpl.addJob(quartz);
        return "redirect:listCategory";
    }
}
