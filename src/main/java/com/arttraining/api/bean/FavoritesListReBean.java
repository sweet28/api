package com.arttraining.api.bean;

import java.util.ArrayList;
import java.util.List;

public class FavoritesListReBean {
	private String error_code;
	private String error_msg;
	private int uid;
	private String user_code;
	private String name;
	private List<FavoritesListBean> favorites;
	
	public FavoritesListReBean() {
		this.uid = 0;
		this.user_code = "";
		this.name = "";
		this.favorites = new ArrayList<FavoritesListBean>();
	}
	
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<FavoritesListBean> getFavorites() {
		return favorites;
	}
	public void setFavorites(List<FavoritesListBean> favorites) {
		this.favorites = favorites;
	}
	
	
}
