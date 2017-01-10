package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class GroupShowUserBean {
	private int uid;
	private String head_pic;
	private String utype;
	private int group_user_id;
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getHead_pic() {
		return head_pic;
	}
	public void setHead_pic(String head_pic) {
		this.head_pic = ImageUtil.parsePicPath(head_pic,5);
	}
	public String getUtype() {
		return utype;
	}
	public void setUtype(String utype) {
		this.utype = utype;
	}
	public int getGroup_user_id() {
		return group_user_id;
	}
	public void setGroup_user_id(int group_user_id) {
		this.group_user_id = group_user_id;
	}
}
