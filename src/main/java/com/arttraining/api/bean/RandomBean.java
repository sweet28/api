package com.arttraining.api.bean;

public class RandomBean implements Comparable<RandomBean> {
	private int id;
	private int weight;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
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
