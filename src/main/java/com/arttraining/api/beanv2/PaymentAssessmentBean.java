package com.arttraining.api.beanv2;

public class PaymentAssessmentBean {
	private int order_id;
	private String order_number;
	private String create_time;
	
	public PaymentAssessmentBean() {
		this.order_id = 0;
		this.order_number = "";
		this.create_time = "";
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
}
