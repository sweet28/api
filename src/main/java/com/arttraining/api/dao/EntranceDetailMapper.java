package com.arttraining.api.dao;

import com.arttraining.api.pojo.EntranceDetail;

public interface EntranceDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EntranceDetail record);

    int insertSelective(EntranceDetail record);

    EntranceDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EntranceDetail record);

    int updateByPrimaryKey(EntranceDetail record);
}