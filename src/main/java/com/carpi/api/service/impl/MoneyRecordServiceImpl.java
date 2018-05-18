package com.carpi.api.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arttraining.commons.util.JsonResult;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;
import com.carpi.api.dao.FensWalletMapper;
import com.carpi.api.dao.FoneyRecordMapper;
import com.carpi.api.pojo.FensWallet;
import com.carpi.api.pojo.FoneyRecord;
import com.carpi.api.service.MoneyRecordService;

@Service
public class MoneyRecordServiceImpl implements MoneyRecordService {

	@Autowired
	private FoneyRecordMapper foneyRecordMapper;

	@Autowired
	private FensWalletMapper fensWalletMapper;

	// 粉丝转入转出cpa记录
	// 交易金额，钱包转入地址（收款人钱包地址）,付款人粉丝id
	@Override
	public JsonResult zhuan(FoneyRecord foneyRecord, FensWallet fensWalle) {
		// 转账人钱包扣除相应的cpa
		// 根据转入钱包的ID查询钱包信息（转出）
		// 先根据粉丝id（付款人）查询出付款人的钱包信息
		FensWallet fensWallet2 = fensWalletMapper.selectByFens(fensWalle.getFensUserId());
		if (fensWallet2.getAbleCpa() < foneyRecord.getPayment()) {
			return JsonResult.build(500, "余额不足");
		}
		FensWallet fensWalletChu = new FensWallet();
		fensWalletChu.setAbleCpa(fensWallet2.getAbleCpa() - foneyRecord.getPayment());
		fensWalletChu.setId(fensWallet2.getId());
		int result = fensWalletMapper.updateByPrimaryKeySelective(fensWalletChu);
		if (result != 1) {
			return JsonResult.build(500, "付款款失败");
		}
		// 转出时间
		Date dateChu = TimeUtil.getTimeStamp();

		// 根据转出钱包的ID查询钱包信息 (转入)
		// 先根据钱包地址查钱包信息
		FensWallet fensWallet = fensWalletMapper.selectAddress(fensWalle);
		FensWallet fensWalletRu = new FensWallet();
		fensWalletRu.setAbleCpa(fensWallet.getAbleCpa() + foneyRecord.getPayment());
		fensWalletRu.setId(fensWallet.getId());

		int status = fensWalletMapper.updateByPrimaryKeySelective(fensWalletRu);
		if (status != 1) {
			return JsonResult.build(500, "收款失败");
		}
		// 到账时间
		Date dateDao = TimeUtil.getTimeStamp();

		// 交易记录信息插入
		// 转入 1 代表接收 2代表转出
		// 转出记录信息的插入
		FoneyRecord recordChu = new FoneyRecord();
		//转出钱包的ID(付款钱包Id)
		recordChu.setBak2(String.valueOf(fensWallet2.getId()));
		recordChu.setCreateDate(TimeUtil.getTimeStamp());
		recordChu.setFensUserId(fensWalle.getFensUserId());
		recordChu.setReceiveAddress(fensWalle.getWalletAddress());
		recordChu.setSendAddress(fensWallet2.getWalletAddress());
		recordChu.setPayment(foneyRecord.getPayment());
		// 待定
		// recordChu.setPoundage(foneyRecord.getPayment()*0.2);
        recordChu.setReceiveDate(dateDao);
        recordChu.setSendDate(dateChu);
        recordChu.setPaymentType(2);
        int i = foneyRecordMapper.insertSelective(recordChu);
        if (i != 1) {
        	ServerLog.getLogger().warn("转出记录信息的插入失败，粉丝id为：" + fensWalle.getFensUserId());
		}
        
     // 转入记录信息的插入
        FoneyRecord recordRu = new FoneyRecord();
        //转入钱包的ID(收款钱包Id)
        recordChu.setBak1(String.valueOf(fensWallet.getId()));
		recordChu.setCreateDate(TimeUtil.getTimeStamp());
		recordChu.setFensUserId(fensWallet.getFensUserId());
		recordChu.setReceiveAddress(fensWalle.getWalletAddress());
		recordChu.setSendAddress(fensWallet2.getWalletAddress());
		recordChu.setPayment(foneyRecord.getPayment());
		// 待定
		// recordChu.setPoundage(foneyRecord.getPayment()*0.2);
        recordChu.setReceiveDate(dateDao);
        recordChu.setSendDate(dateChu);
        recordChu.setPaymentType(1);
        int j = foneyRecordMapper.insertSelective(recordChu);
        if (j != 1) {
        	ServerLog.getLogger().warn("转入记录信息的插入失败，粉丝id为：" + fensWallet.getFensUserId());
		}
		return JsonResult.ok();
	}

	//钱包详情
	@Override
	public JsonResult select(Integer fensUserId) {
		FensWallet fensWallet = fensWalletMapper.selectAll(fensUserId);
		if (fensWallet == null) {
			return JsonResult.build(500, "无钱包信息");
		}
		return JsonResult.ok(fensWallet);
	}

}
