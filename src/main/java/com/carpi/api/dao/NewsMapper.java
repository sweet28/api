package com.carpi.api.dao;

import java.util.List;

import com.carpi.api.pojo.News;

public interface NewsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(News record);

    int insertSelective(News record);

    News selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(News record);

    int updateByPrimaryKey(News record);
    
    //查询新闻列表
    List<News> selectAll();
}