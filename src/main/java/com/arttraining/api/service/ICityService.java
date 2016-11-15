package com.arttraining.api.service;

import java.util.List;

import com.arttraining.api.bean.CityListBean;
import com.arttraining.api.bean.ProvinceListBean;

public interface ICityService {
	 //根据省份获取城市列表--common/get_city/by_province接口调用
    List<CityListBean> getCityListByProvince(String fatherName);
    //获取省份列表--common/get_province接口调用
    List<ProvinceListBean> getProvinceList();
}
