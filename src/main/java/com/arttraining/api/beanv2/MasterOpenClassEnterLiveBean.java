package com.arttraining.api.beanv2;

public class MasterOpenClassEnterLiveBean extends OpenClassEnterLiveBean {
	private String error_code;
	private String error_msg;
	private int room_id;
	private int live_status;
	private int chapter_id;
	private String chapter_name;
	private String is_talk;
	
	public MasterOpenClassEnterLiveBean() {
		this.room_id=0;
		this.live_status = -1;
		this.chapter_id = 0;
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
	public int getLive_status() {
		return live_status;
	}
	public void setLive_status(int live_status) {
		this.live_status = live_status;
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

	public String getIs_talk() {
		return is_talk;
	}

	public void setIs_talk(String is_talk) {
		this.is_talk = is_talk;
	}

	public int getRoom_id() {
		return room_id;
	}

	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}
	
}
