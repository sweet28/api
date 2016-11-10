package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.HomeLikeOrCommentBean;
import com.arttraining.api.bean.HomePageStatusesBean;
import com.arttraining.api.pojo.Works;

public interface IWorksService {
	//依据传递的id获取相应的作品信息
	Works getWorksById(Integer id);
	//主页获取帖子列表信息
	List<HomePageStatusesBean> getWorksListByHomepage(Integer limit);
	//当前用户是否点赞或者名师是否点评
	//HomeLikeOrCommentBean getIsLikeOrCommentOrAtt(Integer u_id, Integer s_id);
	HomeLikeOrCommentBean getIsLikeOrCommentOrAtt(Map<String, Object> map);
	 //查询指定用户id 发布的作品动态 默认显示10条记录
    List<HomePageStatusesBean> selectWorkListByUid(Integer uid,Integer limit);
}
