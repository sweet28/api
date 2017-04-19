package com.arttraining.api.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.beanv2.CloudMoneyDetailBean;
import com.arttraining.api.beanv2.CloudTranformMoneyBean;
import com.arttraining.api.dao.UserStuMapper;
import com.arttraining.api.dao.WalletDetailMapper;
import com.arttraining.api.dao.WalletMapper;
import com.arttraining.api.dao.WalletOrderMapper;
import com.arttraining.api.dao.WalletValueTransformMapper;
import com.arttraining.api.pojo.Wallet;
import com.arttraining.api.pojo.WalletDetail;
import com.arttraining.api.pojo.WalletOrder;
import com.arttraining.api.pojo.WalletValueTransform;
import com.arttraining.api.service.IWalletService;
import com.arttraining.commons.util.TimeUtil;

@Service("walletService")
public class WalletService implements IWalletService {
	@Resource
	private WalletValueTransformMapper valueDao;
	@Resource
	private WalletOrderMapper orderDao;
	@Resource
	private WalletMapper walletDao;
	@Resource
	private WalletDetailMapper detailDao;
	@Resource
	private UserStuMapper stuDao;
	
	@Override
	public List<CloudTranformMoneyBean> getWalletMoneyList() {
		// TODO Auto-generated method stub
		return this.valueDao.selectWalletMoneyList();
	}

	@Override
	public int insertCloudMoneyByRecharge(WalletOrder order) {
		// TODO Auto-generated method stub
		this.orderDao.insertSelective(order);
		int orderId = order.getId();
		return orderId;
	}

	@Override
	public WalletOrder getOneWalletInfoById(Integer order_id,
			String order_number) {
		// TODO Auto-generated method stub
		return this.orderDao.selectOrderInfoById(order_id, order_number);
	}

	@Override
	public int updateCloudMoneyByRecharge(WalletOrder order, Wallet wallet) {
		// TODO Auto-generated method stub
		//1.首先修改订单状态信息
		this.orderDao.updateByPrimaryKeySelective(order);
		//2.然后修改云币钱包信息
		this.walletDao.updateCloudMoneyByUid(wallet);
		//依据用户ID和类型查询相应的钱包信息
		Wallet w = this.getCloudMoneyByUid(wallet.getUserId(), wallet.getUserType());
		double curr_money=0.0;
		if(w!=null) {
			//coffee add 0407
			Integer byint2=order.getByint2();
			String flag="";
			if(byint2.intValue()==wallet.getUserId().intValue()) {
				flag="充值云币";
			} else {
				String name=stuDao.selectUserNameById(byint2);
				flag=name+"好友帮助你充值云币";
			}
			//end
			//3.最后新增云币消费详情信息
			Date date = new Date();
			WalletDetail detail=new WalletDetail();
			detail.setUserId(wallet.getUserId());
			detail.setUserType(wallet.getUserType());
			detail.setCreateTime(date);
			detail.setOrderCode(TimeUtil.getTimeByDate(date));
			detail.setConsumeType("add");
			//coffee add 0315 实际的云币数
			curr_money=wallet.getCloudMoney()+w.getCloudMoney();
			//end
			detail.setCurrCloudMoney(curr_money);
			detail.setCloudMoney(wallet.getCloudMoney());
			//detail.setCloudFlag("充值云币");
			detail.setCloudFlag(flag);
			this.detailDao.insertSelective(detail);
		}
		return 0;
	}

	@Override
	public int recordUserCloudMoneyByLogin(Integer user_id, String user_type) {
		// TODO Auto-generated method stub
		//依据用户ID和类型查询积分信息
	    Wallet wallet=this.walletDao.selectCloudMoneyByUid(user_id, user_type);
	   	if(wallet==null) {
	   		wallet=new Wallet();
			Date date=new Date();
			wallet.setCreateTime(date);
			wallet.setUserId(user_id);
			wallet.setUserType(user_type);
			wallet.setOrderCode(TimeUtil.getTimeByDate(date));
			this.walletDao.insertSelective(wallet);
		}	
		return 0;
	}

	@Override
	public int recordUserCloudMoneyByRegister(Integer user_id, String user_type) {
		// TODO Auto-generated method stub
		Wallet wallet=new Wallet();
		Date date=new Date();
		wallet.setCreateTime(date);
		wallet.setUserId(user_id);
		wallet.setUserType(user_type);
		wallet.setOrderCode(TimeUtil.getTimeByDate(date));
		this.walletDao.insertSelective(wallet);
		return 0;
	}

	@Override
	public WalletOrder getOrderInfoByNumber(String order_number) {
		// TODO Auto-generated method stub
		return this.orderDao.selectOrderInfoByNumber(order_number);
	}

	@Override
	public Wallet getCloudMoneyByUid(Integer uid, String utype) {
		// TODO Auto-generated method stub
		return this.walletDao.selectCloudMoneyByUid(uid, utype);
	}

	@Override
	public List<CloudMoneyDetailBean> getCloudListByUid(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.detailDao.selectCloudListByUid(map);
	}

	@Override
	public int updateCloudAndDetailInfoByUid(Integer uid, String utype,
			Double total_cloud, Double gift_cloud) {
		// TODO Auto-generated method stub
		//1.修改用户云币信息
		Wallet wallet=new Wallet();
		wallet.setUserId(uid);
		wallet.setUserType(utype);
		wallet.setCloudMoney(-gift_cloud);
		this.walletDao.updateCloudMoneyByUid(wallet);

		//2.新增用户消费云币详情信息
		Date date = new Date();
		WalletDetail detail=new WalletDetail();
		detail.setUserId(uid);
		detail.setUserType(utype);
		detail.setCreateTime(date);
		detail.setOrderCode(TimeUtil.getTimeByDate(date));
		detail.setConsumeType("consume");
		Double curr_cloud=total_cloud-gift_cloud;
		detail.setCurrCloudMoney(curr_cloud);
		detail.setCloudMoney(gift_cloud);
		detail.setCloudFlag("直播礼物消费");
		this.detailDao.insertSelective(detail);
		
		return 0;
	}

	@Override
	public WalletValueTransform getWalletValueInfoById(Integer id) {
		// TODO Auto-generated method stub
		return this.valueDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateCloudAndDetailInfoByConsume(Integer uid, String utype,
			Double total_cloud, Double consume_cloud) {
		// TODO Auto-generated method stub
		//1.修改用户云币信息
		Wallet wallet=new Wallet();
		wallet.setUserId(uid);
		wallet.setUserType(utype);
		wallet.setCloudMoney(-consume_cloud);
		this.walletDao.updateCloudMoneyByUid(wallet);

		//2.新增用户消费云币详情信息
		Date date = new Date();
		WalletDetail detail=new WalletDetail();
		detail.setUserId(uid);
		detail.setUserType(utype);
		detail.setCreateTime(date);
		detail.setOrderCode(TimeUtil.getTimeByDate(date));
		detail.setConsumeType("consume");
		Double curr_cloud=total_cloud-consume_cloud;
		detail.setCurrCloudMoney(curr_cloud);
		detail.setCloudMoney(consume_cloud);
		detail.setCloudFlag("购买课时消费");
		this.detailDao.insertSelective(detail);
				
		return 0;
	}

	@Override
	public int updateCloudAndDetailInfoByConsumeRecord(Integer uid,
			String utype, Double total_cloud, Double consume_cloud,
			String consume_way) {
		// TODO Auto-generated method stub
		//1.修改用户云币信息
		Wallet wallet=new Wallet();
		wallet.setUserId(uid);
		wallet.setUserType(utype);
		wallet.setCloudMoney(-consume_cloud);
		this.walletDao.updateCloudMoneyByUid(wallet);

		//2.新增用户消费云币详情信息
		Date date = new Date();
		WalletDetail detail=new WalletDetail();
		detail.setUserId(uid);
		detail.setUserType(utype);
		detail.setCreateTime(date);
		detail.setOrderCode(TimeUtil.getTimeByDate(date));
		detail.setConsumeType("consume");
		Double curr_cloud=total_cloud-consume_cloud;
		detail.setCurrCloudMoney(curr_cloud);
		detail.setCloudMoney(consume_cloud);
		detail.setCloudFlag(consume_way);
		this.detailDao.insertSelective(detail);
						
		return 0;
	}

}
