package com.arttraining.api.beanv2;

import java.util.ArrayList;
import java.util.List;

public class LiveTimeTableBean {
	private int timetable_id;
	private String name;
	private String introduction;
	private double price;
	private int major_one;
	private int major_two;
	private int live_type;
	private int is_free;
	//coffee add 0121
	private List<LiveChapterListBean> chapter_list;
	//end

	public LiveTimeTableBean() {
		this.chapter_list = new ArrayList<LiveChapterListBean>();
	}
	
	public int getTimetable_id() {
		return timetable_id;
	}
	public void setTimetable_id(int timetable_id) {
		this.timetable_id = timetable_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getMajor_one() {
		return major_one;
	}
	public void setMajor_one(int major_one) {
		this.major_one = major_one;
	}
	public int getMajor_two() {
		return major_two;
	}
	public void setMajor_two(int major_two) {
		this.major_two = major_two;
	}
	public int getLive_type() {
		return live_type;
	}
	public void setLive_type(int live_type) {
		this.live_type = live_type;
	}
	public int getIs_free() {
		return is_free;
	}
	public void setIs_free(int is_free) {
		this.is_free = is_free;
	}
	public List<LiveChapterListBean> getChapter_list() {
		return chapter_list;
	}
	public void setChapter_list(List<LiveChapterListBean> chapter_list) {
		this.chapter_list = chapter_list;
	}
	

}
