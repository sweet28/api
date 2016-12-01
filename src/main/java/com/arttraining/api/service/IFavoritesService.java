package com.arttraining.api.service;


import java.util.Map;

import com.arttraining.api.bean.FavoritesListReBean;
import com.arttraining.api.bean.HomeLikeOrCommentBean;
import com.arttraining.api.bean.HomePageStatusesBean;
import com.arttraining.api.pojo.Favorites;
import com.arttraining.api.pojo.UserStu;

public interface IFavoritesService {
     //coffee add 1117--添加收藏 favorites/create接口调用
     int insertOneFavorite(Favorites favorites);
     //coffee add 1117--添加收藏 favorites/create接口调用 更新用户收藏量
     void insertOneFavoriteAndUpdateNum(Favorites favorites,UserStu user);
     
     //根据用户ID获取收藏列表--favorites/list接口调用
     FavoritesListReBean getFavoritesListByUid(Map<String, Object> map);
     //根据用户ID获取收藏列表--favorites/list接口调用
     HomePageStatusesBean getOneStatusByFavorite(Map<String, Object> map);
     //查询是否点赞或者点评--favorites/list接口调用
     HomeLikeOrCommentBean getIsLikeOrCommentOrAtt(Map<String, Object> map);
     //判断是否重复收藏同一个ID
     Favorites getOneFavoriteById(Map<String, Object> map);
}
