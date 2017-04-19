package com.arttraining.api.beanv2;

import com.arttraining.commons.util.ImageUtil;

public class CloudHelpRechargeBean {
	private int uid;
	private String utype;
	private String name;
	private String telephone;
	private String sex;
	private String head_pic;
	//coffee add 0409
	private String login_type;
	//end
	
	public CloudHelpRechargeBean() {
		this.uid = 0;
		this.utype = "";
		this.name = "";
		this.telephone = "";
		this.sex = "";
		this.head_pic = "";
		this.login_type="";
	}
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
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
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getHead_pic() {
		return head_pic;
	}
	public void setHead_pic(String head_pic) {
		this.head_pic = ImageUtil.parsePicPath(head_pic, 5);
	}

	public String getLogin_type() {
		return login_type;
	}

	public void setLogin_type(String login_type) {
		if(login_type.equals("yhy")) {
			login_type="云互艺";
		} else if(login_type.equals("qq")) {
			login_type="QQ";
		} else if(login_type.equals("wx")) {
			login_type="微信";
		}
		this.login_type = login_type;
	}
	
}
