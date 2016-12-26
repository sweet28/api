package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import com.arttraining.api.pojo.VoteCheer;

public interface VoteCheerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VoteCheer record);

    int insertSelective(VoteCheer record);

    VoteCheer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VoteCheer record);

    int updateByPrimaryKeyWithBLOBs(VoteCheer record);

    int updateByPrimaryKey(VoteCheer record);
    
    //用于获取专题活动视频宣传列表信息 cheer/act/list接口调用
    List<VoteCheer> selectCheerListByActId(Map<String, Object> map);
}