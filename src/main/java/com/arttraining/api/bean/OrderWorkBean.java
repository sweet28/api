package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class OrderWorkBean {
	private Integer work_id;
	private String work_title;
	private String work_pic;
	
	public OrderWorkBean() {
		this.work_id = 0;
		this.work_title = "";
		this.work_pic = "";
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
		this.work_pic = ImageUtil.parsePicPath(work_pic,6);
	}
	
	
}
