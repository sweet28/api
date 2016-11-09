package com.arttraining.api.service;

import java.util.List;

import com.arttraining.api.bean.OrgListBean;
import com.arttraining.api.bean.OrgShowBean;
import com.arttraining.api.bean.TecherShowOrgBean;

public interface IUserOrgService {
	TecherShowOrgBean getOneOrgByTecShow(Integer id);
	
	 //获取机构列表信息
    List<OrgListBean> getOrgListPrimaryKey(String city, String province, 
    		String type, Integer offset, Integer limit);
  //依据机构id查询机构详情
    OrgShowBean getOneOrgByOrgShow(Integer id);
}
