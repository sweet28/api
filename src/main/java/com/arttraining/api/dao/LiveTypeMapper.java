package com.arttraining.api.dao;

import java.util.List;

import com.arttraining.api.beanv2.LiveTypeList;
import com.arttraining.api.pojo.LiveType;

public interface LiveTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LiveType record);

    int insertSelective(LiveType record);

    LiveType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LiveType record);

    int updateByPrimaryKey(LiveType record);
    
    //coffee add 0117 直播类型列表 prepare/live/type/list接口调用
    List<LiveTypeList> selectLivesTypeList();
}