package com.arttraining.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.OrgListBean;
import com.arttraining.api.bean.OrgShowBean;
import com.arttraining.api.bean.TecherShowOrgBean;
import com.arttraining.api.dao.UserOrgMapper;
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
	public List<OrgListBean> getOrgListPrimaryKey(String city, String province,
			String type, Integer offset, Integer limit) {
		// TODO Auto-generated method stub
		return this.userOrgDao.selectOrgListPrimaryKey(city, province, type, offset, limit);
	}

	@Override
	public OrgShowBean getOneOrgByOrgShow(Integer id) {
		// TODO Auto-generated method stub
		return this.userOrgDao.selectOneOrgByOrgShow(id);
	}

	
}
