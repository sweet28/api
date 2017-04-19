package com.arttraining.api.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.beanv2.LiveChapterListBean;
import com.arttraining.api.beanv2.LiveCommentBean;
import com.arttraining.api.beanv2.LiveGiftListBean;
import com.arttraining.api.beanv2.LiveHistoryBean;
import com.arttraining.api.beanv2.LiveMemberBean;
import com.arttraining.api.beanv2.LiveTimeTableBean;
import com.arttraining.api.beanv2.LiveTypeList;
import com.arttraining.api.beanv2.OpenClassEnterLiveBean;
import com.arttraining.api.beanv2.OpenClassLiveListBean;
import com.arttraining.api.dao.FollowMapper;
import com.arttraining.api.dao.LiveChapterPlanMapper;
import com.arttraining.api.dao.LiveCommentMapper;
import com.arttraining.api.dao.LiveDetailMapper;
import com.arttraining.api.dao.LiveGiftMapper;
import com.arttraining.api.dao.LiveLikeMapper;
import com.arttraining.api.dao.LiveMemberMapper;
import com.arttraining.api.dao.LiveRoomMapper;
import com.arttraining.api.dao.LiveTimeTableMapper;
import com.arttraining.api.dao.LiveTypeMapper;
import com.arttraining.api.dao.UserTechMapper;
import com.arttraining.api.pojo.Follow;
import com.arttraining.api.pojo.LiveChapterPlan;
import com.arttraining.api.pojo.LiveComment;
import com.arttraining.api.pojo.LiveDetail;
import com.arttraining.api.pojo.LiveGift;
import com.arttraining.api.pojo.LiveLike;
import com.arttraining.api.pojo.LiveMember;
import com.arttraining.api.pojo.LiveRoom;
import com.arttraining.api.pojo.UserTech;
import com.arttraining.api.service.IOpenClassLiveService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.LiveUtil;
import com.arttraining.commons.util.Random;
import com.arttraining.commons.util.TimeUtil;
import com.qiniu.pili.PiliException;

@Service("openClassLiveService")
public class OpenClassLiveService implements IOpenClassLiveService {
	@Resource
	private LiveRoomMapper roomDao;
	@Resource
	private LiveChapterPlanMapper chapterDao;
	@Resource
	private FollowMapper followDao;
	@Resource
	private UserTechMapper tecDao;
	@Resource
	private LiveLikeMapper likeDao;
	@Resource
	private LiveTypeMapper typeDao;
	@Resource
	private LiveMemberMapper memberDao;
	@Resource
	private LiveCommentMapper commentDao;
	@Resource
	private LiveDetailMapper detailDao;
	@Resource
	private LiveTimeTableMapper timeTableDao;
	@Resource
	private LiveGiftMapper giftDao;
	
	@Override
	public List<OpenClassLiveListBean> getRoomLiveListByPre(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.roomDao.selectRoomLiveListByPre(map);
	}

	@Override
	public List<OpenClassLiveListBean> getRoomLiveListByFinish(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.roomDao.selectRoomLiveListByFinish(map);
	}

	@Override
	public LiveChapterPlan getChapterPlanByPreTime(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.chapterDao.selectChapterPlanByPreTime(map);
	}

	@Override
	public LiveRoom getLiveRoomById(Integer room_id) {
		// TODO Auto-generated method stub
		return this.roomDao.selectByPrimaryKey(room_id);
	}

	@Override
	public void insertFollowLikeAndUpdateRoom(LiveRoom room, Integer uid,
			String utype) {
		// TODO Auto-generated method stub
		//1.先获取主播ID和主播类型 直播间ID
		Integer owner=room.getOwner();
		String owner_type=room.getOwnerType();
		Integer room_id=room.getId();
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("utype", utype);
		map.put("id", owner);
		map.put("type", owner_type);
		map.put("room_id", room_id);
		
		//获取当前时间
		Date date = new Date();
		String time = TimeUtil.getTimeByDate(date);
		//2.新增关注信息
		Follow isExist=this.followDao.selectIsExistFollow(map);
		if(isExist==null) {
			UserTech tec = this.tecDao.selectByPrimaryKey(owner);
			String name="";
			if(tec!=null) {
				name=tec.getName();
			}
			Follow follow = new Follow();
			follow.setVisitor(uid);
			follow.setVisitorType(utype);
			follow.setHost(owner);
			follow.setHostType(owner_type);
			follow.setHostName(name);
			follow.setCreateTime(date);
			follow.setOrderCode(time);
			
			this.followDao.insertSelective(follow);
			
			//3.修改教师粉丝数量
			UserTech upd_tec=new UserTech();
			upd_tec.setId(owner);
			upd_tec.setFansNum(1);
			this.tecDao.updateNumberBySelective(upd_tec);
		}
		
		//4.新增直播间点赞信息
		LiveLike likeExist=this.likeDao.selectIsExistLike(map);
		if(likeExist==null) {
			LiveLike like = new LiveLike();
			like.setVisitor(uid);
			like.setVisitorType(utype);
			like.setHost(owner);
			like.setHostType(owner_type);
			like.setCreateTime(date);
			like.setOrderCode(time);
			like.setForeignKey(room_id);
			
			this.likeDao.insertSelective(like);
			
			//5.修改直播间点赞数量
			LiveRoom upd_room=new LiveRoom();
			upd_room.setId(room_id);
			upd_room.setLikeNumber(1);
			upd_room.setBrowseNumber(Random.randomInt1To10());
			this.roomDao.updateByPrimaryKeySelective(upd_room);
			System.out.println(room_id);
			//6.默认加入 均成为主播的学员
			LiveMember member=new LiveMember();
			member.setOwner(owner);
			member.setOwnerType(owner_type);
			member.setForeignKey(room_id);
			member.setHost(uid);
			member.setHostType(utype);
			member.setCreateTime(date);
			member.setOrderCode(time);
			this.memberDao.insertSelective(member);
		}
		//7.默认记录加入房间的浏览情况 begin
		LiveDetail detail=new LiveDetail();
		detail.setOwner(owner);
		detail.setOwnerType(owner_type);
		detail.setForeignKey(room_id);
		detail.setHost(uid);
		detail.setHostType(utype);
		detail.setCreateTime(date);
		detail.setOrderCode(time);
		detail.setLiveOperation("join");
		this.detailDao.insertSelective(detail);
		//end
	}

	@Override
	public LiveChapterPlan getChapterPlan(Integer chapter_id) {
		// TODO Auto-generated method stub
		return this.chapterDao.selectByPrimaryKey(chapter_id);
	}

	@Override
	public OpenClassEnterLiveBean getLiveRoomInfoById(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.roomDao.selectLiveRoomInfoById(map);
	}

	@Override
	public List<LiveChapterListBean> getChapterListById(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.chapterDao.selectChapterListById(map);
	}

	@Override
	public List<LiveTypeList> getLivesTypeList() {
		// TODO Auto-generated method stub
		return this.typeDao.selectLivesTypeList();
	}

	@Override
	public LiveRoom getLiveRoomByUid(Integer uid, String utype) {
		// TODO Auto-generated method stub
		return this.roomDao.selectLiveRoomByUid(uid, utype);
	}

	@Override
	public LiveChapterPlan getChapterInfoByOwner(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.chapterDao.selectChapterInfoByOwner(map);
	}

	@Override
	public int updateChapterInfo(LiveChapterPlan chapter) {
		// TODO Auto-generated method stub
		return this.chapterDao.updateByPrimaryKeySelective(chapter);
	}

	@Override
	public List<LiveCommentBean> getLiveCommentByRoomId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.commentDao.selectLiveCommentByRoomId(map);
	}

	@Override
	public List<LiveMemberBean> getLiveMemberByRoomId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.memberDao.selectLiveMemberByRoomId(map);
	}

	@Override
	public int insertLiveComment(LiveComment comment) {
		// TODO Auto-generated method stub
		return this.commentDao.insertSelective(comment);
	}

	@Override
	public int insertLiveDetail(LiveDetail detail) {
		// TODO Auto-generated method stub
		return this.detailDao.insertSelective(detail);
	}

	@Override
	public void updateLiveRoomAndChapterInfo(LiveRoom room,Integer chapter_id) {
		// TODO Auto-generated method stub
		//获取当前日期
		Date date=new Date();
		String time=TimeUtil.getTimeByDate(date);
		//4.在课时计划表中记录关闭直播时间 同时修改课时直播状态为2
		LiveChapterPlan upd_chapter=new LiveChapterPlan();
		upd_chapter.setId(chapter_id);
		upd_chapter.setRemarks2(time);
		upd_chapter.setLiveStatus(2);
		//3.保存视频信息 
		LiveChapterPlan curr_chapter=this.chapterDao.selectByPrimaryKey(chapter_id);
		if(curr_chapter!=null) {
			Date startDate=TimeUtil.strToDate(curr_chapter.getRemarks1());
			long startTime=startDate.getTime();
			long endTime = date.getTime();
			long  diff = (endTime - startTime)/1000;
			//直播时长
			int duration=Integer.parseInt(String.valueOf(diff));
			//保存直播数据的回放路径
			String fname="";
			//判断直播时长是否小于5分钟 如果小于5分钟 将不会保存直播数据
			if(duration>300) {
				try {
					fname=LiveUtil.saveLiveStream(curr_chapter.getStreamKey(), startTime, endTime);
					fname=ConfigUtil.LIVE_SAVE_VIDEO_URL+"/"+fname;
					upd_chapter.setSdUrl(fname);
					upd_chapter.setDuration(String.valueOf(diff));
				} catch (PiliException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					fname="";
				}
			}
		}
		//修改课时信息 begin
		this.chapterDao.updateByPrimaryKeySelective(upd_chapter);
		//end
		//coffee add 0315 修改直播预告时间
		this.updateRoomPreTimeById(room, chapter_id);
		//end
	}

	@Override
	public List<LiveTimeTableBean> getLiveTimeTableByUid(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.timeTableDao.selectLiveTimeTableByUid(map);
	}

	@Override
	public List<LiveGiftListBean> getLiveGiftList() {
		// TODO Auto-generated method stub
		return this.giftDao.selectLiveGiftList();
	}

	@Override
	public String getGiftPicById(Integer id) {
		// TODO Auto-generated method stub
		return this.giftDao.selectGiftPicById(id);
	}

	@Override
	public LiveGift getGiftInfoById(Integer id) {
		// TODO Auto-generated method stub
		return this.giftDao.selectGiftInfoById(id);
	}

	@Override
	public int getLiveGiftNumber(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.commentDao.selectLiveGiftNumber(map);
	}

	@Override
	public int updateOnePreNumByRoomId(LiveRoom record) {
		// TODO Auto-generated method stub
		return this.roomDao.updateByPrimaryKeySelective(record);
		//return this.roomDao.updatePreNumByRoomId(record);
	}

	@Override
	public List<OpenClassLiveListBean> getRoomLiveListByHome(Integer limit) {
		// TODO Auto-generated method stub
		return this.roomDao.selectRoomLiveListByHome(limit);
	}

	@Override
	public void updateRoomPreTimeById(LiveRoom room, Integer chapter_id) {
		// TODO Auto-generated method stub
		//主播ID和类型
		Integer owner=room.getOwner();
		String owner_type=room.getOwnerType();
		Integer room_id=room.getId();
		
		//2.查询课时计划表中是否存在下一个预告课时 如果存在 则获取课时预告直播时间 begin
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("uid", owner);
		map.put("utype", owner_type);
		map.put("room_id", room_id);
		map.put("live_status", 0);
		LiveChapterPlan chapter=this.chapterDao.selectChapterInfoByOwner(map);
		//如果存在预告课时 
		Integer pre_number=-1;
		String remarks1="";
		//修改直播间信息
		LiveRoom upd_room=new LiveRoom();
		upd_room.setId(room_id);
		if(chapter!=null) {
			upd_room.setPreTime(TimeUtil.getTimeByDate(chapter.getStartTime()));
			remarks1="P";
		} 
		upd_room.setRemarks1(remarks1);
		upd_room.setPreNumber(pre_number);
		this.roomDao.updateByPrimaryKeySelective(upd_room);
	}

	@Override
	public List<OpenClassLiveListBean> getRoomLiveListByPreV2(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.roomDao.selectRoomLiveListByPreV2(map);
	}

	@Override
	public int updateLiveRoomInfoById(LiveRoom room) {
		// TODO Auto-generated method stub
		return this.roomDao.updateByPrimaryKeySelective(room);
	}

	@Override
	public List<LiveHistoryBean> getLiveHistoryChapterList(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.chapterDao.selectLiveHistoryChapterList(map);
	}
}
