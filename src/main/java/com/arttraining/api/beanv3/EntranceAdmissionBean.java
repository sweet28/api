package com.arttraining.api.beanv3;

public class EntranceAdmissionBean {
	private int admission_id;
	private String year;
	private String province;
	private String category;
	private String batch;
	private double culture_score;
	private double art_score;
	private String data_source;
	
	
	public int getAdmission_id() {
		return admission_id;
	}
	public void setAdmission_id(int admission_id) {
		this.admission_id = admission_id;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public double getCulture_score() {
		return culture_score;
	}
	public void setCulture_score(double culture_score) {
		this.culture_score = culture_score;
	}
	public double getArt_score() {
		return art_score;
	}
	public void setArt_score(double art_score) {
		this.art_score = art_score;
	}
	public String getData_source() {
		return data_source;
	}
	public void setData_source(String data_source) {
		this.data_source = data_source;
	}
	
}
