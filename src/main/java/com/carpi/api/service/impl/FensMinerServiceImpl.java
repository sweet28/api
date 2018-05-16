package com.carpi.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.dao.FensMinerMapper;
import com.carpi.api.pojo.FensMiner;
import com.carpi.api.service.FensMinerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class FensMinerServiceImpl implements FensMinerService {

	@Autowired
	private FensMinerMapper fensMinerMapper;

	// 根据粉丝id查询矿机
	@Override
	public PageInfo<FensMiner> selectMinner(Integer page, Integer row, Integer fensUserId) {
		PageHelper.startPage(page, row);
		List<FensMiner> list = fensMinerMapper.selectMiner(fensUserId);
		PageInfo<FensMiner> pageInfo = new PageInfo<FensMiner>(list);
		return pageInfo;
	}
	
	// 根据粉丝id查询A矿机
	@Override
	public PageInfo<FensMiner> selectAMinner(Integer page, Integer row, Integer fensUserId) {
		PageHelper.startPage(page, row);
		List<FensMiner> list = fensMinerMapper.selectAMiner(fensUserId);
		PageInfo<FensMiner> pageInfo = new PageInfo<FensMiner>(list);
		return pageInfo;
	}
	
	// 根据粉丝id查询B矿机
	@Override
	public PageInfo<FensMiner> selectBMinner(Integer page, Integer row, Integer fensUserId) {
		PageHelper.startPage(page, row);
		List<FensMiner> list = fensMinerMapper.selectBMiner(fensUserId);
		PageInfo<FensMiner> pageInfo = new PageInfo<FensMiner>(list);
		return pageInfo;
	}

	// 新增粉丝矿机
	@Override
	public JsonResult addMiner(FensMiner fensMiner) {
		int result = fensMinerMapper.insertSelective(fensMiner);
		if (result == 1) {
			return JsonResult.ok();
		}
		return JsonResult.build(500, "添加失败");
	}

	// 修改粉丝矿机
	@Override
	public JsonResult updateMiner(FensMiner fensMiner) {
		int result = fensMinerMapper.updateByPrimaryKeySelective(fensMiner);
		if (result == 1) {
			return JsonResult.ok();
		}
		return JsonResult.build(500, "更新失败");
	}
}
