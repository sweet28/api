package com.arttraining.api.beanv2;

public class MasterOpenClassPublishUrlBean {
	private String error_code;
	private String error_msg;
	private int owner;
	private String owner_type;
	private String name;
	private String head_pic;
	private int like_number;
	private int follow_number;
	private int chapter_number;
	private String publish_url;
		
	public MasterOpenClassPublishUrlBean() {
		this.owner = 0;
		this.owner_type = "";
		this.name = "";
		this.head_pic = "";
		this.like_number = 0;
		this.follow_number = 0;
		this.chapter_number = 0;
		this.publish_url = "";
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
		this.head_pic = head_pic;
	}
	public int getLike_number() {
		return like_number;
	}
	public void setLike_number(int like_number) {
		this.like_number = like_number;
	}
	public int getFollow_number() {
		return follow_number;
	}
	public void setFollow_number(int follow_number) {
		this.follow_number = follow_number;
	}
	public int getChapter_number() {
		return chapter_number;
	}
	public void setChapter_number(int chapter_number) {
		this.chapter_number = chapter_number;
	}
	public String getPublish_url() {
		return publish_url;
	}
	public void setPublish_url(String publish_url) {
		this.publish_url = publish_url;
	}	
}
