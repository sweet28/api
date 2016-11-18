package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.OrgListBean;
import com.arttraining.api.bean.OrgShowBean;
import com.arttraining.api.bean.TecherShowOrgBean;

public interface IUserOrgService {
	TecherShowOrgBean getOneOrgByTecShow(Integer id);
	
	//获取机构列表信息--org/list接口调用
    List<OrgListBean> getOrgListPrimaryKey(Map<String, Object> map);
    
    //依据机构id查询机构详情
    OrgShowBean getOneOrgByOrgShow(Integer id);
    
    //根据关键字搜索机构 --search/org接口调用
    List<OrgListBean> getOrgListBySearch(Map<String, Object> map);
}
