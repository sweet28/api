package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.AssessmentListReBean;
import com.arttraining.api.bean.OrderListMyBean;
import com.arttraining.api.bean.OrderWorkBean;
import com.arttraining.api.pojo.Assessments;
import com.arttraining.api.pojo.Coupon;
import com.arttraining.api.pojo.Order;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.pojo.Works;
import com.arttraining.api.pojo.WorksAttchment;

public interface IOrdersService {
	int insert(Order order);
	
	int insertAndUpdateWorkAssAtt(Order order, Works works, List<Assessments> assList, WorksAttchment workAtt);
	
	int update(Order order);
	
	int updateAndUpdateWorkAssAtt(Order order, Works works, List<Assessments> assList, WorksAttchment workAtt);
	
	int updateAndUpdateWorkAssAtt(Coupon coupon,Order order, WorksAttchment workAtt, Assessments ass,UserStu user);
	
	Order selectByOrderNumber(String orderNumber);
	
	Order selectByPrimaryKey(Integer id);
	
	//获取用户订单信息列表--orders/list_my接口调用
    List<OrderListMyBean> getMyListOrderByUid(Map<String, Object> map);
    //依据订单类型 订单ID 订单号去查询相应的商品信息(作品或者课程)--orders/list_my接口调用
    OrderWorkBean getWorkInfoByListMy(Map<String, Object> map);
    //根据ID获取订单详情信息-- orders/show接口调用 
    AssessmentListReBean getAssListByShow(Map<String, Object> map);
    //根据ID名师头像-- orders/show接口调用 
    String getTecPicById(Integer id);
    
    //恢复优惠券状态
    int updateOrderAndCoupon(Order order,Map<String, Object> map,Integer flag);
}
