package com.arttraining.api.dao;

import com.arttraining.api.pojo.StatusesAttachment;

public interface StatusesAttachmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StatusesAttachment record);

    int insertSelective(StatusesAttachment record);

    StatusesAttachment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StatusesAttachment record);

    int updateByPrimaryKey(StatusesAttachment record);
}