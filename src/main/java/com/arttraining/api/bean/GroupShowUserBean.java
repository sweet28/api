package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class GroupShowUserBean {
	private Integer uid;
	private String head_pic;
	private String utype;
	private Integer group_user_id;
	
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getHead_pic() {
		return head_pic;
	}
	public void setHead_pic(String head_pic) {
		this.head_pic = ImageUtil.parsePicPath(head_pic);
	}
	public String getUtype() {
		return utype;
	}
	public void setUtype(String utype) {
		this.utype = utype;
	}
	public Integer getGroup_user_id() {
		return group_user_id;
	}
	public void setGroup_user_id(Integer group_user_id) {
		this.group_user_id = group_user_id;
	}
}
