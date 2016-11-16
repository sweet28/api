package com.arttraining.api.dao;

import com.arttraining.api.pojo.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    Order selectByOrderNumber(String order_number);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}