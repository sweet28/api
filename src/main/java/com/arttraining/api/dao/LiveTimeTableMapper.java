package com.arttraining.api.dao;

import java.util.List;
import java.util.Map;

import com.arttraining.api.beanv2.LiveTimeTableBean;
import com.arttraining.api.pojo.LiveTimeTable;

public interface LiveTimeTableMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LiveTimeTable record);

    int insertSelective(LiveTimeTable record);

    LiveTimeTable selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LiveTimeTable record);

    int updateByPrimaryKeyWithBLOBs(LiveTimeTable record);

    int updateByPrimaryKey(LiveTimeTable record);
    
    //coffee add 0113 判断是否设置过相同课表名称
    List<LiveTimeTableBean> selectLiveTimeTableByUid(Map<String, Object> map);
}