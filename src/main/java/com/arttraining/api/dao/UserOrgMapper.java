package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

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
    
    //名师id查询相应名师信息-->对应查询下面的机构信息--techer/show
    TecherShowOrgBean selectOneOrgByTecShow(Integer id);
    //获取机构列表信息--org/list接口调用
    List<OrgListBean> selectOrgListPrimaryKey(Map<String, Object> map);
    //依据机构id查询机构详情--org/show接口调用
    OrgShowBean selectOneOrgByOrgShow(Integer id);
    
    //根据关键字搜索机构 --search/org接口调用
    List<OrgListBean> selectOrgListBySearch(Map<String, Object> map);
}