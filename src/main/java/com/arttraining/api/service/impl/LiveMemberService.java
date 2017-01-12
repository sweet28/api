package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.beanv2.LiveMemberBean;
import com.arttraining.api.dao.LiveDetailMapper;
import com.arttraining.api.dao.LiveMapper;
import com.arttraining.api.dao.LiveMemberMapper;
import com.arttraining.api.pojo.Live;
import com.arttraining.api.pojo.LiveDetail;
import com.arttraining.api.pojo.LiveMember;
import com.arttraining.api.service.ILiveMemberService;

@Service("liveMemberService")
public class LiveMemberService implements ILiveMemberService{
	@Resource
	private LiveDetailMapper detailDao;
	@Resource
	private LiveMapper liveDao;
	@Resource
	private LiveMemberMapper memberDao;

	@Override
	public int insertLiveDetailAndUpdateLive(LiveMember member,
			LiveDetail detail, Live live) {
		// TODO Auto-generated method stub
		//1.新增直播列表信息
		this.detailDao.insertSelective(detail);
		//2.修改直播信息
		this.liveDao.updateByPrimaryKeySelective(live);
		if(member!=null) {
			this.memberDao.insertSelective(member);
		}
		return 0;
	}

	@Override
	public List<LiveMemberBean> getLiveMemberByRoomId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.memberDao.selectLiveMemberByRoomId(map);
	}

	@Override
	public LiveMemberBean getLiveOwnerByRoomId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.memberDao.selectLiveOwnerByRoomId(map);
	}

	@Override
	public LiveMember getLiveMemberInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.memberDao.selectLiveMemberInfoByRoomId(map);
	}

	@Override
	public int insertLiveDetailAndUpdateLiveMember(LiveMember member,
			LiveDetail detail, Live live) {
		// TODO Auto-generated method stub
		//1.新增直播列表信息
		this.detailDao.insertSelective(detail);
		//2.修改直播信息
		this.liveDao.updateByPrimaryKeySelective(live);
		if(member!=null) {
			this.memberDao.updateByPrimaryKeySelective(member);
		}
		return 0;
	}
}
