package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class LoginBeanV2 {
	private String error_code;
	private String error_msg;
	private String access_token;
	private int uid;
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
	private int score;
	private int rank;
	private String title;
	//coffee add
	private int bbs_num;
	private int group_num;
	private int favorite_num;
	private int comment_num;
	private int follow_num;
	private int fans_num;
	private int work_num;
	private String is_bind;
	//end
	public LoginBeanV2() {
		this.access_token = "";
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
		this.score = 0;
		this.rank = 0;
		this.title = "";
		this.bbs_num = 0;
		this.group_num = 0;
		this.favorite_num = 0;
		this.comment_num = 0;
		this.follow_num = 0;
		this.fans_num = 0;
		this.work_num = 0;
		this.is_bind = "";
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
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
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
		this.head_pic=ImageUtil.parsePicPath(head_pic, 5);
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
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getBbs_num() {
		return bbs_num;
	}
	public void setBbs_num(int bbs_num) {
		this.bbs_num = bbs_num;
	}
	public int getGroup_num() {
		return group_num;
	}
	public void setGroup_num(int group_num) {
		this.group_num = group_num;
	}
	public int getFavorite_num() {
		return favorite_num;
	}
	public void setFavorite_num(int favorite_num) {
		this.favorite_num = favorite_num;
	}
	public int getComment_num() {
		return comment_num;
	}
	public void setComment_num(int comment_num) {
		this.comment_num = comment_num;
	}
	public int getFollow_num() {
		return follow_num;
	}
	public void setFollow_num(int follow_num) {
		this.follow_num = follow_num;
	}
	public int getFans_num() {
		return fans_num;
	}
	public void setFans_num(int fans_num) {
		this.fans_num = fans_num;
	}
	public int getWork_num() {
		return work_num;
	}
	public void setWork_num(int work_num) {
		this.work_num = work_num;
	}
	public String getIs_bind() {
		return is_bind;
	}
	public void setIs_bind(String is_bind) {
		this.is_bind = is_bind;
	}

}
