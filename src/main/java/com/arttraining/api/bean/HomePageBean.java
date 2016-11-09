package com.arttraining.api.bean;

import java.util.ArrayList;
import java.util.List;

public class HomePageBean {
	private String error_code;
	private String error_msg;
	private List<Object> statuses;
	
	public HomePageBean() {
		this.statuses = new ArrayList<Object>();
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
	public List<Object> getStatuses() {
		return statuses;
	}
	public void setStatuses(List<Object> statuses) {
		this.statuses = statuses;
	} 

}
