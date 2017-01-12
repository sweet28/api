package com.arttraining.api.service;

import com.arttraining.api.pojo.Live;
import com.arttraining.api.pojo.LiveLike;

public interface ILiveLikeService {
	//新增一条直播点赞信息的同时修改主播的点赞数
	public void insertLiveLikeAndUpdateLive(LiveLike like,Live live);
}
