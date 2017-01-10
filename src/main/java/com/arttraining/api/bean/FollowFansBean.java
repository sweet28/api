package com.arttraining.api.bean;


public class FollowFansBean {
	private int follow_id;
	private String head_pic;
	private int uid;
	private String utype;
	private String name;
	private String time;
	private String city;
	private String identity;
	
	public FollowFansBean() {
		this.follow_id = 0;
		this.head_pic = "";
		this.uid = 0;
		this.utype = "";
		this.name = "";
		this.time = "";
		this.city = "";
		this.identity = "";
	}
	public int getFollow_id() {
		return follow_id;
	}
	public void setFollow_id(int follow_id) {
		this.follow_id = follow_id;
	}
	public String getHead_pic() {
		return head_pic;
	}
	public void setHead_pic(String head_pic) {
		this.head_pic = head_pic;
		//this.head_pic=ImageUtil.parsePicPath(head_pic, 5);
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUtype() {
		return utype;
	}
	public void setUtype(String utype) {
		this.utype = utype;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
	
          
}
