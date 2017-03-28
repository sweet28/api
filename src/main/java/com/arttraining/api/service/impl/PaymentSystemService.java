package com.arttraining.api.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.beanv2.LiveChapterOrderListBean;
import com.arttraining.api.beanv2.PaymentAssessmentBean;
import com.arttraining.api.beanv2.PaymentSystemOrderBean;
import com.arttraining.api.dao.LiveChapterPlanMapper;
import com.arttraining.api.dao.OrderCourseDetailMapper;
import com.arttraining.api.dao.OrderMapper;
import com.arttraining.api.dao.WalletSystemMapper;
import com.arttraining.api.pojo.LiveChapterPlan;
import com.arttraining.api.pojo.Order;
import com.arttraining.api.pojo.OrderCourseDetail;
import com.arttraining.api.pojo.Wallet;
import com.arttraining.api.pojo.WalletSystem;
import com.arttraining.api.service.IPaymentSystemService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.IdWorker;
import com.arttraining.commons.util.TimeUtil;

@Service("paymentSystemService")
public class PaymentSystemService implements IPaymentSystemService {
	@Resource
	private WalletService walletService;
	@Resource
	private WalletSystemMapper paymentDao;
	@Resource
	private OrderCourseDetailMapper detailDao;
	@Resource
	private LiveChapterPlanMapper chapterDao;
	@Resource
	private OrderMapper orderDao;
	
	@Override
	public int updatePaymentLiveChapterByCloud(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer uid=(Integer)map.get("uid");
		String utype=(String)map.get("utype");
		Double total_cloud=(Double)map.get("total_cloud");
		Double consume_cloud=(Double)map.get("consume_cloud");
		String consume_way=(String)map.get("consume_way");
		//1.首先扣除用户云币信息  新增云币消费记录
		this.walletService.updateCloudAndDetailInfoByConsumeRecord(uid, utype, total_cloud, 
				consume_cloud, consume_way);
		//2.接着生成一条购买课时的订单信息
		insertPaymentSystemChapterInfoByCloud(map,"wallet");
		return 0;
	}
	@Override
	public int insertPaymentSystemChapterInfoByCloud(Map<String, Object> map,String pay_type) {
		// TODO Auto-generated method stub
		Integer uid=(Integer)map.get("uid");
		Integer chapter_id=(Integer)map.get("chapter_id");
		String chapter_name=(String)map.get("chapter_name");
		//价格
		Double consume_cloud=(Double)map.get("consume_cloud");
		String consume_way=(String)map.get("consume_way");
		
		//1.新增一条课时订单详情
		WalletSystem payment=new WalletSystem();
		payment.setUserId(String.valueOf(uid));
		IdWorker idWorker = new IdWorker(0, 0);
		String orderNum = idWorker.nextId() + "";
		// 创建时间
		Date date = new Date();
		String time = TimeUtil.getTimeByDate(date);

		payment.setCodeNumber(orderNum);// 订单号
		payment.setType(11);// 11--课时
		payment.setCouponPay(0.0);// 优惠金额
		payment.setFinalPay(consume_cloud);// 实际金额
		payment.setMoney(consume_cloud);// 总金额
		// 新增订单创建时间 create_time order_code 和订单商品数量
		payment.setCreateTime(date);
		payment.setOrderCode(time);
		payment.setOrderDetailNum(1);
		payment.setStatus(ConfigUtil.STATUS_1);
		payment.setPayTime(date);
		payment.setPayType(pay_type);
		payment.setBystr1(consume_way);
		payment.setBystr2(chapter_name);
		payment.setByint1(chapter_id);
		
		this.paymentDao.insertSelective(payment);
		int order_id=payment.getId();
		if(order_id>0) {
			//2.继续新增课时订单详情
			OrderCourseDetail detail=new OrderCourseDetail();
			detail.setStuId(uid);
			detail.setOrderNumber(orderNum);
			detail.setOrderId(order_id);
			detail.setType(11);// 订单类型 1--课表 11--课时 
			detail.setMoney(consume_cloud);// 总金额
			// 新增订单创建时间 create_time order_code 
			detail.setCreateTime(date);
			detail.setOrderCode(time);
			detail.setStatus(ConfigUtil.STATUS_1);
			detail.setIsPay(1);
			detail.setPayTime(date);
			detail.setChapterId(chapter_id);
			detail.setChapterName(chapter_name);
			this.detailDao.insertSelective(detail);
			
			//3.修改课时表中购买数量
			LiveChapterPlan upd_chapter=new LiveChapterPlan();
			upd_chapter.setId(chapter_id);
			upd_chapter.setBuyNumber(1);
			this.chapterDao.updateByPrimaryKeySelective(upd_chapter);
		}
		return 0;
	}
	@Override
	public PaymentSystemOrderBean updatePaymentLiveChapterByOther(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer uid=(Integer)map.get("uid");
		String utype=(String)map.get("utype");
		Double total_cloud=0.0;
		Double consume_cloud=0.0;
		String consume_way=(String)map.get("consume_way");
		String is_check=(String)map.get("is_check");
		//判断用户是否勾选用云币抵扣  yes--云币抵扣 no--不抵扣
		if(is_check.equals("yes")) {
			//查询用户当前的云币数
			Wallet wallet=this.walletService.getCloudMoneyByUid(uid, utype);
			if(wallet!=null) {
				total_cloud=wallet.getCloudMoney();
				if(total_cloud.doubleValue()>0) {
					consume_cloud=wallet.getCloudMoney();
					//1.首先扣除用户云币信息  新增云币消费记录
					this.walletService.updateCloudAndDetailInfoByConsumeRecord(uid, utype, total_cloud, 
							consume_cloud, consume_way);
				}
			}
		} 
		map.put("total_cloud", total_cloud);
		map.put("consume_cloud", consume_cloud);
		//直接插入一条订单信息
		return this.insertPaymentSystemChapterInfoByOther(map);
	}
	@Override
	public PaymentSystemOrderBean insertPaymentSystemChapterInfoByOther(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer uid=(Integer)map.get("uid");
		Integer chapter_id=(Integer)map.get("chapter_id");
		String chapter_name=(String)map.get("chapter_name");
		//云币抵扣价格
		Double consume_cloud=(Double)map.get("consume_cloud");
		String consume_way=(String)map.get("consume_way");
		//课时价格
		Double chapter_price=(Double)map.get("chapter_price");
		//实际价格
		double final_price=chapter_price.doubleValue()-consume_cloud.doubleValue();
		
		PaymentSystemOrderBean order=new PaymentSystemOrderBean();
		//1.新增一条课时订单详情
		WalletSystem payment=new WalletSystem();
		payment.setUserId(String.valueOf(uid));
		IdWorker idWorker = new IdWorker(0, 0);
		String orderNum = idWorker.nextId() + "";
		// 创建时间
		Date date = new Date();
		String time = TimeUtil.getTimeByDate(date);
		
		payment.setCodeNumber(orderNum);// 订单号
		payment.setType(11);// 11--课时
		payment.setCouponPay(consume_cloud);// 优惠金额
		payment.setFinalPay(final_price);// 实际金额
		payment.setMoney(chapter_price);// 总金额
		// 新增订单创建时间 create_time order_code 和订单商品数量
		payment.setCreateTime(date);
		payment.setOrderCode(time);
		payment.setOrderDetailNum(1);
		payment.setStatus(ConfigUtil.STATUS_0);
		payment.setBystr1(consume_way);
		payment.setBystr2(chapter_name);
		payment.setByint1(chapter_id);
		
		this.paymentDao.insertSelective(payment);
		int order_id=payment.getId();
		if(order_id>0) {
			//2.继续新增课时订单详情
			OrderCourseDetail detail=new OrderCourseDetail();
			detail.setStuId(uid);
			detail.setOrderNumber(orderNum);
			detail.setOrderId(order_id);
			detail.setType(11);// 订单类型 1--课表 11--课时 
			detail.setMoney(chapter_price);// 总金额
			// 新增订单创建时间 create_time order_code 
			detail.setCreateTime(date);
			detail.setOrderCode(time);
			detail.setStatus(ConfigUtil.STATUS_0);
			detail.setChapterId(chapter_id);
			detail.setChapterName(chapter_name);
			this.detailDao.insertSelective(detail);
			//add
			order.setCreate_time(time);
			order.setOrder_id(order_id);
			order.setOrder_number(orderNum);
		}
		return order;
	}
	@Override
	public int updatePaymentSystemChapterInfoByOther(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer order_id=(Integer)map.get("order_id");
		String order_number=(String)map.get("order_number");
		String pay_type=(String)map.get("pay_type");
		Integer chapter_id=(Integer)map.get("chapter_id");
		
		// 创建时间
		Date date = new Date();
		//1.先修改订单表信息
		WalletSystem payment=new WalletSystem();
		payment.setId(order_id);
		payment.setPayTime(date);
		payment.setPayType(pay_type);
		payment.setStatus(ConfigUtil.STATUS_1);
		this.paymentDao.updateByPrimaryKeySelective(payment);
		
		//2.修改课时订单信息
		OrderCourseDetail detail=new OrderCourseDetail();
		detail.setOrderId(order_id);
		detail.setOrderNumber(order_number);
		detail.setPayTime(date);
		detail.setStatus(ConfigUtil.STATUS_1);
		detail.setIsPay(ConfigUtil.STATUS_1);
		this.detailDao.updateCourseDetailByOrderId(detail);
		
		//3.修改课时表中购买数量
		LiveChapterPlan upd_chapter=new LiveChapterPlan();
		upd_chapter.setId(chapter_id);
		upd_chapter.setBuyNumber(1);
		this.chapterDao.updateByPrimaryKeySelective(upd_chapter);
		
		return 0;
	}
	@Override
	public WalletSystem getOneWalletSystemInfo(Integer order_id) {
		// TODO Auto-generated method stub
		return this.paymentDao.selectByPrimaryKey(order_id);
	}
	@Override
	public WalletSystem getWalletSystemInfoByOrderNumber(String order_number) {
		// TODO Auto-generated method stub
		return this.paymentDao.selectWalletSystemByOrderNumber(order_number);
	}
	@Override
	public List<LiveChapterOrderListBean> getLiveChapterOrderListByUid(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.detailDao.selectLiveChapterOrderListByUid(map);
	}
	@Override
	public PaymentAssessmentBean updateAssessmentOrderInfoByOther(Map<String, Object> map) {
		// TODO Auto-generated method stub
		//是否勾选云币
		String is_check=(String)map.get("is_check");
		//订单ID和订单编号
		Integer order_id=(Integer)map.get("order_id");
		String order_number=(String)map.get("order_number");
		//用户ID
		Integer uid=(Integer)map.get("uid");
		String utype=(String)map.get("utype");
		
		PaymentAssessmentBean assPay=new PaymentAssessmentBean();
		
		//1.查询用户当前的云币数
		Wallet wallet=this.walletService.getCloudMoneyByUid(uid, utype);
		if(wallet!=null) {
			Double total_cloud=wallet.getCloudMoney();
			//2.继续获取订单信息
			Order order=this.orderDao.selectByOrderNumber(order_number);
			if(order!=null) {
				//订单金额
				Double price=order.getMoney();
				//勾选云币抵扣
				if(is_check.equals("yes")) {
					Double consume_cloud=0.0;
					Double final_price=0.0;
					//如果价格大于云币总数
					if(price.doubleValue()>total_cloud.doubleValue()) {
						consume_cloud=total_cloud;
						//扣除用户云币信息  新增云币消费记录
//						this.walletService.updateCloudAndDetailInfoByConsumeRecord(uid, utype, total_cloud, 
//								consume_cloud, consume_way);
						final_price=price-total_cloud;
//						System.out.println("total_cloud:"+total_cloud+"-price:"+price
//								+"-final_price:"+final_price);
						order.setCouponPay(consume_cloud);
						order.setFinalPay(final_price);
						order.setRemarks("check");
						this.orderDao.updateByPrimaryKeySelective(order);
					} 
				} 
				String create_time=TimeUtil.getTimeByDate(order.getCreateTime());
				assPay.setCreate_time(create_time);
				assPay.setOrder_number(order_number);
				assPay.setOrder_id(order_id);
			}
		}
		return assPay;
	}

}
