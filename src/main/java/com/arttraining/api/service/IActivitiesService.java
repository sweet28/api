package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.ActivityListBean;
import com.arttraining.api.pojo.Activities;

public interface IActivitiesService {
	 //获取活动列表 --activities/list接口调用
    List<ActivityListBean> getActivityList(Map<String, Object> map);
    //依据活动ID查询某个活动详情--activities/show接口调用
    Activities getOneActivityByPrimaryKey(Integer id);
}
