package com.arttraining.api.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.LiveChapterPlanMapper;
import com.arttraining.api.dao.OrderCourseDetailMapper;
import com.arttraining.api.dao.OrderCourseMapper;
import com.arttraining.api.pojo.LiveChapterPlan;
import com.arttraining.api.pojo.OrderCourse;
import com.arttraining.api.pojo.OrderCourseDetail;
import com.arttraining.api.service.IOrderCourseService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.IdWorker;
import com.arttraining.commons.util.TimeUtil;

@Service("orderCourseService")
public class OrderCourseService implements IOrderCourseService{
	@Resource
	private OrderCourseDetailMapper detailDao;
	@Resource
	private OrderCourseMapper courseDao;
	@Resource
	private LiveChapterPlanMapper chapterDao;

	@Override
	public OrderCourseDetail getIsExistCourseDetailById(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.detailDao.selectIsExistCourseDetailById(map);
	}

	@Override
	public int insertCourseAndDetailOrder(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer uid=(Integer)map.get("uid");
		Integer chapter_id=(Integer)map.get("chapter_id");
		String chapter_name=(String)map.get("chapter_name");
		//1.新增一条课时订单详情
		OrderCourse course=new OrderCourse();
		course.setUserId(String.valueOf(uid));
		IdWorker idWorker = new IdWorker(0, 0);
		String orderNum = idWorker.nextId() + "";
		// 创建时间
		Date date = new Date();
		String time = TimeUtil.getTimeByDate(date);

		course.setCodeNumber(orderNum);// 订单号
		course.setType(0);// 订单类型 0--测评 1--课程
		course.setCouponPay(0.0);// 优惠金额
		course.setFinalPay(0.0);// 实际金额
		course.setMoney(0.0);// 总金额
		// 新增订单创建时间 create_time order_code 和订单商品数量
		course.setCreateTime(date);
		course.setOrderCode(time);
		course.setOrderDetailNum(1);
		course.setStatus(ConfigUtil.STATUS_1);
		course.setPayTime(date);
		
		//免费的备注free  收费的备注charge
		course.setRemarks("free");
		int order_id=this.courseDao.insertOrderCourseSelective(course);
		if(order_id>0) {
			//2.继续新增课时订单详情
			OrderCourseDetail detail=new OrderCourseDetail();
			detail.setStuId(uid);
			detail.setOrderNumber(orderNum);
			detail.setOrderId(order_id);
			detail.setType(11);// 订单类型 1--课表 11--课时 
			detail.setMoney(0.0);// 总金额
			// 新增订单创建时间 create_time order_code 和订单商品数量
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

}
