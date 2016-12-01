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
   
    //根据名师ID获取数量接口--masters/num接口调用
    int selectAssNumByMaster(Map<String, Object> map);
    //依据订单号和订单ID来获取测评数量 orders/list_my接口调用
    int selectAssStatusByOrderId(Map<String, Object> map);
    //依据名师ID和作品ID查询测评ID
    int selectAssIdByMaster(Map<String, Object> map);
}