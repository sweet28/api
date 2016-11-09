package com.arttraining.api.service;

import com.arttraining.api.pojo.BBS;
import com.arttraining.api.pojo.BBSForward;

public interface IBBSForwardService {
	//转发一条帖子信息时 执行的方法
	void insertOneBBSForward(BBSForward record,BBS bbs);
}
