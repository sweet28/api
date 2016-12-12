package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.MasterCourseListBean;
import com.arttraining.api.dao.courseMapper;
import com.arttraining.api.service.ICourseService;

@Service("courseService")
public class CourseService implements ICourseService {
	@Resource
	private courseMapper courseDao;
	
	@Override
	public List<MasterCourseListBean> getCourseListByMaster(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.courseDao.selectCourseListByMaster(map);
	}
	
}
