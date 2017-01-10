package com.arttraining.api.bean;

public class CommentsHostBean {
    private int user_id;
	private String name;
	private String user_type;

	public CommentsHostBean() {
		this.user_id = 0;
		this.name = "";
		this.user_type = "";
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
}
