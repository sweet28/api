package com.arttraining.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.CityListBean;
import com.arttraining.api.bean.CitySortListBean;
import com.arttraining.api.bean.ProvinceListBean;
import com.arttraining.api.dao.CityMapper;
import com.arttraining.api.service.ICityService;

@Service("cityService")
public class CityService implements ICityService {
	@Resource
	private CityMapper cityDao;
	
	@Override
	public List<CityListBean> getCityListByProvince(String father_name) {
		// TODO Auto-generated method stub
		return this.cityDao.selectCityListByProvince(father_name);
	}

	@Override
	public List<ProvinceListBean> getProvinceList() {
		// TODO Auto-generated method stub
		return this.cityDao.selectProvinceList();
	}

	@Override
	public List<CitySortListBean> getCityListBySort(String father_name) {
		// TODO Auto-generated method stub
		return this.cityDao.selectCityListBySort(father_name);
	}
	
}
