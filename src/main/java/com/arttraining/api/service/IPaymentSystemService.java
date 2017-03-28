package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.beanv2.LiveChapterOrderListBean;
import com.arttraining.api.beanv2.PaymentAssessmentBean;
import com.arttraining.api.beanv2.PaymentSystemOrderBean;
import com.arttraining.api.pojo.WalletSystem;

public interface IPaymentSystemService {
	//coffee add 0320  直播课时购买
	//1.云币方式购买
	int updatePaymentLiveChapterByCloud(Map<String, Object> map);
	//生成一条购买订单记录
	int insertPaymentSystemChapterInfoByCloud(Map<String, Object> map,String pay_type);
	
	//2.其他支付方式购买(微信/支付宝)
	PaymentSystemOrderBean updatePaymentLiveChapterByOther(Map<String, Object> map);
	//生成一条购买订单记录
	PaymentSystemOrderBean insertPaymentSystemChapterInfoByOther(Map<String, Object> map);
	//支付成功后 修改订单信息
	int updatePaymentSystemChapterInfoByOther(Map<String, Object> map);
	
	//依据订单ID查询相应的订单信息
	WalletSystem getOneWalletSystemInfo(Integer order_id);
	//依据订单编号查询相应的订单信息
	WalletSystem getWalletSystemInfoByOrderNumber(String order_number);
	
	//coffee add 0323 查看我的直播课程列表
	List<LiveChapterOrderListBean> getLiveChapterOrderListByUid(Map<String, Object> map);
	
	 //coffee add 0324 其他支付方式支付开小灶时调用的方法
	PaymentAssessmentBean updateAssessmentOrderInfoByOther(Map<String, Object> map);
	//end
}
