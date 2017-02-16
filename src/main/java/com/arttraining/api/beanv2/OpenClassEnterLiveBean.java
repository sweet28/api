package com.arttraining.api.beanv2;

import java.util.Date;
import com.arttraining.commons.util.TimeUtil;

public class OpenClassEnterLiveBean {
	private int owner;
	private String owner_type;
	private String name;
	private String head_pic;
	private int like_number;
	private int follow_number;
	private int chapter_number;
	private String pre_time;
	private String curr_time;

	public OpenClassEnterLiveBean() {
		this.owner = 0;
		this.owner_type = "";
		this.name = "";
		this.head_pic = "";
		this.like_number = 0;
		this.follow_number = 0;
		this.chapter_number = 0;
		this.pre_time = "";
		//默认获取当前时间 begin
		Date date=new Date();
		this.curr_time=TimeUtil.getTimeByDate(date);
		//end
	}
	public int getOwner() {
		return owner;
	}
	public void setOwner(int owner) {
		this.owner = owner;
	}
	public String getOwner_type() {
		return owner_type;
	}
	public void setOwner_type(String owner_type) {
		this.owner_type = owner_type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHead_pic() {
		return head_pic;
	}
	public void setHead_pic(String head_pic) {
		this.head_pic = head_pic;
	}
	public int getLike_number() {
		return like_number;
	}
	public void setLike_number(int like_number) {
		this.like_number = like_number;
	}
	public int getFollow_number() {
		return follow_number;
	}
	public void setFollow_number(int follow_number) {
		this.follow_number = follow_number;
	}
	public int getChapter_number() {
		return chapter_number;
	}
	public void setChapter_number(int chapter_number) {
		this.chapter_number = chapter_number;
	}
	public String getPre_time() {
		return pre_time;
	}
	public void setPre_time(String pre_time) {
		if(pre_time!=null && !pre_time.equals("")) {
			pre_time=pre_time.replaceAll("/", "-");
			this.pre_time=pre_time;
		} else {
			this.pre_time="";
		}
		//this.pre_time = pre_time;
	}
	public String getCurr_time() {
		return curr_time;
	}
	public void setCurr_time(String curr_time) {
		this.curr_time = curr_time;
	}
	
}
