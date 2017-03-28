package com.arttraining.api.service;

import java.util.Map;

import com.arttraining.api.pojo.OrderCourseDetail;

public interface IOrderCourseService {
	//课程购买订单详情 begin
	//coffee add 0122 判断爱好者端是否购买相应的课时 type 1(购买课程) 11(购买课时) 2(一对一预约课程直播)
    OrderCourseDetail getIsExistCourseDetailById(Map<String, Object> map);
    //end
    
    //如果课时免费 也将其生成一条课时订单和详情记录
    int insertCourseAndDetailOrder(Map<String, Object> map);
    
    //coffee add 0314 封装一个方法用于判断是否购买了课时
    boolean getIsBuyChapterById(Integer uid, Integer chapter_id);
    //end
}
