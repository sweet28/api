package com.arttraining.api.dao;

import java.util.Map;

import com.arttraining.api.pojo.OrderCourseDetail;

public interface OrderCourseDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderCourseDetail record);

    int insertSelective(OrderCourseDetail record);

    OrderCourseDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderCourseDetail record);

    int updateByPrimaryKey(OrderCourseDetail record);
    
    //coffee add 0122 判断爱好者端是否购买相应的课时 type 1(购买课程) 11(购买课时) 2(一对一预约课程直播)
    OrderCourseDetail selectIsExistCourseDetailById(Map<String, Object> map);
}