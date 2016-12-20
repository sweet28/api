package com.arttraining.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.AssessmentListReBean;
import com.arttraining.api.bean.OrderListMyBean;
import com.arttraining.api.bean.OrderWorkBean;
import com.arttraining.api.dao.AssessmentsMapper;
import com.arttraining.api.dao.CouponMapper;
import com.arttraining.api.dao.OrderMapper;
import com.arttraining.api.dao.TokenMapper;
import com.arttraining.api.dao.UserStuMapper;
import com.arttraining.api.dao.UserTechMapper;
import com.arttraining.api.dao.WorksAttchmentMapper;
import com.arttraining.api.dao.WorksMapper;
import com.arttraining.api.pojo.Assessments;
import com.arttraining.api.pojo.Coupon;
import com.arttraining.api.pojo.Order;
import com.arttraining.api.pojo.Token;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.pojo.UserTech;
import com.arttraining.api.pojo.Works;
import com.arttraining.api.pojo.WorksAttchment;
import com.arttraining.api.service.IOrdersService;
import com.arttraining.commons.util.JPushClientUtil;

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
	@Resource
	private CouponMapper couponDao;
	@Resource
	private ScoreRecordService scoreRecordService;
	@Resource
	private UserTechMapper userTecDao;
	@Resource
	private TokenMapper tokenDao;

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
		//coffee add 1207
		result=orderId;
		//end
		return result;
	}
	
	@Override
	public int updateAndUpdateWorkAssAtt(Coupon coupon, Order order, WorksAttchment workAtt, Assessments ass,UserStu user) {
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
				//work.setIsPublic(1);
				this.workDao.updateByPrimaryKeySelective(work);
			}
			//4.修改作品附件的信息
			workAtt.setForeignKey(workId);
			result = this.workAttDao.updateByWorkId(workAtt);
		}
		//记录用户积分表 coffee add 1214 
		Map<String, Object> map=new HashMap<String, Object>();
		Integer user_id=0;
		//end
		//更新爱好者用户作品数
		if(user!=null) {
			this.userStuDao.updateNumberBySelective(user);
			user_id=user.getId();
			//coffee add 1215 新增推送信息
			this.pushMsgAndAlertToTec(orderNumber);
			//end
		} 
		//5.修改优惠券使用信息
		if(coupon!=null) {
			this.couponDao.updateByPrimaryKeySelective(coupon);
		}
		if(user_id!=0) {
			//coffee add 1214 
			map.put("order_id", orderId);
			map.put("order_number", orderNumber);
			this.scoreRecordService.insertScoreRecord(map);
			//end	
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

	@Override
	public int updateOrderAndCoupon(Order order, Map<String, Object> map,
			Integer flag) {
		// TODO Auto-generated method stub
		this.orderDao.updateByPrimaryKeySelective(order);
		//1表示修改优惠券信息
		if(flag>0) {
			this.couponDao.updateCouponInfoByOrderId(map);
		}
		//2.设置作品附件失效 is_deleted=1
		Integer order_id=order.getId();
		this.assDao.updateWorkAttrByOrderId(order_id);
		
		//3.关闭交易的话 恢复积分表 coffee add 1214 关闭交易(尚未支付 并不需要恢复积分)
//		Map<String, Object> info_map=new HashMap<String, Object>();
//		info_map.put("order_id", order_id);
//		this.scoreRecordService.insertAndBackScoreRecord(info_map);
		//end 
		
		return 0;
	}

	@Override
	public void pushMsgAndAlertToTec(String order_number) {
		// TODO Auto-generated method stub
		//1.首先依据订单编号获取订单详情
		List<Assessments> assList=this.assDao.selectAssListByOrderNum(order_number);
		if(assList.size()>0) {
			for (Assessments ass : assList) {
				Integer tec_id=ass.getTecId();
				UserTech tec=this.userTecDao.selectByPrimaryKey(tec_id);
				Integer role=tec.getRole();
				String user_type="";
				switch (role) {
				case 0:
					user_type="ms";
					break;
				case 1:
					user_type="zj";
					break;
				case 2:
					user_type="iartschool";
					break;	
				case 3:
					user_type="dr";
					break;
				default:
					break;
				}
				//coffee add 1215 新增推送信息
				String push_type="alert_msg";
				//String alias=""+tec_id;
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("user_id", tec_id);
				map.put("user_type", "tec");
				Token t= this.tokenDao.selectOneTokenInfo(map);
				String alias="";
				if(t!=null) {
					alias=t.getToken();
					System.out.println("1111=="+tec_id);
					String alert=tec.getName()+"老师,您好 "+ass.getStuName()+"同学请您帮忙点评他的作品哟";
					String push_content="";
					String push_content_type="";
					//封装额外的数据
					String type="stu_ass";
					String value=""+ass.getWorkId();
					String extra_value=JPushClientUtil.eclose_push_extra_json_data(type, value);
					JPushClientUtil.enclose_push_data_alias(user_type, push_type, alias, alert, push_content, push_content_type, extra_value);
				}
				//end
			}
		}
	}

	@Override
	public String getWorkAttById(Integer work_id) {
		// TODO Auto-generated method stub
		return this.orderDao.selectWorkAttById(work_id);
	}

}
