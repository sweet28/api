package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.beanv2.LiveCommentBean;
import com.arttraining.api.pojo.LiveComment;

public interface ILiveCommentService {
	//直播一条评论信息
	public int insertLiveComment(LiveComment comment);
	//coffee add 0110 返回直播房间评论信息列表 live/comment/list接口调用
    List<LiveCommentBean> getLiveCommentByRoomId(Map<String, Object> map);
}
