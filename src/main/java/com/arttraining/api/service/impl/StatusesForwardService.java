package com.arttraining.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.StatusesAttachmentMapper;
import com.arttraining.api.dao.StatusesForwardMapper;
import com.arttraining.api.dao.StatusesMapper;
import com.arttraining.api.pojo.Statuses;
import com.arttraining.api.pojo.StatusesAttachment;
import com.arttraining.api.pojo.StatusesForward;
import com.arttraining.api.service.IStatusesForwardService;

@Service("statusesForwardService")
public class StatusesForwardService implements IStatusesForwardService {
	@Resource
	private StatusesMapper statusDao;
	@Resource
	private StatusesForwardMapper statusForwardDao;
	@Resource
	private StatusesAttachmentMapper statusAttrDao;
	
	@Override
	public void insertOneStatusForward(StatusesForward statusForward,
			Statuses status,StatusesAttachment statusAttr) {
		//先插入一条动态信息
		this.statusDao.insertOneStatusSelective(status);
		int id = status.getId();
		statusForward.setForeignKey(id);
		this.statusForwardDao.insertSelective(statusForward);
		//新增动态附件信息
		int rtn =this.statusAttrDao.insertStatusAttrByForward(statusAttr);
		if(rtn>0) {
			int attr_id =statusAttr.getId();
			statusAttr.setForeignKey(id);
			statusAttr.setId(attr_id);
			this.statusAttrDao.updateByPrimaryKeySelective(statusAttr);
		}
	}

}
