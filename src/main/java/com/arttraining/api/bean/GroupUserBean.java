package com.arttraining.api.bean;

public class GroupUserBean {
	private Integer group_user_id;
	private String head_pic;
	private Integer uid;
	private String utype;
	private String name;
	private String time;
	private String city;
	private String identity;
	
	public Integer getGroup_user_id() {
		return group_user_id;
	}
	public void setGroup_user_id(Integer group_user_id) {
		this.group_user_id = group_user_id;
	}
	public String getHead_pic() {
		return head_pic;
	}
	public void setHead_pic(String head_pic) {
		this.head_pic = head_pic;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
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
