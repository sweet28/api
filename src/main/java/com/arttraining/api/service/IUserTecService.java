package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.TecherListBean;
import com.arttraining.api.pojo.UserTech;

public interface IUserTecService {
	//依据名师id查询相应的名师详情
	UserTech selectOneUserTecById(Integer id);
	//分页查询名师信息 ---techer/list接口调用
	List<TecherListBean> getTecherListBySelective(Map<String, Object> map);
	//首页默认显示2个名师信息
	List<TecherListBean> getTecherListIndexBySelective(Integer offset, Integer limit);
	//查询名师数量
    int countTecherNumer();
}
