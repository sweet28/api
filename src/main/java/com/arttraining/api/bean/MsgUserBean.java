package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class MsgUserBean {
	private Integer uid;
	private String utype;
	private String name;
	private String head_pic;
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
	public String getHead_pic() {
		return head_pic;
	}
	public void setHead_pic(String head_pic) {
		this.head_pic = ImageUtil.parsePicPath(head_pic, 5);
	}
}
