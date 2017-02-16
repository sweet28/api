package com.arttraining.api.dao;

import com.arttraining.api.pojo.OrderCourse;

public interface OrderCourseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderCourse record);

    int insertSelective(OrderCourse record);

    OrderCourse selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderCourse record);

    int updateByPrimaryKey(OrderCourse record);
    
    //coffee add 0215 课时免费时进入直播 自动添加课时订单和详情记录
    int insertOrderCourseSelective(OrderCourse record);
}