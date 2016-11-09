package com.arttraining.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.BBSForwardMapper;
import com.arttraining.api.dao.BBSMapper;
import com.arttraining.api.pojo.BBS;
import com.arttraining.api.pojo.BBSForward;
import com.arttraining.api.service.IBBSForwardService;

@Service("bbsForwardService")
public class BBSForwardService implements IBBSForwardService {
	@Resource
	private BBSForwardMapper bbsForwardDao;
	@Resource
	private BBSMapper bbsDao;

	@Override
	public void insertOneBBSForward(BBSForward record,BBS bbs) {
		// TODO Auto-generated method stub
		//先插入一条bbs帖子动态
		this.bbsDao.insertOneBBSSelective(bbs);
		int id = bbs.getId();
		record.setForeignKey(id);
		this.bbsForwardDao.insertSelective(record);
	}

}
