package com.arttraining.api.bean;

import java.util.ArrayList;
import java.util.List;

public class CouponsListReBean {
	private String error_code;
	private String error_msg;
	private List<CouponsListBean> coupons;
	
	
	public CouponsListReBean() {
		this.coupons = new ArrayList<CouponsListBean>();
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
	public List<CouponsListBean> getCoupons() {
		return coupons;
	}
	public void setCoupons(List<CouponsListBean> coupons) {
		this.coupons = coupons;
	}

}
