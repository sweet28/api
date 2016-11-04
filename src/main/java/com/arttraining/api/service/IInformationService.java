package com.arttraining.api.service;

import java.util.List;

import com.arttraining.api.bean.InformationListBean;
import com.arttraining.api.bean.InformationShowBean;

public interface IInformationService {
	//查询头条信息列表
	List<InformationListBean> getInformationList();
	//依据头条ID查询头条信息
	InformationShowBean getOneInformation(Integer id);
}
