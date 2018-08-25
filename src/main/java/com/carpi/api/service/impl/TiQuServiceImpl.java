package com.carpi.api.service.impl;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.arttraining.commons.util.JsonResult;
import com.arttraining.commons.util.TimeUtil;
import com.carpi.api.dao.FensUserMapper;
import com.carpi.api.dao.QuanBaoLiRecordMapper;
import com.carpi.api.dao.TiQuMapper;
import com.carpi.api.dao.TiquJineMapper;
import com.carpi.api.pojo.FensUser;
import com.carpi.api.pojo.QuanBaoLiRecord;
import com.carpi.api.pojo.TiQu;
import com.carpi.api.pojo.TiquJine;
import com.carpi.api.service.TiQuService;

@Service
public class TiQuServiceImpl implements TiQuService {

	@Autowired
	private TiQuMapper tiQuDao;

	@Autowired
	private TiquJineMapper tiQuJineDao;

	@Autowired
	private FensUserMapper fensUserDao;

	@Autowired
	private QuanBaoLiRecordMapper quanBaoLiRecordMapper;

	// 提取积分
	@Override
	public JsonResult addQuanJiFen(Integer fensUserId) {
		if (StringUtils.isEmpty(fensUserId)) {
			return JsonResult.build(500, "不是本人操作");
		}
		// 查询粉丝姓名
		FensUser fensUser = fensUserDao.selectByPrimaryKey(fensUserId);
		if (StringUtils.isEmpty(fensUser)) {
			return JsonResult.build(500, "会员不存在");
		}
		//查询已经提取过积分
		Double money = tiQuDao.selectMoney(fensUserId);
		if (money == null) {
			money = 0.0;
		}
		
		//防止多次点击
		Integer isCunzai = tiQuDao.selectTiQu(fensUserId);
		if(isCunzai != null){
			if(isCunzai > 0){
				return JsonResult.build(500, "有积分提取未完成的订单，请完成后再进行新的提取");
			}
		}
		
		// 提现积分
//		Double couponRealTotalValue = quanBaoLiRecordMapper.selectCouponGiftRealTotalValue(fensUserId,
//				fensUser.getPhone());
		QuanBaoLiRecord qbr = new QuanBaoLiRecord();
		qbr.setFensUserId(fensUserId);
		qbr.setOrderType(3);
		double couponRealTotalValue = 0.00;
		
		FensUser fUser = fensUserDao.selectByPrimaryKey(fensUserId);
		
		List<QuanBaoLiRecord> parentQBList = quanBaoLiRecordMapper.selectListGiftKTSY(qbr);
		List<QuanBaoLiRecord> childQBList = quanBaoLiRecordMapper.selectChildrenList(fUser.getPhone());
		
		
		if (quanBaoLiRecordMapper.selectCouponGiftRealTotalValue(fensUserId, fUser.getPhone()) != null) {
			couponRealTotalValue = quanBaoLiRecordMapper.selectCouponGiftRealTotalValue(fensUserId, fUser.getPhone());
		}
		
		String pbaoliStr = "";
		String cbaoliStr = "";
		if(parentQBList.size() > 0){
			if(childQBList.size() > 0){
				for(QuanBaoLiRecord baoli : childQBList){
					for (QuanBaoLiRecord pbaoli : parentQBList) {
						if (baoli.getCreateDate().getTime() >= pbaoli.getCreateDate().getTime()) { // 将父、子节点的开始时间调整到排队开始时间
							if (baoli.getCreateDate().getTime() <= pbaoli.getExpiryTime().getTime()) {
								if (pbaoli.getQuanId().intValue() >= baoli.getQuanId().intValue()) { // 此语句为增加烧伤机制
									couponRealTotalValue += baoli.getPosition();
									pbaoliStr += (pbaoli.getId() + ",");
									cbaoliStr += (baoli.getId() + ",");
									break;
								}
							}
						}
					}
				}
			}
		}
		
		Double ketiqu = couponRealTotalValue*0.1-money;
		// 可提现剩余的金额
		int jifen = (int) (ketiqu % 200);
		//可提取积分
		Double jifen2 = ketiqu - (Double.valueOf(jifen));
		if (jifen2 < 200) {
			return JsonResult.build(500, "提取积分不足，请查看规则后提取");
		}
		TiQu tiQu = new TiQu();
		tiQu.setCreateDate(TimeUtil.getTimeStamp());
		tiQu.setTiquDate(TimeUtil.getTimeStamp());
		tiQu.setTiquName(fensUser.getName());
		// 提取状态：1：申请中 2：打款成功
		tiQu.setTiquType(1);
		tiQu.setFensUserId(fensUserId);
		tiQu.setTixianJiner(jifen2);
		// 剩余金额
		tiQu.setBak1(String.valueOf(jifen));
		
		int result = tiQuDao.insertSelective(tiQu);
		if (result != 1) {
			return JsonResult.build(500, "提取失败");
		}
		// 备份数据
		TiquJine tiquJine = new TiquJine();
		tiquJine.setCreateDate(TimeUtil.getTimeStamp());
		tiquJine.setTiquDate(TimeUtil.getTimeStamp());
		tiquJine.setTiquName(fensUser.getName());
		//提取状态1：申请中 2：待打款 3：打款成功
		tiquJine.setTiquType(1);
		tiquJine.setFensUserId(fensUserId);
		tiquJine.setTixianJiner(couponRealTotalValue);
		// 剩余金额
		tiquJine.setBak1(String.valueOf(jifen));
		int status = tiQuJineDao.insertSelective(tiquJine);
		if (status != 1) {
			return JsonResult.build(500, "提取成功，备份失败");
		}
		return JsonResult.ok(money);
	}

}
