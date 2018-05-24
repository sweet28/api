package com.carpi.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arttraining.commons.util.IdWorker;
import com.arttraining.commons.util.JsonResult;
import com.arttraining.commons.util.OrderNumberUtil;
import com.arttraining.commons.util.TimeUtil;
import com.carpi.api.dao.AminerMapper;
import com.carpi.api.dao.AminerRecordMapper;
import com.carpi.api.dao.BminerMapper;
import com.carpi.api.dao.BminerRecordMapper;
import com.carpi.api.dao.FensTransactionMapper;
import com.carpi.api.dao.FensUserMapper;
import com.carpi.api.dao.FensWalletMapper;
import com.carpi.api.pojo.Aminer;
import com.carpi.api.pojo.AminerRecord;
import com.carpi.api.pojo.Bminer;
import com.carpi.api.pojo.BminerRecord;
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
	
	// 粉丝交易记录（可根据粉丝id查个人）
	@Override
	public PageInfo<FensTransaction> selectRecord(Integer page,Integer row,FensTransaction fensTransaction) {
		PageHelper.startPage(page, row);
		System.out.println("tradeID:"+fensTransaction.getTraderId()+"---page:"+page+"---row:"+row+"---type:"+fensTransaction.getTraderType()+"-----state:"+fensTransaction.getTraderState()+"---fensID:"+fensTransaction.getFensUserId());
		if((fensTransaction.getTraderType()==9)){
			fensTransaction.setTraderType(null);
		}else{
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
	public PageInfo<FensTransaction> selectCJRecord(Integer page,Integer row,FensTransaction fensTransaction) {
		
		PageHelper.startPage(page, row);
		System.out.println("tradeID:"+fensTransaction.getTraderId()+"--2222222-page:"+page+"---row:"+row+"---type:"+fensTransaction.getTraderType()+"-----state:"+fensTransaction.getTraderState()+"---fensID:"+fensTransaction.getFensUserId());
		if((fensTransaction.getTraderType()==9)){
			fensTransaction.setTraderType(null);
		}else{
			fensTransaction.setTraderId(null);
		}
		List<FensTransaction> list ;
		if(fensTransaction.getTraderType()==10){
			list = fensTransactionMapper.selectCJFensRecordByID(fensTransaction.getTraderId());
		}else{
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

	//粉丝记录增加
	@Override
	public JsonResult addRecord(FensTransaction fensTransaction) {
		
		int type = fensTransaction.getTraderType();
		double price = fensTransaction.getEntrustPrice();
		
		double zgPrice = 0.12;
		double zdPrice = 0.065;
		if(price > zgPrice){
			return JsonResult.build(500, "今日最高单价："+zgPrice+"美元");
		}
		if(price < zdPrice){
			return JsonResult.build(500, "今日最低单价："+zdPrice+"美元");
		}
		if(type == 2){
			int uid = fensTransaction.getTraderId();
//			FensUser fu = new FensUser();
//			fu = fensUserMapper.selectByPrimaryKey(uid);
			FensWallet fw = new FensWallet();
			fw = fensWalletMapper.selectByFens(uid);
			
			if(fw.getAbleCpa() >= (fensTransaction.getTraderCount()/0.8)){
				fensTransaction.setCreateDate(TimeUtil.getTimeStamp());
				IdWorker idWorker = new IdWorker(0, 0);
				fensTransaction.setOrderNumber(idWorker.nextId()+"");
				int result = fensTransactionMapper.insertSelective(fensTransaction);
				fensTransaction.setId(fensTransaction.getId());
				if (result == 1) {
					return JsonResult.ok(fensTransaction);
				}
				return JsonResult.build(500, "新增失败");
			}else{
				return JsonResult.build(500, "钱包CPA余额不足");
			}
		}else{
			fensTransaction.setCreateDate(TimeUtil.getTimeStamp());
			IdWorker idWorker = new IdWorker(0, 0);
			fensTransaction.setOrderNumber(idWorker.nextId()+"");
			int result = fensTransactionMapper.insertSelective(fensTransaction);
			fensTransaction.setId(fensTransaction.getId());
			if (result == 1) {
				return JsonResult.ok(fensTransaction);
			}
			return JsonResult.build(500, "新增失败");
		}
	}

	//粉丝记录修改
	@Override
	public JsonResult updateRecord(FensTransaction fensTransaction) {
		int result = fensTransactionMapper.updateByPrimaryKeySelective(fensTransaction);
		if (result == 1) {
			return JsonResult.ok();
		}
		return JsonResult.build(500, "更新失败");
	}
	
	//粉丝记录修改
	@Override
	public JsonResult updateRecordCJ(FensTransaction fensTransaction) {

//		int result = fensTransactionMapper.updateByPrimaryKeySelective(fensTransaction);
		
		System.out.println(fensTransaction.getTraderType());
		int cbrID = 0,sbrID = 0;
		
		if(fensTransaction.getTraderType()==1){//买单
			cbrID = fensTransaction.getFensUserId();
			sbrID = fensTransaction.getTraderId();
		}else if(fensTransaction.getTraderType()==2){//卖单
			cbrID = fensTransaction.getTraderId();
			sbrID = fensTransaction.getFensUserId();
		}
		
		System.out.println("------cbrID:"+cbrID+"--------sbrID:"+sbrID);
		
		FensTransaction fensTransaction2 = fensTransactionMapper.selectByPrimaryKey(fensTransaction.getId());
		
		FensWallet tradeWallet = new FensWallet();//出账钱包
		FensWallet fensWallet = new FensWallet();//入账钱包

		tradeWallet = fensWalletMapper.selectAll(cbrID);
		fensWallet = fensWalletMapper.selectAll(sbrID);

		if(tradeWallet.getAbleCpa()>=(fensTransaction2.getTraderCount()/0.8)){
			FensWallet tradeWallet2 = new FensWallet();
			FensWallet fensWallet2 = new FensWallet();
			
			tradeWallet2.setId(tradeWallet.getId());
			tradeWallet2.setAbleCpa(tradeWallet.getAbleCpa()-(fensTransaction2.getTraderCount()/0.8));
//			tradeWallet2.setFensUserId(fensTransaction2.getTraderId());
			tradeWallet2.setCpaCount(tradeWallet.getCpaCount()-(fensTransaction2.getTraderCount()/0.8));
			
			fensWallet2.setId(fensWallet.getId());
			fensWallet2.setAbleCpa(fensWallet.getAbleCpa()+fensTransaction2.getTraderCount());
//			fensWallet2.setFensUserId(fensTransaction2.getFensUserId());
			fensWallet2.setCpaCount(fensWallet.getCpaCount()+fensTransaction2.getTraderCount());
			

			int result1 = fensWalletMapper.updateByPrimaryKeySelective(tradeWallet2);
			int result2 = fensWalletMapper.updateByPrimaryKeySelective(fensWallet2);
			
			if (result1 == 1 && result2 == 1) {
				int result = fensTransactionMapper.updateByPrimaryKeySelective(fensTransaction);
				return JsonResult.ok();
			}
		}else{
			return JsonResult.build(500, "卖方钱包账号CPA余额不足，无法完成交易！");
		}
		
		
		return JsonResult.build(500, "更新失败");
	}

}
