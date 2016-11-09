package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.bean.HomePageStatusesBean;
import com.arttraining.api.pojo.BBS;
import com.arttraining.api.pojo.BBSAttachment;

public interface IBBSService {
	//依据ID获取相应的bbs记录
	BBS getBBSById(Integer id);
	
	//发布帖子时 更新帖子附件信息
	void insertBBSAndInsertAttr(BBS bbs, BBSAttachment bbsAttr);
	//主页获取帖子列表信息
	List<HomePageStatusesBean> getBBSListByHomepage(Integer limit);
	//当前用户是否点赞或者名师是否点评
	//HomeLikeOrCommentBean getIsLikeOrCommentOrAtt(Integer u_id, Integer s_id);
	void getIsLikeOrCommentOrAtt(Map<String, Object> map);
	//获取指定用户发布的帖子列表信息 默认显示10条
    List<HomePageStatusesBean> getBBSListByUid(Integer uid, Integer limit);
}
