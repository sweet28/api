package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.InstitutionsListBean;
import com.arttraining.api.bean.InstitutionsShowBean;

public interface IInstitutionService {
	 //获取院校信息列表--institutions/list接口调用
    List<InstitutionsListBean> getInstitutionsList(Map<String,Object> map);
    //根据院校ID获取院校详情信息--institutions/show接口调用
    InstitutionsShowBean getInstitutionsShow(Integer id);
}
