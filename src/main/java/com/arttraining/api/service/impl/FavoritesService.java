package com.arttraining.api.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.FavoritesListReBean;
import com.arttraining.api.bean.HomeLikeOrCommentBean;
import com.arttraining.api.bean.HomePageStatusesBean;
import com.arttraining.api.dao.FavoritesMapper;
import com.arttraining.api.dao.UserStuMapper;
import com.arttraining.api.pojo.Favorites;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.IFavoritesService;

@Service("favoritesService")
public class FavoritesService implements IFavoritesService {
	@Resource
	private FavoritesMapper favoritesDao;
	@Resource
	private UserStuMapper userStuDao;
	

	@Override
	public int insertOneFavorite(Favorites favorites) {
		// TODO Auto-generated method stub
		return this.favoritesDao.insertSelective(favorites);
	}


	@Override
	public FavoritesListReBean getFavoritesListByUid(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.favoritesDao.selectFavoritesListByUid(map);
	}


	@Override
	public HomePageStatusesBean getOneStatusByFavorite(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.favoritesDao.selectOneStatusByFavorite(map);
	}


	@Override
	public HomeLikeOrCommentBean getIsLikeOrCommentOrAtt(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.favoritesDao.selectIsLikeOrCommentOrAtt(map);
	}


	@Override
	public void insertOneFavoriteAndUpdateNum(Favorites favorites, UserStu user) {
		// TODO Auto-generated method stub
		this.favoritesDao.insertSelective(favorites);
		this.userStuDao.updateNumberBySelective(user);
	}


	@Override
	public Favorites getOneFavoriteById(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.favoritesDao.selectOneFavoriteById(map);
	}

}
