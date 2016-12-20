package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.MasterInfoListBean;
import com.arttraining.api.bean.MasterInfoShowBean;
import com.arttraining.api.pojo.InformationForTec;

public interface InformationForTecMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InformationForTec record);

    int insertSelective(InformationForTec record);

    InformationForTec selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InformationForTec record);

    int updateByPrimaryKey(InformationForTec record);
    //名师端资讯列表接口--info/master/list接口调用
    List<MasterInfoListBean> selectInfoListByMaster(Map<String, Object> map);
    //名师端资讯详情接口--info/master/show接口调用
    MasterInfoShowBean selectInfoShowByMaster(Integer id);
    //爱好者首页返回资讯列表信息
    List<MasterInfoListBean> selectInfoListByHomePage();
}