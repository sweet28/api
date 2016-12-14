package com.arttraining.api.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.OrderMapper;
import com.arttraining.api.dao.ScoreRecordMapper;
import com.arttraining.api.dao.UserStuMapper;
import com.arttraining.api.dao.UserTechMapper;
import com.arttraining.api.pojo.Order;
import com.arttraining.api.pojo.ScoreRecord;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.pojo.UserTech;
import com.arttraining.api.service.IScoreRecordService;
import com.arttraining.commons.util.TimeUtil;

@Service("scoreRecordService")
public class ScoreRecordService implements IScoreRecordService {
	@Resource
	private ScoreRecordMapper soreRecordDao;
	@Resource
	private UserStuMapper userStuDao;
	@Resource
	private UserTechMapper userTecDao;
	@Resource
	private OrderMapper orderDao;
	
	@Override
	public int insertScoreRecord(Map<String, Object> map) {
		// TODO Auto-generated method stub
		//1.首先获取订单ID和订单编号
		String order_number=(String)map.get("order_number");
		Order order=this.orderDao.selectByOrderNumber(order_number);
		//如果订单存在
		if(order!=null) {
			Integer coupon_pay=order.getCouponType();
			if(coupon_pay!=3) {
				//1.首先新增积分表
				ScoreRecord score=new ScoreRecord();
				//用户ID和用户类型
				Integer user_id=Integer.valueOf(order.getUserId());
				String user_type="stu";
				Double consume_money=order.getFinalPay();
				Integer type=order.getType();
				String consume_type="";
				switch (type) {
					case 0:
						consume_type="cp";
						break;
					case 1:
						consume_type="kc";
						break;
					case 2:
						consume_type="cz";
					case 3:
						consume_type="dhhyk";
						break;
					case 4:
						consume_type="ds";
						break;
					default:
						break;
				}
				score.setUserId(user_id);
				score.setUserType(user_type);
				score.setConsumeType(consume_type);
				score.setConsumeMoney(consume_money);
				//获取当前时间
				Date date = new Date();
				String time=TimeUtil.getTimeByDate(date);
				score.setCreateTime(date);
				score.setOrderCode(time);
				score.setOrderId(order.getId());
				score.setOrderNumber(order_number);
				
				this.soreRecordDao.insertSelective(score);
				//2.然后修改爱好者/老师用户积分信息
				if(user_type.equals("stu")) {
					int money=consume_money.intValue();
					UserStu stu=new UserStu();
					stu.setId(user_id);
					stu.setScore(money);
					this.userStuDao.updateByPrimaryKeySelective(stu);
				} else if(user_type.equals("tec")) {
					int money=consume_money.intValue();
					UserTech tec = new UserTech();
					tec.setId(user_id);
					tec.setScore(money);
					this.userTecDao.updateByPrimaryKeySelective(tec);
				}
			}
		}
		
		return 0;
	}

	@Override
	public int insertAndBackScoreRecord(Map<String, Object> map) {
		// TODO Auto-generated method stub
		//1.首先依据订单ID和订单编号来查询相应的积分记录信息
		Integer order_id=(Integer)map.get("order_id");
		Order order=this.orderDao.selectByPrimaryKey(order_id);
		if(order!=null) {
			Integer coupon_type=order.getCouponType();
			if(coupon_type!=3) {
				ScoreRecord score=this.soreRecordDao.selectScoreRecordByOrderId(order_id);
				//2.然后将对应的积分记录标识为撤销积分操作 新增撤销时间和备注(关闭交易)
				if(score!=null) {
					Integer score_id=score.getId();
					Date date = new Date();
					ScoreRecord upd_score=new ScoreRecord();
					upd_score.setId(score_id);
					upd_score.setIsDeleted(1);
					upd_score.setDeleteTime(date);
					upd_score.setRemarks("close deal");
					this.soreRecordDao.updateByPrimaryKeySelective(upd_score);
					
					Integer user_id=score.getUserId();
					String user_type=score.getUserType();
					Double consume_money=score.getConsumeMoney();
					int money=-(consume_money.intValue());
					//3.然后修改爱好者/老师用户积分信息
					if(user_type.equals("stu")) {
						UserStu stu=new UserStu();
						stu.setId(user_id);
						stu.setScore(money);
						this.userStuDao.updateByPrimaryKeySelective(stu);
					} else if(user_type.equals("tec")) {
						UserTech tec = new UserTech();
						tec.setId(user_id);
						tec.setScore(money);
						this.userTecDao.updateByPrimaryKeySelective(tec);
					}
				}
			}
		}
		return 0;
	}

	@Override
	public int insertScoreRecordByMember(Map<String, Object> map) {
		// TODO Auto-generated method stub
		//新增积分表
		Integer user_id=(Integer)map.get("user_id");
		String user_type=(String)map.get("user_type");
		Double consume_money=(Double)map.get("consume_money");
		int money=consume_money.intValue();
		//1.首先新增积分表
		ScoreRecord score=new ScoreRecord();
		String consume_type="dhhyk";
		score.setUserId(user_id);
		score.setUserType(user_type);
		score.setConsumeType(consume_type);
		score.setConsumeMoney(consume_money);
		//获取当前时间
		Date date = new Date();
		String time=TimeUtil.getTimeByDate(date);
		score.setCreateTime(date);
		score.setOrderCode(time);
		this.soreRecordDao.insertSelective(score);
		
		if(user_type.equals("stu")) {
			UserStu stu=new UserStu();
			stu.setId(user_id);
			stu.setScore(money);
			this.userStuDao.updateByPrimaryKeySelective(stu);
		} else if(user_type.equals("tec")) {
			UserTech tec = new UserTech();
			tec.setId(user_id);
			tec.setScore(money);
			this.userTecDao.updateByPrimaryKeySelective(tec);
		}
		return 0;
	}
}
