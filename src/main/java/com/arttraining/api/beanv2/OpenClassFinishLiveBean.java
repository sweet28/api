package com.arttraining.api.beanv2;

import java.util.ArrayList;
import java.util.List;


public class OpenClassFinishLiveBean extends OpenClassEnterLiveBean {
	private String error_code;
	private String error_msg;
	private List<LiveChapterListBean> chapter_list;
	
	public OpenClassFinishLiveBean() {
		this.chapter_list = new ArrayList<LiveChapterListBean>();
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
	public List<LiveChapterListBean> getChapter_list() {
		return chapter_list;
	}
	public void setChapter_list(List<LiveChapterListBean> chapter_list) {
		this.chapter_list = chapter_list;
	}
	
}
