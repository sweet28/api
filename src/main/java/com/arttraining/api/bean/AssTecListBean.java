package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class AssTecListBean {
	private int tec_id;
	private String tec_name;
	private String tec_pic;
	private Boolean tec_status;
	
	
	public int getTec_id() {
		return tec_id;
	}
	public void setTec_id(int tec_id) {
		this.tec_id = tec_id;
	}
	public String getTec_name() {
		return tec_name;
	}
	public void setTec_name(String tec_name) {
		this.tec_name = tec_name;
	}
	public String getTec_pic() {
		return tec_pic;
	}
	public void setTec_pic(String tec_pic) {
		this.tec_pic = ImageUtil.parsePicPath(tec_pic, 5);
	}
	public Boolean getTec_status() {
		return tec_status;
	}
	public void setTec_status(Boolean tec_status) {
		this.tec_status = tec_status;
	}
}
