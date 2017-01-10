package com.arttraining.api.bean;

public class UserNumberBean {
	private String error_code;
	private String error_msg;
	private int bbs_num;
	private int group_num;
	private int favorite_num;
	private int comment_num;
	private int follow_num;
	private int fans_num;
	private int work_num;
	//coffee add 0105
	private int msg_num;
	//end
	
	
	public UserNumberBean() {
		this.bbs_num = 0;
		this.group_num = 0;
		this.favorite_num = 0;
		this.comment_num = 0;
		this.follow_num = 0;
		this.fans_num = 0;
		this.work_num=0;
		this.msg_num=0;
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
	public int getMsg_num() {
		return msg_num;
	}
	public void setMsg_num(int msg_num) {
		this.msg_num = msg_num;
	}
	
}
