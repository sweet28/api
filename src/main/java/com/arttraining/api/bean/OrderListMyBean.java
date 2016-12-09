package com.arttraining.api.bean;

import java.util.ArrayList;
import java.util.List;

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
	private Integer ass_num;
	//coffee add 1208
	private String active_time;
	private Integer remaining_time;
	private Integer coupon_id;
	private Integer coupon_type;
	private List<AssTecListBean> ass_tec_list;
	//end

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
		this.ass_num=0;
		this.active_time="";
		this.coupon_id=0;
		this.coupon_type=-1;
		this.remaining_time=0;
		this.ass_tec_list = new ArrayList<AssTecListBean>();
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
	public Integer getAss_num() {
		return ass_num;
	}
	public void setAss_num(Integer ass_num) {
		this.ass_num = ass_num;
	}
	public String getActive_time() {
		return active_time;
	}
	public void setActive_time(String active_time) {
		this.active_time = active_time;
	}
	
	public Integer getRemaining_time() {
		return remaining_time;
	}
	public void setRemaining_time(Integer remaining_time) {
		this.remaining_time = remaining_time;
	}
	public Integer getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(Integer coupon_id) {
		this.coupon_id = coupon_id;
	}
	public Integer getCoupon_type() {
		return coupon_type;
	}
	public void setCoupon_type(Integer coupon_type) {
		this.coupon_type = coupon_type;
	}
	public List<AssTecListBean> getAss_tec_list() {
		return ass_tec_list;
	}
	public void setAss_tec_list(List<AssTecListBean> ass_tec_list) {
		this.ass_tec_list = ass_tec_list;
	}

}
