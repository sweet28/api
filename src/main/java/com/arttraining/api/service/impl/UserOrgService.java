package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.OrgListBean;
import com.arttraining.api.bean.OrgShowBean;
import com.arttraining.api.bean.TecherShowOrgBean;
import com.arttraining.api.dao.UserOrgMapper;
import com.arttraining.api.pojo.UserOrg;
import com.arttraining.api.service.IUserOrgService;

@Service("userOrgService")
public class UserOrgService implements IUserOrgService {
	@Resource
	private UserOrgMapper userOrgDao;
	
	@Override
	public TecherShowOrgBean getOneOrgByTecShow(Integer id) {
		// TODO Auto-generated method stub
		return this.userOrgDao.selectOneOrgByTecShow(id);
	}


	@Override
	public OrgShowBean getOneOrgByOrgShow(Integer id) {
		// TODO Auto-generated method stub
		return this.userOrgDao.selectOneOrgByOrgShow(id);
	}


	@Override
	public List<OrgListBean> getOrgListPrimaryKey(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.userOrgDao.selectOrgListPrimaryKey(map);
	}


	@Override
	public List<OrgListBean> getOrgListBySearch(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.userOrgDao.selectOrgListBySearch(map);
	}


	@Override
	public int updateOrgNumber(UserOrg record) {
		// TODO Auto-generated method stub
		return this.userOrgDao.updateNumberBySelective(record);
	}

	
}
