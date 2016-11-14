package com.arttraining.api.bean;

import java.util.ArrayList;
import java.util.List;

public class WorkCommentDetailBean {
	private WorkCommentTecInfoBean tec;
	private List<WorkTecCommentBean> tec_comments;
	
	
	public WorkCommentDetailBean() {
		this.tec_comments = new ArrayList<WorkTecCommentBean>();
	}
	public WorkCommentTecInfoBean getTec() {
		return tec;
	}
	public void setTec(WorkCommentTecInfoBean tec) {
		this.tec = tec;
	}
	public List<WorkTecCommentBean> getTec_comments() {
		return tec_comments;
	}
	public void setTec_comments(List<WorkTecCommentBean> tec_comments) {
		this.tec_comments = tec_comments;
	}
	
	
}
