package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.AssessmentListReBean;
import com.arttraining.api.bean.OrderListMyBean;
import com.arttraining.api.bean.OrderWorkBean;
import com.arttraining.api.dao.AssessmentsMapper;
import com.arttraining.api.dao.OrderMapper;
import com.arttraining.api.dao.WorksAttchmentMapper;
import com.arttraining.api.dao.WorksMapper;
import com.arttraining.api.pojo.Assessments;
import com.arttraining.api.pojo.Order;
import com.arttraining.api.pojo.Works;
import com.arttraining.api.pojo.WorksAttchment;
import com.arttraining.api.service.IOrdersService;
import com.arttraining.commons.util.TimeUtil;

@Service("OrdersService")
public class OrdersService implements IOrdersService{
	@Resource
	private OrderMapper orderDao;
	@Resource
	private WorksMapper workDao;
	@Resource
	private AssessmentsMapper assDao;
	@Resource
	private WorksAttchmentMapper workAttDao;

	@Override
	public int insert(Order order) {
		return this.orderDao.insertSelective(order);
	}

	@Override
	public int update(Order order) {
		return this.orderDao.updateByPrimaryKeySelective(order);
	}

	@Override
	public int insertAndUpdateWorkAssAtt(Order order, Works works,
			List<Assessments> assList, WorksAttchment workAtt) {
		int result = 0;
		result = this.orderDao.insertSelective(order);
		
		int orderId = order.getId();
		works.setAssessmentsId(orderId);
		result = this.workDao.insertSelective(works);
		
		int workId = works.getId();
		for(Assessments ass : assList){
			ass.setWorkId(workId);
			
			result = this.assDao.insertSelective(ass);
		}
		
		workAtt.setForeignKey(workId);
		result = workAttDao.insertSelective(workAtt);
		
		return result;
	}
	
	@Override
	public int updateAndUpdateWorkAssAtt(Order order, WorksAttchment workAtt) {
		int result = 0;
		
		result = this.orderDao.updateByPrimaryKeySelective(order);
		
		int orderId = order.getId();
		String orderNumber = order.getCodeNumber();
		
		Assessments ass = new Assessments();
		ass.setIsPay(1);
		ass.setPayTime(TimeUtil.getTimeStamp());
		ass.setStatus(0);
		ass.setOrderNumber(orderNumber);
		result = this.assDao.updateByOrderNumber(ass);
		
		Works work = this.workDao.selectByOrderNumber(orderNumber);
		int workId = work.getId();
		workAtt.setForeignKey(workId);
		
		result = this.workAttDao.updateByWorkId(workAtt);
		
		return result;
	}

	@Override
	public int updateAndUpdateWorkAssAtt(Order order, Works works,
			List<Assessments> assList, WorksAttchment workAtt) {
		return 0;
	}

	@Override
	public Order selectByOrderNumber(String orderNumber) {
		return this.orderDao.selectByOrderNumber(orderNumber);
	}

	@Override
	public Order selectByPrimaryKey(Integer id) {
		return this.selectByPrimaryKey(id);
	}

	@Override
	public List<OrderListMyBean> getMyListOrderByUid(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.orderDao.selectMyListOrderByUid(map);
	}

	@Override
	public OrderWorkBean getWorkInfoByListMy(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.orderDao.selectWorkInfoByListMy(map);
	}

	@Override
	public AssessmentListReBean getAssListByShow(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.orderDao.selectAssListByShow(map);
	}

	@Override
	public String getTecPicById(Integer id) {
		// TODO Auto-generated method stub
		return this.orderDao.selectTecPicById(id);
	}

}
