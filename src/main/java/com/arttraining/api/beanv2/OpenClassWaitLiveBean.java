package com.arttraining.api.beanv2;

import java.util.ArrayList;
import java.util.List;

public class OpenClassWaitLiveBean extends OpenClassEnterLiveBean {
	private String error_code;
	private String error_msg;
	private int chapter_id;
	private String chapter_name;
	private List<LiveChapterListBean> chapter_list;
	
	
	public OpenClassWaitLiveBean() {
		this.chapter_id = 0;
		this.chapter_name = "";
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

	public int getChapter_id() {
		return chapter_id;
	}

	public void setChapter_id(int chapter_id) {
		this.chapter_id = chapter_id;
	}

	public String getChapter_name() {
		return chapter_name;
	}

	public void setChapter_name(String chapter_name) {
		this.chapter_name = chapter_name;
	}

	public List<LiveChapterListBean> getChapter_list() {
		return chapter_list;
	}

	public void setChapter_list(List<LiveChapterListBean> chapter_list) {
		this.chapter_list = chapter_list;
	}
}
