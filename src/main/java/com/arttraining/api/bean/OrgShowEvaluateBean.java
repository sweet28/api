package com.arttraining.api.bean;

import java.util.List;

public class OrgShowEvaluateBean {
	private OrgShowGeneralBean general;
	private List<OrgShowListevaBean> list_eva;
	
	public OrgShowGeneralBean getGeneral() {
		return general;
	}
	public void setGeneral(OrgShowGeneralBean general) {
		this.general = general;
	}
	public List<OrgShowListevaBean> getList_eva() {
		return list_eva;
	}
	public void setList_eva(List<OrgShowListevaBean> list_eva) {
		this.list_eva = list_eva;
	}
}
