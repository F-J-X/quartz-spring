package com.ff.spring.quartzspring.dao;

import com.ff.spring.quartzspring.pojo.Quartz;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Mapper
@Repository
public interface QuartzDao {

    public Quartz loadById(String id);

    public void insert(Quartz schedulerJobBO);

    public void update(Quartz schedulerJobBO);

    public void delete(String id);

    public List<Quartz> list(Map<String, Object> params);


}
