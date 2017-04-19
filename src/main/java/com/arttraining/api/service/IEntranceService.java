package com.arttraining.api.service;

import java.util.List;
import java.util.Map;

import com.arttraining.api.beanv3.EntranceAdmissionBean;
import com.arttraining.api.beanv3.EntranceCategoryBean;
import com.arttraining.api.beanv3.EntranceCollegeBean;
import com.arttraining.api.beanv3.EntranceLineBean;
import com.arttraining.api.beanv3.EntranceProvinceBean;

public interface IEntranceService {
	//coffee add 0415 查看历年来各批次艺术类高校(专业)录取最低控制分数线接口列表信息
    List<EntranceAdmissionBean> getBatchScoreList(String province);
    //coffee add 0415 查看艺术类术科统考本科资格线列表
    List<EntranceLineBean> getLineList(String province);
    
    //coffee add 0417 选择报考生源地列表
    List<EntranceProvinceBean> getProvinceList(Map<String, Object> map);
    //coffee add 0417 选择专业类别列表
    List<EntranceCategoryBean> getCategoryList(Map<String, Object> map);
    //coffee add 0417 输入省术科统考成绩和文化成绩 推荐相应的院校列表
    List<EntranceCollegeBean> getCollegeList(Map<String, Object> map);
}
