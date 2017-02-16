package com.arttraining.api.beanv2;

import com.arttraining.commons.util.ImageUtil;

public class MastersLiveCloseBean {
	private String error_code;
	private String error_msg;
	private int owner;
	private String owner_type;
	private String name;
	private String head_pic;
	private int duration;
	private int browse_number;
	//coffee add 0215 
	private int gift_number;
	//end
	
	public MastersLiveCloseBean() {
		this.owner = 0;
		this.owner_type = "";
		this.name = "";
		this.head_pic = "";
		this.duration = 0;
		this.browse_number = 0;
		this.gift_number=0;
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
	public int getOwner() {
		return owner;
	}
	public void setOwner(int owner) {
		this.owner = owner;
	}
	public String getOwner_type() {
		return owner_type;
	}
	public void setOwner_type(String owner_type) {
		this.owner_type = owner_type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHead_pic() {
		return head_pic;
	}
	public void setHead_pic(String head_pic) {
		this.head_pic = ImageUtil.parsePicPath(head_pic, 5);
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getBrowse_number() {
		return browse_number;
	}
	public void setBrowse_number(int browse_number) {
		this.browse_number = browse_number;
	}
	public int getGift_number() {
		return gift_number;
	}

	public void setGift_number(int gift_number) {
		this.gift_number = gift_number;
	}
	
	
}
