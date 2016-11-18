package com.arttraining.api.dao;

import java.util.Map;

import com.arttraining.api.bean.FavoriteCreateBean;
import com.arttraining.api.pojo.Favorites;

public interface FavoritesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Favorites record);

    int insertSelective(Favorites record);

    Favorites selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Favorites record);

    int updateByPrimaryKey(Favorites record);
    //coffee add 1117--添加收藏 favorites/create接口调用
    FavoriteCreateBean selectUserInfoByFavoriteCreate(Map<String, Object> map);
}