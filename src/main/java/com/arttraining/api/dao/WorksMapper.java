package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;



import org.apache.ibatis.annotations.Param;

import com.arttraining.api.bean.HomeLikeOrCommentBean;
import com.arttraining.api.bean.HomePageStatusesBean;
import com.arttraining.api.pojo.Works;

public interface WorksMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Works record);

    int insertSelective(Works record);

    Works selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Works record);

    int updateByPrimaryKey(Works record);
    
    //点赞帖子时 更新帖子表的点赞数量
    int updateWorkLikeNumByPrimaryKey(Integer id);
    //获取首页帖子列表信息 默认显示10条记录
    List<HomePageStatusesBean> selectWorkListByHomepage(Integer limit);
    //是否点评或点赞信息
    //HomeLikeOrCommentBean selectIsLikeOrCommentOrAtt(@Param("u_id")Integer u_id,
    		//@Param("s_id")Integer s_id);
    HomeLikeOrCommentBean selectIsLikeOrCommentOrAtt(Map<String,Object> map);
    //查询指定用户id 发布的作品动态 默认显示10条记录
    List<HomePageStatusesBean> selectWorkListByUid(@Param("uid")Integer uid,
    		@Param("limit")Integer limit);
}