package com.arttraining.commons.util.pay.utils;

import org.apache.commons.lang3.StringUtils;

public class MessageModel {
	private String error_code;
	private String error_msg;
	private Object model;

	public String getCode() {
		return StringUtils.isEmpty(error_code) ? MsgCodeUtils.CODE_SUCCESS : error_code;
	}

	public void setCode(String error_code) {
		this.error_code = error_code;
	}

	public String getMsg() {
		return StringUtils.isEmpty(error_msg) ? MsgCodeUtils.CODE_SUCCESS_MSG : error_msg;
	}

	public void setMsg(String error_msg) {
		this.error_msg = error_msg;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}
}
