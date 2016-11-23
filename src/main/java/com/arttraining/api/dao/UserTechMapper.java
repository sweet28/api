package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

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
    
    //分页查询名师信息--techer/list接口调用 
    List<TecherListBean> selectTecherListBySelective(Map<String, Object> map);
    
    //首页默认显示2个名师信息
    List<TecherListBean> selectTecherListIndexBySelective(@Param("offset") Integer offset,
    		@Param("limit") Integer limit);
    
    //查询名师数量
    int selectTecherNumer();
    
    //根据关键字搜索教师--search/tec接口调用
    List<TecherListBean> selectTecherListBySearch(Map<String, Object> map);
    
    //更新艺术家/名师相关数量
    int updateNumberBySelective(UserTech record);
}