package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.ActivityListBean;
import com.arttraining.api.dao.ActivitiesMapper;
import com.arttraining.api.pojo.Activities;
import com.arttraining.api.service.IActivitiesService;

@Service("activitiesService")
public class ActivitiesService implements IActivitiesService {
	@Resource
	private ActivitiesMapper activityDao;
	
	@Override
	public List<ActivityListBean> getActivityList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.activityDao.selectActivityList(map);
	}

	@Override
	public Activities getOneActivityByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.activityDao.selectByPrimaryKey(id);
	}

}
