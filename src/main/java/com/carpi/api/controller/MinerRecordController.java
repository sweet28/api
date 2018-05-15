package com.carpi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.Aminer;
import com.carpi.api.pojo.AminerRecord;
import com.carpi.api.pojo.BminerRecord;
import com.carpi.api.pojo.FensTransaction;
import com.carpi.api.pojo.bMiner;
import com.carpi.api.service.FensRecordServcie;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/miner/record")
public class MinerRecordController {

	// @Autowired
	// private MinerRecordService minerRecordService;

	@Autowired
	private FensRecordServcie fensRecordServcie;
	
	//根据粉丝id查询交易记录
	@RequestMapping(value = "/recordlist", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FensTransaction> selectRecord(Integer page, Integer row, FensTransaction fensTransaction) {
		return fensRecordServcie.selectRecord(page, row, fensTransaction);
	}
	
	//根据粉丝交易记录增加
	@RequestMapping(value = "/addRecord", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult addRecord(FensTransaction fensTransaction) {
		return fensRecordServcie.addRecord(fensTransaction);
	}
	
	//根据粉丝交易记录修改
	@RequestMapping(value = "/updateRecord", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult updateRecord(FensTransaction fensTransaction) {
		return fensRecordServcie.updateRecord(fensTransaction);
	}

	// a矿机的所有交易记录
	@RequestMapping(value = "/aminerrecord", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<AminerRecord> selectAminerRecord(Integer page, Integer row, Integer fensUserId) {
		return fensRecordServcie.selectAminerRecord(page, row, fensUserId);
	}

	// b矿机的所有交易记录
	@RequestMapping(value = "/bminerrecord", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<BminerRecord> selectBminerRecord(Integer page, Integer row, Integer fensUserId) {
		return fensRecordServcie.selectBminerRecord(page, row, fensUserId);
	}

	// a矿机列表
	@RequestMapping(value = "/aminer", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<Aminer> selectAMiner(Integer page, Integer row) {

		return fensRecordServcie.selectAMiner(page, row);
	}

	// 矿机列表
	@RequestMapping(value = "/bminer", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<bMiner> selecBtMiner(Integer page, Integer row) {
		return fensRecordServcie.selectBMiner(page, row);
	}
}
