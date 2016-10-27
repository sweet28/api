package com.arttraining.api.dao;

import com.arttraining.api.pojo.City;

public interface CityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);
    
    //coffee add 1027 依据城市地域ID获取相应的城市地域名称
    String selectNameByPrimaryKey(Integer id);
}