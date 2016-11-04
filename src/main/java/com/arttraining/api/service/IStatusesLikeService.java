package com.arttraining.api.service;

import com.arttraining.api.pojo.StatusesLike;

public interface IStatusesLikeService {
	//判断用户是否重复对小组动态进行重复点赞
	StatusesLike selectStatusesLikeByUidAndFid(Integer fid,Integer uid);
	//添加对小组动态进行点赞
	int insertStatusesLikeSelective(StatusesLike record);
	//取消点赞时执行的方法
	int updateStatusesLikeSelective(StatusesLike record);
}
