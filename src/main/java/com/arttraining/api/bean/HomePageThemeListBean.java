package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class HomePageThemeListBean {
	private String pic;
	private String title;
	private Integer thm_id;
	private Integer num;
	
	
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
	public Integer getThm_id() {
		return thm_id;
	}
	public void setThm_id(Integer thm_id) {
		this.thm_id = thm_id;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}

}
