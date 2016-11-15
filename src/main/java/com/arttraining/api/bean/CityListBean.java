package com.arttraining.api.bean;

public class CityListBean {
	private Integer city_id;
	private Integer father_id;
	private String father_name;
	private String name;
	private Integer level;
	
	
	public Integer getCity_id() {
		return city_id;
	}
	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}
	public Integer getFather_id() {
		return father_id;
	}
	public void setFather_id(Integer father_id) {
		this.father_id = father_id;
	}
	public String getFather_name() {
		return father_name;
	}
	public void setFather_name(String father_name) {
		this.father_name = father_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	
}
