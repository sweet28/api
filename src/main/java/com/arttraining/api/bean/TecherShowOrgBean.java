package com.arttraining.api.bean;

public class TecherShowOrgBean {
	private String name;
	private Integer org_id;
	private String auth;
	
	public TecherShowOrgBean() {
		this.name = "";
		this.org_id = 0;
		this.auth = "";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	
}
