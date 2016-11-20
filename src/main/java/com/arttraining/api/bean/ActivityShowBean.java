package com.arttraining.api.bean;

import java.util.ArrayList;
import java.util.List;

import com.arttraining.commons.util.ImageUtil;

public class ActivityShowBean {
	private String error_code;
	private String error_msg;
	private Integer activ_id;
	private String pic;
	private String title;
	private String create_time;
	private String activ_sta_time;
	private String activ_end_time;
	private String sign_sta_time;
	private String sign_end_time;
	private String money;
	private String address;
	private Integer sign_num;
	private String content;
    private List<ActivityOrgBean> activ_org;
    
    
    
	public ActivityShowBean() { 
		this.activ_id = 0;
		this.pic = "";
		this.title = "";
		this.create_time = "";
		this.activ_sta_time = "";
		this.activ_end_time = "";
		this.sign_sta_time = "";
		this.sign_end_time = "";
		this.money = "";
		this.address = "";
		this.sign_num = 0;
		this.content = "";
		this.activ_org = new ArrayList<ActivityOrgBean>();
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
	public Integer getActiv_id() {
		return activ_id;
	}
	public void setActiv_id(Integer activ_id) {
		this.activ_id = activ_id;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = ImageUtil.parsePicPath(pic);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getActiv_sta_time() {
		return activ_sta_time;
	}
	public void setActiv_sta_time(String activ_sta_time) {
		this.activ_sta_time = activ_sta_time;
	}
	public String getActiv_end_time() {
		return activ_end_time;
	}
	public void setActiv_end_time(String activ_end_time) {
		this.activ_end_time = activ_end_time;
	}
	public String getSign_sta_time() {
		return sign_sta_time;
	}
	public void setSign_sta_time(String sign_sta_time) {
		this.sign_sta_time = sign_sta_time;
	}
	public String getSign_end_time() {
		return sign_end_time;
	}
	public void setSign_end_time(String sign_end_time) {
		this.sign_end_time = sign_end_time;
	}
	
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Integer getSign_num() {
		return sign_num;
	}

	public void setSign_num(Integer sign_num) {
		this.sign_num = sign_num;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<ActivityOrgBean> getActiv_org() {
		return activ_org;
	}
	public void setActiv_org(List<ActivityOrgBean> activ_org) {
		this.activ_org = activ_org;
	}

}
