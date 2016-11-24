package com.arttraining.api.bean;

import java.util.ArrayList;
import java.util.List;

public class MasterAssessmentReBean {
	private String error_code;
	private String error_msg;
	private List<MasterAssessmentBean>  assessments;

	public MasterAssessmentReBean() {
		this.assessments = new ArrayList<MasterAssessmentBean>();
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
	public List<MasterAssessmentBean> getAssessments() {
		return assessments;
	}
	public void setAssessments(List<MasterAssessmentBean> assessments) {
		this.assessments = assessments;
	}
}
