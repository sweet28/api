package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.pojo.VoteCheer;

public interface ICheerService {
	//用于获取专题活动视频宣传列表信息 cheer/act/list接口调用
    List<VoteCheer> getCheerListByActId(Map<String, Object> map);
}
