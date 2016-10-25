package com.arttraining.api.dao;

import com.arttraining.api.pojo.StatusesFavorites;

public interface StatusesFavoritesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StatusesFavorites record);

    int insertSelective(StatusesFavorites record);

    StatusesFavorites selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StatusesFavorites record);

    int updateByPrimaryKey(StatusesFavorites record);
}