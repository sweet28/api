package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class WorkCommentTecInfoBean {
	private Integer tec_id;
	private String name;
	private String city;
	private String school;
	private String identity;
	private String tec_pic;
	public Integer getTec_id() {
		return tec_id;
	}
	public void setTec_id(Integer tec_id) {
		this.tec_id = tec_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getTec_pic() {
		return tec_pic;
	}
	public void setTec_pic(String tec_pic) {
		this.tec_pic = ImageUtil.parsePicPath(tec_pic,5);
	}
	
	
}
