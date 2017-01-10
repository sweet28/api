package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;


public class BannerListBean {
	private int banner_id;
	private String title;
	private String pic;
	private String url;

	public int getBanner_id() {
		return banner_id;
	}
	public void setBanner_id(int banner_id) {
		this.banner_id = banner_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = ImageUtil.parsePicPath(pic,4);
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
