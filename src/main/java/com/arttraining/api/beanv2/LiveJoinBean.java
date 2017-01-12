package com.arttraining.api.beanv2;

import com.arttraining.commons.util.ImageUtil;

public class LiveJoinBean {
	private String error_code;
	private String error_msg;
	private String play_url;
	private int owner;
	private String owner_type;
	private String name;
	private String head_pic;
	private int live_number;
	private String title;
	private int like_number;
	private String snapshot_url;
	
	public LiveJoinBean() {
		this.play_url = "";
		this.owner = 0;
		this.owner_type = "";
		this.name = "";
		this.head_pic = "";
		this.live_number = 0;
		this.title = "";
		this.like_number = 0;
		this.snapshot_url = "";
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
	public int getLive_number() {
		return live_number;
	}
	public void setLive_number(int live_number) {
		this.live_number = live_number;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getLike_number() {
		return like_number;
	}
	public void setLike_number(int like_number) {
		this.like_number = like_number;
	}
	public String getSnapshot_url() {
		return snapshot_url;
	}
	public void setSnapshot_url(String snapshot_url) {
		this.snapshot_url = snapshot_url;
	}
	
	
}	
