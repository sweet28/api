package com.arttraining.api.beanv2;

import org.apache.commons.lang3.StringUtils;

import com.arttraining.commons.util.pay.utils.MsgCodeUtils;

public class RechargeModel {
	private String error_code;
	private String error_msg;
	private Object data;

	public String getError_code() {
		return StringUtils.isEmpty(error_code) ? MsgCodeUtils.CODE_SUCCESS : error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getError_msg() {
		return StringUtils.isEmpty(error_msg) ? MsgCodeUtils.CODE_SUCCESS_MSG : error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
}
