package com.carpi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.Aminer;
import com.carpi.api.pojo.AminerRecord;
import com.carpi.api.pojo.Bminer;
import com.carpi.api.pojo.BminerRecord;
import com.carpi.api.pojo.FensTransaction;
import com.carpi.api.service.FensRecordServcie;
import com.carpi.api.service.MinerRecordService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/miner/record")
public class MinerRecordController {

	@Autowired
	private MinerRecordService minerRecordService;

	@Autowired
	private FensRecordServcie fensRecordServcie;

	// 根据粉丝id查询交易记录
	@RequestMapping(value = "/recordlist", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FensTransaction> selectRecord(Integer page, Integer row, FensTransaction fensTransaction) {
		return fensRecordServcie.selectRecord(page, row, fensTransaction);
	}
//	
//	// 根据粉丝待付款交易记录
//	@RequestMapping(value = "/recordDFKlist", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//	@ResponseBody
//	public List<FensTransaction> selectDFKRecord(FensTransaction fensTransaction) {
//		return fensRecordServcie.selectDFKRecord(fensTransaction);
//	}
//	
//	// 根据粉丝待收款交易记录
//	@RequestMapping(value = "/recordDSKlist", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//	@ResponseBody
//	public List<FensTransaction> selectDSKRecord(FensTransaction fensTransaction) {
//		return fensRecordServcie.selectDSKRecord(fensTransaction);
//	}
//	
//	// 根据粉丝完成交易记录
//	@RequestMapping(value = "/recordYWClist", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//	@ResponseBody
//	public List<FensTransaction> selectYWCRecord(FensTransaction fensTransaction) {
//		return fensRecordServcie.selectYWCRecord(fensTransaction);
//	}
//	
//	// 根据粉丝挂单交易记录
//	@RequestMapping(value = "/recordGDlist", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//	@ResponseBody
//	public List<FensTransaction> selectGDRecord(FensTransaction fensTransaction) {
//		return fensRecordServcie.selectGDRecord(fensTransaction);
//	}
	
	// 根据粉丝id查询成交交易记录
	@RequestMapping(value = "/cjlist", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FensTransaction> selectCJRecord(Integer page, Integer row, FensTransaction fensTransaction) {
		return fensRecordServcie.selectCJRecord(page, row, fensTransaction);
	}
	
	// 根据粉丝id查询交易记录
	@RequestMapping(value = "/detail", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public FensTransaction selectRecord(Integer id) {
		return fensRecordServcie.selectRecordByID(id);
	}

//	// 根据粉丝交易记录增加
//	@RequestMapping(value = "/addRecord", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//	@ResponseBody
//	public JsonResult addRecord(FensTransaction fensTransaction) {
//		return fensRecordServcie.addRecord(fensTransaction);
//	}
//
//	// 根据粉丝交易记录修改
	@RequestMapping(value = "/updateRecord", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult updateRecord(FensTransaction fensTransaction) {
		return fensRecordServcie.updateRecord(fensTransaction);
	}
//	
//	// 根据粉丝交易记录修改
//	@RequestMapping(value = "/updateRecordCJ", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//	@ResponseBody
//	public JsonResult updateRecordCJ(FensTransaction fensTransaction) {
//		return fensRecordServcie.updateRecordCJ(fensTransaction);
//	}

//	// a矿机的所有交易记录
//	@RequestMapping(value = "/aminerrecord", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//	@ResponseBody
//	public PageInfo<AminerRecord> selectAminerRecord(Integer page, Integer row, Integer fensUserId) {
//		return fensRecordServcie.selectAminerRecord(page, row, fensUserId);
//	}
//
//	// b矿机的所有交易记录
//	@RequestMapping(value = "/bminerrecord", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//	@ResponseBody
//	public PageInfo<BminerRecord> selectBminerRecord(Integer page, Integer row, Integer fensUserId) {
//		return fensRecordServcie.selectBminerRecord(page, row, fensUserId);
//	}
//
//	// a矿机列表
//	@RequestMapping(value = "/aminer", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//	@ResponseBody
//	public PageInfo<Aminer> selectAMiner(Integer page, Integer row) {
//
//		return fensRecordServcie.selectAMiner(page, row);
//	}
//
//	// 矿机列表
//	@RequestMapping(value = "/bminer", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//	@ResponseBody
//	public PageInfo<Bminer> selecBtMiner(Integer page, Integer row) {
//		return fensRecordServcie.selectBMiner(page, row);
//	}
//
//	// 购买a矿机
//	@RequestMapping(value = "/buyAMiner", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//	@ResponseBody
//	public JsonResult buyAMiner(Aminer aminer) {
//		return minerRecordService.buyAMiner(aminer);
//	}
//
//	// 购买b矿机
//	@RequestMapping(value = "/buyBMiner", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//	@ResponseBody
//	public JsonResult buyBMiner(Bminer bminer) {
//		return minerRecordService.buyBMiner(bminer);
//	}
}
