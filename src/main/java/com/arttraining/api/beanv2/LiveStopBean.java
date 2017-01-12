package com.arttraining.api.beanv2;

public class LiveStopBean {
	private String error_code;
	private String error_msg;
	private int live_time;
	private int live_number;
	private int like_number;
	private int reward_number;
	
	
	public LiveStopBean() {
		this.live_time = 0;
		this.live_number = 0;
		this.like_number = 0;
		this.reward_number = 0;
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
	public int getLive_time() {
		return live_time;
	}
	public void setLive_time(int live_time) {
		this.live_time = live_time;
	}
	public int getLive_number() {
		return live_number;
	}
	public void setLive_number(int live_number) {
		this.live_number = live_number;
	}
	public int getLike_number() {
		return like_number;
	}
	public void setLike_number(int like_number) {
		this.like_number = like_number;
	}
	public int getReward_number() {
		return reward_number;
	}
	public void setReward_number(int reward_number) {
		this.reward_number = reward_number;
	}
	
	
}
