package com.carpi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.FensMiner;
import com.carpi.api.service.FensMinerService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("fenuser/miner")
public class FensMinerController {

	@Autowired
	private FensMinerService fensMinerService;
	
	//根据粉丝id查询矿机
	@RequestMapping(value = "/minerList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FensMiner> minerList(Integer page, Integer row, Integer fensUserId){
		return fensMinerService.selectMinner(page, row, fensUserId);
	}
	
	//根据粉丝id查询A矿机
	@RequestMapping(value = "/minerAList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FensMiner> minerAList(Integer page, Integer row, Integer fensUserId){
		return fensMinerService.selectAMinner(page, row, fensUserId);
	}
	
	//根据粉丝id查询A矿机矿池
	@RequestMapping(value = "/minerAListKC", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FensMiner> minerAListKC(Integer page, Integer row, Integer fensUserId){
		return fensMinerService.selectAMinnerKC(page, row, fensUserId);
	}
	
	//根据粉丝id查询B矿机
	@RequestMapping(value = "/minerBList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FensMiner> minerBList(Integer page, Integer row, Integer fensUserId){
		return fensMinerService.selectBMinner(page, row, fensUserId);
	}
	
	//根据粉丝id查询B矿机矿池
	@RequestMapping(value = "/minerBListKC", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FensMiner> minerBListKC(Integer page, Integer row, Integer fensUserId){
		return fensMinerService.selectBMinnerKC(page, row, fensUserId);
	}
	
	//根据粉丝id查询B矿机
	@RequestMapping(value = "/minerjd", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult minerJD(FensMiner miner){
		return fensMinerService.thawABMiner(miner);
	}
}
