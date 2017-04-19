package com.arttraining.api.dao;

import java.util.List;

import com.arttraining.api.beanv3.EntranceAdmissionBean;
import com.arttraining.api.pojo.AdmissionScore;

public interface AdmissionScoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdmissionScore record);

    int insertSelective(AdmissionScore record);

    AdmissionScore selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdmissionScore record);

    int updateByPrimaryKey(AdmissionScore record);
    
    //coffee add 0415 查看历年来各批次艺术类高校(专业)录取最低控制分数线接口列表信息
    List<EntranceAdmissionBean> selectBatchScoreList(String province);
}