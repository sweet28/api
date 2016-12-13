package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.HomeLikeOrCommentBean;
import com.arttraining.api.bean.HomePageStatusesBean;
import com.arttraining.api.bean.HomePageWorkBean;
import com.arttraining.api.bean.WorkShowBean;
import com.arttraining.api.pojo.Works;

public interface IWorksService {
	//显示指定某一个用户发布的作品信息--statuses/show/work接口调用
	WorkShowBean getOneWorkByid(Integer id);
    
	//依据传递的id获取相应的作品信息
	Works getWorksById(Integer id);
	//主页获取帖子列表信息
	List<HomePageStatusesBean> getWorksListByHomepage(Integer limit);
	//当前用户是否点赞或者名师是否点评
	//HomeLikeOrCommentBean getIsLikeOrCommentOrAtt(Integer u_id, Integer s_id);
	HomeLikeOrCommentBean getIsLikeOrCommentOrAtt(Map<String, Object> map);
	 //查询指定用户id 发布的作品动态 默认显示10条记录
    List<HomePageStatusesBean> getWorkListByUid(Integer uid,Integer offset,Integer limit);
    
    Works getWorkByOrderNumber(String orderNumber);
    
    //更新作品相关数量
    int updateWorksNumber(Works record);
    //获取首页的作品列表信息 homepage/public_timeline/work接口调用
    List<HomePageWorkBean> getWorkListByPublic(Map<String, Object> map);
}
