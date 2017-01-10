package com.arttraining.api.bean;

public class FavoritesListBean {
	private int fav_id;
	private int b_fav_id;
	private String fav_type;
	private HomePageStatusesBean statuses;
	
	
	public FavoritesListBean() {
		this.statuses = new HomePageStatusesBean();
	}
	
	public int getFav_id() {
		return fav_id;
	}
	public void setFav_id(int fav_id) {
		this.fav_id = fav_id;
	}
	
	public int getB_fav_id() {
		return b_fav_id;
	}

	public void setB_fav_id(int b_fav_id) {
		this.b_fav_id = b_fav_id;
	}

	public String getFav_type() {
		return fav_type;
	}
	public void setFav_type(String fav_type) {
		this.fav_type = fav_type;
	}
	public HomePageStatusesBean getStatuses() {
		return statuses;
	}
	public void setStatuses(HomePageStatusesBean statuses) {
		this.statuses = statuses;
	}
	
	
}
