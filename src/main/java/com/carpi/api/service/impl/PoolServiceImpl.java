package com.carpi.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.New;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arttraining.commons.util.JsonResult;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;
import com.carpi.api.dao.APoolMapper;
import com.carpi.api.dao.AminerMapper;
import com.carpi.api.dao.BPoolMapper;
import com.carpi.api.dao.BminerMapper;
import com.carpi.api.dao.FensMinerMapper;
import com.carpi.api.dao.FensWalletMapper;
import com.carpi.api.pojo.APool;
import com.carpi.api.pojo.Aminer;
import com.carpi.api.pojo.BPool;
import com.carpi.api.pojo.Bminer;
import com.carpi.api.pojo.FensMiner;
import com.carpi.api.pojo.FensWallet;
import com.carpi.api.pojo.FoneyRecord;
import com.carpi.api.service.FensWalletService;
import com.carpi.api.service.PoolService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class PoolServiceImpl implements PoolService {
	@Autowired
	private APoolMapper apoolMapper;

	@Autowired
	private BPoolMapper bpoolMapper;

	@Autowired
	private FensWalletMapper fensWalletMapper;

	@Autowired
	private FensWalletService fensWalletService;

	@Autowired
	private FensMinerMapper fensMinerMapper;

	@Autowired
	private AminerMapper aminerMapper;

	@Autowired
	private BminerMapper bminerMapper;

	// a矿池列表
	@Override
	public PageInfo<APool> selectApool(Integer page, Integer num, Integer fensUserId) {
		PageHelper.startPage(page, num);
		List<APool> list = apoolMapper.selectApool(fensUserId);
		PageInfo<APool> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	// b矿池列表
	@Override
	public PageInfo<BPool> selectBpool(Integer page, Integer num, Integer fensUserId) {
		PageHelper.startPage(page, num);
		List<BPool> list = bpoolMapper.selectBpool(fensUserId);
		PageInfo<BPool> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	// 解冻(A)
	// 矿池里把该粉丝的cpa数 转出到他钱包下面 钱包的可用余额增加 钱包的转账记录也增加一条记录
	@Override
	public JsonResult thawAMiner(APool aPool) {
		// 根据粉丝Id查询该粉丝对应的矿池信息
		List<APool> list = apoolMapper.selectApool(aPool.getFensUserId());
		if (list != null && list.size() > 0) {
			// 钱包的可用余额增加相应数目的cpa
			// 根据粉丝Id查询该粉丝对应钱包信息
			/*
			 * List<FensWallet> walletsList =
			 * fensWalletMapper.selectAll(aPool.getFensUserId()); // 钱包为空，请创建 if
			 * (walletsList == null && walletsList.size() == 0) { return
			 * JsonResult.build(500, "请先创建钱包"); }
			 */

			FensWallet fensWallet = fensWalletMapper.selectAll(aPool.getFensUserId());
			if (fensWallet == null) {
				return JsonResult.build(500, "钱包不存在");
			}

			APool aPool2 = list.get(0);
			// 判断矿池cpa是否充足
			if (aPool.getCpaCount() > aPool2.getCpaCount()) {
				return JsonResult.build(500, "您的余额不足");
			}
			// 解冻后矿池减掉相应的cpa
			Double cpa = aPool2.getCpaCount() - aPool.getCpaCount();
			// 转出时间
			Date date1 = TimeUtil.getTimeStamp();
			APool pool = new APool();
			pool.setCpaCount(cpa);
			pool.setId(aPool2.getId());
			// 更新cpa(a矿池)
			int result = apoolMapper.updateByPrimaryKeySelective(pool);
			if (result != 1) {
				ServerLog.getLogger().warn("更新矿池失败，粉丝id：" + aPool2.getFensUserId());
			}

			// 钱包添加cpa
			// FensWallet wallet = walletsList.get(0);
			Double ablecpa = fensWallet.getAbleCpa() + aPool.getCpaCount();
			// 到账时间
			Date date2 = TimeUtil.getTimeStamp();
			FensWallet wallet2 = new FensWallet();
			// 钱包可用余额增加
			wallet2.setAbleCpa(ablecpa);
			wallet2.setId(fensWallet.getId());
			// 更新钱包可用cpa
			int result2 = fensWalletMapper.updateByPrimaryKeySelective(wallet2);
			if (result2 != 1) {
				ServerLog.getLogger().warn("更新钱包可用失败，粉丝id：" + aPool2.getFensUserId());
			}

			// 钱包转账记录表添加一条记录
			FoneyRecord moneyRecord = new FoneyRecord();
			moneyRecord.setFensUserId(aPool.getFensUserId());
			// 交易金额
			moneyRecord.setPayment(aPool.getCpaCount());
			moneyRecord.setReceiveDate(date2);
			moneyRecord.setSendDate(date1);
			// 转账类型1 代表接收 2代表转出
			moneyRecord.setPaymentType(1);
			return fensWalletService.addWalletRecord(moneyRecord);
		}

		return JsonResult.build(500, "不存在矿池信息");
	}

	// 解冻(B)
	@Override
	public JsonResult thawBMiner(BPool bPool) {
		// 根据粉丝Id查询该粉丝对应的矿池信息
		List<BPool> list = bpoolMapper.selectBpool(bPool.getFensUserId());
		if (list != null && list.size() > 0) {
			// 钱包的可用余额增加相应数目的cpa
			// 根据粉丝Id查询该粉丝对应钱包信息
			/*
			 * List<FensWallet> walletsList =
			 * fensWalletMapper.selectAll(bPool.getFensUserId()); // 钱包为空，请创建 if
			 * (walletsList == null && walletsList.size() == 0) { return
			 * JsonResult.build(500, "请先创建钱包"); }
			 */

			FensWallet fensWallet = fensWalletMapper.selectAll(bPool.getFensUserId());
			if (fensWallet == null) {
				return JsonResult.build(500, "钱包不存在");
			}
			BPool bPool2 = list.get(0);
			// 判断矿池cpa是否充足
			if (bPool.getCpaCount() > bPool2.getCpaCount()) {
				return JsonResult.build(500, "您的余额不足");
			}
			// 解冻后矿池减掉相应的cpa
			Double cpa = bPool2.getCpaCount() - bPool.getCpaCount();
			// 转出时间
			Date date1 = TimeUtil.getTimeStamp();
			BPool pool = new BPool();
			pool.setCpaCount(cpa);
			pool.setId(bPool2.getId());
			// 更新cpa(a矿池)
			int result = bpoolMapper.updateByPrimaryKeySelective(pool);
			if (result != 1) {
				ServerLog.getLogger().warn("更新矿池失败，粉丝id：" + bPool2.getFensUserId());
			}

			// 钱包添加cpa
			// FensWallet wallet = walletsList.get(0);
			Double ablecpa = fensWallet.getAbleCpa() + bPool.getCpaCount();
			// 到账时间
			Date date2 = TimeUtil.getTimeStamp();
			FensWallet wallet2 = new FensWallet();
			// 钱包可用余额增加
			wallet2.setAbleCpa(ablecpa);
			wallet2.setId(fensWallet.getId());
			// 更新钱包可用cpa
			int result2 = fensWalletMapper.updateByPrimaryKeySelective(wallet2);
			if (result2 != 1) {
				ServerLog.getLogger().warn("更新钱包可用失败，粉丝id：" + bPool2.getFensUserId());
			}

			// 钱包转账记录表添加一条记录
			FoneyRecord moneyRecord = new FoneyRecord();
			moneyRecord.setFensUserId(bPool.getFensUserId());
			// 交易金额
			moneyRecord.setPayment(bPool.getCpaCount());
			moneyRecord.setReceiveDate(date2);
			moneyRecord.setSendDate(date1);
			// 转账类型1 代表接收 2代表转出
			moneyRecord.setPaymentType(1);
			return fensWalletService.addWalletRecord(moneyRecord);
		}
		return JsonResult.build(500, "不存在矿池信息");
	}

	// 矿池锁定的币购买矿机
	@Override
	public JsonResult suoDingBuy(Integer fensUserId, Integer id, Integer type) {
		if (fensUserId == null) {
			return JsonResult.build(500, "请重新登录");
		}
		if (id == null) {
			return JsonResult.build(500, "请选择矿机");
		}
		if (type == null) {
			return JsonResult.build(500, "请选择矿机2");
		}
		// 根据粉丝id查询所拥有的矿机信息
		List<FensMiner> list = fensMinerMapper.selectMiner3(fensUserId);
		if (list.isEmpty()) {
			return JsonResult.build(500, "无矿机信息");
		}
		// Double zongshouyi = null;
		Double suodingall = null;
		// 遍历信息
		Map<String, String> map2 = new HashMap<>();

		List<Map<String, String>> list2 = new ArrayList<>();

		for (FensMiner fensMiner : list) {
			// 获取锁定收益信息
			Map<String, Double> map = check(fensMiner);
			// zongshouyi = map.get("nowZSY") + zongshouyi;
			suodingall = map.get("kySY") + suodingall;
			System.out.println("-------已锁定cpa--------:" + suodingall);
			map2.put("ytqSY", map.get("yhdSY").toString());
			map2.put("id", fensMiner.getId().toString());
			list2.add(map2);
		}
		System.out.println("----+++++++++++---已锁定cpa--------:" + suodingall);
		// 判断是否能购买矿机
		if (type == 1) {
			Aminer aminer = aminerMapper.selectByPrimaryKey(id);
			if (suodingall >= aminer.getPrice()) {
				// 购买矿机
				FensMiner fensMiner = new FensMiner();
				fensMiner.setFensUserId(fensUserId);
				// 几星矿机
				fensMiner.setBak1(String.valueOf(aminer.getType()));
				// A矿机
				fensMiner.setMinerType(1);
				fensMiner.setMinerId(aminer.getId());
				fensMiner.setMinerComputingPower(aminer.getComputingPower());
				fensMiner.setCreateDate(TimeUtil.getTimeStamp());
				fensMiner.setIsDelete(2);// 购买矿机需要审核cpa合法性
				fensMiner.setBeyong1(String.valueOf(aminer.getPrice()));
				int result = fensMinerMapper.insertSelective(fensMiner);
				if (result != 1) {
					return JsonResult.build(500, "购买矿机失败");
				}
				// 扣cpa
				// double suoding2 = suoding - aminer.getPrice();
				double suoding2 = aminer.getPrice() * 2 - suodingall;
				FensMiner fensMiner2 = new FensMiner();
				if (suoding2 > 0) {
					for (Map<String, String> map3 : list2) {
						// 获取锁定收益信息
						Map<String, Double> map = check(fensMiner);
						// 返还多余的cpa
						// double ytqSY2 = Double.valueOf(map3.get("ytqSY")) - suoding2;

						// 需要填补的cpa
						double ytqSY2 = suoding2 - Double.valueOf(map.get("kySY"));
						fensMiner2.setId(Integer.valueOf(map3.get("id")));
						// 矿机当前的总收益
						Double dqZSY = map.get("nowZSY");

						if (ytqSY2 > dqZSY) {
							// 添加大值
							fensMiner2.setTotalRevenue(dqZSY);
							// 减去添加的值
							ytqSY2 = ytqSY2 - dqZSY;
						} else if (ytqSY2 < dqZSY && ytqSY2 > 0) { // 剩余最后的cpa,直接全部添加到该矿机
							fensMiner2.setTotalRevenue(ytqSY2);
						}
						int status = fensMinerMapper.updateByPrimaryKeySelective(fensMiner2);
						if (status != 1) {
							return JsonResult.build(500, "返还失败");
						}
						// 释放差值
						// if (ytqSY2<0) {
						// fensMiner2.setId(Integer.valueOf(map3.get("id")));
						// fensMiner2.setTotalRevenue(Double.valueOf(0));
						// int status = fensMinerMapper.updateByPrimaryKeySelective(fensMiner2);
						// if (status != 1) {
						// // return JsonResult.build(500, "剩余cpa更新");
						// continue;
						// }
						// suoding2 = suoding2 + ytqSY2;
						// }else if (ytqSY2 >= 0) {
						// fensMiner2.setId(Integer.valueOf(map3.get("id")));
						// fensMiner2.setTotalRevenue(ytqSY2);
						// fensMinerMapper.updateByPrimaryKeySelective(fensMiner2);
						// break;
						// }
					}
					// //扣除矿机费用
					// for(Map<String, String> map3 : list2) {
					// Double.valueOf(map3.get("ytqSY"))
					// }

				} else if (suoding2 == 0) {
					return JsonResult.build(500, "系统错误");
				}
			}
			return JsonResult.build(500, "可用冻结cpa不足");
		} else if (type == 2) {
			Bminer bminer = bminerMapper.selectByPrimaryKey(id);
			// 粉丝矿机表添加数据
			FensMiner fensMiner = new FensMiner();
			fensMiner.setFensUserId(bminer.getFensUserId());
			// 几星矿机
			fensMiner.setBak1(String.valueOf(bminer.getType()));
			// B矿机
			fensMiner.setMinerType(2);
			fensMiner.setMinerId(bminer.getId());
			fensMiner.setMinerComputingPower(bminer.getComputingPower());
			fensMiner.setCreateDate(TimeUtil.getTimeStamp());
			fensMiner.setIsDelete(2);// 购买矿机需要审核cpa合法性
			fensMiner.setBeyong1(String.valueOf(bminer.getPrice()));
			int result2 = fensMinerMapper.insertSelective(fensMiner);
			if (result2 != 1) {
				return JsonResult.build(500, "购买矿机失败");
			}
			return JsonResult.ok();
		}
		return JsonResult.build(500, "系统错误");
	}

	// 可用收益（提取的币）
	public Map<String, Double> check(FensMiner miner2) {
		Date dd = TimeUtil.getTimeStamp();
		// 根据粉丝Id查询该粉丝对应的矿池信息
		FensMiner miner = fensMinerMapper.selectByPrimaryKey(miner2.getId());
		double rundate = dd.getTime() - miner.getCreateDate().getTime();
		rundate = rundate / (1000 * 60 * 60 * 24);

		if (rundate >= 15) {
			rundate = 15;
		}

		System.out.println(rundate + "---" + rundate + "------");

		double nowZSY;
		double syyz = 0;

		if (miner.getMinerType() == 1) {
			if (Integer.parseInt(miner.getBak1()) == 1) {
				syyz = 11;
			} else if (Integer.parseInt(miner.getBak1()) == 2) {
				syyz = 115;
			} else if (Integer.parseInt(miner.getBak1()) == 3) {
				syyz = 1150;
			} else if (Integer.parseInt(miner.getBak1()) == 4) {
				syyz = 6000;
			}
		} else if (miner.getMinerType() == 2) {

			if (Integer.parseInt(miner.getBak1()) == 1) {
				syyz = 5.5;
			} else if (Integer.parseInt(miner.getBak1()) == 2) {
				syyz = 55;
			} else if (Integer.parseInt(miner.getBak1()) == 3) {
				syyz = 550;
			}
		}

		// 当前产生的总收益
		nowZSY = rundate * (syyz / 15);
		Double yhdSY, kySY;
		// 已提取的币
		yhdSY = miner.getTotalRevenue();
		// 可用收益(锁定的币)
		kySY = nowZSY - yhdSY;

		Map<String, Double> map = new HashMap<>();
		map.put("nowZSY", nowZSY);
		map.put("yhdSY", yhdSY);
		map.put("kySY", kySY);
		return map;
	}
}
