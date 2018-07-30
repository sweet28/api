package com.carpi.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.dao.BankCardMapper;
import com.carpi.api.dao.FensUserMapper;
import com.carpi.api.pojo.BankCard;
import com.carpi.api.pojo.FensUser;
import com.carpi.api.service.PayService;

@Service
public class PayServiceImpl implements PayService {

	@Autowired
	private BankCardMapper bankCardMapper;

	@Autowired
	private FensUserMapper fensUserMapper;

	// 添加支付宝微信支付
	@Override
	public JsonResult aliPay(BankCard bankCard) {
		BankCard bankCard2 = bankCardMapper.selectPay(bankCard);
		if (!StringUtils.isEmpty(bankCard2)) {
			return JsonResult.build(500, "每人仅限绑定一个账号");
		}
		
		//查询该支付宝或者微信是否已存在
		BankCard bankCard3 = bankCardMapper.weChatAipay(bankCard);
		if (!StringUtils.isEmpty(bankCard3)) {
			return JsonResult.build(500, "该账号已被绑定");
		}
		int result = bankCardMapper.insertSelective(bankCard);
		if (result == 1) {
			return JsonResult.ok();
		}
		return JsonResult.build(500, "添加失败");
	}

	// 根据粉丝id查询资金密码
	@Override
	public JsonResult checkMiMa(String fensUserId, String mima) {
		if (fensUserId == null) {
			return JsonResult.build(500, "系统错误2");
		}
		if (mima == null) {
			return JsonResult.build(500, "系统错误");
		}
		FensUser fensUser = fensUserMapper.selectzjPwd(Integer.valueOf(fensUserId), mima);
		if (fensUser != null) {
			return JsonResult.ok();
		}
		return JsonResult.build(500, "资金密码错误，请重试");
	}

	// 查询支付宝或者微信
	@Override
	public JsonResult selectPay(BankCard bankCard) {
		if (bankCard.getFensUserId() == null) {
			return JsonResult.build(500, "系统错误1");
		}
		if (bankCard.getBak1() == null) {
			return JsonResult.build(500, "系统错误2");
		}
		BankCard bankCard2 = bankCardMapper.selectPay(bankCard);
		if (bankCard2 != null) {
			return JsonResult.ok(bankCard2);
		} else {
			if ("1".equals(bankCard.getBak1())) {
				return JsonResult.build(500, "无支付宝账户");
			} else if ("2".equals(bankCard.getBak1())) {
				return JsonResult.build(500, "无微信账户");
			} else {
				return JsonResult.build(500, "系统错误");
			}
		}
	}

	//查询支付账户信息
	@Override
	public JsonResult selectZh(Integer fensUserId) {
		List<BankCard> list = bankCardMapper.selectZh(fensUserId);
		if (list.isEmpty()) {
			return JsonResult.build(500, "无信息");
		}
		return JsonResult.ok(list);
	}
}
