package com.carpi.api.service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.Aminer;
import com.carpi.api.pojo.AminerRecord;
import com.carpi.api.pojo.BminerRecord;
import com.carpi.api.pojo.FensTransaction;
import com.carpi.api.pojo.bMiner;
import com.github.pagehelper.PageInfo;

public interface FensRecordServcie {

	// 粉丝交易记录（可查个人）
	public PageInfo<FensTransaction> selectRecord(Integer page, Integer row, FensTransaction fensTransaction);
	
	//粉丝记录增加
	public JsonResult addRecord(FensTransaction fensTransaction);
	
	//粉丝记录修改
	public JsonResult updateRecord(FensTransaction fensTransaction);

	// a矿机的列表
	public PageInfo<Aminer> selectAMiner(Integer page, Integer row);

	// b矿机的列表
	public PageInfo<bMiner> selectBMiner(Integer page, Integer row);

	// a矿机的交易列表
	public PageInfo<AminerRecord> selectAminerRecord(Integer page, Integer row, Integer fensUserId);

	// b矿机的交易列表
	public PageInfo<BminerRecord> selectBminerRecord(Integer page, Integer row, Integer fensUserId);
}
