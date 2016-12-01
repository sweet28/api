package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class WorkTecCommentBean {
	private String type;
	private String content;
	private String comm_type;
	//coffee add
	private Integer comm_id;
	private String comm_time;
	private String duration;
	private String attr;
	//end
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getComm_type() {
		return comm_type;
	}
	public void setComm_type(String comm_type) {
		this.comm_type = comm_type;
	}
	public Integer getComm_id() {
		return comm_id;
	}
	public void setComm_id(Integer comm_id) {
		this.comm_id = comm_id;
	}
	public String getComm_time() {
		return comm_time;
	}
	public void setComm_time(String comm_time) {
		this.comm_time = comm_time;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getAttr() {
		return attr;
	}
	public void setAttr(String attr) {
		this.attr = ImageUtil.parsePicPath(attr, 6);
	}

}
