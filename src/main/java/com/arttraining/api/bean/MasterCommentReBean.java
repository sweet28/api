package com.arttraining.api.bean;

import java.util.ArrayList;
import java.util.List;

import com.arttraining.commons.util.ImageUtil;

public class MasterCommentReBean {
	private String error_code;
	private String error_msg;
	private String type;
	private Integer stus_id;
	private Integer owner;
	private String owner_type;
	private String owner_name;
	private String owner_head_pic;
	private String create_time;
	private String tag;
	private String city;
	private String identity;
	private String title;
	private String content;
	private Integer browse_num;
	private Integer comment_num;
	private Integer like_num;
	//coffee add 1201
	private Integer ass_id;
	private Integer tec_comment_num;
	//end
	private List<HomePageAttBean> att;
	private List<MasterCommentListBean> tec_comments_list;
	
	public MasterCommentReBean() {
		this.type = "";
		this.stus_id = 0;
		this.owner = 0;
		this.owner_type = "";
		this.owner_name = "";
		this.owner_head_pic = "";
		this.create_time = "";
		this.tag = "";
		this.city = "";
		this.identity = "";
		this.title = "";
		this.content = "";
		this.browse_num = 0;
		this.comment_num = 0;
		this.like_num = 0;
		this.ass_id=0;
		this.tec_comment_num=0;
		this.att = new ArrayList<HomePageAttBean>();
		this.tec_comments_list = new ArrayList<MasterCommentListBean>();
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getStus_id() {
		return stus_id;
	}
	public void setStus_id(Integer stus_id) {
		this.stus_id = stus_id;
	}
	public Integer getOwner() {
		return owner;
	}
	public void setOwner(Integer owner) {
		this.owner = owner;
	}
	public String getOwner_type() {
		return owner_type;
	}
	public void setOwner_type(String owner_type) {
		this.owner_type = owner_type;
	}
	public String getOwner_name() {
		return owner_name;
	}
	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}
	public String getOwner_head_pic() {
		return owner_head_pic;
	}
	public void setOwner_head_pic(String owner_head_pic) {
		this.owner_head_pic = ImageUtil.parsePicPath(owner_head_pic, 5);
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getBrowse_num() {
		return browse_num;
	}
	public void setBrowse_num(Integer browse_num) {
		this.browse_num = browse_num;
	}
	public Integer getComment_num() {
		return comment_num;
	}
	public void setComment_num(Integer comment_num) {
		this.comment_num = comment_num;
	}
	public Integer getLike_num() {
		return like_num;
	}
	public void setLike_num(Integer like_num) {
		this.like_num = like_num;
	}
	public List<HomePageAttBean> getAtt() {
		return att;
	}
	public void setAtt(List<HomePageAttBean> att) {
		this.att = att;
	}
	public List<MasterCommentListBean> getTec_comments_list() {
		return tec_comments_list;
	}
	public void setTec_comments_list(List<MasterCommentListBean> tec_comments_list) {
		this.tec_comments_list = tec_comments_list;
	}

	public Integer getAss_id() {
		return ass_id;
	}

	public void setAss_id(Integer ass_id) {
		this.ass_id = ass_id;
	}

	public Integer getTec_comment_num() {
		return tec_comment_num;
	}

	public void setTec_comment_num(Integer tec_comment_num) {
		this.tec_comment_num = tec_comment_num;
	}
	
}
