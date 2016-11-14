package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.ActivityListBean;
import com.arttraining.api.pojo.Activities;

public interface ActivitiesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Activities record);

    int insertSelective(Activities record);

    Activities selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Activities record);

    int updateByPrimaryKey(Activities record);
    
    //获取活动列表 --activities/list接口调用
    List<ActivityListBean> selectActivityList(Map<String, Object> map);
}