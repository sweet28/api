package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class InformationListBean {
	private Integer info_id;
	private String pic;
	private String title;
	private String url;
	private Integer browse_num;
	private String create_time;
	//coffee add 0109 新增资讯类型
	private String info_type;
	//end
	
	
	public Integer getInfo_id() {
		return info_id;
	}
	public void setInfo_id(Integer info_id) {
		this.info_id = info_id;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getBrowse_num() {
		return browse_num;
	}
	public void setBrowse_num(Integer browse_num) {
		this.browse_num = browse_num;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getInfo_type() {
		return info_type;
	}
	public void setInfo_type(String info_type) {
		this.info_type = info_type;
	}	
}
