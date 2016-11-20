package com.arttraining.api.service;

import java.util.List;

import com.arttraining.api.pojo.Assessments;
import com.arttraining.api.pojo.Order;
import com.arttraining.api.pojo.Works;
import com.arttraining.api.pojo.WorksAttchment;

public interface IOrdersService {
	int insert(Order order);
	
	int insertAndUpdateWorkAssAtt(Order order, Works works, List<Assessments> assList, WorksAttchment workAtt);
	
	int update(Order order);
	
	int updateAndUpdateWorkAssAtt(Order order, Works works, List<Assessments> assList, WorksAttchment workAtt);
	
	int updateAndUpdateWorkAssAtt(Order order, WorksAttchment workAtt);
	
	Order selectByOrderNumber(String orderNumber);
	
	Order selectByPrimaryKey(Integer id);
}
