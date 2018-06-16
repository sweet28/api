package com.carpi.api.dao;

import java.util.List;

import com.carpi.api.pojo.QuanBaoLi;

public interface QuanBaoLiMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QuanBaoLi record);

    int insertSelective(QuanBaoLi record);

    QuanBaoLi selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuanBaoLi record);

    int updateByPrimaryKey(QuanBaoLi record);
    
    //券宝理列表
    List<QuanBaoLi> selectList();

}