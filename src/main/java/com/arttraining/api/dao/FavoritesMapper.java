package com.arttraining.api.dao;


import java.util.Map;

import com.arttraining.api.bean.FavoritesListReBean;
import com.arttraining.api.bean.HomeLikeOrCommentBean;
import com.arttraining.api.bean.HomePageStatusesBean;
import com.arttraining.api.pojo.Favorites;

public interface FavoritesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Favorites record);

    int insertSelective(Favorites record);

    Favorites selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Favorites record);

    int updateByPrimaryKey(Favorites record);
    //根据用户ID获取收藏列表--favorites/list接口调用
    FavoritesListReBean selectFavoritesListByUid(Map<String, Object> map);
    //根据用户ID获取收藏列表--favorites/list接口调用
    HomePageStatusesBean selectOneStatusByFavorite(Map<String, Object> map);
    //查询是否点赞或者点评--favorites/list接口调用
    HomeLikeOrCommentBean selectIsLikeOrCommentOrAtt(Map<String, Object> map);
    //判断是否重复收藏同一个ID
    Favorites selectOneFavoriteById(Map<String, Object> map);
}