package com.carpi.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.dao.FensWalletMapper;
import com.carpi.api.dao.FoneyRecordMapper;
import com.carpi.api.pojo.FensWallet;
import com.carpi.api.pojo.FoneyRecord;
import com.carpi.api.service.FensWalletService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class FensWalletServiceImpl implements FensWalletService {

	@Autowired
	private FensWalletMapper fensWalletMapper;
	
	@Autowired
	private FoneyRecordMapper foneyRecordMapper;
	
	
	//粉丝钱包列表
	@Override
	public PageInfo<FensWallet> selectAll(Integer page, Integer num, Integer fensUserId) {
		PageHelper.startPage(page, num);
		List<FensWallet> list = fensWalletMapper.selectAll(fensUserId);
		PageInfo<FensWallet> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	//新增钱包
	@Override
	public JsonResult addWallet(FensWallet fensWallet) {
		int result = fensWalletMapper.insertSelective(fensWallet);
		if (result == 1) {
			return JsonResult.ok();
		}
		return JsonResult.build(500, "新增失败");
	}

	//修改钱包
	@Override
	public JsonResult updateWallet(FensWallet fensWallet) {
		int result = fensWalletMapper.updateByPrimaryKeySelective(fensWallet);
		if (result == 1) {
			return JsonResult.ok();
		}
		return JsonResult.build(500, "新增失败");
	}

	//钱包转账记录列表
	@Override
	public PageInfo<FoneyRecord> selectWalletRecord(Integer page, Integer num, Integer fensUserId) {
		PageHelper.startPage(page, num);
	    List<FoneyRecord> list = foneyRecordMapper.selectWalletRecord(fensUserId);
	    PageInfo<FoneyRecord> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	//添加钱包转账记录
	@Override
	public JsonResult addWalletRecord(FoneyRecord foneyRecord) {
		int result = foneyRecordMapper.insertSelective(foneyRecord);
		if (result == 1) {
			return JsonResult.ok();
		}
		return JsonResult.build(500, "添加出错");
	}

	//更新钱包转账记录
	@Override
	public JsonResult updateWalletRecord(FoneyRecord foneyRecord) {
		int result = foneyRecordMapper.updateByPrimaryKeySelective(foneyRecord);
		if(result == 1) {
			return JsonResult.ok();
		}
		return JsonResult.build(500, "更新出错");
	}
	
}
