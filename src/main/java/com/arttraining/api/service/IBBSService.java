package com.arttraining.api.service;

import com.arttraining.api.pojo.BBS;

public interface IBBSService {
	//依据ID获取相应的bbs记录
	BBS getBBSById(Integer id);
}
