package com.arttraining.api.dao;

import java.util.List;

import com.arttraining.api.bean.InformationListBean;
import com.arttraining.api.bean.InformationShowBean;
import com.arttraining.api.pojo.Information;

public interface InformationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Information record);

    int insertSelective(Information record);

    Information selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Information record);

    int updateByPrimaryKey(Information record);
    //获取头条信息列表
    List<InformationListBean> selectInformationList();
    //依据头条ID查询某一个头条信息
    InformationShowBean selectOneInformation(Integer id);
}