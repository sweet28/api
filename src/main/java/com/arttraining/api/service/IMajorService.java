package com.arttraining.api.service;

import java.util.List;

import com.arttraining.api.bean.MajorListBean;

public interface IMajorService {
	//获取一级专业列表--major/list/level_one接口调用
    List<MajorListBean> getOneLevelMajorByList();
}
