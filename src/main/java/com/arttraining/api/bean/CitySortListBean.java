package com.arttraining.api.bean;

import java.util.List;

public class CitySortListBean {
	private String sort_word;
	private List<CityListBean> sort_citys;
	
	public String getSort_word() {
		return sort_word;
	}
	public void setSort_word(String sort_word) {
		this.sort_word = sort_word;
	}
	public List<CityListBean> getSort_citys() {
		return sort_citys;
	}
	public void setSort_citys(List<CityListBean> sort_citys) {
		this.sort_citys = sort_citys;
	}
	
	
}
