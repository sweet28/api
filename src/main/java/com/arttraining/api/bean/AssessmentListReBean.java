package com.arttraining.api.bean;

import java.util.ArrayList;
import java.util.List;

public class AssessmentListReBean {
	private String error_code;
	private String error_msg;
	private Integer order_type;
	private String order_number;
	private Integer order_id;
	private Integer order_status;
    private String order_time;
    private Integer order_element_num;
    private Double order_total_price;
    private Integer ass_num;
    private List<AssessmentListBean> assessments;
    
	public AssessmentListReBean() {
		this.order_type = 0;
		this.order_number = "";
		this.order_id = 0;
		this.order_status = 0;
		this.order_time = "";
		this.order_element_num = 0;
		this.order_total_price = 0.00;
		this.ass_num=0;
		this.assessments = new ArrayList<AssessmentListBean>();
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
	public List<AssessmentListBean> getAssessments() {
		return assessments;
	}
	public void setAssessments(List<AssessmentListBean> assessments) {
		this.assessments = assessments;
	}
	public Integer getAss_num() {
		return ass_num;
	}
	public void setAss_num(Integer ass_num) {
		this.ass_num = ass_num;
	}
    
    

}
