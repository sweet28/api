package com.arttraining.api.beanv2;


public class OpenClassBeingLiveBean extends OpenClassEnterLiveBean {
	private String error_code;
	private String error_msg;
	private String play_url;
	private String snapshot_url;
	private String chapter_name;
	private String is_talk;
	//coffee add 0314
	private double live_price;
	private double record_price;
	private int order_status;
	//coffee add 0321
	private String live_name;
	private String introduction;
	//coffee add 0327
	private int is_free;
	//end
	
	public OpenClassBeingLiveBean() {
		this.play_url = "";
		this.snapshot_url = "";
		this.chapter_name = "";
		this.is_talk="";
		this.live_price=0.0;
		this.record_price=0.0;
		this.order_status=-1;
		this.live_name="";
		this.introduction="";
		this.is_free=0;
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

	public double getLive_price() {
		return live_price;
	}

	public void setLive_price(double live_price) {
		this.live_price = live_price;
	}

	public double getRecord_price() {
		return record_price;
	}

	public void setRecord_price(double record_price) {
		this.record_price = record_price;
	}

	public int getOrder_status() {
		return order_status;
	}

	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}

	public String getLive_name() {
		return live_name;
	}

	public void setLive_name(String live_name) {
		this.live_name = live_name;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public int getIs_free() {
		return is_free;
	}

	public void setIs_free(int is_free) {
		this.is_free = is_free;
	}
	
}
