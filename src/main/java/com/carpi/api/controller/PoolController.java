package com.carpi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.APool;
import com.carpi.api.pojo.BPool;
import com.carpi.api.service.PoolService;
import com.github.pagehelper.PageInfo;

//@Controller
@RequestMapping("/pool")
public class PoolController {

	@Autowired
	private PoolService poolService;

	// a矿池列表
	@RequestMapping(value = "/poolAlist", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<APool> poolAlist(Integer page, Integer num, Integer fensUserId) {
		return poolService.selectApool(page, num, fensUserId);
	}

	// b矿池列表
	@RequestMapping(value = "/poolBlist", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<BPool> poolBlist(Integer page, Integer num, Integer fensUserId) {
		return poolService.selectBpool(page, num, fensUserId);
	}

	// 解冻(A)
	@RequestMapping(value = "/thawAMiner", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult thawAMiner(APool aPool) {
		return poolService.thawAMiner(aPool);
	}

	// 解冻(B)
	@RequestMapping(value = "/thawAMiner", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult thawBMiner(BPool bPool) {
		return poolService.thawBMiner(bPool);
	}  
}
