package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.HomeLikeOrCommentBean;
import com.arttraining.api.bean.HomePageStatusesBean;
import com.arttraining.api.bean.StatusesShowBean;
import com.arttraining.api.pojo.BBS;
import com.arttraining.api.pojo.BBSAttachment;

public interface IBBSService {
	//依据ID获取相应的bbs记录
	BBS getBBSById(Integer id);
	
	//发布帖子时 更新帖子附件信息
	void insertBBSAndInsertAttr(BBS bbs, BBSAttachment bbsAttr);
	//主页获取帖子列表信息--statuses/public_timeline/bbs接口调用
	List<HomePageStatusesBean> getBBSListByHomepage(Integer offset,Integer limit);
	//当前用户是否点赞或者名师是否点评--statuses/user_timeline/bbs接口调用
	HomeLikeOrCommentBean getIsLikeOrCommentOrAtt(Map<String, Object> map);
	//获取指定用户发布的帖子列表信息 默认显示10条--statuses/user_timeline/bbs接口调用
    List<HomePageStatusesBean> getBBSListByUid(Integer uid,Integer offset,Integer limit);
    //依据帖子ID查询某一个帖子信息
    StatusesShowBean getOneBBSById(Integer id);
}
