package com.arttraining.api.bean;

public class OrderListMyBean {
	private Integer order_type;
	private String order_number;
	private Integer order_id;
	private Integer order_status;
	private String order_time;
	private Integer order_element_num;
	private Double order_total_price;
	private Integer work_id;
	private String work_title;
	private String work_pic;

	public OrderListMyBean() {
		this.order_type = 0;
		this.order_number = "";
		this.order_id = 0;
		this.order_status = 0;
		this.order_time = "";
		this.order_element_num = 0;
		this.order_total_price = 0.00;
		this.work_id = 0;
		this.work_title = "";
		this.work_pic = "";
	}
	public Integer getOrder_type() {
		return order_type;
	}
	public void setOrder_type(Integer order_type) {
		this.order_type = order_type;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public Integer getOrder_status() {
		return order_status;
	}
	public void setOrder_status(Integer order_status) {
		this.order_status = order_status;
	}
	public String getOrder_time() {
		return order_time;
	}
	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}
	public Integer getOrder_element_num() {
		return order_element_num;
	}
	public void setOrder_element_num(Integer order_element_num) {
		this.order_element_num = order_element_num;
	}
	public Double getOrder_total_price() {
		return order_total_price;
	}
	public void setOrder_total_price(Double order_total_price) {
		this.order_total_price = order_total_price;
	}
	public Integer getWork_id() {
		return work_id;
	}
	public void setWork_id(Integer work_id) {
		this.work_id = work_id;
	}
	public String getWork_title() {
		return work_title;
	}
	public void setWork_title(String work_title) {
		this.work_title = work_title;
	}
	public String getWork_pic() {
		return work_pic;
	}
	public void setWork_pic(String work_pic) {
		this.work_pic = work_pic;
	}

}
