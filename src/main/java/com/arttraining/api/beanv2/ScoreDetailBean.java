package com.arttraining.api.beanv2;

public class ScoreDetailBean {
	private int score_id;
	private int score;
	private int curr_score;
	private String consume_type;
	private String consume_time;
	private String remark;
	
	public int getScore_id() {
		return score_id;
	}
	public void setScore_id(int score_id) {
		this.score_id = score_id;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getCurr_score() {
		return curr_score;
	}
	public void setCurr_score(int curr_score) {
		this.curr_score = curr_score;
	}
	public String getConsume_type() {
		return consume_type;
	}
	public void setConsume_type(String consume_type) {
		this.consume_type = consume_type;
	}
	public String getConsume_time() {
		return consume_time;
	}
	public void setConsume_time(String consume_time) {
		this.consume_time = consume_time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
