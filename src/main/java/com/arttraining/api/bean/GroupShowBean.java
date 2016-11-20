package com.arttraining.api.bean;

import java.util.ArrayList;
import java.util.List;

import com.arttraining.commons.util.ImageUtil;

public class GroupShowBean {
	private String error_code;
	private String error_msg;
	private Integer group_id;
	private String name;
	private String introduce;
	private Integer grade;
	private Integer users_num;
	private String pic;
	private String number;
	private String owner_type;
	private Integer owner;
	private String owner_name;
	private String owner_pic;
	private String create_time;
	private List<GroupShowUserBean> users;
	private List<Object> statuses;
	
	
	
	public GroupShowBean() {
		this.group_id = 0;
		this.name = "";
		this.introduce = "";
		this.grade = 0;
		this.users_num = 0;
		this.pic = "";
		this.number = "";
		this.owner_type = "";
		this.owner = 0;
		this.owner_name = "";
		this.owner_pic = "";
		this.create_time = "";
		this.users = new ArrayList<GroupShowUserBean>();
		this.statuses = new ArrayList<Object>();
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
	public Integer getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Integer group_id) {
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
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public Integer getUsers_num() {
		return users_num;
	}
	public void setUsers_num(Integer users_num) {
		this.users_num = users_num;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = ImageUtil.parsePicPath(pic);
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getOwner_type() {
		return owner_type;
	}
	public void setOwner_type(String owner_type) {
		this.owner_type = owner_type;
	}
	public Integer getOwner() {
		return owner;
	}
	public void setOwner(Integer owner) {
		this.owner = owner;
	}
	public String getOwner_name() {
		return owner_name;
	}
	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}
	public String getOwner_pic() {
		return owner_pic;
	}
	public void setOwner_pic(String owner_pic) {
		this.owner_pic = ImageUtil.parsePicPath(owner_pic);
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public List<GroupShowUserBean> getUsers() {
		return users;
	}
	public void setUsers(List<GroupShowUserBean> users) {
		this.users = users;
	}
	public List<Object> getStatuses() {
		return statuses;
	}
	public void setStatuses(List<Object> statuses) {
		this.statuses = statuses;
	}  	
}
