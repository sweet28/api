package com.arttraining.api.bean;

public class TecherShowOrgBean {
	private String name;
	private int org_id;
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
	public int getOrg_id() {
		return org_id;
	}
	public void setOrg_id(int org_id) {
		this.org_id = org_id;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	
}
