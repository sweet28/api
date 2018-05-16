package com.carpi.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.arttraining.api.dao.SMSCheckCodeMapper;
import com.arttraining.api.dao.TokenMapper;
import com.arttraining.api.pojo.SMSCheckCode;
import com.arttraining.api.pojo.Token;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.IDCardUtil;
import com.arttraining.commons.util.JsonResult;
import com.arttraining.commons.util.MD5;
import com.arttraining.commons.util.PhoneUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;
import com.arttraining.commons.util.TokenUtil;
import com.arttraining.commons.util.pay.IpRequestUtil;
import com.carpi.api.dao.FensAuthenticationMapper;
import com.carpi.api.dao.FensComputingPowerMapper;
import com.carpi.api.dao.FensLoginStateMapper;
import com.carpi.api.dao.FensMinerMapper;
import com.carpi.api.dao.FensTeamMapper;
import com.carpi.api.dao.FensUserMapper;
import com.carpi.api.pojo.FensAuthentication;
import com.carpi.api.pojo.FensComputingPower;
import com.carpi.api.pojo.FensLoginState;
import com.carpi.api.pojo.FensMiner;
import com.carpi.api.pojo.FensTeam;
import com.carpi.api.pojo.FensUser;
import com.carpi.api.service.FensUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class FensUserServiceImpl implements FensUserService {

	@Autowired
	private FensUserMapper fensUserMapper;

	@Autowired
	private SMSCheckCodeMapper smsCheckCodeDao;

	@Autowired
	private TokenMapper tokenDao;

	@Autowired
	private FensAuthenticationMapper fensAuthenticationMapper;

	@Autowired
	private FensTeamMapper fensTeamMapper;
	
	@Autowired
	private FensComputingPowerMapper fensComputingPowerMapper;
	
	@Autowired
	private FensLoginStateMapper fensLoginStateMapper;
	
	@Autowired
	private FensMinerMapper fensMinerMapper;

	// 注册
	@Override
	@Transactional
	public JsonResult register(FensUser fensUser, String code_type, String code, String cardNumber) {
		if (fensUser.getPhone() == null || fensUser.getPhone() == "") {
			return JsonResult.build(20032, ErrorCodeConfigUtil.ERROR_MSG_ZH_20032);
		} else if (PhoneUtil.isMobile(fensUser.getPhone())) {
			// 查询该手机号是否注册过
			FensUser user = fensUserMapper.selectRegister(fensUser);
			if (user != null) {
				return JsonResult.build(20024, ErrorCodeConfigUtil.ERROR_MSG_ZH_20024);
			} else {
				return createUser(fensUser, code_type, code, cardNumber);
			}
		} else {
			return JsonResult.build(20044, ErrorCodeConfigUtil.ERROR_MSG_ZH_20044);
		}
	}

	// 添加粉丝
	@Transactional
	public JsonResult createUser(FensUser fensUser, String code_type, String code, String cardNumber) {
		if (fensUser.getPhone() == null || fensUser.getPwd() == null || fensUser.getPhone() == ""
				|| fensUser.getPwd() == "" || cardNumber == null || cardNumber == "") {
			return JsonResult.build(20032, ErrorCodeConfigUtil.ERROR_MSG_ZH_20032);
		}

		SMSCheckCode smsCheckCode = new SMSCheckCode();
		smsCheckCode.setMobile(fensUser.getPhone());
		smsCheckCode.setRemarks(code_type);
		smsCheckCode.setCheckCode(code);

		SMSCheckCode smsCCode = smsCheckCodeDao.selectByMobileAndType(smsCheckCode);// .selectOneSmsInfo(smsCheckCode);
		if (smsCCode != null) {
			long expireTime = smsCCode.getExpireTime().getTime();
			long nowTime = new Date().getTime();
			long expireSeconds = TimeUtil.diffSeconds(expireTime, nowTime);
			if (expireSeconds < 0) {
				return JsonResult.build(20048, ErrorCodeConfigUtil.ERROR_MSG_ZH_20048);
			} else {
				// smsCCode.setIsUsed(1);
				// smsCCode.setUsingTime(TimeUtil.getTimeStamp());
				// smsCheckCodeDao.updateByPrimaryKeySelective(smsCCode);

				String pwd = MD5.encodeString(
						MD5.encodeString(fensUser.getPwd() + ConfigUtil.MD5_PWD_STR) + ConfigUtil.MD5_PWD_STR);
				FensUser fensUser2 = new FensUser();
				FensUser selectReferee = new FensUser();
				if (fensUser.getRefereePhone() != null && fensUser.getRefereePhone() != "") {
					// 查询是否存在该邀请人
					selectReferee = fensUserMapper.selectReferee(fensUser.getRefereePhone());
					if (selectReferee == null) {
						return JsonResult.build(500, "不存在该邀请人");
					}

					fensUser2.setRefereePhone(selectReferee.getPhone());
					fensUser2.setRefereeId(selectReferee.getId());
				}
				// 身份证校验
				boolean idCard = IDCardUtil.isIDCard(cardNumber);
				if (idCard == false) {
					return JsonResult.build(500, "请检查身份证是否正确");
				}
				fensUser2.setPhone(fensUser.getPhone());
				fensUser2.setName(fensUser.getName());
				fensUser2.setPwd(pwd);
				fensUser2.setCreateDate(TimeUtil.getTimeStamp());
				fensUser2.setBak2(cardNumber);
				// 校验身份证号码是否被绑定
				FensAuthentication cardInfo = fensAuthenticationMapper.selectCardInfo(cardNumber);
				if (cardInfo != null) {
					return JsonResult.build(500, "该身份证已被绑定，如需进一步操作，请联系管理员");
				}
				smsCCode.setIsUsed(1);
				smsCCode.setUsingTime(TimeUtil.getTimeStamp());
				smsCheckCodeDao.updateByPrimaryKeySelective(smsCCode);
				// 添加用户信息到粉丝表
				int result = fensUserMapper.insertSelective(fensUser2);
				if (result == 1) {
					// 查询用户信息
					FensUser user = fensUserMapper.selectReferee(fensUser.getPhone());
					// 将身份证号码和姓名插入证件表
					FensAuthentication fensAuthentication = new FensAuthentication();
					// 身份证姓名
					fensAuthentication.setBak1(fensUser.getName());
					// 身份证号码
					fensAuthentication.setCardNumber(cardNumber);
					// 粉丝id
					fensAuthentication.setFensUserId(user.getId());
					// 时间
					fensAuthentication.setCreateDate(TimeUtil.getTimeStamp());
					int fensAuthStatus = fensAuthenticationMapper.insertSelective(fensAuthentication);
					// 将信息插入粉丝安全认证表
					if (fensAuthStatus != 1) {
						ServerLog.getLogger().warn("插入粉丝安全认证表失败，粉丝id为：" + user.getId());
					}

					//添加记录到粉丝矿机表（1代表A矿机，2代表B矿机）（默认A矿机的一星矿机）
					FensMiner fensMiner = new FensMiner();
					fensMiner.setCreateDate(TimeUtil.getTimeStamp());
					//粉丝Id
					fensMiner.setFensUserId(user.getId());
					//矿机类型（1代表A矿机）
					fensMiner.setMinerType(1);
					//矿机类型（1星）
					fensMiner.setBak1(String.valueOf(1));
					//矿机id
					fensMiner.setMinerId(1);
					//矿机算力
					fensMiner.setMinerComputingPower(0.005);
					//添加粉丝矿机表成功（分配A型号1星矿机）
					int statuss = fensMinerMapper.insertSelective(fensMiner);
					if (statuss != 1) {
						ServerLog.getLogger().warn("分配A型号1星矿机失败" + user.getId());
					}
					
					//注册后 ，在A、B两个矿池表添加一条记录
					
					
					
					if (fensUser.getRefereePhone() != null && fensUser.getRefereePhone() != "") {
						// 粉丝注册成功后，把信息插入粉丝团表
						FensTeam fensTeam = new FensTeam();
						// 被邀请人id
						fensTeam.setInviteeId(user.getId());
						// 被邀请人姓名
						fensTeam.setInviteeName(fensUser2.getName());
						// 被邀请人手机号码
						fensTeam.setInviteePhone(fensUser2.getPhone());
						// 粉丝Id(邀请人id）
						fensTeam.setFensUserId(fensUser2.getRefereeId());
						//添加时间
						fensTeam.setCreateDate(TimeUtil.getTimeStamp());

						// 先判断fensTeam（粉丝团表）是否存在和注册所填的手机号码是否相同
						FensTeam team = fensTeamMapper.selectFensTeam(fensUser2.getPhone());
						if (team != null) {
							ServerLog.getLogger().warn("粉丝团已存在该手机号码，被邀请人id：" + fensUser2.getId());
							return JsonResult.build(500, "粉丝团已存在该手机号码，请联系管理员");
						}
						// 插入数据库
						int status = fensTeamMapper.insertSelective(fensTeam);
						if (status != 1) {
							ServerLog.getLogger().warn("插入粉丝团失败，被邀请人id：" + fensUser2.getId());
						}
					}
					// 设置短信已使用
					smsCCode.setIsUsed(2);
					int staus = smsCheckCodeDao.updateByPrimaryKeySelective(smsCCode);
					return JsonResult.ok();
				} else {
					return JsonResult.build(20040, ErrorCodeConfigUtil.ERROR_MSG_ZH_20040);
				}
			}
		} else {
			return JsonResult.build(20049, ErrorCodeConfigUtil.ERROR_MSG_ZH_20049);
		}

	}

	// 忘记密码
	@Override
	public JsonResult forgetPwd(FensUser fensUser, String code_type, String code) {
		// 校验验证码
		SMSCheckCode smsCheckCode = new SMSCheckCode();
		smsCheckCode.setMobile(fensUser.getPhone());
		smsCheckCode.setRemarks(code_type);
		smsCheckCode.setCheckCode(code);
		
		FensUser user = fensUserMapper.selectRegister(fensUser);
		if (user == null) {
			return JsonResult.build(20022, ErrorCodeConfigUtil.ERROR_MSG_ZH_20022);
		}

		SMSCheckCode smsCCode = smsCheckCodeDao.selectByMobileAndType(smsCheckCode);
		if (smsCCode != null) {
			long expireTime = smsCCode.getExpireTime().getTime();
			long nowTime = new Date().getTime();
			long expireSeconds = TimeUtil.diffSeconds(expireTime, nowTime);
			if (expireSeconds < 0) {
				return JsonResult.build(20048, ErrorCodeConfigUtil.ERROR_MSG_ZH_20048);
			} else {
				smsCCode.setIsUsed(1);
				smsCCode.setUsingTime(TimeUtil.getTimeStamp());
				smsCheckCodeDao.updateByPrimaryKeySelective(smsCCode);
				String NewPwd = MD5.encodeString(
						MD5.encodeString(fensUser.getPwd() + ConfigUtil.MD5_PWD_STR) + ConfigUtil.MD5_PWD_STR);
				FensUser fensUser2 = new FensUser();
				fensUser2.setPhone(fensUser.getPhone());
				fensUser2.setPwd(NewPwd);
				fensUser2.setCreateDate(TimeUtil.getTimeStamp());
				int result = fensUserMapper.updatePwd(fensUser2);
				if (result == 1) {
					// 设置短信已使用
					smsCCode.setIsUsed(2);
					int staus = smsCheckCodeDao.updateByPrimaryKeySelective(smsCCode);
					return JsonResult.ok();
				}

				return JsonResult.build(500, "修改失败，请联系管理员");
			}
		} else {
			return JsonResult.build(20049, ErrorCodeConfigUtil.ERROR_MSG_ZH_20049);
		}
	}

	// 登入
	@Override
	public JsonResult login(FensUser fensUser) {
		if (fensUser.getPhone() == null || fensUser.getPwd() == null || fensUser.getPhone() == ""
				|| fensUser.getPwd() == "") {
			return JsonResult.build(20032, ErrorCodeConfigUtil.ERROR_MSG_ZH_20032);
		}
		// 查看是否存在该用户
		FensUser user = fensUserMapper.selectRegister(fensUser);
		if (user == null) {
			return JsonResult.build(20022, ErrorCodeConfigUtil.ERROR_MSG_ZH_20022);
		}
		String pwd = user.getPwd();
		if (!MD5.check(MD5.encodeString(fensUser.getPwd() + ConfigUtil.MD5_PWD_STR) + ConfigUtil.MD5_PWD_STR, pwd)) {
			return JsonResult.build(20023, ErrorCodeConfigUtil.ERROR_MSG_ZH_20023);
		}
		String accessToken = TokenUtil.generateToken(fensUser.getPhone());
		// 1.判断数据库是否有该用户的登录token 如果没有 则新增 如果有 则修改
		Integer user_id = fensUser.getId();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		Token token = tokenDao.selectOneTokenInfo(map);
		Date date = new Date();
		if (token != null) {
			token.setToken(accessToken);
			token.setLoginTime(date);
			token.setIsDeleted(0);
			tokenDao.updateByPrimaryKeySelective(token);
		} else {
			token = new Token();
			token.setLoginTime(date);
			token.setToken(accessToken);
			token.setUserId(user_id);
			tokenDao.insertSelective(token);
		}
		//添加登入日志（登入状态表）
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 
//		String ip = IpRequestUtil.getIpAddr(request).toString();
		FensLoginState fensLoginState = new FensLoginState();
		String ip = request.getRemoteAddr();
		fensLoginState.setFensUserId(user.getId());
		fensLoginState.setIp(ip);
		fensLoginState.setCreateDate(TimeUtil.getTimeStamp());
		fensLoginState.setLoginDate(TimeUtil.getTimeStamp());
		int result = fensLoginStateMapper.insertSelective(fensLoginState);
		if (result != 1) {
			ServerLog.getLogger().warn("插入登入日志失败，粉丝id：" + fensLoginState.getFensUserId());
		}
		user.setPwd(null);
		user.setCapitalPwd(null);
		user.setBak1(accessToken);
		return JsonResult.ok(user);
	}

	// 粉丝团列表
	@Override
	public PageInfo<FensTeam> selectAll(Integer page, Integer num, Integer fensUserId, String type) {
		if ("all".equals(type)) {
			List<FensTeam> list = fensTeamMapper.selectAll(fensUserId);
			PageInfo<FensTeam> pageInfo = new PageInfo<>(list);
			return pageInfo;
		}
		PageHelper.startPage(page, num);
		List<FensTeam> list = fensTeamMapper.selectAll(fensUserId);
		PageInfo<FensTeam> pageInfo = new PageInfo<>(list);
		pageInfo.getTotal();
		return pageInfo;
	}

	//粉丝算力
	@Override
	public PageInfo<FensComputingPower> selectComputingPower(Integer page, Integer num, Integer fensUserId) {
		PageHelper.startPage(page, num);
		List<FensComputingPower> list = fensComputingPowerMapper.selectAll(fensUserId);
		PageInfo<FensComputingPower> pageInfo = new PageInfo<>(list);
 		return pageInfo;
	}
	
	//粉丝算力求和
	@Override
	public JsonResult selectSum(Integer fensUserId) {
		Double sum = fensComputingPowerMapper.sum(fensUserId);
		return JsonResult.ok(sum);
	}

	//添加粉丝算力
	@Override
	public JsonResult addselectComputingPower(FensComputingPower fensComputingPower) {
        int result = fensComputingPowerMapper.insertSelective(fensComputingPower);
        if (result == 1) {
			return JsonResult.ok();
		}
		return JsonResult.build(500, "新增数据失败");
	}

	//修改粉丝算力
	@Override
	public JsonResult updateComputingPower(FensComputingPower fensComputingPower) {
		int result = fensComputingPowerMapper.updateByPrimaryKeySelective(fensComputingPower);
		 if (result == 1) {
				return JsonResult.ok();
			}
			return JsonResult.build(500, "修改数据失败");
	}

	//粉丝登入状态列表
	@Override
	public PageInfo<FensLoginState> selectStatus(Integer page, Integer num) {
		PageHelper.startPage(page, num);
		List<FensLoginState> list = fensLoginStateMapper.selectAll();
		PageInfo<FensLoginState> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	//插入粉丝登入状态
	@Override
	public JsonResult addFensLoginState(FensLoginState fensLoginState) {
		int result = fensLoginStateMapper.insertSelective(fensLoginState);
		if (result == 1) {
			return JsonResult.ok();
		}
		return JsonResult.build(500, "添加粉丝登入状态失败");
	}
	
	// 粉丝团列表2
	@Override
	public PageInfo<FensUser> selectAllUser(Integer page, Integer num, String phone, String type) {
		if ("all".equals(type)) {
			List<FensUser> list = fensUserMapper.selectAllUser(phone);
			PageInfo<FensUser> pageInfo = new PageInfo<>(list);
			
			List<FensUser> listParentRecord = new ArrayList<FensUser>();
			getTreeChildRecord(listParentRecord,phone);
			pageInfo.setPages(listParentRecord.size());
			
			return pageInfo;
		}
		PageHelper.startPage(page, num);
		List<FensUser> list = fensUserMapper.selectAllUser(phone);
		PageInfo<FensUser> pageInfo = new PageInfo<>(list);
		pageInfo.getTotal();
		
//			pageInfo.setPages(getEmployeeBysup(phone));
		
		return pageInfo;
	}
	
	/** 
	    * 说明方法描述：递归查询子节点 
	    *  
	    * @param listParentRecord 粉丝团集合
	    * @param parentUuid 父节点id 
	    * @return 
	    */  
	
	private List<FensUser> getTreeChildRecord(List<FensUser> listParentRecord,String parentUuid) {
//		List<FensUser> listParentRecord = new ArrayList<FensUser>();
		List<FensUser> childList = fensUserMapper.selectAllUser(parentUuid);

		// 遍历tmpList，找出所有的根节点和非根节点
		if (childList != null && childList.size() > 0) {
			for (FensUser record : childList) {
				listParentRecord.add(record);
				getTreeChildRecord(listParentRecord,record.getPhone());
			}
		}
		return listParentRecord;

//		// 遍历tmpList，找出所有的根节点和非根节点
//		if (childList != null && childList.size() > 0) {
//			for (FensUser record : childList) {
//				// 对比找出父节点
//				if (record.getPhone().equals(parentUuid)) {
//					listParentRecord.add(record);
//				} else {
//					listNotParentRecord.add(record);
//				}
//
//			}
//		}
//		// 查询子节点
//		if (listParentRecord.size() > 0) {
//			for (FensUser record : listParentRecord) {
//				// 递归查询子节点
//				getTreeChildRecord(listNotParentRecord, record.getPhone());
//			}
//		}
//		return listParentRecord;
	}
}
