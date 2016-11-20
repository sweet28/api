package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.HomeLikeOrCommentBean;
import com.arttraining.api.bean.HomePageStatusesBean;
import com.arttraining.api.bean.StatusesShowBean;
import com.arttraining.api.pojo.Statuses;
import com.arttraining.api.pojo.StatusesAttachment;

public interface IStatusesService {
	//依据传递的id获取相应的动态信息
	Statuses getStatusesById(Integer id);
	
	//发布小组动态时 更新小组动态附件信息
	void insertStatusAndUpdateAttr(Statuses status,StatusesAttachment statusAttr);
	
	 //获取小组动态列表信息 默认显示10条记录
    List<HomePageStatusesBean> getStatusesListByGid(Integer gid,Integer offset,Integer limit);
    //是否点评或点赞信息
    HomeLikeOrCommentBean getIsLikeOrCommentOrAtt(Map<String,Object> map);
    
    //获取指定用户在某个小组发布的动态列表信息 默认显示10条
    List<HomePageStatusesBean> getStatusesListByUidAndGid(Integer uid,
    		Integer gid,Integer offset, Integer limit);
    //获取某一条小组动态信息
    StatusesShowBean getOneStatusByid(Integer id);
    
    //获取指定小组下的成员列表所发的动态信息--group/show接口调用
    List<HomePageStatusesBean> getGroupStatusesByGid(Integer gid, Integer limit);
    //判断是否对其进行点赞  --group/show接口调用
    HomeLikeOrCommentBean getIsLikeOrAtt(Map<String,Object> map);
    
    //更新小组动态数量
    int updateStatusNumber(Statuses record);
}
