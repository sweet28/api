package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class OrgShowListevaBean {
	private Double total_score;
	private Double price_score;
	private Double result_score;
	private Double teachers_score;
	private Double environment_score;
	private Integer uid;
	private String name;
	private String pic;
	private String content;
	
	
	public Double getTotal_score() {
		return total_score;
	}
	public void setTotal_score(Double total_score) {
		this.total_score = total_score;
	}
	public Double getPrice_score() {
		return price_score;
	}
	public void setPrice_score(Double price_score) {
		this.price_score = price_score;
	}
	public Double getResult_score() {
		return result_score;
	}
	public void setResult_score(Double result_score) {
		this.result_score = result_score;
	}
	public Double getTeachers_score() {
		return teachers_score;
	}
	public void setTeachers_score(Double teachers_score) {
		this.teachers_score = teachers_score;
	}
	public Double getEnvironment_score() {
		return environment_score;
	}
	public void setEnvironment_score(Double environment_score) {
		this.environment_score = environment_score;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
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
		this.pic = ImageUtil.parsePicPath(pic);
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
