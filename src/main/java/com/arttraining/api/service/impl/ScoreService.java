package com.arttraining.api.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.beanv2.ScoreDetailBean;
import com.arttraining.api.dao.LiveGiftMapper;
import com.arttraining.api.dao.ScoreDetailMapper;
import com.arttraining.api.dao.ScoreMapper;
import com.arttraining.api.dao.UserStuMapper;
import com.arttraining.api.pojo.LiveGift;
import com.arttraining.api.pojo.Score;
import com.arttraining.api.pojo.ScoreDetail;
import com.arttraining.api.service.IScoreService;
import com.arttraining.commons.util.TimeUtil;

@Service("scoreService")
public class ScoreService implements IScoreService {
	@Resource
	private ScoreMapper scoreDao;
	@Resource
	private ScoreDetailMapper detailDao;
	@Resource
	private LiveGiftMapper giftDao;
	@Resource
	private UserStuMapper stuDao;
	
	@Override
	public Score getScoreInfoByUid(Integer uid, String utype) {
		// TODO Auto-generated method stub
		return this.scoreDao.selectScoreByUid(uid, utype);
	}

	@Override
	public List<ScoreDetailBean> getScoreListByUid(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.detailDao.selectScoreListByUid(map);
	}

	@Override
	public LiveGift getGiftInfoById(Integer id) {
		// TODO Auto-generated method stub
		return this.giftDao.selectGiftInfoById(id);
	}

	@Override
	public int recordUserScoreInfoByLogin(Integer uid,String utype) {
		// TODO Auto-generated method stub
		//依据用户ID和类型查询积分信息
		Score score=this.scoreDao.selectScoreByUid(uid, utype);
		if(score==null) {
			score=new Score();
			Date date=new Date();
			score.setCreateTime(date);
			score.setUserId(uid);
			score.setUserType(utype);
			score.setOrderCode(TimeUtil.getTimeByDate(date));
			this.scoreDao.insertSelective(score);
		}
		return 0;
	}

	@Override
	public int recordUserScoreInfoByRegister(Integer uid,String utype) {
		// TODO Auto-generated method stub
		Score score=new Score();
		Date date=new Date();
		score.setCreateTime(date);
		score.setUserId(uid);
		score.setUserType(utype);
		score.setOrderCode(TimeUtil.getTimeByDate(date));
		this.scoreDao.insertSelective(score);
		return 0;
	}

	@Override
	public int updateScoreAndDetailInfoByUid(Integer uid,String utype,Integer score,Integer gift_score) {
		// TODO Auto-generated method stub
		//1.修改用户积分信息
		Score upd_score=new Score();
		upd_score.setUserId(uid);
		upd_score.setUserType(utype);
		upd_score.setScore(gift_score);
		this.scoreDao.updateScoreByUid(upd_score);
		//2.新增用户消费积分详情信息
		Date date = new Date();
		ScoreDetail detail=new ScoreDetail();
		detail.setUserId(uid);
		detail.setUserType(utype);
		detail.setCreateTime(date);
		detail.setOrderCode(TimeUtil.getTimeByDate(date));
		detail.setConsumeType("consume");
		Integer curr_score=score-gift_score;
		detail.setCurrScore(curr_score);
		detail.setScore(gift_score);
		detail.setScoreFlag("直播礼物消费");
		this.detailDao.insertSelective(detail);
		return 0;
	}

}
