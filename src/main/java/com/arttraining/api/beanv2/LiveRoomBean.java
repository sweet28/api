package com.arttraining.api.beanv2;

import com.arttraining.commons.util.ImageUtil;

public class LiveRoomBean {
	private String error_code;
	private String error_msg;
	private int room_id;
	private String live_name;
	private String thumbnail;
	
	public LiveRoomBean() {
		this.room_id=0;
		this.live_name = "";
		this.thumbnail = "";
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
	
	public int getRoom_id() {
		return room_id;
	}
	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}
	public String getLive_name() {
		return live_name;
	}
	public void setLive_name(String live_name) {
		this.live_name = live_name;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = ImageUtil.parsePicPath(thumbnail, 7);
	}
	
}
