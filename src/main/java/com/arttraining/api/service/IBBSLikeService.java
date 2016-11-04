package com.arttraining.api.service;

import com.arttraining.api.pojo.BBSLike;

public interface IBBSLikeService {
	//添加点赞记录信息
	int insertBBSLikeSelective(BBSLike record);
	//判断用户是否重复对帖子进行点赞
	BBSLike selectBBSLikeByUidAndFid(Integer fid,Integer uid);
	//删除帖子点赞信息(记录删除标记为1)
	int updateBBSLikeSelective(BBSLike record);
}
