package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import com.arttraining.api.beanv3.EntranceProvinceBean;
import com.arttraining.api.pojo.EntranceProvince;

public interface EntranceProvinceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EntranceProvince record);

    int insertSelective(EntranceProvince record);

    EntranceProvince selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EntranceProvince record);

    int updateByPrimaryKey(EntranceProvince record);
    
    //coffee add 0417 选择报考生源地列表
    List<EntranceProvinceBean> selectProvinceList(Map<String, Object> map);
}