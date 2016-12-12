package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.MasterCourseListBean;

public interface ICourseService {
	//名师端课程列表接口--course/master/list接口调用
    List<MasterCourseListBean> getCourseListByMaster(Map<String, Object> map);
}
