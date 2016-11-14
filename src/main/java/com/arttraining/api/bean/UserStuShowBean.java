package com.arttraining.api.bean;

public class UserStuShowBean {
	private String error_code;
	private String error_msg;
	private Integer uid;
	private String user_code;
	private String name;
	private String mobile;
	private String head_pic;
	private String sex;
	private String city;
	private String identity;
	private String org;
	private String intentional_college;
	private String specialty;
	private String school;
	private String email;
	private String title;
	private Integer score;
	private Integer rank;
	
	public UserStuShowBean(){
		this.uid = 0;
		this.user_code = "";
		this.name = "";
		this.mobile = "";
		this.head_pic = "";
		this.sex = "";
		this.city = "";
		this.identity = "";
		this.org = "";
		this.intentional_college = "";
		this.specialty = "";
		this.school = "";
		this.email = "";
		this.title="";
		this.score = 0;
		this.rank = 0;
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
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getHead_pic() {
		return head_pic;
	}
	public void setHead_pic(String head_pic) {
		this.head_pic = head_pic;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
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
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getIntentional_college() {
		return intentional_college;
	}
	public void setIntentional_college(String intentional_college) {
		this.intentional_college = intentional_college;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}

	
}
