package com.arttraining.api.service;

import java.util.List;

import com.arttraining.api.bean.MajorLevelListBean;
import com.arttraining.api.bean.MajorListBean;

public interface IMajorService {
	//获取一级专业列表--major/list/level_one接口调用
    List<MajorListBean> getOneLevelMajorByList();
    //获取专业列表 --major/list接口调用
    MajorLevelListBean getMajorNodeById(Integer id);
    //依据父节点 获取某专业下的子节点列表--major/list接口调用
    List<MajorLevelListBean> getMajorNodeByFid(Integer father_id);
    //获取所有一级专业列表ID--major/list接口调用
    List<Integer> getAllOneLevelMajor();
}
