package com.arttraining.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.WorksAttchmentMapper;
import com.arttraining.api.pojo.WorksAttchment;
import com.arttraining.api.service.IWorksAttchmentService;
@Service("worksAttchmentService")
public class WorksAttchmentService implements IWorksAttchmentService {
	@Resource
	private WorksAttchmentMapper worksAttchmentDao;
	@Override
	public WorksAttchment getOneAttByWorkId(Integer id) {
		// TODO Auto-generated method stub
		return this.worksAttchmentDao.selectOneAttByWorkId(id);
	}
	
}
