package com.arttraining.api.dao;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.pojo.LiveAuth;
import com.arttraining.api.pojo.LiveAuthWithBLOBs;

public interface LiveAuthMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LiveAuthWithBLOBs record);

    int insertSelective(LiveAuthWithBLOBs record);

    LiveAuthWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LiveAuthWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(LiveAuthWithBLOBs record);

    int updateByPrimaryKey(LiveAuth record);
    
    //coffee add 0112 依据用户ID和类型来判断是否进行了直播资质认证
    LiveAuthWithBLOBs selectLiveAuthByUid(@Param("uid") Integer uid,
    		@Param("utype") String utype);
}