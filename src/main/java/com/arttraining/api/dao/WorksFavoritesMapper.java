package com.arttraining.api.dao;

import com.arttraining.api.pojo.WorksFavorites;

public interface WorksFavoritesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorksFavorites record);

    int insertSelective(WorksFavorites record);

    WorksFavorites selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorksFavorites record);

    int updateByPrimaryKey(WorksFavorites record);
}