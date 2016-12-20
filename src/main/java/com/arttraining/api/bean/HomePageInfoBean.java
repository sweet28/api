package com.arttraining.api.bean;

import java.util.ArrayList;
import java.util.List;

public class HomePageInfoBean {
	private String stus_type;
	private List<MasterInfoListBean> info_list;
	
	
	public HomePageInfoBean() {
		this.stus_type = "info";
		this.info_list = new ArrayList<MasterInfoListBean>();
	}
	public String getStus_type() {
		return stus_type;
	}
	public void setStus_type(String stus_type) {
		this.stus_type = stus_type;
	}
	public List<MasterInfoListBean> getInfo_list() {
		return info_list;
	}
	public void setInfo_list(List<MasterInfoListBean> info_list) {
		this.info_list = info_list;
	}
	
	
}
