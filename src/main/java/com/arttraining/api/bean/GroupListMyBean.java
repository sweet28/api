package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class GroupListMyBean {
	private int group_id;
	private String name;
	private String introduce;
	private int grade;
	private int users_num;
	private String pic;
	private int verify_status;
	
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getUsers_num() {
		return users_num;
	}
	public void setUsers_num(int users_num) {
		this.users_num = users_num;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = ImageUtil.parsePicPath(pic,3);
	}
	public int getVerify_status() {
		return verify_status;
	}
	public void setVerify_status(int verify_status) {
		this.verify_status = verify_status;
	}
}
