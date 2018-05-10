package com.carpi.api.service;

import com.carpi.api.pojo.FensMiner;
import com.github.pagehelper.PageInfo;

public interface FensMinerService {

	//根据粉丝id查询矿机（列表）
	public PageInfo<FensMiner> selectMinner(Integer page,Integer row,Integer fensUserId);
}
