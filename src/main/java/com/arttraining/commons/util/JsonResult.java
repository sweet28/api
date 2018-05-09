package com.arttraining.commons.util;

import java.io.Serializable;

public class JsonResult implements Serializable {

	// 响应业务状态
	private Integer status;

	// 响应消息
	private String msg;

	// 响应中的数据
	private Object data;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public JsonResult(Integer status, String msg, Object data) {
		super();
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public static JsonResult ok() {
		return new JsonResult(null);
	}

	public static JsonResult ok(Object data) {
		return new JsonResult(data);
	}

	public JsonResult() {

	}

	public static JsonResult build(Integer status, String msg) {
		return new JsonResult(status, msg, null);
	}

	public JsonResult(Object data) {
		this.status = 200;
		this.msg = "OK";
		this.data = data;
	}

	@Override
	public String toString() {
		return "JsonResult [status=" + status + ", msg=" + msg + ", data=" + data + "]";
	}

}
