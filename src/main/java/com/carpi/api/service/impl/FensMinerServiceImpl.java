package com.carpi.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carpi.api.dao.FensMinerMapper;
import com.carpi.api.pojo.FensMiner;
import com.carpi.api.service.FensMinerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class FensMinerServiceImpl implements FensMinerService {
	
	@Autowired
    private FensMinerMapper fensMinerMapper;
	
	//根据粉丝id查询矿机
	@Override
	public PageInfo<FensMiner> selectMinner(Integer page, Integer row,Integer fensUserId) {
		PageHelper.startPage(page, row);
		List<FensMiner> list = fensMinerMapper.selectMiner(fensUserId);
		PageInfo<FensMiner> pageInfo = new PageInfo<FensMiner>(list);
		return pageInfo;
	}
}
