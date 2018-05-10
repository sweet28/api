package com.carpi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.carpi.api.pojo.AminerRecord;
import com.carpi.api.pojo.BminerRecord;
import com.carpi.api.service.MinerRecordService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/miner/record")
public class MinerRecordController {

	@Autowired
	private MinerRecordService minerRecordService;

	// a矿机的所有交易记录
	@RequestMapping(value = "/aminerrecord", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<AminerRecord> selectListA(Integer page, Integer row) {
		return minerRecordService.selectListA(page, row);
	}

	// b矿机的所有交易记录
	@RequestMapping(value = "/bminerrecord", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<BminerRecord> selectListB(Integer page, Integer row) {
		return minerRecordService.selectListB(page, row);
	}
}
