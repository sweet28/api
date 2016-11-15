package com.arttraining.api.bean;

import java.util.ArrayList;
import java.util.List;

public class ProvinceListReBean {
	private String error_code;
	private String error_msg;
	private List<ProvinceListBean> province;
	
	
	public ProvinceListReBean() {
		this.province = new ArrayList<ProvinceListBean>();
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
	public List<ProvinceListBean> getProvince() {
		return province;
	}
	public void setProvince(List<ProvinceListBean> province) {
		this.province = province;
	}

}
