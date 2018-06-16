package com.carpi.api.service.impl;

import java.util.Calendar;
import java.util.List;

import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.arttraining.commons.util.ConfigUtil;
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

	// 券保理1星券(7天)
	@Override
	public JsonResult buyqbl(QuanBaoLiRecord quanBaoLiRecord) {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (!(hour >= ConfigUtil.CPA_QBL_START_TIME && hour < ConfigUtil.CPA_QBL_END_TIME)) {
			return JsonResult.build(500, "每天开放购买时间为：12:00至21:00.");
		}
		/*
		 * 两台一型可以购买一型券一张《就是两台一张券，4台两张卷》 一个二型可以购买二型券一张《1:1》 一个三型可以购买三型券一张《1:1》
		 */
		// 一型矿机数量
		int sum = fensMinerDao.selectSum("1", quanBaoLiRecord.getFensUserId());
		// 券保理1星券(7天)数量
		int sum2 = quanBaoLiRecordMapper.selectsum(quanBaoLiRecord.getFensUserId(), 1);
		int sum3 = sum - sum2 * 2;
		if (sum3 < 2) {
			return JsonResult.build(500, "您没有资格购买此券，请查看规则后再购买");
		}
		if (!check(quanBaoLiRecord.getFensUserId())) {
			return JsonResult.build(500, "您今天已经购买过券保理 ，请明天再来");
		}
		// 查询券的总数量
		QuanBaoLi quanBaoLi3 = quanBaoLiDao.selectByPrimaryKey(quanBaoLiRecord.getQuanId());
		if ("1".equals(quanBaoLi3.getBak3())) {
			return JsonResult.build(500, "该券今日已售完");
		}
		if (Integer.valueOf(quanBaoLi3.getBak2()) <= 0) {
			return JsonResult.build(500, "该券已售完");
		}
		return insert(quanBaoLiRecord);
	}

	// 券保理1星券(21天)
	@Override
	public JsonResult buyqbl2(QuanBaoLiRecord quanBaoLiRecord) {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (!(hour >= ConfigUtil.CPA_QBL_START_TIME && hour < ConfigUtil.CPA_QBL_END_TIME)) {
			return JsonResult.build(500, "每天开放购买时间为：12:00至21:00.");
		}
		// 二型矿机数量
		int sum = fensMinerDao.selectSum("1", quanBaoLiRecord.getFensUserId());
		// 券保理1星券(21天)数量
		int sum2 = quanBaoLiRecordMapper.selectsum(quanBaoLiRecord.getFensUserId(), 2);
		int sum3 = sum - sum2 * 2;
		if (sum3 < 2) {
			return JsonResult.build(500, "您没有资格购买此券，请查看规则后再购买 ");
		}
		if (!check(quanBaoLiRecord.getFensUserId())) {
			return JsonResult.build(500, "您今天已经购买过券保理 ，请明天再来");
		}
		// 查询券的总数量
		QuanBaoLi quanBaoLi3 = quanBaoLiDao.selectByPrimaryKey(quanBaoLiRecord.getQuanId());
		if ("1".equals(quanBaoLi3.getBak3())) {
			return JsonResult.build(500, "该券今日已售完");
		}
		if (Integer.valueOf(quanBaoLi3.getBak2()) <= 0) {
			return JsonResult.build(500, "该券已售完");
		}
		return insert(quanBaoLiRecord);
	}

	// 券保理2星券(15天)
	@Override
	public JsonResult buyqbl3(QuanBaoLiRecord quanBaoLiRecord) {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (!(hour >= ConfigUtil.CPA_QBL_START_TIME && hour < ConfigUtil.CPA_QBL_END_TIME)) {
			return JsonResult.build(500, "每天开放购买时间为：12:00至21:00.");
		}
		// 三型矿机数量
		int sum = fensMinerDao.selectSum("2", quanBaoLiRecord.getFensUserId());
		// 券保理2星券(15天)数量
		int sum2 = quanBaoLiRecordMapper.selectsum(quanBaoLiRecord.getFensUserId(), 3);
		int sum3 = sum - sum2 * 1;
		if (sum3 < 1) {
			return JsonResult.build(500, "您没有资格购买此券，请查看规则后再购买 ");
		}
		if (!check(quanBaoLiRecord.getFensUserId())) {
			return JsonResult.build(500, "您今天已经购买 ，请明天再来");
		}
		// 查询券的总数量
		QuanBaoLi quanBaoLi3 = quanBaoLiDao.selectByPrimaryKey(quanBaoLiRecord.getQuanId());
		if ("1".equals(quanBaoLi3.getBak3())) {
			return JsonResult.build(500, "该券今日已售完");
		}
		if (Integer.valueOf(quanBaoLi3.getBak2()) <= 0) {
			return JsonResult.build(500, "该券已售完");
		}
		return insert(quanBaoLiRecord);
	}

	// 券保理3星券(10天)
	@Override
	public JsonResult buyqbl4(QuanBaoLiRecord quanBaoLiRecord) {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (!(hour >= ConfigUtil.CPA_QBL_START_TIME && hour < ConfigUtil.CPA_QBL_END_TIME)) {
			return JsonResult.build(500, "每天开放购买时间为：9:00至21:00.");
		}
		// 三型矿机数量
		int sum = fensMinerDao.selectSum("3", quanBaoLiRecord.getFensUserId());
		// 券保理3星券(10天)数量
		int sum2 = quanBaoLiRecordMapper.selectsum(quanBaoLiRecord.getFensUserId(), 4);
		int sum3 = sum - sum2 * 1;
		if (sum3 < 1) {
			return JsonResult.build(500, "您没有资格购买此券，请查看规则后再购买 ");
		}
		if (!check(quanBaoLiRecord.getFensUserId())) {
			return JsonResult.build(500, "您今天已经购买 ，请明天再来");
		}
		// 查询券的总数量
		QuanBaoLi quanBaoLi3 = quanBaoLiDao.selectByPrimaryKey(quanBaoLiRecord.getQuanId());
		if ("1".equals(quanBaoLi3.getBak3())) {
			return JsonResult.build(500, "该券今日已售完");
		}
		if (Integer.valueOf(quanBaoLi3.getBak2()) <= 0) {
			return JsonResult.build(500, "该券已售完");
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
		// 查询粉丝信息
		FensUser fensUser = fensUserDao.selectByPrimaryKey(quanBaoLiRecord.getFensUserId());
		if (StringUtils.isEmpty(fensUser)) {
			return JsonResult.build(500, "不存在用户会员信息");
		}
		// 查询券信息
		QuanBaoLi quanBaoLi = quanBaoLiDao.selectByPrimaryKey(quanBaoLiRecord.getQuanId());
		if (StringUtils.isEmpty(quanBaoLi)) {
			return JsonResult.build(500, "不存在此券信息");
		}
		quanBaoLiRecord.setCreateDate(TimeUtil.getTimeStamp());
		// 券保理名字
		quanBaoLiRecord.setName(quanBaoLi.getName());
		// 额度
		quanBaoLiRecord.setPosition(quanBaoLi.getPosition());
		// 天数
		quanBaoLiRecord.setDay(quanBaoLi.getDay());
		// 收益比例
		quanBaoLiRecord.setEarnProportion(quanBaoLi.getEarnProportion());
		// 截止日期
		quanBaoLiRecord.setDeadline(quanBaoLi.getDeadline());
		// 出局额度
		quanBaoLiRecord.setOutPrice(Double.valueOf(quanBaoLi.getBak1()));
		// 购买提交时间
		quanBaoLiRecord.setBuySubDate(TimeUtil.getTimeStamp());
		// 订单状态 1：购买待匹配
		quanBaoLiRecord.setOrderType(1);
		int result = quanBaoLiRecordMapper.insertSelective(quanBaoLiRecord);
		if (result != 1) {
			return JsonResult.build(500, "提交失败");
		}
		// 查询券的总数量
		QuanBaoLi quanBaoLi2 = quanBaoLiDao.selectByPrimaryKey(quanBaoLiRecord.getQuanId());
		if ("1".equals(quanBaoLi2.getBak3())) {
			return JsonResult.build(500, "该券今日已售完");
		}
		Integer bak2 = Integer.valueOf(quanBaoLi2.getBak2());
		if (bak2 == 0) {
			return JsonResult.build(500, "该券已售完");
		}
		Integer newcount = bak2 - 1;
		QuanBaoLi quanBaoLi3 = new QuanBaoLi();
		quanBaoLi3.setBak2(String.valueOf(newcount));
		quanBaoLi3.setId(quanBaoLiRecord.getQuanId());
		int stus = quanBaoLiDao.updateByPrimaryKeySelective(quanBaoLi3);
		if (stus != 1) {
			return JsonResult.build(500, "更新数量失败");
		}
		return JsonResult.ok(newcount);
	}

	// 券的总数量
	@Override
	public JsonResult count(Integer id) {
		QuanBaoLi quanBaoLi = quanBaoLiDao.selectByPrimaryKey(id);
		if (StringUtils.isEmpty(quanBaoLi)) {
			return JsonResult.build(500, "不存在该券");
		}
		return JsonResult.ok(quanBaoLi.getBak2());
	}

}
