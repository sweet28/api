package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.MasterAssessmentBean;

public interface IAssessmentService {
	//根据用户ID获取名师待测评列表--assessments/list/no接口调用
    List<MasterAssessmentBean> getAssessmentNoListByMaster(Map<String, Object> map);
    //根据用户ID获取名师已测评列表 assessments/list/yes接口调用
    List<MasterAssessmentBean> getAssessmentYesListByMaster(Map<String, Object> map);
    //根据名师ID获取数量接口--masters/num接口调用
    int getAssNumByMaster(Map<String, Object> map);
    //依据订单号和订单ID来获取测评数量 orders/list_my接口调用
    int getAssStatusByOrderId(Map<String, Object> map);
    //依据名师ID和作品ID查询测评ID
    int getAssIdByMaster(Map<String, Object> map);
}
