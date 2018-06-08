package com.carpi.api.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.JsonResult;
import com.carpi.api.dao.BankCardMapper;
import com.carpi.api.dao.FensTransactionMapper;
import com.carpi.api.dao.FensUserMapper;
import com.carpi.api.dao.FensWalletMapper;
import com.carpi.api.pojo.BankCard;
import com.carpi.api.pojo.FensTransaction;
import com.carpi.api.pojo.FensUser;
import com.carpi.api.pojo.FensWallet;
import com.carpi.api.service.JiaoYiService;

@Service
public class JiaoYiServiceImpl implements JiaoYiService {

	@Autowired
	private FensUserMapper fensUserMapper;

	@Autowired
	private FensWalletMapper fensWalletMapper;

	@Autowired
	private FensTransactionMapper fensTransactionMapper;

	@Autowired
	private BankCardMapper bankCardMapper;

	// 接单人接单(买单)
	@Override
	public JsonResult buyDanJieDan(FensTransaction fensTransaction) {
		
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		
		if (!(hour >= ConfigUtil.CPA_JY_START_TIME && hour < ConfigUtil.CPA_JY_END_TIME)) {
			return JsonResult.build(500, "每天开放交易时间为：11:00至18:00.");
		}
		
		if (fensTransaction.getFensUserId() == null || fensTransaction.getId() == null) {
			return JsonResult.build(500, "交易失败");
		}
		// 判断改单是否被抢走
		FensTransaction fensTransaction3 = fensTransactionMapper.selectByPrimaryKey(fensTransaction.getId());
		
		if(fensTransaction3.getTraderId().equals(fensTransaction.getFensUserId())){
			return JsonResult.build(500, "不能自己交易自己的订单");
		}
		
		try {
			Thread.sleep(300);
		} catch (Exception e) {
			System.exit(0);// 退出程序
		}
		
		// 判断是否在挂单中
		if (fensTransaction3.getTraderState() == 0) {

			if (fensTransaction3.getFensUserId() != null) {
				return JsonResult.build(500, "此订单已被抢");
			}
			if (fensTransaction3.getTraderId() == null) {
				return JsonResult.build(500, "订单异常");
			}
			
			FensUser fensUser = fensUserMapper.selectByPrimaryKey(fensTransaction.getFensUserId());
			FensUser fensUser2 = fensUserMapper.selectByPrimaryKey(fensTransaction3.getTraderId());
			// 判断是否存在该接单人
			if (fensUser == null || fensUser2 == null) {
				return JsonResult.build(500, "交易失败，不存在此人");
			}

			// 查询身份证是否认证
			if (Integer.valueOf(fensUser.getAttachment()) != 1 || Integer.valueOf(fensUser2.getAttachment()) != 1) {
				return JsonResult.build(500, "身份证未认证");
			}
			// 银行卡
			List<BankCard> list = bankCardMapper.selectAll(fensTransaction.getFensUserId());
			List<BankCard> list2 = bankCardMapper.selectAll(fensTransaction3.getTraderId());
			if (list.size() <= 0 || list2.size() <= 0) {
				return JsonResult.build(500, "请绑定银行卡");
			}

			// 判断接单人的钱包余额是否足够
			if (!isCPAEnough(fensTransaction.getFensUserId(),fensTransaction3.getId(), fensTransaction3.getTraderCount())) {
				return JsonResult.build(500, "钱包余额不足。或者卖家有其他交易挂单，导致核算后钱包CPA余额不足，不能再次挂卖单。");
			}
			FensTransaction fensTransaction2 = new FensTransaction();
			fensTransaction2.setTraderState(4);
			fensTransaction2.setId(fensTransaction.getId());
			fensTransaction2.setFensUserId(fensTransaction.getFensUserId());
			int result = fensTransactionMapper.updateByPrimaryKeySelective(fensTransaction2);
			if (result != 1) {
				return JsonResult.build(500, "交易失败，请联系管理员");
			}
			return JsonResult.ok();
		} else {
			return JsonResult.build(500, "该订单不在挂单中");
		}
	}

	// 买单人付款(已付款)（买单）
	@Override
	public JsonResult buyDanYiFu(FensTransaction fensTransaction) {
		
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		
		if (!(hour >= ConfigUtil.CPA_JY_START_TIME && hour < ConfigUtil.CPA_JY_END_TIME)) {
			return JsonResult.build(500, "每天开放交易时间为：11:00至18:00.");
		}
		
		if (fensTransaction.getTraderId() == null || fensTransaction.getId() == null) {
			return JsonResult.build(500, "交易失败");
		}
		// 查询是否是本人操作
		FensTransaction fensTransaction3 = fensTransactionMapper.selectByPrimaryKey(fensTransaction.getId());
		
		if(fensTransaction3.getTraderId().equals(fensTransaction.getFensUserId())){
			return JsonResult.build(500, "不能自己交易自己的订单");
		}
		
		try {
			Thread.sleep(300);
		} catch (Exception e) {
			System.exit(0);// 退出程序
		}
		
		if (fensTransaction3.getTraderState() == 1) {
			
			int fensid = fensTransaction3.getTraderId();
			int fensid2 = fensTransaction.getTraderId();
			if (fensid != fensid2) {
				return JsonResult.build(500, "交易异常");
			}

//			if (fensTransaction3.getTraderId() != fensTransaction.getTraderId()) {
//				return JsonResult.build(500, "交易异常");
//			}
			FensUser fensUser = fensUserMapper.selectByPrimaryKey(fensTransaction3.getFensUserId());
			FensUser fensUser2 = fensUserMapper.selectByPrimaryKey(fensTransaction3.getTraderId());
			// 判断是否存在该接单人
			if (fensUser == null || fensUser2 == null) {
				return JsonResult.build(500, "交易失败，不存在此人");
			}

			// 查询身份证是否认证
			if (Integer.valueOf(fensUser.getAttachment()) != 1 || Integer.valueOf(fensUser2.getAttachment()) != 1) {
				return JsonResult.build(500, "身份证未认证");
			}
			// 银行卡
			List<BankCard> list = bankCardMapper.selectAll(fensTransaction3.getFensUserId());
			List<BankCard> list2 = bankCardMapper.selectAll(fensTransaction3.getTraderId());
			if (list.size() <= 0 || list2.size() <= 0) {
				return JsonResult.build(500, "请绑定银行卡");
			}

			// 判断接单人的钱包余额是否足够
			if (!isCPAEnough(fensTransaction3.getFensUserId(), fensTransaction3.getId(),fensTransaction3.getTraderCount())) {
				return JsonResult.build(500, "钱包余额不足。或者卖家有其他交易挂单，导致后钱包CPA余额不足，不能再次挂卖单。");
			}
			FensTransaction fensTransaction2 = new FensTransaction();
			fensTransaction2.setTraderState(2);
			fensTransaction2.setId(fensTransaction.getId());
			int result = fensTransactionMapper.updateByPrimaryKeySelective(fensTransaction2);
			if (result != 1) {
				return JsonResult.build(500, "交易失败，请联系管理员");
			}
			return JsonResult.ok();
		} else {
			return JsonResult.build(500, "该订单不是待付款状态");
		}
	}

	// 接单人确认（买单）接单人已确认收款
	// @Override
	// public JsonResult buyDanQueRen(FensTransaction fensTransaction) {
	// if (fensTransaction.getFensUserId() != null || fensTransaction.getId() ==
	// null) {
	// return JsonResult.build(500, "交易失败");
	// }
	// FensTransaction fensTransaction3 =
	// fensTransactionMapper.selectByPrimaryKey(fensTransaction.getId());
	// if (fensTransaction3.getTraderState() == 2) {
	// if (fensTransaction3.getFensUserId() != fensTransaction.getFensUserId()) {
	// return JsonResult.build(500, "这不是你的订单，无权交易");
	// }
	// FensUser fensUser =
	// fensUserMapper.selectByPrimaryKey(fensTransaction3.getFensUserId());
	// FensUser fensUser2 =
	// fensUserMapper.selectByPrimaryKey(fensTransaction3.getTraderId());
	// // 判断是否存在该接单人
	// if (fensUser == null || fensUser2 == null) {
	// return JsonResult.build(500, "交易失败，不存在此人");
	// }
	//
	// // 身份证
	// // 银行卡
	//
	// // 判断接单人的钱包余额是否足够
	// if (!isCPAEnough(fensTransaction3.getFensUserId(),
	// fensTransaction3.getTraderCount())) {
	// return JsonResult.build(500, "钱包余额不足。或者你作为卖家有交易挂单，核算后钱包CPA余额不足，不能再次挂卖单。");
	// }
	//
	// FensTransaction fensTransaction2 = new FensTransaction();
	// fensTransaction2.setId(fensTransaction.getId());
	// fensTransaction2.setTraderState(3);
	// return JsonResult.ok();
	// } else {
	// return JsonResult.build(500, "对方未付款");
	// }
	// }

	// 接单人接单(卖单)
	@Override
	public JsonResult sellDanJieDan(FensTransaction fensTransaction) {
		
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		
		if (!(hour >= ConfigUtil.CPA_JY_START_TIME && hour < ConfigUtil.CPA_JY_END_TIME)) {
			return JsonResult.build(500, "每天开放交易时间为：11:00至18:00.");
		}
		
		if (fensTransaction.getFensUserId() == null || fensTransaction.getId() == null) {
			return JsonResult.build(500, "交易失败");
		}
		// 判断改单是否被抢走
		FensTransaction fensTransaction3 = fensTransactionMapper.selectByPrimaryKey(fensTransaction.getId());
		
		if(fensTransaction3.getTraderId().equals(fensTransaction.getFensUserId())){
			return JsonResult.build(500, "不能自己交易自己的订单");
		}
		
		try {
			Thread.sleep(300);
		} catch (Exception e) {
			System.exit(0);// 退出程序
		}
		
		// 判断是否在挂单中
		if (fensTransaction3.getTraderState() == 0) {

			if (fensTransaction3.getFensUserId() != null) {
				return JsonResult.build(500, "此订单已被抢");
			}
			if (fensTransaction3.getTraderId() == null) {
				return JsonResult.build(500, "订单异常");
			}
			FensUser fensUser = fensUserMapper.selectByPrimaryKey(fensTransaction.getFensUserId());
			FensUser fensUser2 = fensUserMapper.selectByPrimaryKey(fensTransaction3.getTraderId());
			// 判断是否存在该接单人
			if (fensUser == null || fensUser2 == null) {
				return JsonResult.build(500, "交易失败，不存在此人");
			}

			// 查询身份证是否认证
			if (Integer.valueOf(fensUser.getAttachment()) != 1 || Integer.valueOf(fensUser2.getAttachment()) != 1) {
				return JsonResult.build(500, "身份证未认证");
			}
			// 银行卡
			List<BankCard> list = bankCardMapper.selectAll(fensTransaction.getFensUserId());
			List<BankCard> list2 = bankCardMapper.selectAll(fensTransaction3.getTraderId());
			if (list.size() <= 0 || list2.size() <= 0) {
				return JsonResult.build(500, "请绑定银行卡");
			}

			// 判断接单人的钱包余额是否足够
			if (!isCPAEnough(fensTransaction3.getTraderId(),fensTransaction3.getId(), fensTransaction3.getTraderCount())) {
				return JsonResult.build(500, "钱包余额不足。或者卖家有其他交易挂单，导致后钱包CPA余额不足，不能再次挂卖单。");
			}
			FensTransaction fensTransaction2 = new FensTransaction();
			fensTransaction2.setTraderState(4);
			fensTransaction2.setId(fensTransaction.getId());
			fensTransaction2.setFensUserId(fensTransaction.getFensUserId());
			int result = fensTransactionMapper.updateByPrimaryKeySelective(fensTransaction2);
			if (result != 1) {
				return JsonResult.build(500, "交易失败，请联系管理员");
			}
			return JsonResult.ok();
		} else {
			return JsonResult.build(500, "该订单不在挂单中");
		}
	}

	// 买单人已付款（卖单）
	@Override
	public JsonResult sellDanYiFu(FensTransaction fensTransaction) {
		
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		
		if (!(hour >= ConfigUtil.CPA_JY_START_TIME && hour < ConfigUtil.CPA_JY_END_TIME)) {
			return JsonResult.build(500, "每天开放交易时间为：11:00至18:00.");
		}
		
		if (fensTransaction.getFensUserId() == null || fensTransaction.getId() == null) {
			return JsonResult.build(500, "交易失败");
		}
		// 查询是否是本人操作
		FensTransaction fensTransaction3 = fensTransactionMapper.selectByPrimaryKey(fensTransaction.getId());
		
		if(fensTransaction3.getTraderId().equals(fensTransaction.getFensUserId())){
			return JsonResult.build(500, "不能自己交易自己的订单");
		}
		
		try {
			Thread.sleep(300);
		} catch (Exception e) {
			System.exit(0);// 退出程序
		}
		
		if (fensTransaction3.getTraderState() == 1) {
            System.out.println("==================:"+(fensTransaction3.getFensUserId() != fensTransaction.getFensUserId()));
			
            int fensid = fensTransaction3.getFensUserId();
            int fensid2 = fensTransaction.getFensUserId();
            if (fensid != fensid2) {
				return JsonResult.build(500, "交易异常");
			}
//            if (fensTransaction3.getFensUserId() != fensTransaction.getFensUserId()) {
//				return JsonResult.build(500, "交易异常");
//			}
			FensUser fensUser = fensUserMapper.selectByPrimaryKey(fensTransaction3.getFensUserId());
			FensUser fensUser2 = fensUserMapper.selectByPrimaryKey(fensTransaction3.getTraderId());
			// 判断是否存在该接单人
			if (fensUser == null || fensUser2 == null) {
				return JsonResult.build(500, "交易失败，不存在此人");
			}

			// 查询身份证是否认证
			if (Integer.valueOf(fensUser.getAttachment()) != 1 || Integer.valueOf(fensUser2.getAttachment()) != 1) {
				return JsonResult.build(500, "身份证未认证");
			}
			// 银行卡
			List<BankCard> list = bankCardMapper.selectAll(fensTransaction3.getFensUserId());
			List<BankCard> list2 = bankCardMapper.selectAll(fensTransaction3.getTraderId());
			if (list.size() <= 0 || list2.size() <= 0) {
				return JsonResult.build(500, "请绑定银行卡");
			}

			// 判断接单人的钱包余额是否足够
			if (!isCPAEnough(fensTransaction3.getTraderId(),fensTransaction3.getId(), fensTransaction3.getTraderCount())) {
				return JsonResult.build(500, "钱包余额不足。或者卖家有其他交易挂单，导致后钱包CPA余额不足，不能再次挂卖单。");
			}
			FensTransaction fensTransaction2 = new FensTransaction();
			fensTransaction2.setTraderState(2);
			fensTransaction2.setId(fensTransaction.getId());
			int result = fensTransactionMapper.updateByPrimaryKeySelective(fensTransaction2);
			if (result != 1) {
				return JsonResult.build(500, "交易失败，请联系管理员");
			}
			return JsonResult.ok();
		} else {
			return JsonResult.build(500, "该订单不是代付款状态");
		}
	}

	// 校验cpa是否充足
	public boolean isCPAEnough(Integer uid, Integer id, double count) {
		FensWallet fw = fensWalletMapper.selectByFens(uid);
		Map allBlockCPA = fensTransactionMapper.getAllBlockCPA(uid,id);

		double cpa = 0;
		System.out.println("------alllockcpa::::" + allBlockCPA);
		if (allBlockCPA != null) {
			cpa = (double) allBlockCPA.get("sum");
		}

		System.out.println("------alllockcpa::::" + allBlockCPA + "------cpa:" + cpa + "-------count:" + count
				+ "-----ablecpa:");

		if ((fw.getAbleCpa() - cpa * 1.2) >= count * 1.2) {
			return true;
		}

		return false;
	}

	//查询订单（买单，卖单）
	@Override
	public JsonResult selectjl(String phone, Integer traderType) {
		if ("".equals(phone)) {
		    return JsonResult.build(500, "手机号码为空");
		}
		if (traderType == null) {
			return JsonResult.build(500, "请正确操作");
		}
		List<FensTransaction> list = fensTransactionMapper.selectjl(phone, traderType);
		if (list.size()>0) {
			return JsonResult.ok(list);
		}
		return JsonResult.build(500, "不存在此订单");
	}

	//粉丝交易量(当天)
	@Override
	public JsonResult JYLsum() {
		int sum = fensTransactionMapper.JYLsum();
		return JsonResult.ok(sum);
	}
}
