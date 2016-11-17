package com.arttraining.api.dao;

import com.arttraining.api.pojo.Favorites;

public interface FavoritesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Favorites record);

    int insertSelective(Favorites record);

    Favorites selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Favorites record);

    int updateByPrimaryKey(Favorites record);
}