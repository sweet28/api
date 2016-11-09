package com.arttraining.api.service;

import com.arttraining.api.pojo.WorksLike;


public interface IWorksLikeService {
	//判断用户是否重复对测评作品进行重复点赞
	WorksLike selectWorksLikeByUidAndFid(Integer fid,Integer uid);
	//添加对测评作品信息进行点赞 后更新点赞数量
	void insertAndUpdateWork(WorksLike record,Integer id);
	
	int insertWorksLikeSelective(WorksLike record);
	//取消点赞时执行的方法
	int updateWorksLikeSelective(WorksLike record);
}
