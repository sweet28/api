package com.carpi.api.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.IdWorker;
import com.arttraining.commons.util.JsonResult;
import com.arttraining.commons.util.OrderNumberUtil;
import com.arttraining.commons.util.TimeUtil;
import com.carpi.api.dao.AminerMapper;
import com.carpi.api.dao.AminerRecordMapper;
import com.carpi.api.dao.BankCardMapper;
import com.carpi.api.dao.BminerMapper;
import com.carpi.api.dao.BminerRecordMapper;
import com.carpi.api.dao.FensMinerMapper;
import com.carpi.api.dao.FensTransactionMapper;
import com.carpi.api.dao.FensUserMapper;
import com.carpi.api.dao.FensWalletMapper;
import com.carpi.api.dao.QuanBaoLiRecordMapper;
import com.carpi.api.pojo.Aminer;
import com.carpi.api.pojo.AminerRecord;
import com.carpi.api.pojo.BankCard;
import com.carpi.api.pojo.Bminer;
import com.carpi.api.pojo.BminerRecord;
import com.carpi.api.pojo.FensMiner;
import com.carpi.api.pojo.FensTransaction;
import com.carpi.api.pojo.FensUser;
import com.carpi.api.pojo.FensWallet;
import com.carpi.api.service.FensRecordServcie;
import com.carpi.api.service.FensWalletService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class FensRecordServcieImpl implements FensRecordServcie {

	@Autowired
	private FensTransactionMapper fensTransactionMapper;

	@Autowired
	private FensUserMapper fensUserMapper;

	@Autowired
	private AminerMapper aminerMapper;

	@Autowired
	private BminerMapper bminerMapper;

	@Autowired
	private AminerRecordMapper aminerRecordMapper;

	@Autowired
	private BminerRecordMapper bminerRecordMapper;

	@Autowired
	private FensWalletMapper fensWalletMapper;

	@Autowired
	private BankCardMapper bankCardMapper;
	
	@Autowired
	private FensMinerMapper fensMinerMapper;
	
	@Autowired
	private QuanBaoLiRecordMapper quanBaoLiRecordMapper;

	// 粉丝交易记录（可根据粉丝id查个人）
	@Override
	public PageInfo<FensTransaction> selectRecord(Integer page, Integer row, FensTransaction fensTransaction) {
		PageHelper.startPage(page, row);
		System.out.println("tradeID:" + fensTransaction.getTraderId() + "---page:" + page + "---row:" + row + "---type:"
				+ fensTransaction.getTraderType() + "-----state:" + fensTransaction.getTraderState() + "---fensID:"
				+ fensTransaction.getFensUserId());
		if ((fensTransaction.getTraderType() == 9)) {
			fensTransaction.setTraderType(null);
		} else {
			fensTransaction.setTraderId(null);
		}
		List<FensTransaction> list = fensTransactionMapper.selectFensRecord(fensTransaction);
		PageInfo<FensTransaction> pageInfo = new PageInfo<FensTransaction>(list);
		return pageInfo;
	}

	// 粉丝待付款交易记录（可根据粉丝id查个人）
	@Override
	public List<FensTransaction> selectDFKRecord(FensTransaction fensTransaction) {
		List<FensTransaction> list = fensTransactionMapper.selectDFKRecord(fensTransaction);
		return list;
	}

	// 粉丝待收款交易记录（可根据粉丝id查个人）
	@Override
	public List<FensTransaction> selectDSKRecord(FensTransaction fensTransaction) {
		List<FensTransaction> list = fensTransactionMapper.selectDSKRecord(fensTransaction);
		return list;
	}

	// 粉丝完成交易记录（可根据粉丝id查个人）
	@Override
	public List<FensTransaction> selectYWCRecord(FensTransaction fensTransaction) {
		List<FensTransaction> list = fensTransactionMapper.selectYWCRecord(fensTransaction);
		return list;
	}

	// 粉丝挂单交易记录（可根据粉丝id查个人）
	@Override
	public List<FensTransaction> selectGDRecord(FensTransaction fensTransaction) {
		List<FensTransaction> list = fensTransactionMapper.selectGDRecord(fensTransaction);
		return list;
	}

	// 粉丝成交交易记录（可根据粉丝id查个人）
	@Override
	public PageInfo<FensTransaction> selectCJRecord(Integer page, Integer row, FensTransaction fensTransaction) {

		PageHelper.startPage(page, row);
		System.out.println("tradeID:" + fensTransaction.getTraderId() + "--2222222-page:" + page + "---row:" + row
				+ "---type:" + fensTransaction.getTraderType() + "-----state:" + fensTransaction.getTraderState()
				+ "---fensID:" + fensTransaction.getFensUserId());
		if ((fensTransaction.getTraderType() == 9)) {
			fensTransaction.setTraderType(null);
		} else {
			fensTransaction.setTraderId(null);
		}
		List<FensTransaction> list;
		if (fensTransaction.getTraderType() == 10) {
			list = fensTransactionMapper.selectCJFensRecordByID(fensTransaction.getTraderId());
		} else {
			list = fensTransactionMapper.selectFensRecord(fensTransaction);
		}
		PageInfo<FensTransaction> pageInfo = new PageInfo<FensTransaction>(list);
		return pageInfo;
	}

	// 交易记录（可根据id查）
	@Override
	public FensTransaction selectRecordByID(Integer id) {
		FensTransaction recode = fensTransactionMapper.selectByPrimaryKey(id);
		return recode;
	}

	// a矿机的列表
	@Override
	public PageInfo<Aminer> selectAMiner(Integer page, Integer row) {
		PageHelper.startPage(page, row);
		List<Aminer> list = aminerMapper.selectAMiner();
		PageInfo<Aminer> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	// b矿机的列表
	@Override
	public PageInfo<Bminer> selectBMiner(Integer page, Integer row) {
		PageHelper.startPage(page, row);
		List<Bminer> list = bminerMapper.selectBMiner();
		PageInfo<Bminer> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	// a矿机的交易列表
	@Override
	public PageInfo<AminerRecord> selectAminerRecord(Integer page, Integer row, Integer fensUserId) {
		PageHelper.startPage(page, row);
		List<AminerRecord> list = aminerRecordMapper.selectARecord(fensUserId);
		PageInfo<AminerRecord> pageInfo = new PageInfo<AminerRecord>(list);
		return pageInfo;
	}

	// b矿机的交易列表
	@Override
	public PageInfo<BminerRecord> selectBminerRecord(Integer page, Integer row, Integer fensUserId) {
		PageHelper.startPage(page, row);
		List<BminerRecord> list = bminerRecordMapper.selectBRecord(fensUserId);
		PageInfo<BminerRecord> pageInfo = new PageInfo<BminerRecord>(list);
		return pageInfo;
	}

	// 粉丝记录增加（挂单）
	@Override
	public JsonResult addRecord(FensTransaction fensTransaction) {

		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);

		if (!(hour >= ConfigUtil.CPA_JY_START_TIME && hour < ConfigUtil.CPA_JY_END_TIME)) {
			return JsonResult.build(500, ConfigUtil.CPA_JY_NOTICE);
		}

		try {
			Thread.sleep(500);
		} catch (Exception e) {
			System.exit(0);// 退出程序
		}

		if (fensTransaction.getTraderCount() == null) {
			return JsonResult.build(500, "交易数量不能为空");
		} else if (fensTransaction.getTraderCount() < 10) {
			return JsonResult.build(500, "交易数量不能小于10");
		}

		if (fensTransaction.getEntrustPrice() == null) {
			return JsonResult.build(500, "交易单价不能为空");
		}

		if (fensTransaction.getTraderType() == null) {
			return JsonResult.build(500, "新增失败");
		}

		if (fensTransaction.getTraderId() != null) {
			int fuid = fensTransaction.getTraderId();
			FensUser fus = fensUserMapper.selectByPrimaryKey(fuid);
			if (fus != null) {

				// 查询身份证是否认证
				if (Integer.valueOf(fus.getAttachment()) != 1) {
					return JsonResult.build(500, "身份证未认证");
				}
				
				// 查询是否设置交易密码
				if(StringUtils.isEmpty(fus.getCapitalPwd())){
					return JsonResult.build(500, "请前往个人中心设置交易密码");
				}
				
				// 银行卡
				List<BankCard> list = bankCardMapper.selectAll(fus.getId());
				if (list.size() <= 0) {
					return JsonResult.build(500, "请绑定银行卡");
				}

				int type = fensTransaction.getTraderType();
				double price = fensTransaction.getEntrustPrice();

				fensTransaction
						.setMoneyCount(fensTransaction.getEntrustPrice() * 6.5 * fensTransaction.getTraderCount());

				double zgPrice = 0.94;
				double zdPrice = 0.80;
				//1、凡持有四星矿机的会员，可挂卖该级别矿机挖矿收益50%的CPA，以高于平台价1元（人民币）的价格被基金会回收。
				//2、凡持有三星矿机且有三张三星在参与券保理的会员，可挂卖该级别矿机挖矿收益30%的CPA，以高于平台价1元（人民币）的价格被基金会回收。
				//3、凡持有二星矿机的会员，且有六张二星在参与券保理的会员，可挂卖该级别矿机挖矿收益20%的CPA，以高于平台价1元（人民币）的价格被基金会回收。
				double giftPrice = 0.00;
				
				int Miner4Num = 0;
				int Miner3Num = 0;
				int Miner2Num = 0;
				
				int quan3Num = 0;
				int quan2Num = 0;
				int flag = 0;//何种回收机制标记  0：无；4：四星回收；3：三星回收；2：二星回收

				Miner4Num = fensMinerMapper.selectSumHuishou("4", fuid);
				Miner3Num = fensMinerMapper.selectSumHuishou("3", fuid);
				Miner2Num = fensMinerMapper.selectSumHuishou("2", fuid);

				quan3Num = quanBaoLiRecordMapper.selectsumOnline(fuid, 4);
				quan2Num = quanBaoLiRecordMapper.selectsumOnline(fuid, 3);
				
				double cpaCount = fensTransaction.getTraderCount();
				
				if(Miner4Num > 0){
					giftPrice = 0.16;
					flag = 4;
					
					if(cpaCount > 500){
						return JsonResult.build(500, "您4星矿机不能被回收大于500个cpa");
					}
				}else if(Miner3Num > 0 && quan3Num >= 3){
					giftPrice = 0.16;
					flag = 3;
					
					if(cpaCount > Miner3Num * 45){
						return JsonResult.build(500, "您"+Miner3Num+"台3星矿机不能被回收大于"+ (Miner3Num * 45) +"个cpa");
					}
				}else if(Miner2Num > 0 && quan2Num >= 6){
					giftPrice = 0.16;
					flag = 2;
					
					if(cpaCount > Miner2Num * 5){
						return JsonResult.build(500, "您"+Miner2Num+"台2星矿机不能被回收大于"+ (Miner2Num * 5) +"个cpa");
					}
				}
				
				BigDecimal b1 = BigDecimal.valueOf(zgPrice);
		        BigDecimal b2 = BigDecimal.valueOf(giftPrice);
		        
		        
		        double finalzgPrice =  b1.add(b2).doubleValue();
				if (price > finalzgPrice) {
					return JsonResult.build(500, "最高单价：" + zgPrice + "美元");
				}
				if (price < zdPrice) {
					return JsonResult.build(500, "今日最低单价：" + zdPrice + "美元");
				}

				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					System.exit(0);// 退出程序
				}

				if (type == 2) {
					
					int uid = fensTransaction.getTraderId();
					
					List<FensMiner> fmList = fensMinerMapper.selectAMiner(uid);
					
					if(fmList.size() < 1){
						return JsonResult.build(500, "为保障诚信会员利益，阻止羊毛党、死粉扰乱市场，卖cpa需要手持至少1台存活矿机！");
					}
					
					FensUser fu = new FensUser();
					fu = fensUserMapper.selectByPrimaryKey(uid);
					FensWallet fw = new FensWallet();
					fw = fensWalletMapper.selectByFens(uid);

					// if(fw.getAbleCpa() >=
					// (fensTransaction.getTraderCount()/0.8)){
					if (isCPAEnough(uid, 0, fensTransaction.getTraderCount())) {
						fensTransaction.setCreateDate(TimeUtil.getTimeStamp());
						IdWorker idWorker = new IdWorker(0, 0);
						fensTransaction.setOrderNumber(idWorker.nextId() + "");

						fensTransaction.setTraderState(4);// 卖单需要审核后才能挂单

						if(flag == 0){
							int result = fensTransactionMapper.insertSelective(fensTransaction);
							fensTransaction.setId(fensTransaction.getId());
							if (result == 1) {
								return JsonResult.ok(fensTransaction);
							}
						}else{
							int minerResult = 0;
							FensMiner fm = new FensMiner();
							fm.setFensUserId(uid);
							fm.setBeyong2("yes");
							
							if(flag == 4){
								// 标记当前4星矿机被回收过了cpa
								fm.setBak1("4");
								minerResult = fensMinerMapper.updateByFensId(fm);
							}else if(flag == 3){
								// 标记当前3星矿机被回收过了cpa
								fm.setBak1("3");
								minerResult = fensMinerMapper.updateByFensId(fm);
							}else if(flag == 2){
								// 标记当前2星矿机被回收过了cpa
								fm.setBak1("2");
								minerResult = fensMinerMapper.updateByFensId(fm);
							}
							
							if(minerResult > 0){
								int result = fensTransactionMapper.insertSelective(fensTransaction);
								fensTransaction.setId(fensTransaction.getId());
								if (result == 1) {
									return JsonResult.ok(fensTransaction);
								}
							}else{
								return JsonResult.build(500, "新增失败");
							}
						}
						return JsonResult.build(500, "新增失败");
					} else {
						return JsonResult.build(500, "钱包余额不足。或者你作为卖家有交易挂单，核算后钱包CPA余额不足，不能再次挂卖单。");
					}
				} else if (type == 1) {
					fensTransaction.setCreateDate(TimeUtil.getTimeStamp());
					IdWorker idWorker = new IdWorker(0, 0);
					fensTransaction.setOrderNumber(idWorker.nextId() + "");
					fensTransaction.setTraderState(0);
					int result = fensTransactionMapper.insertSelective(fensTransaction);
					fensTransaction.setId(fensTransaction.getId());
					if (result == 1) {
						return JsonResult.ok(fensTransaction);
					}
					return JsonResult.build(500, "新增失败");
				} else {
					return JsonResult.build(500, "新增失败");
				}
			} else {
				return JsonResult.build(500, "新增失败");
			}
		} else {
			return JsonResult.build(500, "新增失败");
		}
	}

	// 粉丝记录修改
	@Override
	public JsonResult updateRecord(FensTransaction fensTransaction) {

		try {
			Thread.sleep(500);
		} catch (Exception e) {
			System.exit(0);// 退出程序
		}

		if (fensTransaction.getAttachment() == null) {
			return JsonResult.build(500, "交易失败，请选择其他订单。");
		} else {
			int fuid = Integer.parseInt(fensTransaction.getAttachment());
			FensUser fus = fensUserMapper.selectByPrimaryKey(fuid);

			if (fus == null) {
				return JsonResult.build(500, "交易失败，请选择其他订单。");
			}
		}

		if (fensTransaction.getTraderId() != null) {
			int fuid = fensTransaction.getTraderId();
			FensUser fus = fensUserMapper.selectByPrimaryKey(fuid);

			if (fus == null) {
				return JsonResult.build(500, "交易失败，请选择其他订单。");
			}
		}

		if (fensTransaction.getFensUserId() != null) {
			int fuid = fensTransaction.getFensUserId();
			FensUser fus = fensUserMapper.selectByPrimaryKey(fuid);

			if (fus == null) {
				return JsonResult.build(500, "交易失败，请选择其他订单。");
			}

		}

		int fuid = Integer.parseInt(fensTransaction.getAttachment());

		FensTransaction ft = new FensTransaction();
		ft = fensTransactionMapper.selectByPrimaryKey(fensTransaction.getId());

		if (fuid != ft.getTraderId()) {
			return JsonResult.build(500, "交易失败，请选择其他订单。");
		}

		// 撤单流程
		if (fensTransaction.getIsDelete() != null) {
			if (fensTransaction.getIsDelete() == 1) {
				ft.setIsDelete(1);

				int result = fensTransactionMapper.updateByPrimaryKeySelective(ft);
				if (result == 1) {
					if (ft.getTraderType() == 1) {
						return JsonResult.build(200, "撤销订单成功。");
					}
					return JsonResult.ok();
				} else {
					return JsonResult.build(500, "交易失败，请检查网络服务。");
				}
			} else {
				return JsonResult.build(500, "交易失败，请检查网络服务。");
			}
		}
		return JsonResult.build(500, "交易失败，请选择其他订单。");
	}

	// 粉丝记录修改
	@Override
	public JsonResult updateRecordCJ(FensTransaction fensTransaction) {

		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);

		if (!(hour >= ConfigUtil.CPA_JY_START_TIME && hour < ConfigUtil.CPA_JY_END_TIME)) {
			return JsonResult.build(500, ConfigUtil.CPA_JY_NOTICE);
		}

		System.out.println(fensTransaction.getTraderType());
		int cbrID = 0, sbrID = 0;

		FensTransaction fensTransaction2 = fensTransactionMapper.selectByPrimaryKey(fensTransaction.getId());

		if(fensTransaction2.getTraderId().equals(fensTransaction.getFensUserId())){
			return JsonResult.build(500, "不能自己交易自己的订单");
		}

		FensUser fensUser = fensUserMapper.selectByPrimaryKey(fensTransaction2.getFensUserId());
		FensUser fensUser2 = fensUserMapper.selectByPrimaryKey(fensTransaction2.getTraderId());
		// 判断是否存在该接单人
		if (fensUser == null || fensUser2 == null) {
			return JsonResult.build(500, "交易失败，不存在此人");
		}
		if (fensTransaction2.getTraderState() == 2) {

			if (fensTransaction2.getFensUserId().equals(fensTransaction.getFensUserId())
					&& fensTransaction2.getTraderId().equals(fensTransaction.getTraderId())
					&& fensTransaction2.getTraderType().equals(fensTransaction.getTraderType())) {

				if (fensTransaction2.getTraderType() == 1) {// 买单
					cbrID = fensTransaction2.getFensUserId();
					sbrID = fensTransaction2.getTraderId();
				} else if (fensTransaction2.getTraderType() == 2) {// 卖单
					cbrID = fensTransaction2.getTraderId();
					sbrID = fensTransaction2.getFensUserId();
				}

				FensWallet tradeWallet = new FensWallet();// 出账钱包
				FensWallet fensWallet = new FensWallet();// 入账钱包

				tradeWallet = fensWalletMapper.selectAll(cbrID);
				fensWallet = fensWalletMapper.selectAll(sbrID);
				
				if (isCPAEnough(cbrID, fensTransaction2.getId(), fensTransaction2.getTraderCount())) {

					if (tradeWallet.getAbleCpa() >= (fensTransaction2.getTraderCount() * 1.2)) {
						FensWallet tradeWallet2 = new FensWallet();
						FensWallet fensWallet2 = new FensWallet();
	
						tradeWallet2.setId(tradeWallet.getId());
						tradeWallet2.setAbleCpa(tradeWallet.getAbleCpa() - (fensTransaction2.getTraderCount() * 1.2));
						tradeWallet2.setCpaCount(tradeWallet.getCpaCount() - (fensTransaction2.getTraderCount() * 1.2));
	
						fensWallet2.setId(fensWallet.getId());
						fensWallet2.setAbleCpa(fensWallet.getAbleCpa() + fensTransaction2.getTraderCount());
						fensWallet2.setCpaCount(fensWallet.getCpaCount() + fensTransaction2.getTraderCount());
	
						int result1 = fensWalletMapper.updateByPrimaryKeySelective(tradeWallet2);
						int result2 = fensWalletMapper.updateByPrimaryKeySelective(fensWallet2);
	
						if (result1 == 1 && result2 == 1) {
							fensTransaction2.setTraderState(3);
							int result = fensTransactionMapper.updateByPrimaryKeySelective(fensTransaction2);
							return JsonResult.ok();
						}
					} else {
						return JsonResult.build(500, "卖方钱包账号CPA余额不足，无法完成交易！");
					}
				}

				return JsonResult.build(500, "更新失败");
			} else {
				return JsonResult.build(500, "操作非法,更新失败");
			}
		} else {
			return JsonResult.build(500, "操作非法,更新失败");
		}
	}

	public boolean isCPAEnough(Integer uid, Integer id, double count) {
		FensWallet fw = fensWalletMapper.selectByFens(uid);
		Map allBlockCPA = fensTransactionMapper.getAllBlockCPA(uid, id);

		double cpa = 0;
		System.out.println("------alllockcpa::::" + allBlockCPA);
		if (allBlockCPA != null) {
			cpa = (double) allBlockCPA.get("sum");
		}

		System.out.println("------alllockcpa::::" + allBlockCPA + "------cpa:" + cpa + "-------count:" + count
				+ "-----ablecpa:" + fw.getAbleCpa());

		if ((fw.getAbleCpa() - cpa * 1.2) >= count * 1.2) {
			return true;
		}

		return false;
	}
}
