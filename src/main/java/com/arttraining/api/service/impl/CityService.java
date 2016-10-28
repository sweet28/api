package com.arttraining.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.CityMapper;
import com.arttraining.api.service.ICityService;

@Service("cityService")
public class CityService implements ICityService {
	@Resource
	private CityMapper cityDao;

	@Override
	public String getNameById(Integer cityId) {
		// TODO Auto-generated method stub
		return this.cityDao.selectNameByPrimaryKey(cityId);
	}

}
