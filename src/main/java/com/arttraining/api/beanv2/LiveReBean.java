package com.arttraining.api.beanv2;

public class LiveReBean {
	private String error_code;
	private String error_msg;
	private int live_status;
	private int room_status;

	public LiveReBean() {
		this.live_status = 0;
		this.room_status = 0;
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

	public int getRoom_status() {
		return room_status;
	}
	public void setRoom_status(int room_status) {
		this.room_status = room_status;
	}
	
	
}
