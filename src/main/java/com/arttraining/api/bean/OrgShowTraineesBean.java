package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class OrgShowTraineesBean {
	private int tra_id;
	private String tra_name;
	private String tra_pic;
	private String tra_school;
	
	public int getTra_id() {
		return tra_id;
	}
	public void setTra_id(int tra_id) {
		this.tra_id = tra_id;
	}
	public String getTra_name() {
		return tra_name;
	}
	public void setTra_name(String tra_name) {
		this.tra_name = tra_name;
	}
	public String getTra_pic() {
		return tra_pic;
	}
	public void setTra_pic(String tra_pic) {
		this.tra_pic = ImageUtil.parsePicPath(tra_pic,5);
	}
	public String getTra_school() {
		return tra_school;
	}
	public void setTra_school(String tra_school) {
		this.tra_school = tra_school;
	}
}
