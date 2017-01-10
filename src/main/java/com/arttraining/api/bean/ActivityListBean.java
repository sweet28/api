package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class ActivityListBean {
	private int act_id;
	private String pic;
	private String title;
	
	public int getAct_id() {
		return act_id;
	}
	public void setAct_id(int act_id) {
		this.act_id = act_id;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = ImageUtil.parsePicPath(pic, 4);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
