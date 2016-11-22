package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class HomePageAdvertiseBean {
	private String stus_type;
	private Integer ad_id;
	private String ad_pic;
	
	public HomePageAdvertiseBean() {
		this.stus_type = "ad";
		this.ad_id = 0;
		this.ad_pic = "";
	}
	
	public String getStus_type() {
		return stus_type;
	}
	public void setStus_type(String stus_type) {
		this.stus_type = stus_type;
	}
	public Integer getAd_id() {
		return ad_id;
	}
	public void setAd_id(Integer ad_id) {
		this.ad_id = ad_id;
	}
	public String getAd_pic() {
		return ad_pic;
	}
	public void setAd_pic(String ad_pic) {
		this.ad_pic = ImageUtil.parsePicPath(ad_pic,4);
	}
	
}
