package com.arttraining.api.dao;

import com.arttraining.api.pojo.BBSAttachment;

public interface BBSAttachmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BBSAttachment record);

    int insertSelective(BBSAttachment record);

    BBSAttachment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BBSAttachment record);

    int updateByPrimaryKey(BBSAttachment record);
    
    //转发帖子时更新帖子附件表
    int insertBBSAttrByForward(BBSAttachment record);
}