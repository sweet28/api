package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.TecherListBean;
import com.arttraining.api.pojo.UserTech;

public interface IUserTecService {
	//依据名师id查询相应的名师详情
	UserTech getOneUserTecById(Integer id);
	//分页查询名师信息 ---techer/list接口调用
	List<TecherListBean> getTecherListBySelective(Map<String, Object> map);
	//首页默认显示2个名师信息
	List<TecherListBean> getTecherListIndexBySelective(Integer offset, Integer limit);
	//查询名师数量
    int countTecherNumer();
    //根据关键字搜索教师--search/tec接口调用
    List<TecherListBean> getTecherListBySearch(Map<String, Object> map);
    
    //更新艺术家/名师相关数量
    int updateTecNumber(UserTech record);
    
    //以下接口均用于名师端 begin
    //根据名师账号密码登录APP--login/master/login接口调用
    UserTech getMasterInfoByName(String account);
    //找回密码 更新密码时执行的方法 --forgot_pwd/master/create接口调用
    int updateMasterInfoBySelective(UserTech record);
    //设置名师信息时执行的方法--masters/set_info接口调用
    int updateMasterInfoByPrimaryKeySelective(UserTech record);
    //end
}
