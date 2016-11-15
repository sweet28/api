package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.HelpListBean;
import com.arttraining.api.bean.HelpShowBean;

public interface IHelpService {
	 //查看帮助列表信息 ---help/list接口调用
    List<HelpListBean> getHelpList(Map<String, Object> map);
    //依据帮助ID查询详情--help/show接口调用 
    HelpShowBean getHelpShow(Integer id);
}
