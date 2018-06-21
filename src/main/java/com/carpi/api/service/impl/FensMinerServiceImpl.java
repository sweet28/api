package com.carpi.api.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.arttraining.commons.util.JsonResult;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;
import com.carpi.api.dao.BankCardMapper;
import com.carpi.api.dao.FensComputingPowerMapper;
import com.carpi.api.dao.FensMinerMapper;
import com.carpi.api.dao.FensUserMapper;
import com.carpi.api.dao.FensWalletMapper;
import com.carpi.api.dao.FoneyRecordMapper;
import com.carpi.api.pojo.BankCard;
import com.carpi.api.pojo.FensComputingPower;
import com.carpi.api.pojo.FensMiner;
import com.carpi.api.pojo.FensUser;
import com.carpi.api.pojo.FensWallet;
import com.carpi.api.pojo.FoneyRecord;
import com.carpi.api.service.FensMinerService;
import com.carpi.api.service.FensWalletService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class FensMinerServiceImpl implements FensMinerService {

	@Autowired
	private FensMinerMapper fensMinerMapper;
	@Autowired
	private FensWalletMapper fensWalletMapper;
	@Autowired
	private FensWalletService fensWalletService;
	@Autowired
	private FensUserMapper fensUserMapper;
	@Autowired
	private BankCardMapper bankCardMapper;
	@Autowired
	private FoneyRecordMapper foneyRecordMapper;
	
	@Autowired
	private FensComputingPowerMapper fensComputingPowerMapper;

	// 根据粉丝id查询矿机
	@Override
	public JsonResult selectMinner(Integer fensUserId) {
		List<FensMiner> list = fensMinerMapper.selectMiner(fensUserId);
		if (list.size() > 0) {
			return JsonResult.ok(list);
		}
		return JsonResult.build(500, "无数据");
	}

	// 根据粉丝id查询A矿机
	@Override
	public PageInfo<FensMiner> selectAMinner(Integer page, Integer row, Integer fensUserId) {
		PageHelper.startPage(page, row);
		List<FensMiner> list = fensMinerMapper.selectAMiner(fensUserId);
		PageInfo<FensMiner> pageInfo = new PageInfo<FensMiner>(list);
		return pageInfo;
	}

	// 根据粉丝id查询A矿机矿池
	@Override
	public PageInfo<FensMiner> selectAMinnerKC(Integer page, Integer row, Integer fensUserId) {
		PageHelper.startPage(page, row);
		List<FensMiner> list = fensMinerMapper.selectAMinerKC(fensUserId);
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

	// 根据粉丝id查询B矿机矿池
	@Override
	public PageInfo<FensMiner> selectBMinnerKC(Integer page, Integer row, Integer fensUserId) {
		PageHelper.startPage(page, row);
		List<FensMiner> list = fensMinerMapper.selectBMinerKC(fensUserId);
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

	// 解冻矿机收益
	@Override
	public JsonResult thawABMiner(FensMiner miner1) {
		// 根据粉丝Id查询该粉丝对应的矿池信息
		FensMiner miner = fensMinerMapper.selectByPrimaryKey(miner1.getId());

		FensUser fensUser = fensUserMapper.selectByPrimaryKey(miner.getFensUserId());
		// 判断是否存在该接单人
		if (fensUser == null) {
			return JsonResult.build(500, "交易失败，不存在此人");
		}

		// 查询身份证是否认证
		if (Integer.valueOf(fensUser.getAttachment()) != 1) {
			return JsonResult.build(500, "身份证未认证");
		}
		// 银行卡
		List<BankCard> list = bankCardMapper.selectAll(miner.getFensUserId());
		if (list.size() <= 0) {
			return JsonResult.build(500, "请绑定银行卡");
		}

		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (miner != null) {
			//当前时间
			Date dd = TimeUtil.getTimeStamp();
			//最近一次获取收益时间
			String sqDT = miner.getAttachment();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 获取String类型的时间
			if (sqDT.isEmpty()) {
				sqDT = sdf.format(miner.getCreateDate());
			}

			//格式化最近一次获取收益时间
			Date sqDD = TimeUtil.strToDateDayByFormat(sqDT);

			System.out.println("sqDT:::" + sqDT + "-------sqDD::" + sqDD.toString() + "--sqDT.gettime:::"
					+ sqDD.getTime() + "------dd2string:::" + dd.toString() + "------dd.getTIme:" + dd.getTime());

			/*
			 * 比对当前时间与最近一次获取收益时间，获取时间差值，
			 * 用来计算矿机上次收取收益后至今的产值
			 */
			long a = TimeUtil.isOverTime(dd, sqDD);
			System.out.println("-------chazhi::::" + a);
			double b = a / (60 * 60 * 24);
			System.out.println("-------chazhi::bbb:" + b);

			/*
			 * 若自上次提取后超过24小时，可进行提取收益
			 */
			if (b >= 1) {
				double rundate = dd.getTime() - miner.getCreateDate().getTime();//矿机总的运行时间
				rundate = rundate / (1000 * 60 * 60 * 24);//转化成天数
				
				/**
				 * 用于判定不同类型的矿机
				 */
				double realRunday = rundate;
				int minerType = 0;
				int minerGrade = 0;

				minerType = miner.getMinerType();
				minerGrade = Integer.parseInt(miner.getBak1());
				
				/*
				 * 总运行时间若超过15天生命周期则设定为15
				 */
				if (rundate >= 15) {
					rundate = 15;
				}

				System.out.println(rundate + "---" + rundate + "------");

				double nowZSY;//当前总的产值
				double syyz = 0;//矿机的总产量  盐值::::::::后面需要叠加上叠加算力的收入<----这句很重要
				double beishu = 1;//提取收益的倍数比例，默认可全部提取收益

				/*
				 * AB两个类型库矿机总产量及提取倍数比例不同。
				 * CA1型矿机不限制，可完全提取
				 * B型矿机统一是0.2.
				 */
				if (miner.getMinerType() == 1) {
					if (Integer.parseInt(miner.getBak1()) == 1) {
						syyz = 11;
					} else if (Integer.parseInt(miner.getBak1()) == 2) {
						syyz = 115;
						beishu = 0.2;
					} else if (Integer.parseInt(miner.getBak1()) == 3) {
						syyz = 1150;
						beishu = 0.1;
					} else if (Integer.parseInt(miner.getBak1()) == 4) {
						syyz = 6000;
						beishu = 0.05;
					}
				} else if (miner.getMinerType() == 2) {

					beishu = 0.2;
					if (Integer.parseInt(miner.getBak1()) == 1) {
						syyz = 5.5;
					} else if (Integer.parseInt(miner.getBak1()) == 2) {
						syyz = 55;
					} else if (Integer.parseInt(miner.getBak1()) == 3) {
						syyz = 550;
					}
				}
				
				/*
				 * 叠加收益相关
				 */
				Double djyz = 0.00;// 叠加产值  盐值
				Double djSL = 0.00;// 叠加算力
				System.out.println(")))))))))))叠加))))))))):"+miner.getDiejia());
				if (miner.getDiejia() != null) {
					djSL = Double.valueOf(miner.getDiejia());
				}

				if (miner.getMinerComputingPower() > 0) {
					djyz = (djSL / miner.getMinerComputingPower()) * syyz;
				}
				
				/**
				 * 这里修改的很重要,将矿机的产值提升到叠加之后的
				 */
				syyz += djyz;
				
				
				/*
				 * 矿机本身收益+叠加收益之后的收益
				 */
				// 运行至当前产生的价值
				nowZSY = rundate * (syyz / 15);
				Double yhdSY, kySY;//已提取的收益与本身算力下可提取的收益

				// 已经提取的收益
				yhdSY = miner.getTotalRevenue();
				
				
				/*
				 * 判断当矿机运行周期是否结束，来觉得提取倍数比例的数值，影响提币----需要考虑叠加算力的部分。
				 * 1、未结束：按正常提取比例提取
				 * 2、已结束，且可提取大于等于1：按正常提取比例走
				 * 3、已结束，且可提取小于1：一次性提取完，不考虑倍数比例
				 */
				if (rundate >= 15) {
					if ((nowZSY - yhdSY) < 1) {
						beishu = 1;
					}
					
					/*
					 * ca2/3/4  cb按照20天、25天、35天取币结束分别计算
					 */
					if(minerType == 1){
						if(minerGrade == 2){
							if(realRunday >= 20){
								beishu = 1;
							}
						}
						if(minerGrade == 3){
							if(realRunday >= 25){
								beishu = 1;
							}
						}
						if(minerGrade == 4){
							if(realRunday >= 35){
								beishu = 1;
							}
						}
						
					}
					
					if(minerType == 2){
						if(realRunday >= 20){
							beishu = 1;
						}
						
					}
				} 
				
				// 可提取收益（不带倍数比例的）
				kySY = (nowZSY - yhdSY);

				System.out.println("nowzsy:" + nowZSY + "---yhdSY:" + yhdSY);

				/*
				 * 若在矿机生命周期内，可提取收益小于1，则不能提取收益
				 */
				if (kySY < 1 && rundate < 15) {
					return JsonResult.build(500, "收益少于1个CPA时，不能转入钱包。");
				}

				/*
				 * 解冻收益需要给领导人分红,同时更新矿机、钱包数据
				 */
				FensMiner fm = new FensMiner();
				fm.setTotalRevenue(kySY * beishu + yhdSY);//收益实际提取时需要乘以倍数比例
				fm.setId(miner.getId());
				fm.setFensUserId(miner.getFensUserId());
				fm.setAttachment(dd.toString());
				
				/*
				 * 若倍数为1（需要考虑CA1型矿机倍数一直是1的情况，所以加上收益判断<1）且运行周期结束，
				 * 说明该矿机本次提币结束后运行结束且提币结束，对矿机做好标记
				 */
				if (beishu == 1 && rundate >= 15 && (nowZSY - yhdSY) < 1) {
					fm.setIsDelete(30);// 30：标记运行结束且提币结束
				}
				
				if((kySY + yhdSY) == syyz && beishu == 1){
					fm.setIsDelete(30);// 30：标记运行结束且提币结束
				}

				int result = fensMinerMapper.updateByPrimaryKeySelective(fm);

				if (result != 1) {
					ServerLog.getLogger().warn("更新矿池失败，粉丝id：" + fm.getFensUserId());
				}

				FensWallet fensWallet = fensWalletMapper.selectAll(miner.getFensUserId());
				if (fensWallet == null) {
					return JsonResult.build(500, "钱包不存在");
				}

				// 钱包添加cpa，****记得乘以倍数*****
				Double ablecpa = fensWallet.getAbleCpa() + kySY * beishu;
				// 到账时间
				Date date2 = TimeUtil.getTimeStamp();
				FensWallet wallet2 = new FensWallet();
				// 钱包可用余额增加
				wallet2.setAbleCpa(ablecpa);
				wallet2.setCpaCount(fensWallet.getCpaCount() + kySY * beishu);
				wallet2.setId(fensWallet.getId());
				// 更新钱包可用cpa
				
				if(kySY > 0){
					int result2 = fensWalletMapper.updateByPrimaryKeySelective(wallet2);
					if (result2 != 1) {
						ServerLog.getLogger().warn("更新钱包可用失败，粉丝id：" + miner.getFensUserId());
					}
				}

				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				FensUser fuser = fensUserMapper.selectByPrimaryKey(miner.getFensUserId());
				/*
				 * 只有A型矿机给领导人分红,且叠加算力产出的CPA不计入分红
				 */
				if(miner.getMinerType() == 1) {
					if (fuser != null) {
						FensUser jsr = new FensUser();
						jsr.setPhone(fuser.getRefereePhone());
						FensUser jsrUser = fensUserMapper.selectRegister(jsr);
						if (jsrUser != null) {
							// 介绍人钱包
							FensWallet ldrWallet = fensWalletMapper.selectAll(jsrUser.getId());

							// 钱包添加cpa
							Double ablecpa2 = ldrWallet.getAbleCpa() + (kySY - (djSL / miner.getMinerComputingPower()) * syyz * rundate / 15) * beishu * 0.01;
							// 到账时间
							FensWallet ldrWallet2 = new FensWallet();
							// 钱包可用余额增加
							ldrWallet2.setAbleCpa(ablecpa2);
							ldrWallet2.setCpaCount(ldrWallet.getCpaCount() + (kySY - (djSL / miner.getMinerComputingPower()) * syyz * rundate / 15) * beishu * 0.01);
							ldrWallet2.setId(ldrWallet.getId());
							// 更新钱包可用cpa
							
							if((kySY - (djSL / miner.getMinerComputingPower()) * syyz * rundate / 15) > 0){
								int result3 = fensWalletMapper.updateByPrimaryKeySelective(ldrWallet2);
								if (result3 != 1) {
									ServerLog.getLogger().warn("更新钱包可用失败，粉丝id：" + miner.getFensUserId());
								}
							}
						}
					}
				}

				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// 钱包转账记录表添加一条记录
				FoneyRecord moneyRecord = new FoneyRecord();
				moneyRecord.setFensUserId(miner.getFensUserId());
				// 交易金额
				moneyRecord.setPayment(kySY * beishu);
				moneyRecord.setReceiveDate(date2);
				moneyRecord.setSendDate(date2);
				// 转账类型1 代表接收 2代表转出
				moneyRecord.setPaymentType(1);
				return fensWalletService.addWalletRecord(moneyRecord);
			} else {
				return JsonResult.build(500, "请明天再收取CPA收益。");
			}
		}
		return JsonResult.build(500, "不存在矿池信息");
	}

	// 解冻矿机收益(一键转入)
	@Override
	public JsonResult thawABMiner2(FensMiner miner1) {

		FensUser fensUser = fensUserMapper.selectByPrimaryKey(miner1.getFensUserId());
		// 判断是否存在该接单人
		if (fensUser == null) {
			return JsonResult.build(500, "交易失败，不存在此人");
		}

		// 查询身份证是否认证
		if (Integer.valueOf(fensUser.getAttachment()) != 1) {
			return JsonResult.build(500, "身份证未认证");
		}
		// 银行卡
		List<BankCard> list = bankCardMapper.selectAll(miner1.getFensUserId());
		if (list.size() <= 0) {
			return JsonResult.build(500, "请绑定银行卡");
		}

		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 查询运行中的矿机
		List<FensMiner> list2 = fensMinerMapper.selectMiner2(miner1.getFensUserId());
		for (FensMiner miner : list2) {
			if (!StringUtils.isEmpty(miner)) {
				//当前时间
				Date dd = TimeUtil.getTimeStamp();
				//最近一次获取收益时间
				String sqDT = miner.getAttachment();

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// 获取String类型的时间
				if (sqDT.isEmpty()) {
					sqDT = sdf.format(miner.getCreateDate());
				}

				//格式化最近一次获取收益时间
				Date sqDD = TimeUtil.strToDateDayByFormat(sqDT);

				System.out.println("sqDT:::" + sqDT + "-------sqDD::" + sqDD.toString() + "--sqDT.gettime:::"
						+ sqDD.getTime() + "------dd2string:::" + dd.toString() + "------dd.getTIme:" + dd.getTime());

				/*
				 * 比对当前时间与最近一次获取收益时间，获取时间差值，
				 * 用来计算矿机上次收取收益后至今的产值
				 */
				long a = TimeUtil.isOverTime(dd, sqDD);
				System.out.println("-------chazhi::::" + a);
				double b = a / (60 * 60 * 24);
				System.out.println("-------chazhi::bbb:" + b);

				/*
				 * 若自上次提取后超过24小时，可进行提取收益
				 */
				if (b >= 1) {
					double rundate = dd.getTime() - miner.getCreateDate().getTime();//矿机总的运行时间
					rundate = rundate / (1000 * 60 * 60 * 24);//转化成天数

					/**
					 * 用于判定不同类型的矿机
					 */
					double realRunday = rundate;
					int minerType = 0;
					int minerGrade = 0;

					minerType = miner.getMinerType();
					minerGrade = Integer.parseInt(miner.getBak1());
					
					/*
					 * 总运行时间若超过15天生命周期则设定为15
					 */
					if (rundate >= 15) {
						rundate = 15;
					}

					System.out.println(rundate + "---" + rundate + "------");

					double nowZSY;//当前总的产值
					double syyz = 0;//矿机的总产量
					double beishu = 1;//提取收益的倍数比例，默认可全部提取收益

					/*
					 * AB两个类型库矿机总产量及提取倍数比例不同。
					 * CA1型矿机不限制，可完全提取
					 * B型矿机统一是0.2.
					 */
					if (miner.getMinerType() == 1) {
						if (Integer.parseInt(miner.getBak1()) == 1) {
							syyz = 11;
						} else if (Integer.parseInt(miner.getBak1()) == 2) {
							syyz = 115;
							beishu = 0.2;
						} else if (Integer.parseInt(miner.getBak1()) == 3) {
							syyz = 1150;
							beishu = 0.1;
						} else if (Integer.parseInt(miner.getBak1()) == 4) {
							syyz = 6000;
							beishu = 0.05;
						}
					} else if (miner.getMinerType() == 2) {

						beishu = 0.2;
						if (Integer.parseInt(miner.getBak1()) == 1) {
							syyz = 5.5;
						} else if (Integer.parseInt(miner.getBak1()) == 2) {
							syyz = 55;
						} else if (Integer.parseInt(miner.getBak1()) == 3) {
							syyz = 550;
						}
					}

					/*
					 * 叠加收益相关
					 */
					Double djyz = 0.00;// 叠加产值  盐值
					Double djSL = 0.00;// 叠加算力
					System.out.println(")))))))))))叠加))))))))):"+miner.getDiejia());
					if (miner.getDiejia() != null) {
						djSL = Double.valueOf(miner.getDiejia());
					}

					if (miner.getMinerComputingPower() > 0) {
						djyz = (djSL / miner.getMinerComputingPower()) * syyz;
					}
					
					/**
					 * 这里修改的很重要,将矿机的产值提升到叠加之后的
					 */
					syyz += djyz;
					
					
					/*
					 * 矿机本身收益+叠加收益之后的收益
					 */
					// 运行至当前产生的价值
					nowZSY = rundate * (syyz / 15);
					Double yhdSY, kySY;//已提取的收益与本身算力下可提取的收益

					// 已经提取的收益
					yhdSY = miner.getTotalRevenue();
					
					
					/*
					 * 判断当矿机运行周期是否结束，来觉得提取倍数比例的数值，影响提币----需要考虑叠加算力的部分。
					 * 1、未结束：按正常提取比例提取
					 * 2、已结束，且可提取大于等于1：按正常提取比例走
					 * 3、已结束，且可提取小于1：一次性提取完，不考虑倍数比例
					 */
					if (rundate >= 15) {
						if ((nowZSY - yhdSY) < 1) {
							beishu = 1;
						}
						
						/*
						 * ca2/3/4  cb按照20天、25天、35天取币结束分别计算
						 */
						if(minerType == 1){
							if(minerGrade == 2){
								if(realRunday >= 20){
									beishu = 1;
								}
							}
							if(minerGrade == 3){
								if(realRunday >= 25){
									beishu = 1;
								}
							}
							if(minerGrade == 4){
								if(realRunday >= 35){
									beishu = 1;
								}
							}
							
						}
						
						if(minerType == 2){
							if(realRunday >= 20){
								beishu = 1;
							}
							
						}
					} 
					
					
					// 可提取收益（不带倍数比例的）
					kySY = (nowZSY - yhdSY);

					System.out.println("nowzsy:" + nowZSY + "---yhdSY:" + yhdSY);

					/*
					 * 若在矿机生命周期内，可提取收益小于1，则不能提取收益
					 */
					if (kySY < 1 && rundate < 15) {
						continue;
					}

					/*
					 * 解冻收益需要给领导人分红,同时更新矿机、钱包数据
					 */
					FensMiner fm = new FensMiner();
					fm.setTotalRevenue(kySY * beishu + yhdSY);//收益实际提取时需要乘以倍数比例
					fm.setId(miner.getId());
					fm.setFensUserId(miner.getFensUserId());
					fm.setAttachment(dd.toString());
					
					/*
					 * 若倍数为1（需要考虑CA1型矿机倍数一直是1的情况，所以加上收益判断<1）且运行周期结束，
					 * 说明该矿机本次提币结束后运行结束且提币结束，对矿机做好标记
					 */
					if (beishu == 1 && rundate >= 15 && (nowZSY - yhdSY) < 1) {
						fm.setIsDelete(30);// 30：标记运行结束且提币结束
					}
					
					if((kySY + yhdSY) == syyz && beishu == 1){
						fm.setIsDelete(30);// 30：标记运行结束且提币结束
					}

					int result = fensMinerMapper.updateByPrimaryKeySelective(fm);

					if (result != 1) {
						ServerLog.getLogger().warn("更新矿池失败，粉丝id：" + fm.getFensUserId());
					}

					FensWallet fensWallet = fensWalletMapper.selectAll(miner.getFensUserId());
					if (fensWallet == null) {
						return JsonResult.build(500, "钱包不存在");
					}

					// 钱包添加cpa
					Double ablecpa = fensWallet.getAbleCpa() + kySY * beishu;
					// 到账时间
					Date date2 = TimeUtil.getTimeStamp();
					FensWallet wallet2 = new FensWallet();
					// 钱包可用余额增加
					wallet2.setAbleCpa(ablecpa);
					wallet2.setCpaCount(fensWallet.getCpaCount() + kySY * beishu);
					wallet2.setId(fensWallet.getId());
					// 更新钱包可用cpa
					if(kySY > 0){
						int result2 = fensWalletMapper.updateByPrimaryKeySelective(wallet2);
						if (result2 != 1) {
							ServerLog.getLogger().warn("更新钱包可用失败，粉丝id：" + miner.getFensUserId());
						}
					}

					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					FensUser fuser = fensUserMapper.selectByPrimaryKey(miner.getFensUserId());
					/*
					 * 只有A型矿机给领导人分红,且叠加算力产出的CPA不计入分红
					 */
					if(miner.getMinerType() == 1) {
						if (fuser != null) {
							FensUser jsr = new FensUser();
							jsr.setPhone(fuser.getRefereePhone());
							FensUser jsrUser = fensUserMapper.selectRegister(jsr);
							if (jsrUser != null) {
								// 介绍人钱包
								FensWallet ldrWallet = fensWalletMapper.selectAll(jsrUser.getId());

								// 钱包添加cpa
								Double ablecpa2 = ldrWallet.getAbleCpa() + (kySY - (djSL / miner.getMinerComputingPower()) * syyz * rundate / 15) * beishu * 0.01;
								// 到账时间
								FensWallet ldrWallet2 = new FensWallet();
								// 钱包可用余额增加
								ldrWallet2.setAbleCpa(ablecpa2);
								ldrWallet2.setCpaCount(ldrWallet.getCpaCount() + (kySY - (djSL / miner.getMinerComputingPower()) * syyz * rundate / 15) * beishu * 0.01);
								ldrWallet2.setId(ldrWallet.getId());
								// 更新钱包可用cpa
								if((kySY - (djSL / miner.getMinerComputingPower()) * syyz * rundate / 15) > 0){
									int result3 = fensWalletMapper.updateByPrimaryKeySelective(ldrWallet2);
									if (result3 != 1) {
										ServerLog.getLogger().warn("更新钱包可用失败，粉丝id：" + miner.getFensUserId());
									}
								}
							}
						}
					}

					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// 钱包转账记录表添加一条记录
					FoneyRecord moneyRecord = new FoneyRecord();
					moneyRecord.setFensUserId(miner.getFensUserId());
					// 交易金额
					moneyRecord.setPayment(kySY * beishu);
					moneyRecord.setReceiveDate(date2);
					moneyRecord.setSendDate(date2);
					// 转账类型1 代表接收 2代表转出
					moneyRecord.setPaymentType(1);
					// return fensWalletService.addWalletRecord(moneyRecord);
					int status = foneyRecordMapper.insertSelective(moneyRecord);
					if (status != 1) {
						ServerLog.getLogger().warn("转入钱包失败，粉丝id：" + miner.getFensUserId());
					}

				} else {
					continue;
				}
			}
		}
		return JsonResult.ok();
	}

	@Override
	public PageInfo<FensMiner> selectABMinnerKC(Integer page, Integer row, Integer fensUserId) {
		PageHelper.startPage(page, row);
		List<FensMiner> list = fensMinerMapper.selectABMinnerKC(fensUserId);
		PageInfo<FensMiner> pageInfo = new PageInfo<FensMiner>(list);
		return pageInfo;
	}

	// 转入运行池
	@Override
	public JsonResult zhuanyxc(Integer id, Integer fensUserId, String type) {
		// 查询正在运行的矿机数量

		if (type == null) {
			return JsonResult.build(500, "转入失败.");
		}

		if (fensUserId == null) {
			return JsonResult.build(500, "转入失败!");
		} else {
			FensUser fus = fensUserMapper.selectByPrimaryKey(fensUserId);
			if (fus == null) {
				return JsonResult.build(500, "转入失败!!");
			}
		}
		// 根据id查询出矿机信息
		// FensMiner fensMiner = fensMinerMapper.selectByPrimaryKey(fensUserId);

		// FensMiner fm2 = new FensMiner();
		// fm2.setFensUserId(fensUserId);
		// fm2.setMinerType(2);

		// a型矿机
		int sum = fensMinerMapper.selectASum(fensUserId);
		// int bcount = fensMinerMapper.selectUserMiner(fm2);

		// if (sum > 12 || bcount > 10) {
		// return JsonResult.build(500, "你的可运行矿机已满");
		// }

		int sum1 = fensMinerMapper.selectSum(type, fensUserId);
		if (type.equals("1")) {
			if (sum1 >= 12) {
				return JsonResult.build(500, "每个用户最多拥有CA1型矿机12台");
			}
			return update(id, fensUserId);

		} else if (type.equals("2")) { // CA2最多购买6台
			if (sum1 >= 6) {
				return JsonResult.build(500, "每个用户最多拥有CA2型矿机6台");
			}
			return update(id, fensUserId);

		} else if (type.equals("3")) { // CA3最多购买3台
			if (sum1 >= 3) {
				return JsonResult.build(500, "每个用户最多购买CA3型矿机3台");
			}
			return update(id, fensUserId);
		} else if (type.equals("4")) { // CA4最多购买1台
			if (sum1 >= 1) {
				return JsonResult.build(500, "每个用户最多购买CA4型矿机1台");
			}
			return update(id, fensUserId);
		}

		return JsonResult.build(500, "转入失败!!!");
	}

	public JsonResult update(Integer id, Integer fensUserId) {
		FensMiner fensMiner = new FensMiner();
		fensMiner.setId(id);
		fensMiner.setFensUserId(fensUserId);
		fensMiner.setIsDelete(0);
		fensMiner.setCreateDate(TimeUtil.getTimeStamp());
		fensMiner.setIsUserGoumai("1");

		int result = fensMinerMapper.updateyxc(fensMiner);
		if (result != 1) {
			return JsonResult.build(500, "转入失败");
		}
		return JsonResult.ok();

	}

	@Override
	public JsonResult shuaxinyxc(Integer fensUserId) {
//		return JsonResult.build(500, "直推赠送活动的库存矿机已经全部送完，活动已经结束，");
		
		FensUser fu = fensUserMapper.selectByPrimaryKey(fensUserId);

		if (fu == null) {
			return JsonResult.build(500, "无数据");
		}

		if (fu.getPhone().isEmpty()) {
			return JsonResult.build(500, "无数据");
		}

//		if (fu.getRefereePhone().isEmpty()) {
//			return JsonResult.build(500, "无数据");
//		}
		// //活动时间内 直推总人数
		Integer reNum = fensUserMapper.selectRefereeYXC(fu.getPhone());
		// //直推送出矿机总数
		int sjNum = reNum / 5;
		System.out.println(reNum + "<-re----sj->" + sjNum);
		// //已送矿机数
		int ysNum = 0;
		if (!fu.getCountryRegion().isEmpty()) {
			ysNum = Integer.parseInt(fu.getCountryRegion());
		}
		// //还应送矿机数
		int yscNum = sjNum - ysNum;
		//
		FensUser fu2 = new FensUser();
		fu2.setId(fu.getId());
		fu2.setCountryRegion(sjNum + "");
		int result2 = fensUserMapper.updateByPrimaryKeySelective(fu2);
		//
		for (int i = 0; i < yscNum; i++) {
			FensMiner fm = new FensMiner();
			fm.setBak1("1");
			fm.setMinerType(1);
			fm.setMinerId(1);
			fm.setFensUserId(fensUserId);
			fm.setMinerComputingPower(0.005);
			fm.setCreateDate(TimeUtil.getTimeStamp());
			fm.setIsDelete(10);
			//
			int result = fensMinerMapper.insertSelective(fm);

			if (result != 1) {
				return JsonResult.build(500, "无数据2");
			}
		}

		return JsonResult.ok();
	}

	// 粉丝算力和（亲友团算力和）
	@Override
	public JsonResult qinyoutuanHe(String phone) {
		Double sum = fensMinerMapper.selectSuanLiHe(phone);
		return JsonResult.ok(sum);
	}

	// 粉丝算力列表（或者收益列表）
	@Override
	public JsonResult suanLiList(String phone) {
		List<FensMiner> list = fensMinerMapper.suanLiList(phone);
		if (list.size() > 0) {
			return JsonResult.ok(list);
		}
		return JsonResult.build(500, "无数据");
	}

	// 收益列表
	@Override
	public JsonResult suanLiList2(String phone) {
		List<FensMiner> list = fensMinerMapper.suanLiList2(phone);
		if (list.size() > 0) {
			return JsonResult.ok(list);
		}
		return JsonResult.build(500, "无数据");
	}

	// 亲友团收益（矿机价格的1%）
	@Override
	public JsonResult shouYiHe(String phone) {
		Double sum = fensMinerMapper.shouYiHe(phone);
		return JsonResult.ok(sum);
	}

	// 算力叠加到矿机
	@Override
	public JsonResult kuanJiSuanLiHe(Double diejia, Integer fensUserId, Integer id, String phone) {
		
		Double sum = fensMinerMapper.selectSuanLiHe(phone) * 0.05;
		
		if(sum < 0 ){
			return JsonResult.build(500, "算力大于0才可叠加");
		}
		
		if(sum != diejia ){
			return JsonResult.build(500, "您的算力有变化，请刷新页面重新叠加");
		}
		
		FensMiner fensMiner2 = fensMinerMapper.selectByPrimaryKey(id);
		if (!fensMiner2.getFensUserId().equals(fensUserId)) {
			return JsonResult.build(500, "不是本人操作，请重新登入");
		}
		
		double addPower = diejia;
		if(fensMiner2.getDiejia()!=null){
			addPower += Double.valueOf(fensMiner2.getDiejia());
		}

		FensMiner fensMiner = new FensMiner();
		fensMiner.setId(id);
		fensMiner.setDiejia(String.valueOf(addPower));
		// fensMiner.setFensUserId(fensUserId);
		// fensMiner.setIsUseSuanli("1");//////波波你个坑，这个地方不能设置，设置是否使用了算力是设置到直推粉丝那里去，你个坑坑坑

		int result = fensMinerMapper.updateIsUseDIEJIA(phone);
		if (result == 1) {
			int result2 = fensMinerMapper.updateByPrimaryKeySelective(fensMiner);
			if (result2 != 1) {
				return JsonResult.build(500, "叠加算力失败");
			} else {
				return JsonResult.ok();
			}
		} else {
			return JsonResult.build(500, "叠加算力失败");
		}
	}
	
	@Override
	public JsonResult kjaddGP(Integer fensUserId, Integer powerType, String phone, Integer minerId) {
		
		Integer type = 0;
		
		if(powerType == 1){
			type = 41;
		}
		
		if(powerType == 2){
			type = 42;
		}
		
		if(powerType == 2){
			type = 42;
		}
		
		FensComputingPower fcp = fensComputingPowerMapper.selectFensGradePowerAble(fensUserId, type);
		if(fcp == null){
			return JsonResult.build(500, "无可用节点奖励算力叠加");
		}
		
		FensMiner fm = fensMinerMapper.selectByPrimaryKey(minerId);
		if (!fm.getFensUserId().equals(fensUserId)) {
			return JsonResult.build(500, "不是本人操作，请重新登入");
		}
		
		FensUser fensUser = fensUserMapper.selectByPrimaryKey(fm.getFensUserId());
		// 判断是否存在该接单人
		if (fensUser == null) {
			return JsonResult.build(500, "交易失败，不存在此人");
		}

		// 查询身份证是否认证
		if (Integer.valueOf(fensUser.getAttachment()) != 1) {
			return JsonResult.build(500, "身份证未认证");
		}
		// 银行卡
		List<BankCard> list = bankCardMapper.selectAll(fm.getFensUserId());
		if (list.size() <= 0) {
			return JsonResult.build(500, "请绑定银行卡");
		}
		
		double addPower = fcp.getComputingPower();
		if(fm.getDiejia()!=null){
			addPower += Double.valueOf(fm.getDiejia());
		}

		FensMiner fensMiner = new FensMiner();
		fensMiner.setId(fm.getId());
		fensMiner.setDiejia(String.valueOf(addPower));

		FensComputingPower fcp2 = new FensComputingPower();
		fcp2.setId(fcp.getId());
		fcp2.setBak1(1+"");
		fcp2.setDeleteDate(TimeUtil.getTimeStamp());
		
		int result = fensComputingPowerMapper.updateByPrimaryKeySelective(fcp2);
		if (result == 1) {
			int result2 = fensMinerMapper.updateByPrimaryKeySelective(fensMiner);
			if (result2 != 1) {
				return JsonResult.build(500, "叠加算力失败");
			} else {
				return JsonResult.ok();
			}
		} else {
			return JsonResult.build(500, "叠加算力失败");
		}
	}

	// 收益提取接口
	@Override
	public JsonResult syTiQu(Integer id, String phone, Integer fensUserId) {
		FensMiner fensMiner2 = fensMinerMapper.selectByPrimaryKey(id);
		if (fensMiner2.getFensUserId() != fensUserId) {
			return JsonResult.build(500, "不是本人操作，请重新登入");
		}
		// 查询矿机的总价值
		Double sum = fensMinerMapper.kjJz(phone);
		Double sum2 = sum * 0.01;
		FensWallet wallet = fensWalletMapper.selectByFens(fensUserId);

		FensWallet fensWallet = new FensWallet();
		fensWallet.setAbleCpa(sum2 + wallet.getAbleCpa());
		fensWallet.setId(wallet.getId());
		int result = fensWalletMapper.updateByPrimaryKeySelective(fensWallet);
		if (result != 1) {
			return JsonResult.build(500, "转入失败");
		}
		return JsonResult.ok();
	}

	// 查询矿机的算力（根据粉丝id）
	@Override
	public JsonResult geRen(Integer fensUserId) {
		Double sum = fensMinerMapper.sum(fensUserId);
		return JsonResult.ok(sum);
	}

	// 粉丝算力列表（个人）
	@Override
	public JsonResult geRenList(Integer fensUserId) {
		List<FensMiner> list = fensMinerMapper.geRenList(fensUserId);
		if (list.size() > 0) {
			return JsonResult.ok(list);
		}
		return JsonResult.build(500, "无数据");
	}

	// 直推粉丝信息（每个人的算力，直推人数）
	@Override
	public JsonResult selectFens(String phone) {
		List<FensUser> list = fensUserMapper.selectAllUser(phone);
		List<Map<String, Object>> list2 = new ArrayList<>();
		for (FensUser fensUser : list) {
			Map<String, Object> map = new HashMap<>();
			// 该粉丝下面的算力
			Double sum = fensMinerMapper.selectSuanLiHe(fensUser.getPhone());
			// 该粉丝下面的直推人数
			int reshu = fensUserMapper.selectAllSum(fensUser.getPhone());

			map.put("user", fensUser);
			map.put("suanli", sum);
			map.put("reshu", reshu);
			list2.add(map);
		}
		return JsonResult.ok(list2);
	}

}
