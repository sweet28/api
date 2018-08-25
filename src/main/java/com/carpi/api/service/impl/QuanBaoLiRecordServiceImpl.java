package com.carpi.api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.New;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.JsonResult;
import com.arttraining.commons.util.TimeUtil;
import com.carpi.api.dao.BankCardMapper;
import com.carpi.api.dao.FensUserMapper;
import com.carpi.api.dao.QuanBaoLiMapper;
import com.carpi.api.dao.QuanBaoLiRecordMapper;
import com.carpi.api.dao.QuanDakuanRecordMapper;
import com.carpi.api.dao.TiQuMapper;
import com.carpi.api.pojo.BankCard;
import com.carpi.api.pojo.FensUser;
import com.carpi.api.pojo.QuanBaoLi;
import com.carpi.api.pojo.QuanBaoLiRecord;
import com.carpi.api.pojo.QuanDakuanRecord;
import com.carpi.api.pojo.TiQu;
import com.carpi.api.service.QuanBaoLiRecordService;

@Service
public class QuanBaoLiRecordServiceImpl implements QuanBaoLiRecordService {

	@Autowired
	private QuanBaoLiMapper quanBaoLiMapper;

	@Autowired
	private QuanBaoLiRecordMapper quanBaoLiRecordMapper;

	@Autowired
	private QuanDakuanRecordMapper quanDakuanRecordMapper;

	@Autowired
	private FensUserMapper fensUserDao;

	@Autowired
	private BankCardMapper bankCardDao;
	
	@Autowired
	private TiQuMapper tiQuDao;

	// 券宝理列表
	@Override
	public JsonResult selectList() {
		List<QuanBaoLi> list = quanBaoLiMapper.selectList();
		if (list.size() > 0) {
			return JsonResult.ok();
		}
		return JsonResult.build(500, "无信息");
	}

	// 券宝理个人订单
	@Override
	public JsonResult selectOne(QuanBaoLiRecord quanBaoLiRecord) {
		List<QuanBaoLiRecord> list = quanBaoLiRecordMapper.selectList(quanBaoLiRecord);
		if (!list.isEmpty()) {
			return JsonResult.ok(list);
		} else {
			return JsonResult.build(500, "无订单信息");
		}
	}
	
	// 券宝理积分信息汇总
	@Override
	public JsonResult couponGiftInfo(Integer fensUserId, String phone) {
		
		//查询1星券7天的
		List<QuanBaoLiRecord> list1 = quanBaoLiRecordMapper.selectCouponGiftInfo(fensUserId,1,phone);
		//查询1星券21天的
		List<QuanBaoLiRecord> list2 = quanBaoLiRecordMapper.selectCouponGiftInfo(fensUserId,2,phone);
		//查询2星券15天的
		List<QuanBaoLiRecord> list3 = quanBaoLiRecordMapper.selectCouponGiftInfo(fensUserId,3,phone);
		//查询3星券10天的
		List<QuanBaoLiRecord> list4 = quanBaoLiRecordMapper.selectCouponGiftInfo(fensUserId,4,phone);
		
		//查询已经提取过积分
		Double money = tiQuDao.selectMoney(fensUserId);
		if (money == null) {
			money = 0.0;
		}
		//查询1星券7天的实际购买张数
		List<QuanBaoLiRecord> listReal1 = quanBaoLiRecordMapper.selectCouponRealGiftInfo(fensUserId,1,phone);
		//查询1星券21天的实际购买张数
		List<QuanBaoLiRecord> listReal2 = quanBaoLiRecordMapper.selectCouponRealGiftInfo(fensUserId,2,phone);
		//查询2星券15天的实际购买张数
		List<QuanBaoLiRecord> listReal3 = quanBaoLiRecordMapper.selectCouponRealGiftInfo(fensUserId,3,phone);
		//查询3星券10天的实际购买张数
		List<QuanBaoLiRecord> listReal4 = quanBaoLiRecordMapper.selectCouponRealGiftInfo(fensUserId,4,phone);
		
		/*
		 * 7月13日-8月15日直推粉丝购券送20积分活动
		 */
		int fensQNum = 0;
		int fensQNumReal = 0;
		List<FensUser> listZTuser = fensUserDao.selectZTuser7yue(phone);
		if(listZTuser.size() > 0 ){
			fensQNum = listZTuser.size();
//			List<QuanBaoLiRecord> allRealQList = quanBaoLiRecordMapper.selectCouponRealGiftInfoNoTJ();
//			
//			for(int j = 0; j < listZTuser.size(); j++){
//				if(allRealQList.contains(list.get(i).getPhone())){
//					fensQNumReal++;
//				}
//			}
			
		}
		
		Double couponTotalValue = 0.00;
		couponTotalValue =  quanBaoLiRecordMapper.selectCouponGiftTotalValue(fensUserId, phone);
		double couponTenValue = 0.00;
		if(couponTotalValue != null){
			couponTenValue = couponTotalValue * 0.1 + fensQNum * 20;
		}
		
		QuanBaoLiRecord qbr = new QuanBaoLiRecord();
		qbr.setFensUserId(fensUserId);
		qbr.setOrderType(3);
		List<QuanBaoLiRecord> parentQBList = quanBaoLiRecordMapper.selectListGiftKTSY(qbr);
		List<QuanBaoLiRecord> childQBList = quanBaoLiRecordMapper.selectChildrenList(phone);
		
		// Double couponRealTotalValue = 0.00;
		// couponRealTotalValue =
		// quanBaoLiRecordMapper.selectCouponGiftRealTotalValue(fensUserId,
		// phone);
		// double couponRealTenValue = 0.00;
		// if(couponRealTotalValue != null){
		// couponRealTenValue = couponRealTotalValue * 0.1 + fensQNumReal * 20;
		// }
		
		double couponRealTotalValue = 0.00;
		
		if (quanBaoLiRecordMapper.selectCouponGiftRealTotalValue(fensUserId, phone) != null) {
			couponRealTotalValue = quanBaoLiRecordMapper.selectCouponGiftRealTotalValue(fensUserId, phone);
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
		System.out.println("pbaoli_id:"+pbaoliStr);
		System.out.println("cbaoli_id:"+cbaoliStr);
		
		double couponRealTenValue = 0.00;
		couponRealTenValue = couponRealTotalValue * 0.1 + fensQNumReal * 20;
		
		JSONObject jo = new JSONObject();
		jo.put("one7", list1.size());
		jo.put("one21", list2.size());
		jo.put("two15", list3.size());
		jo.put("three10", list4.size());
		jo.put("couponTotalScore", couponTenValue);
		jo.put("one7Real", listReal1.size());
		jo.put("one21Real", listReal2.size());
		jo.put("two15Real", listReal3.size());
		jo.put("three10Real", listReal4.size());
		jo.put("couponTotalScoreReal", couponRealTenValue);

		jo.put("fensQNum", fensQNum);
		jo.put("fensQNumReal", fensQNumReal);
		
		jo.put("couponYiyongScore", money);
		
		
		return JsonResult.ok(jo);
	}

	// 查询券详情
	@Override
	public JsonResult xiangQing(Integer id) {
		if (id == null) {
			return JsonResult.build(500, "请选择订单");
		}
		// 查询订单信息
		QuanBaoLiRecord quanBaoLiRecord = quanBaoLiRecordMapper.selectByPrimaryKey(id);
		if (StringUtils.isEmpty(quanBaoLiRecord)) {
			return JsonResult.build(500, "无订单信息");
		}
		// 查询打款信息(根据券id)
		// QuanDakuanRecord quanDakuanRecord =
		// quanDakuanRecordMapper.selectQuan(quanBaoLiRecord.getId());

		// if (StringUtils.isEmpty(quanDakuanRecord)) {
		// return JsonResult.build(500, "无打款信息");
		// }
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ddxx", quanBaoLiRecord);
		// map.put("dkxx", quanDakuanRecord);
		return JsonResult.ok(map);
	}

	/**
	 * 出场，两个人或多个人给出场人打款（匹配上了） order_type 1：购买待匹配 2：购买待打款 3：收益进行中（已打款） 4：周期结束 5：提取带匹配
	 * 6：提取匹配待打款 7：收益完成
	 */
	@Override
	public JsonResult quanChuChang(Integer id, Integer type, Integer dakuantype) {
		// 先查询出场人的用户信息
		// FensUser fensUser = fensUserDao.selectByPrimaryKey(fensUserId);
		// if (StringUtils.isEmpty(fensUser)) {
		// return JsonResult.build(500, "不是本人操作，请重新登入");
		// }
		// 出场人为收款方（收款人id）
		QuanBaoLiRecord quanBaoLiRecord = quanBaoLiRecordMapper.selectById(id, type);
		if (StringUtils.isEmpty(quanBaoLiRecord)) {
			return JsonResult.build(500, "不存在此券信息");
		}
		// 查询该券的打款人id
		List<QuanDakuanRecord> list = quanDakuanRecordMapper.selectlist(quanBaoLiRecord.getId(), dakuantype);

		if (list.isEmpty()) {
			return JsonResult.build(500, "不存在此券信息1");
		}
		List<Map<String, Object>> list2 = new ArrayList<>();
		for (QuanDakuanRecord quanDakuanRecord : list) {
			Map<String, Object> map = new HashMap<>();
			// 打款人id
			map.put("da_id", quanDakuanRecord.getDakuangId());
			// 打款人名字
			map.put("da_name", quanDakuanRecord.getDaName());
			map.put("id", quanDakuanRecord.getId());
			map.put("quan", quanBaoLiRecord);
			map.put("pip", quanDakuanRecord);
			list2.add(map);
			// 打款人的银行卡信息掉其他接口
		}
		return JsonResult.ok(list2);
	}

	// 根据id查询匹配订单信息
	@Override
	public JsonResult pipeixiangQing(Integer id) {
		if (id == null) {
			return JsonResult.build(500, "无匹配信息");
		}
		QuanDakuanRecord quanDakuanRecord = quanDakuanRecordMapper.selectByPrimaryKey(id);
		if (StringUtils.isEmpty(quanDakuanRecord)) {
			return JsonResult.build(500, "无匹配信息2");
		}
		return JsonResult.ok(quanDakuanRecord);
	}

	// 券保理买家付款
	@Override
	public JsonResult fuk(Integer pipeiId, String yfkUrl) {
		if(StringUtils.isEmpty(pipeiId)) {
			return JsonResult.build(500, "请选择打款信息");
		}
		if(StringUtils.isEmpty(yfkUrl)){
			return JsonResult.build(500, "请上传打款截图");
		}
		
		QuanDakuanRecord quanDakuanRecord = new QuanDakuanRecord();
		//2：已打款待收款（待确认)
		quanDakuanRecord.setDakuanType(2); 
		quanDakuanRecord.setId(pipeiId);
		quanDakuanRecord.setDakuanImg(yfkUrl);
		//打款时间
		quanDakuanRecord.setDakuanDate(TimeUtil.getTimeStamp());
		int reult = quanDakuanRecordMapper.updateByPrimaryKeySelective(quanDakuanRecord);
		if (reult != 1) {
			return JsonResult.build(500, "打款失败");
		}
		
		if(reult == 1){

			QuanDakuanRecord qdkRecord = quanDakuanRecordMapper.selectByPrimaryKey(pipeiId);
			
			/**
			 * 优先判断是否为出局订单，若为出局订单，则需要同时关照进场的券和出场的券
			 **/
			//如果为出场+进场券
			if(!StringUtils.isEmpty(qdkRecord.getBak4())){
				
				String outQuanID = qdkRecord.getBak4();//出场券ID
				Integer inQuanID = qdkRecord.getQuanId();//进场券ID
				//////订单状态  1：购买待匹配    2：购买待打款   3：收益进行中（已打款）   4：周期结束  5：提取带匹配  6：提取匹配待打款  7：收益完成   8：提取待收款（买家已打款）20：认购待收款  80：出局
				//进行出场券下订单完成情况的判断
				List<QuanDakuanRecord> outDKList = quanDakuanRecordMapper.selectOutQuanlist(outQuanID, 1);
				if(outDKList.size() <= 0 ){
					QuanBaoLiRecord qbr = quanBaoLiRecordMapper.selectByPrimaryKey(Integer.valueOf(outQuanID));
					QuanBaoLiRecord qbr2 = new QuanBaoLiRecord();
					
					qbr2.setId(qbr.getId());
					// if(qbr.getOrderType() == 2){
					// qbr2.setOrderType(20);
					// }
					if(qbr.getOrderType() == 6){
						qbr2.setOrderType(8);
					}
					quanBaoLiRecordMapper.updateByPrimaryKeySelective(qbr2);
				}
				
				//进行进场券下订单完成情况的判断
				List<QuanDakuanRecord> inDKList = quanDakuanRecordMapper.selectlist(inQuanID, 1);
				if(inDKList.size() <= 0){
					QuanBaoLiRecord qbr = quanBaoLiRecordMapper.selectByPrimaryKey(inQuanID);
					QuanBaoLiRecord qbr2 = new QuanBaoLiRecord();
					
					qbr2.setId(qbr.getId());
					if (qbr.getOrderType() == 2) {
						qbr2.setOrderType(20);
					}
					// if(qbr.getOrderType() == 6){
					// qbr2.setOrderType(8);
					// }
					quanBaoLiRecordMapper.updateByPrimaryKeySelective(qbr2);
				}
				
			}else{
				//否则为纯进场券
				List<QuanDakuanRecord> qdkrList = quanDakuanRecordMapper.selectlist(qdkRecord.getQuanId(), 1);
				/*
				 * 如果券下有多个订单，判断是否都完成当前操作，若完成，则将券变成下一个状态
				 */
				if(qdkrList.size() <= 0){
					//判断当前券的状态
					//是在入局阶段还是出局阶段，状态不同
					QuanBaoLiRecord qbr = quanBaoLiRecordMapper.selectByPrimaryKey(qdkRecord.getQuanId());
					QuanBaoLiRecord qbr2 = new QuanBaoLiRecord();
					
					qbr2.setId(qbr.getId());
					if(qbr.getOrderType() == 2){
						qbr2.setOrderType(20);
					}
					if(qbr.getOrderType() == 6){
						qbr2.setOrderType(8);
					}
					quanBaoLiRecordMapper.updateByPrimaryKeySelective(qbr2);
				}
			}
		}
		return JsonResult.ok();
	}

	//券保理卖家收款
	@Override
	public JsonResult shouk(Integer pipeiId) {
		if(StringUtils.isEmpty(pipeiId)) {
			return JsonResult.build(500, "请选择打款信息");
		}
		QuanDakuanRecord quanDakuanRecord = new QuanDakuanRecord();
		//已确认收款
		quanDakuanRecord .setDakuanType(3);
		quanDakuanRecord.setId(pipeiId);
		quanDakuanRecord.setShoukuanDate(TimeUtil.getTimeStamp());
		int reult = quanDakuanRecordMapper.updateByPrimaryKeySelective(quanDakuanRecord);
		if (reult != 1) {
			return JsonResult.build(500, "收款失败");
		}
		
		if(reult == 1){
			QuanDakuanRecord qdkRecord = quanDakuanRecordMapper.selectByPrimaryKey(pipeiId);
			/**
			 * 优先判断是否为出局订单，若为出局订单，则需要同时关照进场的券和出场的券
			 **/
			//如果为出场+进场券
			if(!StringUtils.isEmpty(qdkRecord.getBak4())){
				
				String outQuanID = qdkRecord.getBak4();//出场券ID
				Integer inQuanID = qdkRecord.getQuanId();//进场券ID
				
				//进行出场券下订单完成情况的判断
				List<QuanDakuanRecord> outDKList = quanDakuanRecordMapper.selectOutQuanlist(outQuanID, 1);
				List<QuanDakuanRecord> outDKList2 = quanDakuanRecordMapper.selectOutQuanlist(outQuanID, 2);
				if(outDKList.size() <= 0 && outDKList2.size() <= 0){
					QuanBaoLiRecord qbr = quanBaoLiRecordMapper.selectByPrimaryKey(Integer.valueOf(outQuanID));
					QuanBaoLiRecord qbr2 = new QuanBaoLiRecord();
					
					qbr2.setId(qbr.getId());
					if(qbr.getOrderType() == 8){
						qbr2.setOrderType(80);
					}
					quanBaoLiRecordMapper.updateByPrimaryKeySelective(qbr2);
				}
				
				//进行进场券下订单完成情况的判断
				List<QuanDakuanRecord> inDKList = quanDakuanRecordMapper.selectlist(inQuanID, 1);
				List<QuanDakuanRecord> inDKList2 = quanDakuanRecordMapper.selectlist(inQuanID, 2);
				if(inDKList.size() <= 0 && inDKList2.size() <= 0){
					QuanBaoLiRecord qbr = quanBaoLiRecordMapper.selectByPrimaryKey(inQuanID);
					QuanBaoLiRecord qbr2 = new QuanBaoLiRecord();
					
					qbr2.setId(qbr.getId());
					if (qbr.getOrderType() == 20) {
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Date startDate = new Date();

						Calendar calendar = Calendar.getInstance();
						calendar.setTime(startDate);
						calendar.add(Calendar.DAY_OF_MONTH, qbr.getDay());

						Date endDate = calendar.getTime();

						qbr2.setOrderType(3);
						qbr2.setInterestDate(startDate);
						qbr2.setExpiryTime(endDate);
					}
					
					quanBaoLiRecordMapper.updateByPrimaryKeySelective(qbr2);
				}
				
			}else{
				List<QuanDakuanRecord> qdkrList = quanDakuanRecordMapper.selectlist(qdkRecord.getQuanId(), 2);
				
				if(qdkrList.size() <= 0){
					//判断当前券的状态
					//是在入局阶段还是出局阶段，状态不同
					QuanBaoLiRecord qbr = quanBaoLiRecordMapper.selectByPrimaryKey(qdkRecord.getQuanId());
					QuanBaoLiRecord qbr2 = new QuanBaoLiRecord();
					
					qbr2.setId(qbr.getId());
					if(qbr.getOrderType() == 20){
						qbr2.setOrderType(3);
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Date startDate = new Date();

						Calendar calendar = Calendar.getInstance();
						calendar.setTime(startDate);
						calendar.add(Calendar.DAY_OF_MONTH, qbr.getDay());

						Date endDate = calendar.getTime();
						
						qbr2.setInterestDate(startDate);
						qbr2.setExpiryTime(endDate);
					}
					if(qbr.getOrderType() == 8){
						qbr2.setOrderType(80);
					}
					
					quanBaoLiRecordMapper.updateByPrimaryKeySelective(qbr2);
				}
			}
		}
		
		return JsonResult.ok();
	}
	
	//券保理卖家收款
	@Override
	public JsonResult shoukuanCoupon(Integer pipeiId, Integer type) {
		if(StringUtils.isEmpty(pipeiId)) {
			return JsonResult.build(500, "请选择打款信息");
		}
		QuanDakuanRecord quanDakuanRecord = new QuanDakuanRecord();
		//已确认收款
		quanDakuanRecord .setDakuanType(3);
		quanDakuanRecord.setId(pipeiId);
		quanDakuanRecord.setShoukuanDate(TimeUtil.getTimeStamp());
		int reult = quanDakuanRecordMapper.updateByPrimaryKeySelective(quanDakuanRecord);
		if (reult != 1) {
			return JsonResult.build(500, "收款失败");
		}
		
		if(reult == 1){
			QuanDakuanRecord qdkRecord = quanDakuanRecordMapper.selectByPrimaryKey(pipeiId);
			List<QuanDakuanRecord> qdkrList = quanDakuanRecordMapper.selectlistCouponGiftbyType(qdkRecord.getQuanId(),type);
			
			if(qdkrList.size() <= 0){
				TiQu tiQu = new TiQu();
				tiQu.setId(qdkRecord.getQuanId());
				tiQu.setTiquType(4);
				
				tiQuDao.updateByPrimaryKeySelective(tiQu);
			}
		}
		
		return JsonResult.ok();
	}
	
	//券积分买家付款
	@Override
	public JsonResult fukCoupon(Integer pipeiId, Integer type) {
		if(StringUtils.isEmpty(pipeiId)) {
			return JsonResult.build(500, "请选择打款信息");
		}
		QuanDakuanRecord quanDakuanRecord = new QuanDakuanRecord();
		//已确认付款
		quanDakuanRecord .setDakuanType(2);
		quanDakuanRecord.setId(pipeiId);
		quanDakuanRecord.setShoukuanDate(TimeUtil.getTimeStamp());
		int reult = quanDakuanRecordMapper.updateByPrimaryKeySelective(quanDakuanRecord);
		if (reult != 1) {
			return JsonResult.build(500, "付款失败");
		}
		
		if(reult == 1){
			QuanDakuanRecord qdkRecord = quanDakuanRecordMapper.selectByPrimaryKey(pipeiId);
			List<QuanDakuanRecord> qdkrList = quanDakuanRecordMapper.selectlistCouponGiftbyType(qdkRecord.getQuanId(),type);
			
			if(qdkrList.size() <= 0){
				TiQu tiQu = new TiQu();
				tiQu.setId(qdkRecord.getQuanId());
				tiQu.setTiquType(3);
				
				tiQuDao.updateByPrimaryKeySelective(tiQu);
			}
		}
		
		return JsonResult.ok();
	}
	
	@Override
	public JsonResult fukCoupon(Integer pipeiId, String yfkurl, int type) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(pipeiId)) {
			return JsonResult.build(500, "请选择打款信息");
		}
		QuanDakuanRecord quanDakuanRecord = new QuanDakuanRecord();
		//已确认付款
		quanDakuanRecord .setDakuanType(2);
		quanDakuanRecord.setId(pipeiId);
		quanDakuanRecord.setShoukuanDate(TimeUtil.getTimeStamp());
		quanDakuanRecord.setDakuanImg(yfkurl);
		
		int reult = quanDakuanRecordMapper.updateByPrimaryKeySelective(quanDakuanRecord);
		if (reult != 1) {
			return JsonResult.build(500, "付款失败");
		}
		
		if(reult == 1){
			QuanDakuanRecord qdkRecord = quanDakuanRecordMapper.selectByPrimaryKey(pipeiId);
			List<QuanDakuanRecord> qdkrList = quanDakuanRecordMapper.selectlistCouponGiftbyType(qdkRecord.getQuanId(),type);
			
			if(qdkrList.size() <= 0){
				TiQu tiQu = new TiQu();
				tiQu.setId(qdkRecord.getQuanId());
				tiQu.setTiquType(3);
				
				tiQuDao.updateByPrimaryKeySelective(tiQu);
			}
		}
		
		return JsonResult.ok();
	}
	
	@Override
	public JsonResult couponGiftList(Integer uid, String phone) {
		List<TiQu> list = tiQuDao.selectTiQuListByUid(uid);

		if (list.size() <= 0) {
			return JsonResult.build(500, "暂无记录");
		}
		
		return JsonResult.ok(list);
	}

	@Override
	public JsonResult couponGiftListInfo(Integer id) {
		TiQu tiQu = tiQuDao.selectByPrimaryKey(id);

		if (tiQu == null) {
			return JsonResult.build(500, "暂无记录");
		}
		
		List<QuanDakuanRecord> qdkrList = quanDakuanRecordMapper.selectlistCouponGift(id);
		
		Map<String,Object> map = new HashMap<>();
		map.put("tiquInfo", tiQu);
		map.put("orderList", qdkrList);
		
		return JsonResult.ok(map);
	}

	@Override
	public JsonResult selectCouponDFK(Integer fensUserId) {
		// TODO Auto-generated method stub
		List<QuanDakuanRecord> qdkList = quanDakuanRecordMapper.selectDFKlistByFensUserId(fensUserId);
		if(qdkList.size() > 0){
			return JsonResult.ok(qdkList);
		}else{
			return JsonResult.build(500, "暂无数据");
		}
		
	}

	@Override
	public JsonResult selectCouponDSK(Integer fensUserId) {
		// TODO Auto-generated method stub
		List<QuanDakuanRecord> qdkList = quanDakuanRecordMapper.selectDSKlistByFensUserId(fensUserId);
		if(qdkList.size() > 0){
			return JsonResult.ok(qdkList);
		}else{
			return JsonResult.build(500, "暂无数据");
		}
	}

	@Override
	public JsonResult couponOrderList(Integer id) {
		
		List<QuanDakuanRecord> qdkrList = quanDakuanRecordMapper.selectlistCouponOrder(id,id+"");
		
		if (qdkrList.size() > 0) {
			return JsonResult.ok(qdkrList);
		}else{
			return JsonResult.build(500, "暂无记录");
		}
		
	}

	@Override
	public JsonResult quanOut(Integer quanId, Integer fensUserId) throws ParseException {
		
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (!(hour >= ConfigUtil.CPA_QBL_START_TIME && hour < ConfigUtil.CPA_QBL_END_TIME)) {
			return JsonResult.build(500, "每天开放购买时间为："+ConfigUtil.CPA_QBL_START_TIME+"点至"+ConfigUtil.CPA_QBL_END_TIME+"点.");
		}
		
		FensUser fus = fensUserDao.selectByPrimaryKey(fensUserId);
		if (fus == null) {
			return JsonResult.build(500, "非法用户");
		}
		// 查询身份证是否认证
		if (Integer.valueOf(fus.getAttachment()) != 1) {
			return JsonResult.build(500, "身份证未认证");
		}
		// 银行卡
		List<BankCard> list = bankCardDao.selectAll(fus.getId());
		if (list.size() <= 0) {
			return JsonResult.build(500, "请绑定银行卡");
		}
		
		QuanBaoLiRecord quanbaoli = quanBaoLiRecordMapper.selectByPrimaryKey(quanId);

		if (quanbaoli.getFensUserId().equals(fensUserId)) {
			
			if(quanbaoli.getOrderType() == 3 || quanbaoli.getOrderType() == 4){
				Date endTime = quanbaoli.getExpiryTime();
				// 当前时间
				Date dd = TimeUtil.getTimeStamp();
				long a = TimeUtil.isOverDay(TimeUtil.getTimeByDate(dd), TimeUtil.getTimeByDate(endTime));
				//double b = a / (60 * 60 * 24);
				double b = a;

				if (b >= 1) {
					QuanBaoLiRecord qbl = new QuanBaoLiRecord();
					qbl.setId(quanId);
					qbl.setOrderType(5);

					int result = quanBaoLiRecordMapper.updateByPrimaryKeySelective(qbl);

					if (result == 1) {
						return JsonResult.ok();
					} else {
						return JsonResult.build(500, "申请失败，请稍后重试");
					}
				} else {
					return JsonResult.build(500, "还未到出局时间");
				}
			}else{
				return JsonResult.build(500, "不能进行该操作");
			}

		} else {
			return JsonResult.build(500, "申请失败，不能操作他人券保理");
		}

	}

	@Override
	public JsonResult fuk(Integer pipeiId) {
		// TODO Auto-generated method stub
		return null;
	}
}
