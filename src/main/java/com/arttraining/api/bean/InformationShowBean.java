package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class InformationShowBean {
	private String error_code;
	private String error_msg;
	private Integer info_id;
	private String title;
	private String create_time;
	private String content;
	private String pic;
	private String url;
	
	public InformationShowBean() {
		this.info_id = 0;
		this.title = "";
		this.create_time = "";
		this.content = "";
		this.pic = "";
		this.url = "";
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
	public Integer getInfo_id() {
		return info_id;
	}
	public void setInfo_id(Integer info_id) {
		this.info_id = info_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = ImageUtil.parsePicPath(pic,4);
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
