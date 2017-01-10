package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class MasterCommentListBean {
	//评论ID
	private int comm_id;
	//评论人类型
	private String type;
	//评论人ID
	private int visitor_id;
	//评论人
	private String name;
	//爱好者  名师头像
	private String pic;
	//评论类型
	private String content_type;
	//评论内容
	private String content;
	//附件时长
	private String duration;
	//附件地址
	private String attr;
	//评论类型
	private String comm_type;
	//评论时间
	private String comm_time;
	
	
	public MasterCommentListBean() {
		this.comm_id = 0;
		this.type = "";
		this.visitor_id = 0;
		this.name = "";
		this.pic = "";
		this.content_type = "";
		this.content = "";
		this.duration = "";
		this.attr = "";
		this.comm_type = "";
		this.comm_time = "";
	}
	
	public int getComm_id() {
		return comm_id;
	}
	public void setComm_id(int comm_id) {
		this.comm_id = comm_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getVisitor_id() {
		return visitor_id;
	}
	public void setVisitor_id(int visitor_id) {
		this.visitor_id = visitor_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getContent_type() {
		return content_type;
	}
	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = ImageUtil.parsePicPath(content, 6);;
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
	public String getComm_type() {
		return comm_type;
	}
	public void setComm_type(String comm_type) {
		this.comm_type = comm_type;
	}
	public String getComm_time() {
		return comm_time;
	}
	public void setComm_time(String comm_time) {
		this.comm_time = comm_time;
	}
	
	
	
}
