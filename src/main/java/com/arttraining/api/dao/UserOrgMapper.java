package com.arttraining.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.bean.OrgListBean;
import com.arttraining.api.bean.OrgShowBean;
import com.arttraining.api.bean.TecherShowOrgBean;
import com.arttraining.api.pojo.UserOrg;

public interface UserOrgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserOrg record);

    int insertSelective(UserOrg record);

    UserOrg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserOrg record);

    int updateByPrimaryKey(UserOrg record);
    
    //名师id查询相应名师信息-->对应查询下面的机构信息
    TecherShowOrgBean selectOneOrgByTecShow(Integer id);
    //获取机构列表信息
    List<OrgListBean> selectOrgListPrimaryKey(@Param("city") String city, 
    		@Param("province") String province, 
    		@Param("type") String type, 
    		@Param("offset") Integer offset, 
    		@Param("limit") Integer limit);
    //依据机构id查询机构详情
    OrgShowBean selectOneOrgByOrgShow(Integer id);
}