package com.arttraining.api.bean;

public class InstitutionsShowReBean {
	private String error_code;
	private String error_msg;
	private InstitutionsShowBean institutions;
	
	
	public InstitutionsShowReBean() {
		this.institutions = new InstitutionsShowBean();
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
	public InstitutionsShowBean getInstitutions() {
		return institutions;
	}
	public void setInstitutions(InstitutionsShowBean institutions) {
		this.institutions = institutions;
	}

}
