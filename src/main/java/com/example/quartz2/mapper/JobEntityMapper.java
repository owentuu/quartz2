package com.example.quartz2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.quartz2.model.JobEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface JobEntityMapper extends BaseMapper<JobEntity> {

    List<JobEntity> findAll();
}
