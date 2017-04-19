package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import com.arttraining.api.beanv3.EntranceCategoryBean;
import com.arttraining.api.pojo.EntranceCategory;

public interface EntranceCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EntranceCategory record);

    int insertSelective(EntranceCategory record);

    EntranceCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EntranceCategory record);

    int updateByPrimaryKey(EntranceCategory record);
    
    //coffee add 0417 选择专业类别列表
    List<EntranceCategoryBean> selectCategoryList(Map<String, Object> map);
}