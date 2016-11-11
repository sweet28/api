package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.bean.GroupListBean;
import com.arttraining.api.bean.GroupListMyBean;
import com.arttraining.api.bean.GroupShowBean;
import com.arttraining.api.pojo.Group;
import com.arttraining.api.pojo.GroupUser;

public interface IGroupService {
	//创建小组时 执行的方法
	void insertOneGroupAndUser(Group group,GroupUser groupUser);
	//依据用户类型和ID查询对应的头像
    void getUerPicByIdAndType(Map<String,Object> map);
    //创建小组时 判断是否重复创建
    Group getIsRepeatGroup(Integer uid,String utype,String name);

    //获取所有小组列表信息 默认是10条记录
    List<GroupListBean> getGroupList(Integer offset, Integer limit);
  //查看我的小组列表信息
    List<GroupListMyBean> getGroupListMy(Map<String,Object> map);
    
  //查询指定小组信息
   GroupShowBean getGroupShowById(Integer id,Integer limit);
}
