package com.arttraining.api.beanv2;

import com.arttraining.commons.util.ImageUtil;

public class LiveAuthBean {
	private String error_code;
	private String error_msg;
	private int uid;
	private String utype;
	private String name;
	private String telephone;
	private String sex;
	private String city;
	private String address;
	private String work_place;
	private String work_year;
	private String birth;
	private String id_number;
	private String introduction;
	private String resume;
	private String win_price;
	private String id_number_pic;
	private String teaching_certify_pic;
	private String graduate_certify_pic;
	private String industry_certify_pic;
	private String other_certify_pic;
	//coffee add 0113
	private int is_publish;
	
	//end
	public LiveAuthBean() {
		this.uid = 0;
		this.utype = "";
		this.name = "";
		this.telephone = "";
		this.sex = "";
		this.city = "";
		this.address = "";
		this.work_place = "";
		this.work_year = "";
		this.birth = "";
		this.id_number = "";
		this.introduction = "";
		this.resume = "";
		this.win_price = "";
		this.id_number_pic = "";
		this.teaching_certify_pic = "";
		this.graduate_certify_pic = "";
		this.industry_certify_pic = "";
		this.other_certify_pic = "";
		this.is_publish=0;
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
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUtype() {
		return utype;
	}
	public void setUtype(String utype) {
		this.utype = utype;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWork_place() {
		return work_place;
	}
	public void setWork_place(String work_place) {
		this.work_place = work_place;
	}
	
	public String getWork_year() {
		return work_year;
	}

	public void setWork_year(String work_year) {
		this.work_year = work_year;
	}

	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getId_number() {
		return id_number;
	}
	public void setId_number(String id_number) {
		this.id_number = id_number;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public String getWin_price() {
		return win_price;
	}
	public void setWin_price(String win_price) {
		this.win_price = win_price;
	}
	public String getId_number_pic() {
		return id_number_pic;
	}
	public void setId_number_pic(String id_number_pic) {
		this.id_number_pic = ImageUtil.parsePicPath(id_number_pic, 5);
	}
	public String getTeaching_certify_pic() {
		return teaching_certify_pic;
	}
	public void setTeaching_certify_pic(String teaching_certify_pic) {
		this.teaching_certify_pic = ImageUtil.parsePicPath(teaching_certify_pic, 5);
	}
	public String getGraduate_certify_pic() {
		return graduate_certify_pic;
	}
	public void setGraduate_certify_pic(String graduate_certify_pic) {
		this.graduate_certify_pic = ImageUtil.parsePicPath(graduate_certify_pic, 5);
	}
	public String getIndustry_certify_pic() {
		return industry_certify_pic;
	}
	public void setIndustry_certify_pic(String industry_certify_pic) {
		this.industry_certify_pic = ImageUtil.parsePicPath(industry_certify_pic, 5);
	}
	public String getOther_certify_pic() {
		return other_certify_pic;
	}
	public void setOther_certify_pic(String other_certify_pic) {
		this.other_certify_pic = ImageUtil.parsePicPath(other_certify_pic, 5);
	}
	public int getIs_publish() {
		return is_publish;
	}
	public void setIs_publish(int is_publish) {
		this.is_publish = is_publish;
	}
}
