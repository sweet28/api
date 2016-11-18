package com.arttraining.api.service;

import java.util.Map;

import com.arttraining.api.bean.FavoriteCreateBean;
import com.arttraining.api.pojo.Favorites;

public interface IFavoritesService {
	 //coffee add 1117--添加收藏 favorites/create接口调用
     FavoriteCreateBean getUserInfoByFavoriteCreate(Map<String, Object> map);
     //coffee add 1117--添加收藏 favorites/create接口调用
     int insertOneFavorite(Favorites favorites);
    
}
