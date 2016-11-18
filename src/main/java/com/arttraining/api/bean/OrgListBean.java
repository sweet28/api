package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class OrgListBean {
	private Integer org_id;
	private String name;
	private String pic;
	private Integer comment;
	private Integer fans_num;
	private String auth;
	private Integer sign_up;
	private String city;
	private String province;
	
	public OrgListBean() {
		this.org_id = 0;
		this.name = "";
		this.pic = "";
		this.comment = 0;
		this.fans_num = 0;
		this.auth = "";
		this.sign_up = 0;
		this.city = "";
		this.province = "";
	}
	
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
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
		this.pic = ImageUtil.parsePicPath(pic);
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
	public Integer getSign_up() {
		return sign_up;
	}
	public void setSign_up(Integer sign_up) {
		this.sign_up = sign_up;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}

}
