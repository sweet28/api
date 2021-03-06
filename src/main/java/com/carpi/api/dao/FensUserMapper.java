package com.carpi.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.carpi.api.pojo.FensUser;

public interface FensUserMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(FensUser record);

	int insertSelective(FensUser record);

	FensUser selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(FensUser record);

	int updateByPrimaryKey(FensUser record);

	// 根据手机号查询用户是否注册
	FensUser selectRegister(FensUser fensUser);

	// 查询推荐人是否存在
	FensUser selectReferee(String refereePhone);
	
	// 查询推荐人是否存在
	FensUser selectIsUsePhone(String phone);

	// 更新密码
	int updatePwd(FensUser fensUser);
	
	// 所有用户列表
	List<FensUser> selectAllUserNoTJ();
	
	List<FensUser> selectAllUserESPTdel();

	// 粉丝团列表
	List<FensUser> selectAllUser(String phone);

	// <!--直推人数 -->
	int selectAllSum(String phone);

	// 根据身份证号查询数量
	List<FensUser> selectICDNum(String bak2);

	// 根据旧密码查询用户
	FensUser selectOldCapitalPwd(FensUser fensUser);

	// 根据旧密码查询用户
	FensUser selectOldPwd(FensUser fensUser);

	Integer selectRefereeYXC(String refereePhone);
	
	// 7月-8月直推送20积分活动查询直推粉丝
	List<FensUser> selectZTuser7yue(String refereePhone);

	// 校验资金密码
	FensUser selectzjPwd(@Param("fensUserId") Integer fensUserId, @Param("capitalPwd") String capitalPwd);

	FensUser selectRegJuneFF(Integer id);
}