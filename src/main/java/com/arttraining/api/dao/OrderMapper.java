package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.AssessmentListReBean;
import com.arttraining.api.bean.OrderListMyBean;
import com.arttraining.api.bean.OrderWorkBean;
import com.arttraining.api.pojo.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    Order selectByOrderNumber(String order_number);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    
    //获取用户订单信息列表--orders/list_my接口调用
    List<OrderListMyBean> selectMyListOrderByUid(Map<String, Object> map);
    //依据订单类型 订单ID 订单号去查询相应的商品信息(作品或者课程)--orders/list_my接口调用
    OrderWorkBean selectWorkInfoByListMy(Map<String, Object> map);
    
    //根据ID获取订单详情信息-- orders/show接口调用 
    AssessmentListReBean selectAssListByShow(Map<String, Object> map);
    //根据ID名师头像-- orders/show接口调用 
    String selectTecPicById(Integer id);
}