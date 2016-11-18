package com.arttraining.api.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.FavoriteCreateBean;
import com.arttraining.api.dao.FavoritesMapper;
import com.arttraining.api.pojo.Favorites;
import com.arttraining.api.service.IFavoritesService;

@Service("favoritesService")
public class FavoritesService implements IFavoritesService {
	@Resource
	private FavoritesMapper favoritesDao;
	
	@Override
	public FavoriteCreateBean getUserInfoByFavoriteCreate(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.favoritesDao.selectUserInfoByFavoriteCreate(map);
	}

	@Override
	public int insertOneFavorite(Favorites favorites) {
		// TODO Auto-generated method stub
		return this.favoritesDao.insertSelective(favorites);
	}

}
