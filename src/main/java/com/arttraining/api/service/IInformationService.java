package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.InformationListBean;
import com.arttraining.api.bean.InformationShowBean;
import com.arttraining.api.pojo.Information;

public interface IInformationService {
	//查询头条信息列表--information/list接口调用
	List<InformationListBean> getInformationList();
	//依据头条ID查询头条信息
	InformationShowBean getOneInformation(Integer id);
	//爱好者首页返回资讯列表信息
    List<InformationListBean> getInfoListByHomePage();  
    //coffee add 0106 依据传递的头条消息类型来查询相应的头条列表信息
    List<InformationListBean> getInformationListByType(Map<String, Object> map);
    
    //浏览头条信息时 修改某一个头条信息
    public int updateOneInformation(Information info);
    
    
}
