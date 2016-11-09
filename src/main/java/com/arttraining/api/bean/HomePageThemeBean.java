package com.arttraining.api.bean;

import java.util.ArrayList;
import java.util.List;

public class HomePageThemeBean {
	private String stus_type;
	private List<HomePageThemeListBean> themes;

	public HomePageThemeBean() {
		this.stus_type = "theme";
		this.themes = new ArrayList<HomePageThemeListBean>();
	}
	
	public String getStus_type() {
		return stus_type;
	}
	public void setStus_type(String stus_type) {
		this.stus_type = stus_type;
	}
	public List<HomePageThemeListBean> getThemes() {
		return themes;
	}
	public void setThemes(List<HomePageThemeListBean> themes) {
		this.themes = themes;
	}

}
