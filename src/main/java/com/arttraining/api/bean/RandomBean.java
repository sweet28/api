package com.arttraining.api.bean;

public class RandomBean implements Comparable<RandomBean> {
	private Integer id;
	private Integer weight;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	@Override
	public int compareTo(RandomBean o) {
		// TODO Auto-generated method stub
		int compare = 0;
		int result = this.getWeight() - o.getWeight();
        if (result > 0) {
            compare = 1;
        } else if (result < 0) {
            compare = -1;
        } 
        return compare;
	}
}
