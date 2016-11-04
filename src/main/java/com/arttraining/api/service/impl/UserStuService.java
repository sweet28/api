package com.arttraining.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.bean.LikeUserBean;
import com.arttraining.api.bean.LikeUserPicBean;
import com.arttraining.api.bean.UserStuShowBean;
import com.arttraining.api.dao.UserStuMapper;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.IUserStuService;

// @Service("") service名称可选,之所以写service名的原因是为了方便外部调用
@Service("userStuService")
public class UserStuService implements IUserStuService{
	@Resource
	private UserStuMapper userStuDao;

	@Override
	public UserStu getUserStuById(Integer userStuId) {
		// TODO Auto-generated method stub
		return this.userStuDao.selectByPrimaryKey(userStuId);
	}

	@Override
	public Integer updateUserStuBySelective(UserStu record) {
		// TODO Auto-generated method stub
		return this.userStuDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<LikeUserBean> listBBSLikeUserByFid(Integer fid, Integer offset,
			Integer limit) {
		// TODO Auto-generated method stub
		return this.listBBSLikeUserByFid(fid, offset, limit);
	}

	@Override
	public List<LikeUserBean> listWorksLikeUserByFid(Integer fid,
			Integer offset, Integer limit) {
		// TODO Auto-generated method stub
		return this.listWorksLikeUserByFid(fid, offset, limit);
	}

	@Override
	public List<LikeUserBean> listStatusesLikeUserByFid(Integer fid,
			Integer offset, Integer limit) {
		// TODO Auto-generated method stub
		return this.listStatusesLikeUserByFid(fid, offset, limit);
	}

	@Override
	public List<LikeUserPicBean> listBBSLikeUserPicByFid(Integer fid,
			Integer offset, Integer limit) {
		// TODO Auto-generated method stub
		return this.userStuDao.selectBBSLikeUserPicByFid(fid, offset, limit);
	}

	@Override
	public List<LikeUserPicBean> listWorksLikeUserPicByFid(Integer fid,
			Integer offset, Integer limit) {
		// TODO Auto-generated method stub
		return this.userStuDao.selectWorksLikeUserPicByFid(fid, offset, limit);
	}

	@Override
	public List<LikeUserPicBean> listStatusesLikeUserPicByFid(Integer fid,
			Integer offset, Integer limit) {
		// TODO Auto-generated method stub
		return this.userStuDao.selectStatusesLikeUserPicByFid(fid, offset, limit);
	}

	@Override
	public UserStuShowBean showUserStuById(Integer userStuId) {
		// TODO Auto-generated method stub
		return this.userStuDao.selectUserByUid(userStuId);
	}

	@Override
	public Integer setUserStuInfoBySelective(UserStu record) {
		// TODO Auto-generated method stub
		return this.userStuDao.setUserStuInfo(record);
	}
	
}