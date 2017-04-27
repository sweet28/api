package com.arttraining.api.beanv2;

import com.arttraining.commons.util.ImageUtil;

public class LiveHistoryBean {
	private int chapter_id;
	private String chapter_name;
	private String start_time;
	private String end_time;
	private double live_price;
	private double record_price;
	private int is_free;
	private int live_status;
	private String introduction;
	
	private int room_id;
	private int owner;
	private String owner_type;
	private String name;
	private String head_pic;
	private int browse_number;
	private int fans_num;
	private String thumbnail;
	
	public int getChapter_id() {
		return chapter_id;
	}
	public void setChapter_id(int chapter_id) {
		this.chapter_id = chapter_id;
	}
	public String getChapter_name() {
		return chapter_name;
	}
	public void setChapter_name(String chapter_name) {
		this.chapter_name = chapter_name;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public double getLive_price() {
		return live_price;
	}
	public void setLive_price(double live_price) {
		this.live_price = live_price;
	}
	public double getRecord_price() {
		return record_price;
	}
	public void setRecord_price(double record_price) {
		this.record_price = record_price;
	}
	public int getIs_free() {
		return is_free;
	}
	public void setIs_free(int is_free) {
		this.is_free = is_free;
	}
	public int getLive_status() {
		return live_status;
	}
	public void setLive_status(int live_status) {
		this.live_status = live_status;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public int getRoom_id() {
		return room_id;
	}
	public void setRoom_id(int room_id) {
		this.room_id = room_id;
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
		this.head_pic = ImageUtil.parsePicPath(head_pic, 5);
	}
	public int getBrowse_number() {
		return browse_number;
	}
	public void setBrowse_number(int browse_number) {
		this.browse_number = browse_number;
	}
	public int getFans_num() {
		return fans_num;
	}
	public void setFans_num(int fans_num) {
		this.fans_num = fans_num;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = ImageUtil.parsePicPath(thumbnail, 5);
	}
}
