package com.arttraining.api.dao;

import java.util.List;

import com.arttraining.api.beanv3.EntranceLineBean;
import com.arttraining.api.pojo.QualificationLine;

public interface QualificationLineMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QualificationLine record);

    int insertSelective(QualificationLine record);

    QualificationLine selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QualificationLine record);

    int updateByPrimaryKey(QualificationLine record);
    //coffee add 0415 查看艺术类术科统考本科资格线列表
    List<EntranceLineBean> selectLineList(String province);
}