package com.arttraining.api.bean;

public class MasterNumBean {
	private String error_code;
	private String error_msg;
	private int no_num;
	private int yes_num;
	private int msg_num;
	
	public MasterNumBean() {
		this.no_num = 0;
		this.yes_num = 0;
		this.msg_num = 0;
	}
	
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	public int getNo_num() {
		return no_num;
	}
	public void setNo_num(int no_num) {
		this.no_num = no_num;
	}
	public int getYes_num() {
		return yes_num;
	}
	public void setYes_num(int yes_num) {
		this.yes_num = yes_num;
	}
	public int getMsg_num() {
		return msg_num;
	}
	public void setMsg_num(int msg_num) {
		this.msg_num = msg_num;
	}
	
	
}
