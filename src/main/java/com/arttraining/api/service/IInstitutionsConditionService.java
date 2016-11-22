package com.arttraining.api.service;

import java.util.List;

import com.arttraining.api.bean.InstitutionConditionBean;

public interface IInstitutionsConditionService {
	 //获取院校地域、类型条件列表--institutions/conditions接口调用
    List<InstitutionConditionBean> getConditionList();
}
