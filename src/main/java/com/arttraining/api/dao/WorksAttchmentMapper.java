package com.arttraining.api.dao;

import com.arttraining.api.pojo.WorksAttchment;

public interface WorksAttchmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorksAttchment record);

    int insertSelective(WorksAttchment record);

    WorksAttchment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorksAttchment record);

    int updateByPrimaryKey(WorksAttchment record);
    
    int updateByWorkId(WorksAttchment record);
    
    //根据用户ID获取名师测评详情 --assessments/master/show接口调用
    WorksAttchment selectOneAttByWorkId(Integer id);
}