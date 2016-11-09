package com.arttraining.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.AdvertiseListBean;
import com.arttraining.api.bean.AdvertiseShowBean;
import com.arttraining.api.bean.HomePageAdvertiseBean;
import com.arttraining.api.dao.AdvertisingMapper;
import com.arttraining.api.service.IAdvertiseService;

@Service("advertiseService")
public class AdvertiseService implements IAdvertiseService {
	@Resource
	private AdvertisingMapper advertiseDao;
	

	@Override
	public AdvertiseShowBean getAdShowByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return this.advertiseDao.selectAdShowByPrimaryKey(id);
	}

	@Override
	public List<AdvertiseListBean> getAdList() {
		// TODO Auto-generated method stub
		return this.advertiseDao.selectAdList();
	}

	@Override
	public HomePageAdvertiseBean selectOneAdByHomepage() {
		// TODO Auto-generated method stub
		return this.advertiseDao.selectOneAdByHomepage();
	}

}
