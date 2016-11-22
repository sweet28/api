package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class LikeUserBean {
	private Integer b_like_id;
	private Integer uid;
	private String pic;
	private String user_type;
	private String name;
	private String time;
	private String city;
	private String identity;
	

	public Integer getB_like_id() {
		return b_like_id;
	}
	public void setB_like_id(Integer b_like_id) {
		this.b_like_id = b_like_id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = ImageUtil.parsePicPath(pic,5);
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
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
