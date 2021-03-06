package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class TecherListBean {
	private int tec_id;
	private String name;
	private String pic;
	private int comment;
	private int fans_num;
	private String auth;
	private Double ass_pay;
	private String title;
	private String specialty;
	private Boolean click;
	//coffee add
	private String identity;
	private String bg_pic;
	private String introduction;
	//end
	
	public TecherListBean() {
		this.tec_id = 0;
		this.name = "";
		this.pic = "";
		this.comment = 0;
		this.fans_num = 0;
		this.auth = "";
		this.ass_pay = 0.0;
		this.title = "";
		this.specialty = "";
		this.click = false;
		this.identity="";
		this.bg_pic="";
		this.introduction="";
	}
	public int getTec_id() {
		return tec_id;
	}
	public void setTec_id(int tec_id) {
		this.tec_id = tec_id;
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
		this.pic = ImageUtil.parsePicPath(pic,5);
	}
	public int getComment() {
		return comment;
	}
	public void setComment(int comment) {
		this.comment = comment;
	}
	public int getFans_num() {
		return fans_num;
	}
	public void setFans_num(int fans_num) {
		this.fans_num = fans_num;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public Double getAss_pay() {
		return ass_pay;
	}
	public void setAss_pay(Double ass_pay) {
		this.ass_pay = ass_pay;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public Boolean getClick() {
		return click;
	}
	public void setClick(Boolean click) {
		this.click = click;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getBg_pic() {
		return bg_pic;
	}
	public void setBg_pic(String bg_pic) {
		this.bg_pic = ImageUtil.parsePicPath(bg_pic, 5);
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}	
}
