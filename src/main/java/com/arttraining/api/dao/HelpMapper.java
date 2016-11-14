package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.HelpListBean;
import com.arttraining.api.bean.HelpShowBean;
import com.arttraining.api.pojo.Help;

public interface HelpMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Help record);

    int insertSelective(Help record);

    Help selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Help record);

    int updateByPrimaryKey(Help record);
    //查看帮助列表信息 ---help/list接口调用
    List<HelpListBean> selectHelpList(Map<String, Object> map);
    //依据帮助ID查询详情--help/show接口调用 
    HelpShowBean selectHelpShow(Integer id);
}