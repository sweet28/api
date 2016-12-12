package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.MasterCourseListBean;
import com.arttraining.api.pojo.course;

public interface courseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(course record);

    int insertSelective(course record);

    course selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(course record);

    int updateByPrimaryKey(course record);
    //名师端课程列表接口--course/master/list接口调用
    List<MasterCourseListBean> selectCourseListByMaster(Map<String, Object> map);
}