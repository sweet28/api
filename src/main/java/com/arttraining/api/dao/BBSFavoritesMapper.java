package com.arttraining.api.dao;

import com.arttraining.api.pojo.BBSFavorites;

public interface BBSFavoritesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BBSFavorites record);

    int insertSelective(BBSFavorites record);

    BBSFavorites selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BBSFavorites record);

    int updateByPrimaryKey(BBSFavorites record);
}