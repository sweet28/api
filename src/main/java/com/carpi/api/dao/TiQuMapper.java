package com.carpi.api.dao;

import java.util.List;

import com.carpi.api.pojo.TiQu;

public interface TiQuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TiQu record);

    int insertSelective(TiQu record);

    TiQu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TiQu record);

    int updateByPrimaryKey(TiQu record);
    
    //查询已经提取的金额
    Double selectMoney(Integer fensUserId);
    
    //防止多次点击
    Integer selectTiQu(Integer fensUserId);
    
    List<TiQu> selectTiQuListByUid(Integer fensUserId);
}