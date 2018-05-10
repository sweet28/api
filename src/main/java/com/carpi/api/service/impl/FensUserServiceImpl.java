package com.carpi.api.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.carpi.api.dao.FensAuthenticationMapper;
import com.carpi.api.dao.FensTeamMapper;
import com.carpi.api.dao.FensUserMapper;
import com.carpi.api.pojo.FensAuthentication;
import com.carpi.api.pojo.FensTeam;
import com.carpi.api.pojo.FensUser;
import com.carpi.api.service.FensUserService;

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

	// 注册
	@Override
	public JsonResult register(FensUser fensUser, String code_type, String code, String cardNumber) {
		if (fensUser.getPhone() == null || fensUser.getPhone() == "") {
			return JsonResult.build(20032, ErrorCodeConfigUtil.ERROR_MSG_ZH_20032);
		} else if (PhoneUtil.isMobile(fensUser.getPhone())) {
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
	public JsonResult createUser(FensUser fensUser, String code_type, String code, String cardNumber) {
		if (fensUser.getPhone() == null || fensUser.getPwd() == null || fensUser.getPhone() == ""
				|| fensUser.getPwd() == "" || cardNumber == null || cardNumber == "") {
			return JsonResult.build(20032, ErrorCodeConfigUtil.ERROR_MSG_ZH_20032);
		}

		SMSCheckCode smsCheckCode = new SMSCheckCode();
		smsCheckCode.setMobile(fensUser.getPhone());
		smsCheckCode.setRemarks(code_type);
		smsCheckCode.setCheckCode(code);
		SMSCheckCode smsCCode = smsCheckCodeDao.selectOneSmsInfo(smsCheckCode);
		if (smsCCode != null) {
			long useTime = smsCCode.getUsingTime().getTime();
			long nowTime = new Date().getTime();
			long diffSeconds = TimeUtil.diffSeconds(nowTime, useTime);
			if (diffSeconds > ConfigUtil.VERIFY_TIME) {
				return JsonResult.build(20048, ErrorCodeConfigUtil.ERROR_MSG_ZH_20048);
			} else {
				String pwd = MD5.encodeString(
						MD5.encodeString(fensUser.getPwd() + ConfigUtil.MD5_PWD_STR) + ConfigUtil.MD5_PWD_STR);
				FensUser fensUser2 = new FensUser();
				FensUser selectReferee = new FensUser();
				if (fensUser.getRefereePhone() != null) {

					selectReferee = fensUserMapper.selectReferee(fensUser);
					if (selectReferee == null) {
						return JsonResult.build(500, "不存在该邀请人");
					}

					fensUser2.setRefereePhone(selectReferee.getRefereePhone());
					fensUser2.setRefereeId(selectReferee.getRefereeId());
				}
				// 身份证校验
				boolean idCard = IDCardUtil.isIDCard(cardNumber);
				if (idCard == false) {
					return JsonResult.build(500, "请检查身份证是否正确");
				}
				fensUser2.setPhone(fensUser.getPhone());
				fensUser2.setName(fensUser.getName());
				fensUser2.setPwd(pwd);
				fensUser2.setCreateDate(new Date());
				int result = fensUserMapper.insertSelective(fensUser2);
				if (result == 1) {
					if (fensUser.getRefereePhone() != null) {
						// 粉丝注册成功后，把信息插入粉丝团表
						FensTeam fensTeam = new FensTeam();
						// 被邀请人id
						fensTeam.setInviteeId(fensUser2.getId());
						// 被邀请人姓名
						fensTeam.setInviteeName(fensUser2.getName());
						// 被邀请人手机号码
						fensTeam.setInviteePhone(fensUser2.getPhone());
						// 粉丝Id(邀请人id）
						fensTeam.setFensUserId(fensUser2.getRefereeId());
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
		SMSCheckCode smsCCode = smsCheckCodeDao.selectOneSmsInfo(smsCheckCode);
		if (smsCCode == null) {
			return JsonResult.build(20049, ErrorCodeConfigUtil.ERROR_MSG_ZH_20049);
		}
		long useTime = smsCCode.getUsingTime().getTime();
		long nowTime = new Date().getTime();
		long diffSeconds = TimeUtil.diffSeconds(nowTime, useTime);
		if (diffSeconds > ConfigUtil.VERIFY_TIME) {
			return JsonResult.build(20048, ErrorCodeConfigUtil.ERROR_MSG_ZH_20048);
		}
		String NewPwd = MD5
				.encodeString(MD5.encodeString(fensUser.getPwd() + ConfigUtil.MD5_PWD_STR) + ConfigUtil.MD5_PWD_STR);
		FensUser fensUser2 = new FensUser();
		fensUser2.setPhone(fensUser.getPhone());
		fensUser2.setPwd(NewPwd);
		int result = fensUserMapper.updatePwd(fensUser2);
		if (result == 1) {
			return JsonResult.ok();
		}
		return JsonResult.build(500, "修改失败，请联系管理员");
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
		user.setPwd(null);
		user.setCapitalPwd(null);
		user.setBak1(accessToken);
		return JsonResult.ok(user);
	}

	// 身份验证(插入身份证信息)
	@Override
	public JsonResult addCard(FensAuthentication fensAuthentication) {
		// 身份校验
		boolean idCard = IDCardUtil.isIDCard(fensAuthentication.getCardNumber());
		if (idCard == false) {
			return JsonResult.build(500, "请检查身份证是否正确");
		}
		// 根据身份证查询，看看是否被绑定
		FensAuthentication selectCardInfo = fensAuthenticationMapper.selectCardInfo(fensAuthentication.getCardNumber());
		if (selectCardInfo != null) {
			return JsonResult.build(500, "该身份证已被绑定，如需进一步操作，请联系管理员");
		}
		// 绑定
		int result = fensAuthenticationMapper.insertSelective(fensAuthentication);
		if (result == 1) {
			return JsonResult.ok();
		} else {
			return JsonResult.build(500, "填写失败，请联系管理员");
		}
	}

}
