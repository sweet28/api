package com.carpi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.FensEarn;
import com.carpi.api.service.FensEarnService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/earn")
public class FensEarnController {

	@Autowired
	private FensEarnService fensEarnService;
	//粉丝收益列表
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FensEarn> selectList(Integer page, Integer num, Integer fensUserId){
		return fensEarnService.selectAll(page, num, fensUserId);
	}
	
	//新增粉丝收益
	@RequestMapping(value = "/addEarn", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult addFensEarn(FensEarn fensEarn){
		return fensEarnService.addFensEarn(fensEarn);
	}
	
	//更新粉丝收益
	@RequestMapping(value = "/updateERarn", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult updateFensRarn(FensEarn fensEarn){
		return fensEarnService.updateFensRarn(fensEarn);
	}
}
