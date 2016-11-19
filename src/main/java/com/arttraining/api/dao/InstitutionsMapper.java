package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.InstitutionsListBean;
import com.arttraining.api.bean.InstitutionsShowBean;
import com.arttraining.api.pojo.Institutions;

public interface InstitutionsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Institutions record);

    int insertSelective(Institutions record);

    Institutions selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Institutions record);

    int updateByPrimaryKey(Institutions record);
    //获取院校信息列表--institutions/list接口调用
    List<InstitutionsListBean> selectInstitutionsList(Map<String,Object> map);
    //根据院校ID获取院校详情信息--institutions/show接口调用
    InstitutionsShowBean selectInstitutionsShow(Integer id);
}