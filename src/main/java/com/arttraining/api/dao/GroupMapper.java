package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.arttraining.api.bean.GroupListBean;
import com.arttraining.api.bean.GroupListMyBean;
import com.arttraining.api.bean.GroupShowBean;
import com.arttraining.api.pojo.Group;

public interface GroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Group record);

    int insertSelective(Group record);

    Group selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Group record);

    int updateByPrimaryKey(Group record);
    
    //新增一个小组时执行的方法 
    int insertOneGroup(Group record);
    //依据用户类型和ID查询对应的头像
    void selectUerPicByIdAndType(Map<String,Object> map);
    //创建小组时 判断是否重复创建
    Group selectIsRepeatGroup(@Param("uid") Integer uid,
    		@Param("utype") String utype, @Param("name") String name);
    //加入小组时 同时更新小组的人数
    int updatePeopleNumByCreate(Group record);
    //退出小组时 同时更新小组的人数
    int updatePeopleNumByExit(Group record);
    
    //获取所有小组列表信息 默认是10条记录
    List<GroupListBean> selectGroupList(@Param("offset") Integer offset, 
    		@Param("limit") Integer limit);
    //查看我的小组列表信息
    List<GroupListMyBean> selectGroupListMy(Map<String,Object> map);
    
    //查询指定小组信息
    GroupShowBean selectGroupShowById(@Param("id") Integer id, 
    		@Param("limit") Integer limit);
}