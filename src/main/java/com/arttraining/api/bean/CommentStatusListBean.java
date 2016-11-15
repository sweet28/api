package com.arttraining.api.bean;

import java.util.ArrayList;
import java.util.List;

public class CommentStatusListBean {
	private String error_code;
	private String error_msg;
	private Integer stus_id;
	private Integer comment_num;
	private List<CommentsBean> comments;
	
	public CommentStatusListBean() {
		this.error_code = "";
		this.error_msg = "";
		this.stus_id = 0;
		this.comment_num = 0;
		this.comments = new ArrayList<CommentsBean>();
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
	public Integer getStus_id() {
		return stus_id;
	}
	public void setStus_id(Integer stus_id) {
		this.stus_id = stus_id;
	}
	public Integer getComment_num() {
		return comment_num;
	}
	public void setComment_num(Integer comment_num) {
		this.comment_num = comment_num;
	}
	public List<CommentsBean> getComments() {
		return comments;
	}
	public void setComments(List<CommentsBean> comments) {
		this.comments = comments;
	}
	
	

}
