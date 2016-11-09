package com.arttraining.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.bean.TecherListBean;
import com.arttraining.api.pojo.UserTech;

public interface UserTechMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserTech record);

    int insertSelective(UserTech record);

    UserTech selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserTech record);

    int updateByPrimaryKey(UserTech record);
    
    //分页查询名师信息
    List<TecherListBean> selectTecherListBySelective(@Param("spec") String spec,
    		@Param("city") String city,
    		@Param("college") String college,
    		@Param("offset") Integer offset,@Param("limit") Integer limit);
    
    //首页默认显示2个名师信息
    List<TecherListBean> selectTecherListIndexBySelective(@Param("offset") Integer offset,
    		@Param("limit") Integer limit);
}