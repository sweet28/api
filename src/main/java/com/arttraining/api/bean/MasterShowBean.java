package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class MasterShowBean {
	private String error_code;
	private String error_msg;
	private Integer tec_id;
	private String name;
	private String pic;
	private Integer comment;
	private Integer fans_num;
	private String auth;
	private Integer browse_num;
	private String city;
	private String college;
	private Double ass_pay;
	private String title;
	private String specialty;
	private String introduction;
	private TecherShowOrgBean org;
	
	public MasterShowBean() {
		this.tec_id = 0;
		this.name = "";
		this.pic = "";
		this.comment = 0;
		this.fans_num = 0;
		this.auth = "";
		this.browse_num = 0;
		this.city = "";
		this.college = "";
		this.ass_pay = 0.0;
		this.title = "";
		this.specialty = "";
		this.introduction = "";
		this.org=new TecherShowOrgBean();
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
	public Integer getTec_id() {
		return tec_id;
	}
	public void setTec_id(Integer tec_id) {
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
	public Integer getComment() {
		return comment;
	}
	public void setComment(Integer comment) {
		this.comment = comment;
	}
	public Integer getFans_num() {
		return fans_num;
	}
	public void setFans_num(Integer fans_num) {
		this.fans_num = fans_num;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	
	public Integer getBrowse_num() {
		return browse_num;
	}

	public void setBrowse_num(Integer browse_num) {
		this.browse_num = browse_num;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
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
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public TecherShowOrgBean getOrg() {
		return org;
	}
	public void setOrg(TecherShowOrgBean org) {
		this.org = org;
	}

}
