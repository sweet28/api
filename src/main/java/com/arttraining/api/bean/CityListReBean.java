package com.arttraining.api.bean;

import java.util.ArrayList;
import java.util.List;

public class CityListReBean {
	private String error_code;
	private String error_msg;
	private List<CityListBean> citys;
	
	public CityListReBean() {
		this.citys = new ArrayList<CityListBean>();
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
	public List<CityListBean> getCitys() {
		return citys;
	}
	public void setCitys(List<CityListBean> citys) {
		this.citys = citys;
	}

	
}
