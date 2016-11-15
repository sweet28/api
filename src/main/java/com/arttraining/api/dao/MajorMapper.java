package com.arttraining.api.dao;

import java.util.List;

import com.arttraining.api.bean.MajorListBean;
import com.arttraining.api.pojo.Major;

public interface MajorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Major record);

    int insertSelective(Major record);

    Major selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Major record);

    int updateByPrimaryKey(Major record);
    //获取一级专业列表--major/list/level_one接口调用
    List<MajorListBean> selectOneLevelMajorByList();
}