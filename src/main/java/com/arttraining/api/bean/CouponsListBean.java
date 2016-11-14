package com.arttraining.api.bean;

public class CouponsListBean {
	private Integer coupon_id;
	private String coupon_name;
	private String destribe;
	private Integer face_value;
	private String expiry_date;
	private Integer coupon_type;
	private Integer is_used;
	
	public Integer getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(Integer coupon_id) {
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
	public Integer getFace_value() {
		return face_value;
	}
	public void setFace_value(Integer face_value) {
		this.face_value = face_value;
	}
	public String getExpiry_date() {
		return expiry_date;
	}
	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}
	public Integer getCoupon_type() {
		return coupon_type;
	}
	public void setCoupon_type(Integer coupon_type) {
		this.coupon_type = coupon_type;
	}
	public Integer getIs_used() {
		return is_used;
	}
	public void setIs_used(Integer is_used) {
		this.is_used = is_used;
	}
	
}
