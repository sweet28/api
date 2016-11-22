package com.arttraining.api.dao;

import java.util.List;

import com.arttraining.api.bean.InstitutionConditionBean;
import com.arttraining.api.pojo.InstitutionsConditions;

public interface InstitutionsConditionsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InstitutionsConditions record);

    int insertSelective(InstitutionsConditions record);

    InstitutionsConditions selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InstitutionsConditions record);

    int updateByPrimaryKey(InstitutionsConditions record);
    //获取院校地域、类型条件列表--institutions/conditions接口调用
    List<InstitutionConditionBean> selectConditionList();
}