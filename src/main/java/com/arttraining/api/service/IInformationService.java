package com.arttraining.api.service;

import java.util.List;

import com.arttraining.api.bean.InformationListBean;
import com.arttraining.api.bean.InformationShowBean;
import com.arttraining.api.bean.MasterInfoListBean;

public interface IInformationService {
	//查询头条信息列表--information/list接口调用
	List<InformationListBean> getInformationList();
	//依据头条ID查询头条信息
	InformationShowBean getOneInformation(Integer id);
	
	//爱好者首页返回资讯列表信息
    List<MasterInfoListBean> getInfoListByHomePage();
}
