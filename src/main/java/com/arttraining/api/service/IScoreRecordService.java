package com.arttraining.api.service;

import java.util.Map;

public interface IScoreRecordService {
	/***
	 * 积分记录调用的方法
	 * 课程--kc 测评--cp 充值--cz 打赏--ds
	 * 兑换会员卡--dhhyk
	 */
	public int insertScoreRecord(Map<String, Object> map);
	//关闭交易时 恢复积分记录表
	public int insertAndBackScoreRecord(Map<String, Object> map);
	//兑换会员卡时 新增积分记录表
	public int insertScoreRecordByMember(Map<String, Object> map);
}
