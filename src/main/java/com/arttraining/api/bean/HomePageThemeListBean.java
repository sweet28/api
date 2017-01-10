package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class HomePageThemeListBean {
	private String pic;
	private String title;
	private int thm_id;
	private int num;
	
	
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = ImageUtil.parsePicPath(pic,4);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getThm_id() {
		return thm_id;
	}
	public void setThm_id(int thm_id) {
		this.thm_id = thm_id;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}

}
