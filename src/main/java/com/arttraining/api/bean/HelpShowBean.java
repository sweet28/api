package com.arttraining.api.bean;

public class HelpShowBean {
	private String error_code;
	private String error_msg;
	private Integer help_id;
	private String title;
	private String content;
	private String remark;
	
	
	public HelpShowBean() {
		this.help_id = 0;
		this.title = "";
		this.content = "";
		this.remark = "";
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
	public Integer getHelp_id() {
		return help_id;
	}
	public void setHelp_id(Integer help_id) {
		this.help_id = help_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
