package com.arttraining.api.bean;

public class VersionUpdateBean {
	private String error_code;
	private String error_msg;
	private Integer version_no;
	private String version_name;
	private String update_time;
	private String version_url;
	private String describle;
	
	
	public VersionUpdateBean() {
		this.version_no = 0;
		this.version_name = "";
		this.update_time = "";
		this.version_url = "";
		this.describle = "";
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
	public Integer getVersion_no() {
		return version_no;
	}
	public void setVersion_no(Integer version_no) {
		this.version_no = version_no;
	}
	public String getVersion_name() {
		return version_name;
	}
	public void setVersion_name(String version_name) {
		this.version_name = version_name;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getVersion_url() {
		return version_url;
	}
	public void setVersion_url(String version_url) {
		this.version_url = version_url;
	}
	public String getDescrible() {
		return describle;
	}
	public void setDescrible(String describle) {
		this.describle = describle;
	}
	
	

}
