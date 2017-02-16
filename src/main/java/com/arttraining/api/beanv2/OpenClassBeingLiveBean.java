package com.arttraining.api.beanv2;


public class OpenClassBeingLiveBean extends OpenClassEnterLiveBean {
	private String error_code;
	private String error_msg;
	private String play_url;
	private String snapshot_url;
	private String chapter_name;
	private String is_talk;
	
	public OpenClassBeingLiveBean() {
		this.play_url = "";
		this.snapshot_url = "";
		this.chapter_name = "";
		this.is_talk="";
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

	public String getPlay_url() {
		return play_url;
	}

	public void setPlay_url(String play_url) {
		this.play_url = play_url;
	}

	public String getSnapshot_url() {
		return snapshot_url;
	}

	public void setSnapshot_url(String snapshot_url) {
		this.snapshot_url = snapshot_url;
	}

	public String getChapter_name() {
		return chapter_name;
	}

	public void setChapter_name(String chapter_name) {
		this.chapter_name = chapter_name;
	}

	public String getIs_talk() {
		return is_talk;
	}

	public void setIs_talk(String is_talk) {
		this.is_talk = is_talk;
	}
	
}
