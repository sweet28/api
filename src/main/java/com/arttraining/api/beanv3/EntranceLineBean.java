package com.arttraining.api.beanv3;

public class EntranceLineBean {
	private int line_id;
	private String year;
	private String province;
	private String category;
	private double art_line;
	private String remarks;
	private String data_source;
	
	
	public int getLine_id() {
		return line_id;
	}
	public void setLine_id(int line_id) {
		this.line_id = line_id;
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
	public double getArt_line() {
		return art_line;
	}
	public void setArt_line(double art_line) {
		this.art_line = art_line;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getData_source() {
		return data_source;
	}
	public void setData_source(String data_source) {
		this.data_source = data_source;
	}
}
