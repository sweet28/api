package com.arttraining.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.bean.CityListBean;
import com.arttraining.api.bean.CitySortListBean;
import com.arttraining.api.bean.ProvinceListBean;
import com.arttraining.api.pojo.City;

public interface CityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);
    //根据省份获取城市列表--common/get_city/by_province接口调用
    List<CityListBean> selectCityListByProvince(@Param("fatherName") String fatherName);
    //获取省份列表--common/get_province接口调用
    List<ProvinceListBean> selectProvinceList();
    //获取城市列表-- common/get_city接口调用
    List<CitySortListBean> selectCityListBySort(@Param("fatherName") String fatherName); 
}