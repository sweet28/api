package com.carpi.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.dao.BankCardMapper;
import com.carpi.api.pojo.BankCard;
import com.carpi.api.service.BankCardService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class BankCardServiceImpl implements BankCardService {

	@Autowired
	private BankCardMapper bankCardMapper;

	// 根据粉丝Id查询粉丝银行卡列表
	@Override
	public PageInfo<BankCard> selectAll(Integer pageNum, Integer pageSize, Integer fensUserId) {
		PageHelper.startPage(pageNum, pageSize);
		List<BankCard> list = bankCardMapper.selectAll(fensUserId);
		PageInfo<BankCard> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	// 设置默认使用的银行卡(0：不使用；1：使用)
	@Override
	public JsonResult updateApply(BankCard bankCard) {
		int result = bankCardMapper.updateByPrimaryKeySelective(bankCard);
		if (result != 1) {
			return JsonResult.build(500, "设置失败");
		}
		return JsonResult.ok();
	}

	// 粉丝添加银行卡
	@Override
	public JsonResult addBlank(BankCard bankCard) {
		BankCard selectCard = bankCardMapper.selectCard(bankCard.getCardNumber());
		if (selectCard != null) {
			return JsonResult.build(500, "该银行卡已存在");
		}
		int result = bankCardMapper.insertSelective(bankCard);
		if (result != 1) {
			return JsonResult.build(500, "添加失败");
		}
		return JsonResult.ok();
	}

	// 粉丝修改银行卡
	@Override
	public JsonResult updateBlank(BankCard bankCard) {
		int result = bankCardMapper.updateByPrimaryKeySelective(bankCard);
		if (result != 1) {
			return JsonResult.build(500, "修改失败");
		}
		return JsonResult.ok();
	}

}
