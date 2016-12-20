package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.MasterInfoListBean;
import com.arttraining.api.bean.MasterInfoShowBean;

public interface IInformationForTecService {
	 //名师端资讯列表接口--info/master/list接口调用
    List<MasterInfoListBean> getInfoListByMaster(Map<String, Object> map);
    //名师端资讯详情接口--info/master/show接口调用
    MasterInfoShowBean getInfoShowByMaster(Integer id);
    
    //爱好者首页返回资讯列表信息
    List<MasterInfoListBean> getInfoListByHomePage();
}
