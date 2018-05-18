package com.carpi.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.dao.AminerMapper;
import com.carpi.api.dao.BminerMapper;
import com.carpi.api.dao.FensMinerMapper;
import com.carpi.api.dao.FensWalletMapper;
import com.carpi.api.pojo.Aminer;
import com.carpi.api.pojo.Bminer;
import com.carpi.api.pojo.FensMiner;
import com.carpi.api.pojo.FensWallet;
import com.carpi.api.service.MinerRecordService;

@Service
public class MinerRecordServiceImpl implements MinerRecordService {

	@Autowired
	private FensWalletMapper fensWalletMapper;
	@Autowired
	private AminerMapper aminerMapper;
	@Autowired
	private BminerMapper bminerMapper;

	@Autowired
	private FensMinerMapper fensMinerMapper;

	// 购买a矿机
	@Override
	public JsonResult buyAMiner(Aminer aminer) {
		// 查询需要购买的矿机信息（根据类型1星，2星等）
		Aminer aminer2 = aminerMapper.selectType(aminer.getType());
		// 粉丝购买矿机后粉丝钱包的剩余的可用cpa余额
		// 先查询粉丝钱包的可用余额(根据粉丝id)
		/*List<FensWallet> list = fensWalletMapper.selectAll(aminer.getFensUserId());
		FensWallet fensWallet = list.get(0);*/
		FensWallet fensWallet = fensWalletMapper.selectAll(aminer.getFensUserId());
		// 如果粉丝购买矿机的数量乘以单价大于可用余额
		// 购买矿机所花的总cpa
		Double money = aminer.getCount() * aminer.getPrice();
		if (money > fensWallet.getAbleCpa()) {
			JsonResult.build(500, "余额不足");
		}
		// 粉丝钱包的剩余的可用cpa余额减少相应cpa
		FensWallet fensWallet2 = new FensWallet();
		// 粉丝钱包的剩余的可用cpa余额
		// Double money2 = fensWallet.getAbleCpa()-money;
		fensWallet2.setAbleCpa(fensWallet.getAbleCpa() - money);
		fensWallet2.setId(fensWallet.getId());
		// 修改余额
		int result = fensWalletMapper.updateByPrimaryKeySelective(fensWallet2);
		if (result != 1) {
			return JsonResult.build(500, "购买矿机出错");
		}
		// 粉丝矿机表添加数据
		FensMiner fensMiner = new FensMiner();
		fensMiner.setFensUserId(aminer.getFensUserId());
		// 几星矿机
		fensMiner.setBak1(String.valueOf(aminer2.getType()));
		// A矿机
		fensMiner.setMinerType(1);
		fensMiner.setMinerId(aminer2.getId());
		fensMiner.setMinerComputingPower(aminer2.getComputingPower());
		int result2 = fensMinerMapper.insertSelective(fensMiner);
		if (result2 != 1) {
			return JsonResult.build(500, "购买矿机失败");
		}
		return JsonResult.ok();
	}

	// 购买b矿机
	@Override
	public JsonResult buyBMiner(Bminer bminer) {
		// 查询需要购买的矿机信息（根据类型1星，2星等）
		Bminer bminer2 = bminerMapper.selectType(bminer.getType());
		// 粉丝购买矿机后粉丝钱包的剩余的可用cpa余额
		// 先查询粉丝钱包的可用余额(根据粉丝id)
		/*List<FensWallet> list = fensWalletMapper.selectAll(bminer.getFensUserId());
		FensWallet fensWallet = list.get(0);*/
		FensWallet fensWallet = fensWalletMapper.selectAll(bminer.getFensUserId());
		// 如果粉丝购买矿机的数量乘以单价大于可用余额
		// 购买矿机所花的总cpa
		Double money = bminer.getCount() * bminer.getPrice();
		if (money > fensWallet.getAbleCpa()) {
			JsonResult.build(500, "余额不足");
		}
		// 粉丝钱包的剩余的可用cpa余额减少相应cpa
		FensWallet fensWallet2 = new FensWallet();
		// 粉丝钱包的剩余的可用cpa余额
		// Double money2 = fensWallet.getAbleCpa()-money;
		fensWallet2.setAbleCpa(fensWallet.getAbleCpa() - money);
		fensWallet2.setId(fensWallet.getId());
		// 修改余额
		int result = fensWalletMapper.updateByPrimaryKeySelective(fensWallet2);
		if (result != 1) {
			return JsonResult.build(500, "购买矿机出错");
		}
		// 粉丝矿机表添加数据
		FensMiner fensMiner = new FensMiner();
		fensMiner.setFensUserId(bminer.getFensUserId());
		// 几星矿机
		fensMiner.setBak1(String.valueOf(bminer2.getType()));
		// A矿机
		fensMiner.setMinerType(1);
		fensMiner.setMinerId(bminer2.getId());
		fensMiner.setMinerComputingPower(bminer2.getComputingPower());
		int result2 = fensMinerMapper.insertSelective(fensMiner);
		if (result2 != 1) {
			return JsonResult.build(500, "购买矿机失败");
		}
		return JsonResult.ok();
	}

}
