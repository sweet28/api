package com.arttraining.api.beanv3;

import com.arttraining.commons.util.ImageUtil;
import com.arttraining.commons.util.NumberUtil;

public class EntranceCollegeBean {
	private int college_id;
	private String province;
	private String college_name;
	private String college_code;
	private String college_logo;
	private String website;
	private String trend;
	private double score;
	private String level;
	private double p_major_score;
	private double p_culture_score;
	private double c_major_score;
	private double c_culture_score;
	private String entroller_rule;
	
	
	public int getCollege_id() {
		return college_id;
	}
	public void setCollege_id(int college_id) {
		this.college_id = college_id;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getCollege_name() {
		return college_name;
	}
	public void setCollege_name(String college_name) {
		this.college_name = college_name;
	}
	public String getCollege_code() {
		return college_code;
	}
	public void setCollege_code(String college_code) {
		this.college_code = college_code;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getTrend() {
		return trend;
	}
	public void setTrend(String trend) {
		this.trend = trend;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = NumberUtil.formatDouble2(score);
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public double getP_major_score() {
		return p_major_score;
	}
	public void setP_major_score(double p_major_score) {
		this.p_major_score = NumberUtil.formatDouble2(p_major_score);
	}
	public double getP_culture_score() {
		return p_culture_score;
	}
	public void setP_culture_score(double p_culture_score) {
		this.p_culture_score = NumberUtil.formatDouble2(p_culture_score);
	}
	public double getC_major_score() {
		return c_major_score;
	}
	public void setC_major_score(double c_major_score) {
		this.c_major_score = NumberUtil.formatDouble2(c_major_score);
	}
	public double getC_culture_score() {
		return c_culture_score;
	}
	public void setC_culture_score(double c_culture_score) {
		this.c_culture_score = NumberUtil.formatDouble2(c_culture_score);
	}
	public String getEntroller_rule() {
		return entroller_rule;
	}
	public void setEntroller_rule(String entroller_rule) {
		this.entroller_rule = entroller_rule;
	}
	public String getCollege_logo() {
		return college_logo;
	}
	public void setCollege_logo(String college_logo) {
		this.college_logo = ImageUtil.parsePicPath(college_logo, 5);
	}
	
}
