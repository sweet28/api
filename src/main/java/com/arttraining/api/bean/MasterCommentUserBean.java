package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class MasterCommentUserBean {
	private String name;
	private String pic;
	
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
		this.pic = ImageUtil.parsePicPath(pic, 5);
	}
	
}
