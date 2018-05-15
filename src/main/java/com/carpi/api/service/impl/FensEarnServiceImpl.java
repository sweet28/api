package com.carpi.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.dao.FensEarnMapper;
import com.carpi.api.pojo.FensEarn;
import com.carpi.api.service.FensEarnService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class FensEarnServiceImpl implements FensEarnService {
	@Autowired
	private FensEarnMapper fensEarnMapper;

	//粉丝收益列表
	@Override
	public PageInfo<FensEarn> selectAll(Integer page, Integer num, Integer fensUserId) {
		PageHelper.startPage(page, num);
		List<FensEarn> list = fensEarnMapper.selectFensEarn(fensUserId);
		PageInfo<FensEarn> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	//新增粉丝收益
	@Override
	public JsonResult addFensEarn(FensEarn fensEarn) {
		int result = fensEarnMapper.insertSelective(fensEarn);
		if (result == 1) {
			return JsonResult.ok();
		}
		return JsonResult.build(500, "添加失败");
	}

	//更新粉丝收益
	@Override
	public JsonResult updateFensRarn(FensEarn fensEarn) {
		int result = fensEarnMapper.updateByPrimaryKeySelective(fensEarn);
		if (result == 1) {
			return JsonResult.ok();
		}
		return JsonResult.build(500, "更新失败");
	}

}
