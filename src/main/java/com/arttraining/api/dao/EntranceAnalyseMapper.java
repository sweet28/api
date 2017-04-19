package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import com.arttraining.api.beanv3.EntranceCollegeBean;
import com.arttraining.api.pojo.EntranceAnalyse;

public interface EntranceAnalyseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EntranceAnalyse record);

    int insertSelective(EntranceAnalyse record);

    EntranceAnalyse selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EntranceAnalyse record);

    int updateByPrimaryKey(EntranceAnalyse record);
    
    //coffee add 0417 输入省术科统考成绩和文化成绩 推荐相应的院校列表
    List<EntranceCollegeBean> selectCollegeList(Map<String, Object> map);
}