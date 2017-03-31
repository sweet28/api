package com.arttraining.api.beanv2;

import java.util.ArrayList;
import java.util.List;

public class OpenClassLiveListReBean {
	private String error_code;
	private String error_msg;
	//coffee add 0331
	private int pre_page;
	private int finish_page;
	private int page_limit;
	//end
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
	public int getPre_page() {
		return pre_page;
	}
	public void setPre_page(int pre_page) {
		this.pre_page = pre_page;
	}
	public int getFinish_page() {
		return finish_page;
	}
	public void setFinish_page(int finish_page) {
		this.finish_page = finish_page;
	}
	public int getPage_limit() {
		return page_limit;
	}
	public void setPage_limit(int page_limit) {
		this.page_limit = page_limit;
	}
	
}
