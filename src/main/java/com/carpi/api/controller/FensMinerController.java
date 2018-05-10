package com.carpi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.carpi.api.pojo.FensMiner;
import com.carpi.api.service.FensMinerService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("fenuser/miner")
public class FensMinerController {

	@Autowired
	private FensMinerService fensMinerService;
	
	@RequestMapping("/minerList")
	@ResponseBody
	public PageInfo<FensMiner> minerList(Integer page, Integer row, Integer fensUserId){
		return fensMinerService.selectMinner(page, row, fensUserId);
	}
}