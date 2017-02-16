package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import com.arttraining.api.beanv2.LiveCommentBean;
import com.arttraining.api.pojo.LiveComment;

public interface LiveCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LiveComment record);

    int insertSelective(LiveComment record);

    LiveComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LiveComment record);

    int updateByPrimaryKey(LiveComment record);
    //coffee add 0110 返回直播房间评论信息列表 open/class/comment/list接口调用
    List<LiveCommentBean> selectLiveCommentByRoomId(Map<String, Object> map);
    //coffee add 0215 依据主播ID和课时ID来获取赠送礼物数 
    int selectLiveGiftNumber(Map<String, Object> map);
}