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
import com.arttraining.api.dao.UserStuMapper;
import com.arttraining.api.dao.WorksAttchmentMapper;
import com.arttraining.api.dao.WorksMapper;
import com.arttraining.api.pojo.Assessments;
import com.arttraining.api.pojo.Order;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.pojo.Works;
import com.arttraining.api.pojo.WorksAttchment;
import com.arttraining.api.service.IOrdersService;

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
	@Resource
	private UserStuMapper userStuDao;

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
		//1.先新增订单信息
		result = this.orderDao.insertSelective(order);
		//2.获取刚插入的订单ID 然后插入作品信息
		int orderId = order.getId();
		works.setAssessmentsId(orderId);
		result = this.workDao.insertSelective(works);
		//3.获取刚插入的作品ID 然后插入测评列表信息
		int workId = works.getId();
		for(Assessments ass : assList){
			ass.setWorkId(workId);
			ass.setOrderId(orderId);
			result = this.assDao.insertSelective(ass);
		}
		//4.插入作品附件信息
		workAtt.setForeignKey(workId);
		result = workAttDao.insertSelective(workAtt);
		return result;
	}
	
	@Override
	public int updateAndUpdateWorkAssAtt(Order order, WorksAttchment workAtt, Assessments ass,UserStu user) {
		int result = 0;
		//1.更新订单信息 依据订单自增ID
		result = this.orderDao.updateByPrimaryKeySelective(order);
		//2.然后依据订单自增ID和订单号 修改测评信息
		int orderId = order.getId();
		String orderNumber = order.getCodeNumber();
		//coffee add
		ass.setOrderId(orderId);
		ass.setOrderNumber(orderNumber);
		//end
		result = this.assDao.updateByOrderNumber(ass);
		//3.然后依据订单号去查询作品信息 
		Works work = this.workDao.selectByOrderNumber(orderNumber);
		int workId=0;
		if(work!=null) {
			workId=work.getId();
		}
		if(workAtt!=null) {
			if(workAtt.getThumbnail() != null && !("").equals(workAtt.getThumbnail().trim())){
				work.setAttachment(workAtt.getThumbnail());
				this.workDao.updateByPrimaryKeySelective(work);
			}
			//4.修改作品附件的信息
			workAtt.setForeignKey(workId);
			result = this.workAttDao.updateByWorkId(workAtt);
		}
		//更新爱好者用户作品数
		if(user!=null) {
			this.userStuDao.updateNumberBySelective(user);
		}
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
