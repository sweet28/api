package com.arttraining.api.bean;

import java.util.ArrayList;
import java.util.List;

public class HomePageInfoBean {
	private String stus_type;
	private List<InformationListBean> info_list;
	
	
	public HomePageInfoBean() {
		this.stus_type = "info";
		this.info_list = new ArrayList<InformationListBean>();
	}
	public String getStus_type() {
		return stus_type;
	}
	public void setStus_type(String stus_type) {
		this.stus_type = stus_type;
	}
	public List<InformationListBean> getInfo_list() {
		return info_list;
	}
	public void setInfo_list(List<InformationListBean> info_list) {
		this.info_list = info_list;
	}
	
	
}
