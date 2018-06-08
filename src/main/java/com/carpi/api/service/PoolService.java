package com.carpi.api.service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.APool;
import com.carpi.api.pojo.BPool;
import com.carpi.api.pojo.FensMiner;
import com.github.pagehelper.PageInfo;

public interface PoolService {

	// a矿池列表
	public PageInfo<APool> selectApool(Integer page, Integer num, Integer fensUserId);

	// b矿池列表
	public PageInfo<BPool> selectBpool(Integer page, Integer num, Integer fensUserId);
	
	//解冻(A)
	public JsonResult thawAMiner(APool aPool);
	
	//解冻(B)
	public JsonResult thawBMiner(BPool bPool);
	
	//矿池锁定的币购买矿机
	public JsonResult suoDingBuy(Integer fensUserId,Integer id);
}
