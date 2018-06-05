package com.carpi.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.dao.FensMinerMapper;
import com.carpi.api.dao.QuanBaoLiRecordMapper;
import com.carpi.api.pojo.FensMiner;
import com.carpi.api.pojo.QuanBaoLiRecord;
import com.carpi.api.service.QblBuyService;

@Service
public class QblBuyServiceImpl implements QblBuyService {

	@Autowired
	private FensMinerMapper fensMinerDao;

	@Autowired
	private QuanBaoLiRecordMapper quanBaoLiRecordMapper;

	// 购买券宝理商品券(一型矿机)
	@Override
	public JsonResult buyqbl(QuanBaoLiRecord quanBaoLiRecord) {
		/*
		 * 两台一型可以购买一型券一张《就是两台一张券，4台两张卷》 一个二型可以购买二型券一张《1:1》 一个三型可以购买三型券一张《1:1》
		 */
		// 一型矿机数量
		int sum = fensMinerDao.selectSum("1", quanBaoLiRecord.getFensUserId());
		if (sum < 2) {
			return JsonResult.build(500, "您没有资格购买此券，请查看规则后再购买");
		}
		if (!check()) {
			return JsonResult.build(500, "您今天已经购买过券保理 ，请明天再来");
		}

		// 插入数据库
		int result = insert(quanBaoLiRecord);
		if (result != 1) {
			return JsonResult.build(500, "提交失败");
		}
		return JsonResult.ok();
	}

	// 购买券宝理商品券(二型矿机数量)
	@Override
	public JsonResult buyqbl2(QuanBaoLiRecord quanBaoLiRecord) {
		// 二型矿机数量
		int sum2 = fensMinerDao.selectSum("2", quanBaoLiRecord.getFensUserId());
		if (sum2 < 1) {
			return JsonResult.build(500, "您没有资格购买此券，请查看规则后再购买 ");
		}
		if (!check()) {
			return JsonResult.build(500, "您今天已经购买过券保理 ，请明天再来");
		}

		// 插入数据库
		int result = insert(quanBaoLiRecord);
		if (result != 1) {
			return JsonResult.build(500, "提交失败");
		}
		return JsonResult.ok();
	}

	// 购买券宝理商品券(三型矿机数量)
	@Override
	public JsonResult buyqbl3(QuanBaoLiRecord quanBaoLiRecord) {
		//三型矿机数量
		int sum3 = fensMinerDao.selectSum("3", quanBaoLiRecord.getFensUserId());
		if (sum3 < 1) {
			return JsonResult.build(500, "您没有资格购买此券，请查看规则后再购买 ");
		}
		if (!check()) {
			return JsonResult.build(500, "您今天已经购买过券保理 ，请明天再来");
		}

		// 插入数据库
		int result = insert(quanBaoLiRecord);
		if (result != 1) {
			return JsonResult.build(500, "提交失败");
		}
		return JsonResult.ok();
	}

	// 没人每天只能购买一张券
	public boolean check() {
		List<QuanBaoLiRecord> list = quanBaoLiRecordMapper.check();
		if (list.size() > 0) {
			return false;
		}
		return true;
	}

	// 购买插入数据库
	public int insert(QuanBaoLiRecord quanBaoLiRecord) {
		return quanBaoLiRecordMapper.insertSelective(quanBaoLiRecord);
	}
	
	
	

}
