package com.arttraining.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.IdentityListBean;
import com.arttraining.api.dao.IdentityMapper;
import com.arttraining.api.service.IIdentityService;

@Service("identityService")
public class IdentityService implements IIdentityService {
	@Resource
	private IdentityMapper identityDao;
	@Override
	public List<IdentityListBean> getIdentityList() {
		// TODO Auto-generated method stub
		return this.identityDao.selectIdentityList();
	}

}
