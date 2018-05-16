package com.carpi.api.service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.Aminer;
import com.carpi.api.pojo.Bminer;

public interface MinerRecordService {

	// 购买a矿机
	public JsonResult buyAMiner(Aminer aminer);

	// 购买b矿机
	public JsonResult buyBMiner(Bminer bminer);

}
