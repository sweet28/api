package com.arttraining.api.bean;

public class MsgListBean {
	//消息ID
	private Integer msg_id;
	//用户ID 
	private Integer uid;
	//用户类型
	private String utype;
	//姓名
	private String name;
	//头像
	private String head_pic;
	//消息时间
	private String msg_time;
	//消息类别
	private String msg_type;
	//消息内容
	private String msg_content;
	//动态ID
	private Integer status_id;
	//动态类型
	private String status_type;
	//封面
	private String status_pic;
	//被回复人ID
	private Integer b_uid;
	//被回复人类型
	private String b_utype;
	//被回复人姓名
	private String b_name;
	//被回复人头像
	private String b_head_pic;
	//coffee add 0104
	private String status_content;
	//end
	
	
	public Integer getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(Integer msg_id) {
		this.msg_id = msg_id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getUtype() {
		return utype;
	}
	public void setUtype(String utype) {
		this.utype = utype;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHead_pic() {
		return head_pic;
	}
	public void setHead_pic(String head_pic) {
		this.head_pic = head_pic;
	}
	public String getMsg_time() {
		return msg_time;
	}
	public void setMsg_time(String msg_time) {
		this.msg_time = msg_time;
	}
	public String getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}
	public String getMsg_content() {
		return msg_content;
	}
	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
	}
	public Integer getStatus_id() {
		return status_id;
	}
	public void setStatus_id(Integer status_id) {
		this.status_id = status_id;
	}
	public String getStatus_type() {
		return status_type;
	}
	public void setStatus_type(String status_type) {
		this.status_type = status_type;
	}
	public String getStatus_pic() {
		return status_pic;
	}
	public void setStatus_pic(String status_pic) {
		this.status_pic = status_pic;
	}
	public Integer getB_uid() {
		return b_uid;
	}
	public void setB_uid(Integer b_uid) {
		this.b_uid = b_uid;
	}
	public String getB_utype() {
		return b_utype;
	}
	public void setB_utype(String b_utype) {
		this.b_utype = b_utype;
	}
	public String getB_name() {
		return b_name;
	}
	public void setB_name(String b_name) {
		this.b_name = b_name;
	}
	public String getB_head_pic() {
		return b_head_pic;
	}
	public void setB_head_pic(String b_head_pic) {
		this.b_head_pic = b_head_pic;
	}
	public String getStatus_content() {
		return status_content;
	}
	public void setStatus_content(String status_content) {
		this.status_content = status_content;
	}
	
}
