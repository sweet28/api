package com.carpi.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arttraining.commons.util.JsonResult;
import com.arttraining.commons.util.TimeUtil;
import com.carpi.api.dao.AminerMapper;
import com.carpi.api.dao.AminerRecordMapper;
import com.carpi.api.dao.BminerMapper;
import com.carpi.api.dao.BminerRecordMapper;
import com.carpi.api.dao.FensTransactionMapper;
import com.carpi.api.pojo.Aminer;
import com.carpi.api.pojo.AminerRecord;
import com.carpi.api.pojo.Bminer;
import com.carpi.api.pojo.BminerRecord;
import com.carpi.api.pojo.FensTransaction;
import com.carpi.api.service.FensRecordServcie;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class FensRecordServcieImpl implements FensRecordServcie {

	@Autowired
	private FensTransactionMapper fensTransactionMapper;
	
	@Autowired
	private AminerMapper aminerMapper;
	
	@Autowired
	private BminerMapper bminerMapper;
	
	@Autowired
	private AminerRecordMapper aminerRecordMapper;
	
	@Autowired
	private BminerRecordMapper bminerRecordMapper;
	
	// 粉丝交易记录（可根据粉丝id查个人）
	@Override
	public PageInfo<FensTransaction> selectRecord(Integer page,Integer row,FensTransaction fensTransaction) {
		PageHelper.startPage(page, row);
		List<FensTransaction> list = fensTransactionMapper.selectFensRecord(fensTransaction);
		PageInfo<FensTransaction> pageInfo = new PageInfo<FensTransaction>(list);
		return pageInfo;
	}
	
	// a矿机的列表
	@Override
	public PageInfo<Aminer> selectAMiner(Integer page, Integer row) {
		PageHelper.startPage(page, row);
		List<Aminer> list = aminerMapper.selectAMiner();
		PageInfo<Aminer> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	// b矿机的列表
	@Override
	public PageInfo<Bminer> selectBMiner(Integer page, Integer row) {
		PageHelper.startPage(page, row);
		List<Bminer> list = bminerMapper.selectBMiner();
		PageInfo<Bminer> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	// a矿机的交易列表
	@Override
	public PageInfo<AminerRecord> selectAminerRecord(Integer page, Integer row, Integer fensUserId) {
		PageHelper.startPage(page, row);
		List<AminerRecord> list = aminerRecordMapper.selectARecord(fensUserId);
		PageInfo<AminerRecord> pageInfo = new PageInfo<AminerRecord>(list);
		return pageInfo;
	}
	
	// b矿机的交易列表
	@Override
	public PageInfo<BminerRecord> selectBminerRecord(Integer page, Integer row, Integer fensUserId) {
		PageHelper.startPage(page, row);
		List<BminerRecord> list = bminerRecordMapper.selectBRecord(fensUserId);
		PageInfo<BminerRecord> pageInfo = new PageInfo<BminerRecord>(list);
		return pageInfo;
	}

	//粉丝记录增加
	@Override
	public JsonResult addRecord(FensTransaction fensTransaction) {
		fensTransaction.setCreateDate(TimeUtil.getTimeStamp());
		int result = fensTransactionMapper.insertSelective(fensTransaction);
		if (result == 1) {
			return JsonResult.ok();
		}
		return JsonResult.build(500, "新增失败");
	}

	//粉丝记录修改
	@Override
	public JsonResult updateRecord(FensTransaction fensTransaction) {
		int result = fensTransactionMapper.updateByPrimaryKeySelective(fensTransaction);
		if (result == 1) {
			JsonResult.ok();
		}
		return JsonResult.build(500, "更新失败");
	}

}
