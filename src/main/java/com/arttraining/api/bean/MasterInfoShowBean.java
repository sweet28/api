package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class MasterInfoShowBean {
	private String error_code;
	private String error_msg;
	private Integer info_id;
	private String pic;
	private String title;
	private String url;
	private String content;
	private Integer browse_num;
	private String create_time;
	
	public MasterInfoShowBean() {
		this.error_code = "";
		this.error_msg = "";
		this.info_id = 0;
		this.pic = "";
		this.title = "";
		this.url = "";
		this.content = "";
		this.browse_num = 0;
		this.create_time = "";
	}
	
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
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
