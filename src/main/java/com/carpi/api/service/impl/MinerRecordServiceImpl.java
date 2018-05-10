package com.carpi.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carpi.api.dao.AminerRecordMapper;
import com.carpi.api.dao.BminerRecordMapper;
import com.carpi.api.pojo.AminerRecord;
import com.carpi.api.pojo.BminerRecord;
import com.carpi.api.service.MinerRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class MinerRecordServiceImpl implements MinerRecordService {

	@Autowired
	private AminerRecordMapper aminerRecordMapper;
	@Autowired
	private BminerRecordMapper bminerRecordMapper;

	//a矿机的所有交易记录
	@Override
	public PageInfo<AminerRecord> selectListA(Integer page, Integer row) {
		PageHelper.startPage(page, row);
		List<AminerRecord> list = aminerRecordMapper.selectARecord();
		PageInfo<AminerRecord> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	//矿机的所有交易记录
	@Override
	public PageInfo<BminerRecord> selectListB(Integer page, Integer row) {
		PageHelper.startPage(page, row);
		List<BminerRecord> list = bminerRecordMapper.selectBRecord();
		PageInfo<BminerRecord> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

}
