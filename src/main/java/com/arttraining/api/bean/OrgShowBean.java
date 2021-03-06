package com.arttraining.api.bean;

import java.util.ArrayList;
import java.util.List;

import com.arttraining.commons.util.ImageUtil;

public class OrgShowBean {
	private String error_code;
	private String error_msg;
	private int org_id;
	private String name;
	private int comment;
	private int fans_num;
	private String auth;
	private int sign_up;
	private int browse_num;
	private String introduction;
	private String remarks;
	private String city;
	private String province;
	private String skill;
	private String head_pic;
	//图片地址
	private List<String> pic;
	//标签
	private List<String> tags;
	//教师
	private List<OrgShowTecherBean> teachers;
	//课程
	private List<OrgShowCourseBean> course;
	//优秀考生
	private List<OrgShowTraineesBean> trainees;
	//联系方式
	private String contact_phone;
    private String contact_address;

	//评价
	private OrgShowEvaluateBean evaluate;
	
	//新增是否关注
	private String is_follow;

	
	public OrgShowBean() {
		this.sign_up = 0;
		this.tags = new ArrayList<String>();
		this.teachers = new ArrayList<OrgShowTecherBean>();
		this.course = new ArrayList<OrgShowCourseBean>();
		this.trainees = new ArrayList<OrgShowTraineesBean>();
		this.contact_address = "";
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

	public int getOrg_id() {
		return org_id;
	}

	public void setOrg_id(int org_id) {
		this.org_id = org_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

	public int getFans_num() {
		return fans_num;
	}

	public void setFans_num(int fans_num) {
		this.fans_num = fans_num;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public int getSign_up() {
		return sign_up;
	}

	public void setSign_up(int sign_up) {
		this.sign_up = sign_up;
	}

	public int getBrowse_num() {
		return browse_num;
	}

	public void setBrowse_num(int browse_num) {
		this.browse_num = browse_num;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks =  ImageUtil.parsePicPath(remarks,5);
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getHead_pic() {
		return head_pic;
	}

	public void setHead_pic(String head_pic) {
		this.head_pic = ImageUtil.parsePicPath(head_pic,5);
	}

	public List<String> getPic() {
		return pic;
	}

	public void setPic(List<String> pic) {
		this.pic = pic;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<OrgShowTecherBean> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<OrgShowTecherBean> teachers) {
		this.teachers = teachers;
	}

	public List<OrgShowCourseBean> getCourse() {
		return course;
	}

	public void setCourse(List<OrgShowCourseBean> course) {
		this.course = course;
	}

	public List<OrgShowTraineesBean> getTrainees() {
		return trainees;
	}

	public void setTrainees(List<OrgShowTraineesBean> trainees) {
		this.trainees = trainees;
	}

	public String getContact_phone() {
		return contact_phone;
	}

	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	public String getContact_address() {
		return contact_address;
	}

	public void setContact_address(String contact_address) {
		this.contact_address = contact_address;
	}

	public OrgShowEvaluateBean getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(OrgShowEvaluateBean evaluate) {
		this.evaluate = evaluate;
	}

	public String getIs_follow() {
		return is_follow;
	}

	public void setIs_follow(String is_follow) {
		this.is_follow = is_follow;
	}
	
}
