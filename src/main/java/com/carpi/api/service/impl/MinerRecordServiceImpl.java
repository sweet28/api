package com.carpi.api.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.JsonResult;
import com.arttraining.commons.util.TimeUtil;
import com.carpi.api.dao.AminerMapper;
import com.carpi.api.dao.BminerMapper;
import com.carpi.api.dao.FensMinerMapper;
import com.carpi.api.dao.FensTransactionMapper;
import com.carpi.api.dao.FensUserMapper;
import com.carpi.api.dao.FensWalletMapper;
import com.carpi.api.pojo.Aminer;
import com.carpi.api.pojo.Bminer;
import com.carpi.api.pojo.FensMiner;
import com.carpi.api.pojo.FensUser;
import com.carpi.api.pojo.FensWallet;
import com.carpi.api.service.MinerRecordService;

@Service
public class MinerRecordServiceImpl implements MinerRecordService {
	
	@Autowired
	private FensTransactionMapper fensTransactionMapper;
	@Autowired
	private FensUserMapper fensUserMapper;
	@Autowired
	private FensWalletMapper fensWalletMapper;
	@Autowired
	private AminerMapper aminerMapper;
	@Autowired
	private BminerMapper bminerMapper;

	@Autowired
	private FensMinerMapper fensMinerMapper;

	// 购买a矿机
	public JsonResult buy(Aminer aminer) {
		
		// 查询需要购买的矿机信息（根据类型1星，2星等）
		Aminer aminer2 = aminerMapper.selectType(aminer.getType());
		// 粉丝购买矿机后粉丝钱包的剩余的可用cpa余额
		// 先查询粉丝钱包的可用余额(根据粉丝id)
		/*
		 * List<FensWallet> list = fensWalletMapper.selectAll(aminer.getFensUserId());
		 * FensWallet fensWallet = list.get(0);
		 */
		FensWallet fensWallet = fensWalletMapper.selectAll(aminer.getFensUserId());
		// 如果粉丝购买矿机的数量乘以单价大于可用余额
		// 购买矿机所花的总cpa
		
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			System.exit(0);// 退出程序
		}
		Double money = aminer2.getPrice();//aminer.getCount() * aminer2.getPrice();
//		if (money > fensWallet.getAbleCpa()) {
		if(!isCPAEnough(aminer.getFensUserId(),0,money)){
			return JsonResult.build(500, "余额不足");
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
		fensMiner.setCreateDate(TimeUtil.getTimeStamp());
		fensMiner.setIsDelete(2);//购买矿机需要审核cpa合法性
		int result2 = fensMinerMapper.insertSelective(fensMiner);
		if (result2 != 1) {
			return JsonResult.build(500, "购买矿机失败");
		}
		return JsonResult.ok();
	}

	@Override
	public JsonResult buyAMiner(Aminer aminer) {
		Date date = new Date();
		date.getHours();

		if (date.getHours() < ConfigUtil.CPA_JY_START_TIME || date.getHours() > ConfigUtil.CPA_JY_END_TIME) {
			return JsonResult.build(500, "每天开放交易时间为：11:00至18:00.");
		}
		
		if( aminer.getType() == null){
			return JsonResult.build(500, "交易失败。");
		}
		
		aminer.setCount((long) 1);
		
		if(aminer.getFensUserId()==null){
			return JsonResult.build(500, "交易失败。");
		}else{
			int fuid = aminer.getFensUserId();
			FensUser fus = fensUserMapper.selectByPrimaryKey(fuid);
			
			if(fus==null){
				return JsonResult.build(500, "交易失败。");
			}
		}
		
		FensMiner fm1 = new FensMiner();
		fm1.setFensUserId(aminer.getFensUserId());
		fm1.setMinerType(1);
		fm1.setIsDelete(0);
		
		FensMiner fm2 = new FensMiner();
		fm2.setFensUserId(aminer.getFensUserId());
		fm2.setMinerType(2);
		

		int acount = fensMinerMapper.selectUserMiner(fm1);
		int bcount = fensMinerMapper.selectUserMiner(fm2);
		
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			System.exit(0);// 退出程序
		}
		
//		if(bcount <= 0){
			if(acount < 12){
				
				// 粉丝矿机总数不超过12台
				//根据粉丝id查询A矿机总数
				int sum = fensMinerMapper.selectASum(aminer.getFensUserId());
				if (sum >= 12) {
					return JsonResult.build(500, "每个用户最多购买12台矿机");
				}
				//根据粉丝id，矿机类型查询A矿机总数
				int sum1 = fensMinerMapper.selectSum(String.valueOf(aminer.getType()),aminer.getFensUserId());
				// CA1最多购买12台
				if (aminer.getType() == 1) {
					if (sum1 >= 12) {
						return JsonResult.build(500, "每个用户最多购买CA1型矿机12台");
					}
					// 还能购买几台ca1矿机
					double ca1 = 12 - sum;
					if (aminer.getCount() > ca1) {
						return JsonResult.build(500, "您最多能购买CA1型矿机" + ca1 + "台");
					}
					return buy(aminer);
				} else if (aminer.getType() == 2) { // CA2最多购买6台
					if (sum1 >= 6) {
						return JsonResult.build(500, "每个用户最多购买CA2型矿机6台");
					}
					if ((sum1 + 1) > 6 && (sum + aminer.getCount()) > 12) {
						return JsonResult.build(500, "您最多能购买CA2型矿机" + (12 - (sum + aminer.getCount())) + "台");
					}
					return buy(aminer);
		//			if ((sum + aminer.getCount()) > 12) {
		//				return JsonResult.build(500, "您最多能购买CA2型矿机" + (12 - (sum + aminer.getCount())) + "台");
		//			}
				} else if (aminer.getType() == 3) { // CA3最多购买3台
					if (sum1 >= 3) {
						return JsonResult.build(500, "每个用户最多购买CA3型矿机3台");
					}
					if ((aminer.getCount() + sum1) > 3 && (sum + aminer.getCount()) > 12) {
						return JsonResult.build(500, "您最多能购买CA2型矿机" + (12 - (sum + aminer.getCount())) + "台");
					}
					return buy(aminer);
		//			if ((sum + aminer.getCount()) > 12) {
		//				return JsonResult.build(500, "您最多能购买CA2型矿机" + (12 - (sum + aminer.getCount())) + "台");
		//			}
				} else if (aminer.getType() == 4) { // CA4最多购买1台
					if (sum1 >= 1) {
						return JsonResult.build(500, "每个用户最多购买CA4型矿机1台");
					}
					return buy(aminer);
				}
				return JsonResult.ok();
			}else{
				return JsonResult.build(500, "A矿机已满12台，不能继续购买。");
			}
//		}else{
//			return JsonResult.build(500, "B矿机在运行，不能购买A矿机。");
//		}
	}

	// 购买b矿机
	@Override
	public JsonResult buyBMiner(Bminer bminer) {
		
		bminer.setCount((long) 1);
		
		Date date = new Date();
		date.getHours();

		if (date.getHours() < ConfigUtil.CPA_JY_START_TIME || date.getHours() > ConfigUtil.CPA_JY_END_TIME) {
			return JsonResult.build(500, "每天开放交易时间为：11:00至18:00.");
		}
		
		if(bminer.getFensUserId()==null){
			return JsonResult.build(500, "交易失败。");
		}else{
			int fuid = bminer.getFensUserId();
			FensUser fus = fensUserMapper.selectByPrimaryKey(fuid);
			
			if(fus==null){
				return JsonResult.build(500, "交易失败。");
			}
		}
		
		if( bminer.getType() == null){
			return JsonResult.build(500, "交易失败。");
		}
		
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			System.exit(0);// 退出程序
		}
		
		// 查询需要购买的矿机信息（根据类型1星，2星等）
		Bminer bminer2 = bminerMapper.selectType(bminer.getType());
		// 粉丝购买矿机后粉丝钱包的剩余的可用cpa余额
		// 先查询粉丝钱包的可用余额(根据粉丝id)
		/*
		 * List<FensWallet> list = fensWalletMapper.selectAll(bminer.getFensUserId());
		 * FensWallet fensWallet = list.get(0);
		 */
		FensWallet fensWallet = fensWalletMapper.selectAll(bminer.getFensUserId());
		// 如果粉丝购买矿机的数量乘以单价大于可用余额
		// 购买矿机所花的总cpa
		
		FensMiner fm1 = new FensMiner();
		fm1.setFensUserId(bminer.getFensUserId());
		fm1.setMinerType(1);
		fm1.setIsDelete(0);
		
		FensMiner fm2 = new FensMiner();
		fm2.setFensUserId(bminer.getFensUserId());
		fm2.setMinerType(2);
		

		int acount = fensMinerMapper.selectUserMiner(fm1);
		int bcount = fensMinerMapper.selectUserMiner(fm2);
		
//		if(acount <= 0){
			if(bcount < 10){
				Double money = bminer2.getPrice();//bminer.getCount() * bminer2.getPrice();
				//		if (money > fensWallet.getAbleCpa()) {
				if(!isCPAEnough(bminer.getFensUserId(),0,money)){
					return JsonResult.build(500, "余额不足");
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
				// B矿机
				fensMiner.setMinerType(2);
				fensMiner.setMinerId(bminer2.getId());
				fensMiner.setMinerComputingPower(bminer2.getComputingPower());
				fensMiner.setCreateDate(TimeUtil.getTimeStamp());
				fensMiner.setIsDelete(2);//购买矿机需要审核cpa合法性
				int result2 = fensMinerMapper.insertSelective(fensMiner);
				if (result2 != 1) {
					return JsonResult.build(500, "购买矿机失败");
				}
				return JsonResult.ok();
			}else{
				return JsonResult.build(500, "B矿机已经买过10次，不能继续购买。");
			}
//		}else{
//			return JsonResult.build(500, "A矿机在运行，不能购买B矿机。");
//		}
		
	}

	public boolean isCPAEnough(Integer uid, Integer id, double count){
		FensWallet fw = fensWalletMapper.selectByFens(uid);
		Map allBlockCPA =  fensTransactionMapper.getAllBlockCPA(uid,id);
		double cpa = 0;
		System.out.println("------alllockcpa::::"+allBlockCPA);
		
//		double minerCount = 0;
//		
//		List<FensMiner> fmList = fensMinerMapper.selectEranMiner(uid);
//		if(fmList != null){
//			if(fmList.size() > 0){
//				
//			}
//		}
		
		if(allBlockCPA != null){
			cpa = (double) allBlockCPA.get("sum");
		}

		System.out.println("------alllockcpa::::"+allBlockCPA+"------cpa:"+cpa+"-------count:"+count+"-----ablecpa:"+fw.getAbleCpa()+"---panduan:::"+((fw.getAbleCpa()-cpa*1.25) >= count));
		
		System.out.println("---cha:"+(fw.getAbleCpa()-cpa*1.25));
		
		if((fw.getAbleCpa()-cpa*1.25) >= count){
			return true;
		}
		
		return false;
	}
}
