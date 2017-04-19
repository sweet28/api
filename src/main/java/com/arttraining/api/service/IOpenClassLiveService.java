package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.beanv2.LiveChapterListBean;
import com.arttraining.api.beanv2.LiveCommentBean;
import com.arttraining.api.beanv2.LiveGiftListBean;
import com.arttraining.api.beanv2.LiveHistoryBean;
import com.arttraining.api.beanv2.LiveMemberBean;
import com.arttraining.api.beanv2.LiveTimeTableBean;
import com.arttraining.api.beanv2.LiveTypeList;
import com.arttraining.api.beanv2.OpenClassEnterLiveBean;
import com.arttraining.api.beanv2.OpenClassLiveListBean;
import com.arttraining.api.pojo.LiveChapterPlan;
import com.arttraining.api.pojo.LiveComment;
import com.arttraining.api.pojo.LiveDetail;
import com.arttraining.api.pojo.LiveGift;
import com.arttraining.api.pojo.LiveRoom;

public interface IOpenClassLiveService {
	//爱好者端 直播列表调用方法 begin
	//coffee add 0413
	List<OpenClassLiveListBean> getRoomLiveListByPreV2(Map<String, Object> map);
	int updateLiveRoomInfoById(LiveRoom room);
	List<LiveHistoryBean> getLiveHistoryChapterList(Map<String, Object> map);
	//end
	
    //coffee add 0116 直播列表open/class/live/list接口调用
    List<OpenClassLiveListBean> getRoomLiveListByPre(Map<String, Object> map);
    List<OpenClassLiveListBean> getRoomLiveListByFinish(Map<String, Object> map);
    //coffee add 0117 房间ID 主播ID和类型去查询信息
    LiveChapterPlan getChapterPlanByPreTime(Map<String, Object> map);
    //教师直播间认证调用方法
    //coffee add 0113 判断教师是否设置过直播间
    LiveRoom getLiveRoomById(Integer room_id);
    //coffee add 0118 一进入直播间 默认点赞直播间和关注主播
    void insertFollowLikeAndUpdateRoom(LiveRoom room,Integer uid,String utype);
    //coffee add 0118 依据课时ID 查询对应的课时信息
    LiveChapterPlan getChapterPlan(Integer chapter_id);
    //coffee add 0118 依据直播间ID来查询对应的直播信息
    OpenClassEnterLiveBean getLiveRoomInfoById(Map<String, Object> map);
    //coffee add 0119 按照课时开始时间升序排列获取所有课时信息 
    List<LiveChapterListBean> getChapterListById(Map<String, Object> map);
    //直播类型调用方法
    //coffee add 0117 直播类型列表 prepare/live/type/list接口调用
    List<LiveTypeList> getLivesTypeList();
    //end 爱好者端
    
    //老师端 begin
    //coffee add 0113 判断教师是否设置过直播间
    LiveRoom getLiveRoomByUid(Integer uid,String utype);
    //coffee add 0119 依据主播ID和类型判断是否存在预告课时信息
    LiveChapterPlan getChapterInfoByOwner(Map<String, Object> map);
    //coffee add 0114 修改直播课时信息
    int updateChapterInfo(LiveChapterPlan chapter);
    //end
    
    //begin
    //coffee add 0110 返回直播房间评论信息列表 open/class/comment/list接口调用
    List<LiveCommentBean> getLiveCommentByRoomId(Map<String, Object> map);
    //获取直播成员列表信息 live/member/list接口调用
    List<LiveMemberBean> getLiveMemberByRoomId(Map<String, Object> map);
    int insertLiveComment(LiveComment comment);
    //插入一条直播成员浏览记录信息
    int insertLiveDetail(LiveDetail detail);
    //修改直播间信息
    void updateLiveRoomAndChapterInfo(LiveRoom room,Integer chapter_id);
    //coffee add 0113 判断是否设置过相同课表名称
    List<LiveTimeTableBean> getLiveTimeTableByUid(Map<String, Object> map);
    //end
    
    //begin
    //coffee add 0208 获取直播间的礼物列表
    List<LiveGiftListBean> getLiveGiftList();
    //coffee add 0208 依据礼物ID来获取礼物图片
    String getGiftPicById(Integer id);
    //coffee add 0209 依据礼物ID来获取礼物信息
    LiveGift getGiftInfoById(Integer id);
    //coffee add 0215 依据主播ID和课时ID来获取赠送礼物数 
    int getLiveGiftNumber(Map<String, Object> map);
    //end
    
    //coffee add 0216 修改直播预告课时数
    int updateOnePreNumByRoomId(LiveRoom record);
    //coffee add 0217 首页新增直播列表接口 
    List<OpenClassLiveListBean> getRoomLiveListByHome(Integer limit);
    
    //coffee add 0315 老师端关闭直播间时 更新最新预告时间
    void updateRoomPreTimeById(LiveRoom room,Integer chapter_id);
    //end
   
}
