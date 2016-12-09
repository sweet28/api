package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class MasterAssessmentBean {
	private Integer ass_id;
	private String order_number;
	private Integer order_id;
	private String codes;
	private String ass_time;
	private Integer status;
	private Integer stu_id;
	private String stu;
	private Integer work_id;
	private String work_title;
	private String work_pic;
	private String city;
	private String identity;
	private String pic;
	private String order_time;
	
	public MasterAssessmentBean() {
		this.ass_id = 0;
		this.order_number = "";
		this.order_id = 0;
		this.codes = "";
		this.ass_time = "";
		this.status = 0;
		this.stu_id = 0;
		this.stu = "";
		this.work_id = 0;
		this.work_title = "";
		this.work_pic = "";
		this.city = "";
		this.identity = "";
		this.pic = "";
		this.order_time="";
	}
	public Integer getAss_id() {
		return ass_id;
	}
	public void setAss_id(Integer ass_id) {
		this.ass_id = ass_id;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public String getCodes() {
		return codes;
	}
	public void setCodes(String codes) {
		this.codes = codes;
	}
	public String getAss_time() {
		return ass_time;
	}
	public void setAss_time(String ass_time) {
		this.ass_time = ass_time;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getStu_id() {
		return stu_id;
	}
	public void setStu_id(Integer stu_id) {
		this.stu_id = stu_id;
	}
	public String getStu() {
		return stu;
	}
	public void setStu(String stu) {
		this.stu = stu;
	}
	public Integer getWork_id() {
		return work_id;
	}
	public void setWork_id(Integer work_id) {
		this.work_id = work_id;
	}
	public String getWork_title() {
		return work_title;
	}
	public void setWork_title(String work_title) {
		this.work_title = work_title;
	}
	public String getWork_pic() {
		return work_pic;
	}
	public void setWork_pic(String work_pic) {
		this.work_pic = work_pic;
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
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = ImageUtil.parsePicPath(pic, 5);
	}
	public String getOrder_time() {
		return order_time;
	}
	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}
}
