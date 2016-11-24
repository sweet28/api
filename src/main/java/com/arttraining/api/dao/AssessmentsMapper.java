package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.MasterAssessmentBean;
import com.arttraining.api.pojo.Assessments;

public interface AssessmentsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Assessments record);

    int insertSelective(Assessments record);

    Assessments selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Assessments record);

    int updateByPrimaryKey(Assessments record);
    
    int updateByOrderNumber(Assessments record);
    
    //根据用户ID获取名师待测评列表--assessments/list/no接口调用
    List<MasterAssessmentBean> selectAssessmentNoListByMaster(Map<String, Object> map);
    //根据用户ID获取名师已测评列表 assessments/list/yes接口调用
    List<MasterAssessmentBean> selectAssessmentYesListByMaster(Map<String, Object> map);
   
}