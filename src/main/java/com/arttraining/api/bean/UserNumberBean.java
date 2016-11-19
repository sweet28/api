package com.arttraining.api.bean;

public class UserNumberBean {
	private String error_code;
	private String error_msg;
	private Integer bbs_num;
	private Integer group_num;
	private Integer favorite_num;
	private Integer comment_num;
	private Integer follow_num;
	private Integer fans_num;
	
	
	public UserNumberBean() {
		this.bbs_num = 0;
		this.group_num = 0;
		this.favorite_num = 0;
		this.comment_num = 0;
		this.follow_num = 0;
		this.fans_num = 0;
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
	public Integer getBbs_num() {
		return bbs_num;
	}
	public void setBbs_num(Integer bbs_num) {
		this.bbs_num = bbs_num;
	}
	public Integer getGroup_num() {
		return group_num;
	}
	public void setGroup_num(Integer group_num) {
		this.group_num = group_num;
	}
	public Integer getFavorite_num() {
		return favorite_num;
	}
	public void setFavorite_num(Integer favorite_num) {
		this.favorite_num = favorite_num;
	}
	public Integer getComment_num() {
		return comment_num;
	}
	public void setComment_num(Integer comment_num) {
		this.comment_num = comment_num;
	}
	public Integer getFollow_num() {
		return follow_num;
	}
	public void setFollow_num(Integer follow_num) {
		this.follow_num = follow_num;
	}
	public Integer getFans_num() {
		return fans_num;
	}
	public void setFans_num(Integer fans_num) {
		this.fans_num = fans_num;
	}
	
	

}
