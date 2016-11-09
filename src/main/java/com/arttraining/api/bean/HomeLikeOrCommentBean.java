package com.arttraining.api.bean;

public class HomeLikeOrCommentBean {
	private String is_like;
	private String is_comment;
	private Integer att_id;
	private String duration;
	private String att_type;
	private String thumbnail;
	private String store_path;
	
	
	
	public HomeLikeOrCommentBean() {
		this.is_like = "";
		this.is_comment = "";
		this.att_id = 0;
		this.duration = "";
		this.att_type = "";
		this.thumbnail = "";
		this.store_path = "";
	}
	public String getIs_like() {
		return is_like;
	}
	public void setIs_like(String is_like) {
		this.is_like = is_like;
	}
	public String getIs_comment() {
		return is_comment;
	}
	public void setIs_comment(String is_comment) {
		this.is_comment = is_comment;
	}
	public Integer getAtt_id() {
		return att_id;
	}
	public void setAtt_id(Integer att_id) {
		this.att_id = att_id;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getAtt_type() {
		return att_type;
	}
	public void setAtt_type(String att_type) {
		this.att_type = att_type;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getStore_path() {
		return store_path;
	}
	public void setStore_path(String store_path) {
		this.store_path = store_path;
	}
}
