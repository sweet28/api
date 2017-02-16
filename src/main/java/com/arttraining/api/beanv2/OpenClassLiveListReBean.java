package com.arttraining.api.beanv2;

import java.util.ArrayList;
import java.util.List;

public class OpenClassLiveListReBean {
	private String error_code;
	private String error_msg;
	private List<OpenClassLiveListBean> openclass_list;
	
	public OpenClassLiveListReBean() {
		this.openclass_list = new ArrayList<OpenClassLiveListBean>();
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
	public List<OpenClassLiveListBean> getOpenclass_list() {
		return openclass_list;
	}
	public void setOpenclass_list(List<OpenClassLiveListBean> openclass_list) {
		this.openclass_list = openclass_list;
	}
}
