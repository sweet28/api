package com.arttraining.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.HomeLikeOrCommentBean;
import com.arttraining.api.bean.HomePageStatusesBean;
import com.arttraining.api.dao.WorksMapper;
import com.arttraining.api.pojo.Works;
import com.arttraining.api.service.IWorksService;

@Service("worksService")
public class WorksService implements IWorksService {
	@Resource
	private WorksMapper worksDao;
	
	@Override
	public Works getWorksById(Integer id) {
		// TODO Auto-generated method stub
		return this.worksDao.selectByPrimaryKey(id);
	}
	@Override
	public List<HomePageStatusesBean> getWorksListByHomepage(Integer limit) {
		// TODO Auto-generated method stub
		return this.worksDao.selectWorkListByHomepage(limit);
	}
	@Override
	public HomeLikeOrCommentBean getIsLikeOrCommentOrAtt(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.worksDao.selectIsLikeOrCommentOrAtt(map);
	}
	@Override
	public List<HomePageStatusesBean> selectWorkListByUid(Integer uid,
			Integer limit) {
		// TODO Auto-generated method stub
		return this.worksDao.selectWorkListByUid(uid, limit);
	}


}
