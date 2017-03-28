package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.beanv2.CloudMoneyDetailBean;
import com.arttraining.api.beanv2.CloudTranformMoneyBean;
import com.arttraining.api.pojo.Wallet;
import com.arttraining.api.pojo.WalletOrder;
import com.arttraining.api.pojo.WalletValueTransform;

public interface IWalletService {
    //coffee add 0302 查询云币和售价转换的列表信息
    List<CloudTranformMoneyBean> getWalletMoneyList();
    //coffee add 0302 依据订单ID和订单编号查询相应的信息
    WalletOrder getOneWalletInfoById(Integer order_id,String order_number);
    //coffee add 0302 充值云币接口
    int insertCloudMoneyByRecharge(WalletOrder order);
    //先修改云币订单详情 再充值云币信息
    int updateCloudMoneyByRecharge(WalletOrder order,Wallet wallet);
    
    //coffee add 0302 登录时记录云币信息
    int recordUserCloudMoneyByLogin(Integer user_id,String user_type);
    //coffee add 0302 注册时记录云币信息
    int recordUserCloudMoneyByRegister(Integer user_id,String user_type);
    //coffee add 0302 依据订单编号查询相应的订单信息
    WalletOrder getOrderInfoByNumber(String order_number);
    
    //coffee add 0302 依据用户ID和类型查询云币信息 
    Wallet getCloudMoneyByUid(Integer uid,String utype);
    
    //coffee add 0301 依据用户ID和类型查询云币消费详情列表信息 
    List<CloudMoneyDetailBean> getCloudListByUid(Map<String, Object> map);
    //coffee add 0302 直播时赠送付费礼物
	int updateCloudAndDetailInfoByUid(Integer uid,String utype,Double total_cloud,Double gift_cloud);
	//coffee add 0303 依据价值转换ID来查询相应的价值转换信息
	WalletValueTransform getWalletValueInfoById(Integer id);
	//coffee add 0304 购买时消费的云币
	int updateCloudAndDetailInfoByConsume(Integer uid,String utype,Double total_cloud,Double consume_cloud);
    
	//coffee add 0320　采用云币方式购买直播课时、报考等
	int updateCloudAndDetailInfoByConsumeRecord(Integer uid,String utype,Double total_cloud,Double consume_cloud,
			String consume_way);
}
