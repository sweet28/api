package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.CouponsListBean;
import com.arttraining.api.pojo.Coupon;

public interface CouponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);
    //获取优惠券列表信息--coupon/list接口调用
    List<CouponsListBean> selectCouponListByUid(Map<String, Object> map);
    /*List<CouponsListBean> selectCouponListByUid(@Param("uid") Integer uid,
    		@Param("utype") String utype,
    		@Param("offset") Integer offset, @Param("limit") Integer limit);*/
}