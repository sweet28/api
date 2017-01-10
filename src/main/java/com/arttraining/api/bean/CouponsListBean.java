package com.arttraining.api.bean;

public class CouponsListBean {
	private int coupon_id;
	private String coupon_name;
	private String destribe;
	private Double face_value;
	private String expiry_date;
	private int coupon_type;
	private int is_used;
	private Double usage_value;
	private String use_time;
	
	public int getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(int coupon_id) {
		this.coupon_id = coupon_id;
	}
	public String getCoupon_name() {
		return coupon_name;
	}
	public void setCoupon_name(String coupon_name) {
		this.coupon_name = coupon_name;
	}
	public String getDestribe() {
		return destribe;
	}
	public void setDestribe(String destribe) {
		this.destribe = destribe;
	}
	public Double getFace_value() {
		return face_value;
	}
	public void setFace_value(Double face_value) {
		this.face_value = face_value;
	}
	public Double getUsage_value() {
		return usage_value;
	}
	public void setUsage_value(Double usage_value) {
		this.usage_value = usage_value;
	}
	public String getExpiry_date() {
		return expiry_date;
	}
	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}
	public int getCoupon_type() {
		return coupon_type;
	}
	public void setCoupon_type(int coupon_type) {
		this.coupon_type = coupon_type;
	}
	public int getIs_used() {
		return is_used;
	}
	public void setIs_used(int is_used) {
		this.is_used = is_used;
	}
	public String getUse_time() {
		return use_time;
	}
	public void setUse_time(String use_time) {
		this.use_time = use_time;
	}
	
	
}
