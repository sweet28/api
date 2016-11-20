package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;


public class BannerListBean {
	private Integer banner_id;
	private String title;
	private String pic;
	private String url;

	public Integer getBanner_id() {
		return banner_id;
	}
	public void setBanner_id(Integer banner_id) {
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
		this.pic = ImageUtil.parsePicPath(pic);
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
