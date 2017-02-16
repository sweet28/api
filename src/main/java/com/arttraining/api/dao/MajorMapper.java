package com.arttraining.api.dao;

import java.util.List;

import com.arttraining.api.bean.MajorLevelListBean;
import com.arttraining.api.bean.MajorListBean;
import com.arttraining.api.pojo.Major;

public interface MajorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Major record);

    int insertSelective(Major record);

    Major selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Major record);

    int updateByPrimaryKey(Major record);
    
    //获取一级专业列表--major/list/level_one接口调用
    List<MajorListBean> selectOneLevelMajorByList();
    //获取专业列表 --major/list接口调用
    MajorLevelListBean selectMajorNodeById(Integer id);
    //依据父节点 获取某专业下的子节点列表--major/list接口调用
    List<MajorLevelListBean> selectMajorNodeByFid(Integer father_id);
    //获取所有一级专业列表ID--major/list接口调用
    List<Integer> selectAllOneLevelMajor();
    
    //获取所有二级专业列表 依据一级专业ID major/list/level_two接口
    List<MajorListBean> selectTwoLevelMajorByList(Integer father_id);
}