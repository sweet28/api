package com.arttraining.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.BBSAttachmentMapper;
import com.arttraining.api.dao.BBSForwardMapper;
import com.arttraining.api.dao.BBSMapper;
import com.arttraining.api.dao.UserStuMapper;
import com.arttraining.api.pojo.BBS;
import com.arttraining.api.pojo.BBSAttachment;
import com.arttraining.api.pojo.BBSForward;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.IBBSForwardService;

@Service("bbsForwardService")
public class BBSForwardService implements IBBSForwardService {
	@Resource
	private BBSForwardMapper bbsForwardDao;
	@Resource
	private BBSMapper bbsDao;
	@Resource
	private UserStuMapper userStuDao;
	@Resource
	private BBSAttachmentMapper bbsAttachmentDao;

	@Override
	public void insertOneBBSForward(BBSForward record,BBS bbs,UserStu user,BBSAttachment bbsAttr) {
		// TODO Auto-generated method stub
		//先插入一条bbs帖子动态
		this.bbsDao.insertOneBBSSelective(bbs);
		//然后查询bbs转发信息
		int id = bbs.getId();
		record.setForeignKey(id);
		this.bbsForwardDao.insertSelective(record);
		//最后更新用户数量
		this.userStuDao.updateNumberBySelective(user);
		
		//先新增帖子附件表
		int rtn = this.bbsAttachmentDao.insertBBSAttrByForward(bbsAttr);
		if(rtn>0) {
			Integer att_id = bbsAttr.getId();
			//更新帖子附件的信息
			bbsAttr.setForeignKey(id);
			bbsAttr.setId(att_id);
			this.bbsAttachmentDao.updateByPrimaryKeySelective(bbsAttr);
		}
		
	}

}
