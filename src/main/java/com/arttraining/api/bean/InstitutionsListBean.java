package com.arttraining.api.bean;

import com.arttraining.commons.util.ImageUtil;

public class InstitutionsListBean {
	private Integer institution_id;
	private String name;
	private String institution_pic;
	private String admissions_guide;
	private Integer follow_num;
	private Integer browse_num;
	
	
	public Integer getInstitution_id() {
		return institution_id;
	}
	public void setInstitution_id(Integer institution_id) {
		this.institution_id = institution_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInstitution_pic() {
		return institution_pic;
	}
	public void setInstitution_pic(String institution_pic) {
		this.institution_pic = ImageUtil.parsePicPath(institution_pic,5);
	}
	public String getAdmissions_guide() {
		return admissions_guide;
	}
	public void setAdmissions_guide(String admissions_guide) {
		this.admissions_guide = admissions_guide;
	}
	public Integer getFollow_num() {
		return follow_num;
	}
	public void setFollow_num(Integer follow_num) {
		this.follow_num = follow_num;
	}
	public Integer getBrowse_num() {
		return browse_num;
	}
	public void setBrowse_num(Integer browse_num) {
		this.browse_num = browse_num;
	}
	
	

}
