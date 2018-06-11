package com.carpi.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.arttraining.commons.util.JsonResult;
import com.arttraining.commons.util.TimeUtil;
import com.carpi.api.dao.FensMinerMapper;
import com.carpi.api.dao.FensUserMapper;
import com.carpi.api.dao.QuanBaoLiMapper;
import com.carpi.api.dao.QuanBaoLiRecordMapper;
import com.carpi.api.pojo.FensUser;
import com.carpi.api.pojo.QuanBaoLi;
import com.carpi.api.pojo.QuanBaoLiRecord;
import com.carpi.api.service.QblBuyService;

@Service
public class QblBuyServiceImpl implements QblBuyService {

	@Autowired
	private FensMinerMapper fensMinerDao;

	@Autowired
	private QuanBaoLiRecordMapper quanBaoLiRecordMapper;
	
	@Autowired
	private FensUserMapper fensUserDao;

	@Autowired
	private QuanBaoLiMapper quanBaoLiDao;
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
		if (!check(quanBaoLiRecord.getFensUserId())) {
			return JsonResult.build(500, "您今天已经购买过券保理 ，请明天再来");
		}
		return insert(quanBaoLiRecord);
	}

	// 购买券宝理商品券(二型矿机数量)
	@Override
	public JsonResult buyqbl2(QuanBaoLiRecord quanBaoLiRecord) {
		// 二型矿机数量
		int sum2 = fensMinerDao.selectSum("2", quanBaoLiRecord.getFensUserId());
		if (sum2 < 1) {
			return JsonResult.build(500, "您没有资格购买此券，请查看规则后再购买 ");
		}
		if (!check(quanBaoLiRecord.getFensUserId())) {
			return JsonResult.build(500, "您今天已经购买过券保理 ，请明天再来");
		}
		return insert(quanBaoLiRecord);
	}

	// 购买券宝理商品券(三型矿机数量)
	@Override
	public JsonResult buyqbl3(QuanBaoLiRecord quanBaoLiRecord) {
		//三型矿机数量
		int sum3 = fensMinerDao.selectSum("3", quanBaoLiRecord.getFensUserId());
		if (sum3 < 1) {
			return JsonResult.build(500, "您没有资格购买此券，请查看规则后再购买 ");
		}
		if (!check(quanBaoLiRecord.getFensUserId())) {
			return JsonResult.build(500, "您今天已经购买 ，请明天再来");
		}
		return insert(quanBaoLiRecord);
	}

	// 没人每天只能购买一张券
	public boolean check(Integer fensUserId) {
		List<QuanBaoLiRecord> list = quanBaoLiRecordMapper.check(fensUserId);
		if (!list.isEmpty()) {
			return false;
		}
		return true;
	}

	// 购买插入数据库
	public JsonResult insert(QuanBaoLiRecord quanBaoLiRecord) {

		if (StringUtils.isEmpty(quanBaoLiRecord.getFensUserId())) {
			return JsonResult.build(500, "请检查网络是否畅通");
		}
		if (StringUtils.isEmpty(quanBaoLiRecord.getQuanId())) {
			return JsonResult.build(500, "不存在此订单");
		}
		//查询粉丝信息
		FensUser fensUser = fensUserDao.selectByPrimaryKey(quanBaoLiRecord.getFensUserId());
		if (StringUtils.isEmpty(fensUser)) {
			return JsonResult.build(500, "不存在用户会员信息");
		}
		//查询券信息
		QuanBaoLi quanBaoLi = quanBaoLiDao.selectByPrimaryKey(quanBaoLiRecord.getQuanId());
		if (StringUtils.isEmpty(quanBaoLi)) {
			return JsonResult.build(500, "不存在此券信息");
		}
		quanBaoLiRecord.setCreateDate(TimeUtil.getTimeStamp());
		quanBaoLiRecord.setName(fensUser.getName());
		//额度
		quanBaoLiRecord.setPosition(quanBaoLi.getPosition());
		//天数
		quanBaoLiRecord.setDay(quanBaoLi.getDay());
		//收益比例
		quanBaoLiRecord.setEarnProportion(quanBaoLi.getEarnProportion());
		//截止日期
		quanBaoLiRecord.setDeadline(quanBaoLi.getDeadline());
        //出局额度
		quanBaoLiRecord.setOutPrice(Double.valueOf(quanBaoLi.getBak1()));
		//购买提交时间
		quanBaoLiRecord.setBuySubDate(TimeUtil.getTimeStamp());
		//订单状态  1：购买待匹配
		quanBaoLiRecord.setOrderType(1);
		int result = quanBaoLiRecordMapper.insertSelective(quanBaoLiRecord);
		if (result != 1) {
			return JsonResult.build(500, "提交失败");
		}
		return JsonResult.ok(result);
	}

}
