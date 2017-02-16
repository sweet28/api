package com.arttraining.api.beanv2;

import com.arttraining.commons.util.ImageUtil;

public class OpenClassLiveListBean {
	private int room_id;
	private int owner;
	private String name;
	private String owner_type;
	private String live_name;
	private String thumbnail;
	private int browse_number;
	private int chapter_number;
	private String pre_time;
	private int live_status;
	private String chapter_name;
	//coffee add 0122
	private int chapter_id;
	//end
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOwner_type() {
		return owner_type;
	}
	public void setOwner_type(String owner_type) {
		this.owner_type = owner_type;
	}
	public String getLive_name() {
		return live_name;
	}
	public void setLive_name(String live_name) {
		this.live_name = live_name;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		//this.thumbnail = ImageUtil.parsePicPath(thumbnail, 7);
		this.thumbnail = ImageUtil.parsePicPath(thumbnail, 5);
	}
	public int getBrowse_number() {
		return browse_number;
	}
	public void setBrowse_number(int browse_number) {
		this.browse_number = browse_number;
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
	}
	public int getLive_status() {
		return live_status;
	}
	public void setLive_status(int live_status) {
		this.live_status = live_status;
	}
	public String getChapter_name() {
		return chapter_name;
	}
	public void setChapter_name(String chapter_name) {
		this.chapter_name = chapter_name;
	}
	public int getChapter_id() {
		return chapter_id;
	}
	public void setChapter_id(int chapter_id) {
		this.chapter_id = chapter_id;
	}
	
}
