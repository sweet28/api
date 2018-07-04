package com.carpi.api.service.impl;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.carpi.api.dao.APoolMapper;
import com.carpi.api.dao.BPoolMapper;
import com.carpi.api.dao.BankCardMapper;
import com.carpi.api.dao.FensAuthenticationMapper;
import com.carpi.api.dao.FensComputingPowerMapper;
import com.carpi.api.dao.FensEarnMapper;
import com.carpi.api.dao.FensLoginStateMapper;
import com.carpi.api.dao.FensMinerMapper;
import com.carpi.api.dao.FensTeamMapper;
import com.carpi.api.dao.FensTransactionMapper;
import com.carpi.api.dao.FensUserInfoMapper;
import com.carpi.api.dao.FensUserMapper;
import com.carpi.api.dao.FensWalletMapper;
import com.carpi.api.pojo.APool;
import com.carpi.api.pojo.BPool;
import com.carpi.api.pojo.BankCard;
import com.carpi.api.pojo.FensAuthentication;
import com.carpi.api.pojo.FensComputingPower;
import com.carpi.api.pojo.FensEarn;
import com.carpi.api.pojo.FensLoginState;
import com.carpi.api.pojo.FensMiner;
import com.carpi.api.pojo.FensTeam;
import com.carpi.api.pojo.FensTransaction;
import com.carpi.api.pojo.FensUser;
import com.carpi.api.pojo.FensUserInfo;
import com.carpi.api.pojo.FensWallet;
import com.carpi.api.service.FensUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.mysql.fabric.xmlrpc.base.Data;

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

	@Autowired
	private APoolMapper apoolMapper;

	@Autowired
	private BPoolMapper bpoolMapper;

	@Autowired
	private FensWalletMapper fensWalletMapper;

	@Autowired
	private FensTransactionMapper fensTransactionMapper;

	@Autowired
	private BankCardMapper bankcardMapper;
	
	@Autowired
	private FensEarnMapper fensEarnMapper;
	
	@Autowired
	private FensUserInfoMapper fensUserInfoMapper;

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

	@Override
	public FensUser info(FensUser fensUser) {
		FensUser fu = new FensUser();
		fu = fensUserMapper.selectByPrimaryKey(fensUser.getId());
		return fu;
	}

	// 添加粉丝
	@Transactional
	public JsonResult createUser(FensUser fensUser, String code_type, String code, String cardNumber) {
		if (fensUser.getPhone() == null || fensUser.getPwd() == null || fensUser.getPhone() == ""
				|| fensUser.getPwd() == "" || cardNumber == null || cardNumber == "") {
			return JsonResult.build(20032, ErrorCodeConfigUtil.ERROR_MSG_ZH_20032);
		}
		
		FensUser fu = new FensUser();
		fu = fensUserMapper.selectIsUsePhone(fensUser.getPhone());
		if(fu != null){
			return JsonResult.build(500, "该手机号已经注册过");
		}

		SMSCheckCode smsCheckCode = new SMSCheckCode();
		smsCheckCode.setMobile(fensUser.getPhone());
		// smsCheckCode.setRemarks(code_type);
		smsCheckCode.setCheckCode(code);

		SMSCheckCode smsCCode = smsCheckCodeDao.selectByMobileAndType(smsCheckCode);// .selectOneSmsInfo(smsCheckCode);
		if (smsCCode != null) {
			long expireTime = smsCCode.getExpireTime().getTime();
			long nowTime = new Date().getTime();
			long expireSeconds = TimeUtil.diffSeconds(expireTime, nowTime);
			if (expireSeconds < 0) {
				return JsonResult.build(20048, ErrorCodeConfigUtil.ERROR_MSG_ZH_20048);
			} else {

				String pwd = MD5.encodeString(
						MD5.encodeString(fensUser.getPwd() + ConfigUtil.MD5_PWD_STR) + ConfigUtil.MD5_PWD_STR);
				FensUser fensUser2 = new FensUser();
				FensUser selectReferee = new FensUser();
				if (!StringUtils.isEmpty(fensUser.getRefereePhone())) {
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
				// 粉丝身份证图片
				fensUser2.setBak3(fensUser.getBak3());
				fensUser2.setAttachment("1");
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

					// 添加记录到粉丝矿机表（1代表A矿机，2代表B矿机）（默认A矿机的一星矿机）
					FensMiner fensMiner = new FensMiner();
					fensMiner.setCreateDate(TimeUtil.getTimeStamp());
					// 粉丝Id
					fensMiner.setFensUserId(user.getId());
					// 矿机类型（1代表A矿机）
					fensMiner.setMinerType(1);
					// 矿机类型（1星）
					fensMiner.setBak1(String.valueOf(1));
					// 矿机id
					fensMiner.setMinerId(1);
					// 矿机算力
					fensMiner.setMinerComputingPower(0.005);
					// 矿机价格
					fensMiner.setBeyong1("10");
					// 是否赠送 1 赠送
					fensMiner.setIsUserGoumai("1");
					// 添加粉丝矿机表成功（分配A型号1星矿机）
					int statuss = fensMinerMapper.insertSelective(fensMiner);
					if (statuss != 1) {
						ServerLog.getLogger().warn("分配A型号1星矿机失败,粉丝id :" + user.getId());
					}

					// 注册后 ，在A、B两个矿池表添加一条记录
					// 注册后 ，在A、B两个矿池表添加一条记录
					APool aPool = new APool();
					APool aPool2 = new APool();
					APool aPool3 = new APool();
					APool aPool4 = new APool();
					// 四种矿机对应的矿池，插入4条记录
					aPool.setFensUserId(user.getId());
					aPool.setType(1);

					aPool2.setFensUserId(user.getId());
					aPool2.setType(2);

					aPool3.setFensUserId(user.getId());
					aPool3.setType(3);

					aPool4.setFensUserId(user.getId());
					aPool4.setType(4);
					// 插入A矿池表
					apoolMapper.insertSelective(aPool);
					apoolMapper.insertSelective(aPool2);
					apoolMapper.insertSelective(aPool3);
					apoolMapper.insertSelective(aPool4);

					BPool bPool = new BPool();
					bPool.setFensUserId(user.getId());

					// 插入B矿池表
					bpoolMapper.insertSelective(bPool);

					// 钱包表插入一条记录
					FensWallet fensWallet = new FensWallet();
					// 目前只插入粉丝id
					fensWallet.setFensUserId(user.getId());
					// 粉丝钱包地址uuid生成
					fensWallet.setWalletAddress(UUID.randomUUID().toString());
					// 插入记录到粉丝钱包表
					fensWalletMapper.insertSelective(fensWallet);
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
						// 添加时间
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

	// 修改密码
	@Override
	public JsonResult updatePwd(String OldPwd, String newPwd, Integer fensUserId) {

		FensUser fensUser = new FensUser();
		fensUser.setPwd(MD5.encodeString(MD5.encodeString(OldPwd + ConfigUtil.MD5_PWD_STR) + ConfigUtil.MD5_PWD_STR));
		fensUser.setId(fensUserId);
		// 根据旧密码查询用户
		FensUser fensUser2 = fensUserMapper.selectOldPwd(fensUser);
		if (StringUtils.isEmpty(fensUser2)) {
			return JsonResult.build(500, "原登入密码错误，请重新出入");
		}

		FensUser fensUser3 = new FensUser();
		// 新密码
		fensUser3.setPwd(MD5.encodeString(MD5.encodeString(newPwd + ConfigUtil.MD5_PWD_STR) + ConfigUtil.MD5_PWD_STR));
		fensUser3.setId(fensUserId);
		int result = fensUserMapper.updateByPrimaryKeySelective(fensUser3);
		if (result != 1) {
			return JsonResult.build(500, "修改密码失败");
		}
		return JsonResult.ok();
	}

	// 交易密码
	@Override
	public JsonResult jiaoYi(FensUser fensUser, String code) {
		SMSCheckCode smsCheckCode = new SMSCheckCode();
		smsCheckCode.setMobile(fensUser.getPhone());
		smsCheckCode.setCheckCode(code);

		SMSCheckCode smsCCode = smsCheckCodeDao.selectByMobileAndType(smsCheckCode);// .selectOneSmsInfo(smsCheckCode);
		if (smsCCode != null) {
			long expireTime = smsCCode.getExpireTime().getTime();
			long nowTime = new Date().getTime();
			long expireSeconds = TimeUtil.diffSeconds(expireTime, nowTime);
			if (expireSeconds < 0) {
				return JsonResult.build(20048, ErrorCodeConfigUtil.ERROR_MSG_ZH_20048);
			} else {
				// 根据手机号码
				FensUser user = fensUserMapper.selectRegister(fensUser);
				if (user.getCapitalPwd() == null) {
					FensUser fensUser2 = new FensUser();
					fensUser2.setCapitalPwd(
							MD5.encodeString(MD5.encodeString(fensUser.getCapitalPwd() + ConfigUtil.MD5_PWD_STR)
									+ ConfigUtil.MD5_PWD_STR));
					fensUser2.setId(fensUser.getId());
					int result = fensUserMapper.updateByPrimaryKeySelective(fensUser2);
					smsCCode.setIsUsed(1);
					smsCCode.setUsingTime(TimeUtil.getTimeStamp());
					smsCheckCodeDao.updateByPrimaryKeySelective(smsCCode);
					if (result != 1) {
						return JsonResult.build(500, "设置交易密码失败");
					}
					smsCCode.setIsUsed(2);
					int staus = smsCheckCodeDao.updateByPrimaryKeySelective(smsCCode);
				}
			}
		}
		return JsonResult.ok();

	}

	// 修改交易密码
	@Override
	public JsonResult updateJiaoYi(String oldCapitalPwd, String newCapitalPwd, Integer fensUserId, String code,
			String phone) {
//		SMSCheckCode smsCheckCode = new SMSCheckCode();
//		smsCheckCode.setMobile(phone);
//		smsCheckCode.setCheckCode(code);

//		SMSCheckCode smsCCode = smsCheckCodeDao.selectByMobileAndType(smsCheckCode);
//		if (smsCCode != null) {
//			long expireTime = smsCCode.getExpireTime().getTime();
//			long nowTime = new Date().getTime();
//			long expireSeconds = TimeUtil.diffSeconds(expireTime, nowTime);
//			if (expireSeconds < 0) {
//				return JsonResult.build(20048, ErrorCodeConfigUtil.ERROR_MSG_ZH_20048);
//			} else {
				FensUser fensUser = new FensUser();
				if (StringUtils.isEmpty(oldCapitalPwd)) {
					fensUser.setCapitalPwd(null);
				} else {
					fensUser.setCapitalPwd(MD5.encodeString(
							MD5.encodeString(oldCapitalPwd + ConfigUtil.MD5_PWD_STR) + ConfigUtil.MD5_PWD_STR));
				}
				fensUser.setId(fensUserId);
				FensUser fensUser4 = fensUserMapper.selectByPrimaryKey(fensUserId);
				if (!StringUtils.isEmpty(fensUser4)) {
					if (StringUtils.isEmpty(fensUser4.getCapitalPwd())) {
						FensUser fensUser3 = new FensUser();
						// 新密码
						fensUser3.setCapitalPwd(MD5.encodeString(
								MD5.encodeString(newCapitalPwd + ConfigUtil.MD5_PWD_STR) + ConfigUtil.MD5_PWD_STR));
						fensUser3.setId(fensUserId);
						int result = fensUserMapper.updateByPrimaryKeySelective(fensUser3);
						if (result != 1) {
//							smsCCode.setIsUsed(1);
//							smsCCode.setUsingTime(TimeUtil.getTimeStamp());
//							smsCheckCodeDao.updateByPrimaryKeySelective(smsCCode);
							return JsonResult.build(500, "修改密码失败");
						}
//						smsCCode.setIsUsed(2);
//						int staus = smsCheckCodeDao.updateByPrimaryKeySelective(smsCCode);
						return JsonResult.ok();
					} else if (!StringUtils.isEmpty(fensUser4.getCapitalPwd())) {
						// 根据旧密码查询用户
						FensUser fensUser2 = fensUserMapper.selectOldCapitalPwd(fensUser);
						if (fensUser2 == null) {
							return JsonResult.build(500, "原交易密码错误，请重新出入");
						}
						FensUser fensUser3 = new FensUser();
						// 新密码
						fensUser3.setCapitalPwd(MD5.encodeString(
								MD5.encodeString(newCapitalPwd + ConfigUtil.MD5_PWD_STR) + ConfigUtil.MD5_PWD_STR));
						fensUser3.setId(fensUserId);
						int result = fensUserMapper.updateByPrimaryKeySelective(fensUser3);
						if (result != 1) {
//							smsCCode.setIsUsed(1);
//							smsCCode.setUsingTime(TimeUtil.getTimeStamp());
//							smsCheckCodeDao.updateByPrimaryKeySelective(smsCCode);
							return JsonResult.build(500, "修改密码失败");
						}
//						smsCCode.setIsUsed(2);
//						int staus = smsCheckCodeDao.updateByPrimaryKeySelective(smsCCode);
						return JsonResult.ok();
					} else {
						return JsonResult.build(500, "不存在该用户");
					}
				} else {
					return JsonResult.build(500, "不是本人操作,请重新登入");
				}
//			}
//		}
//		return JsonResult.build(500, "验证码错误");
	}

	// 校验交易密码
	@Override
	public JsonResult zijin(Integer fensUserId, String zjMiMa) {
		if (StringUtils.isEmpty(zjMiMa)) {
			return JsonResult.build(500, "请输入交易密码");
		}
		if (StringUtils.isEmpty(fensUserId)) {
			return JsonResult.build(500, "请重新登入");
		}

		zjMiMa = MD5.encodeString(MD5.encodeString(zjMiMa + ConfigUtil.MD5_PWD_STR) + ConfigUtil.MD5_PWD_STR);
		FensUser fensUser = fensUserMapper.selectzjPwd(fensUserId, zjMiMa);

		FensUser fu = fensUserMapper.selectByPrimaryKey(fensUserId);

		if (StringUtils.isEmpty(fu.getCapitalPwd())) {
			return JsonResult.build(500, "请前往安全中心设置交易密码");
		}

		if (StringUtils.isEmpty(fensUser)) {
			return JsonResult.build(500, "密码错误，请重试");
		}

		if (StringUtils.isEmpty(fensUser)) {
			return JsonResult.build(500, "密码错误，请重试");
		}
		if (StringUtils.isEmpty(fensUser.getCapitalPwd())) {
			return JsonResult.build(500, " 请前往安全中心设置交易密码");
		}
		return JsonResult.ok();
	}

	// 登入(手机号验证码)
	@Override
	public JsonResult login2(String phone, String code, String code_type) {
		// 校验验证码
		SMSCheckCode smsCheckCode = new SMSCheckCode();
		smsCheckCode.setMobile(phone);
		smsCheckCode.setRemarks(code_type);
		smsCheckCode.setCheckCode(code);

		if (phone == null || code == null) {
			return JsonResult.build(20032, ErrorCodeConfigUtil.ERROR_MSG_ZH_20032);
		}
		FensUser fensUser = new FensUser();
		fensUser.setPhone(phone);
		// 查看是否存在该用户
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

				// 登入成功---------------------------------
				String accessToken = TokenUtil.generateToken(fensUser.getPhone());
				// 1.判断数据库是否有该用户的登录token 如果没有 则新增 如果有 则修改
				Integer user_id = user.getId();
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

				// 添加登入日志（登入状态表）
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
						.getRequest();
				// String ip = IpRequestUtil.getIpAddr(request).toString();
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

				// 直推收益计算
				// String phone = user.getPhone();
				String oldZTRSString = user.getCreater();

				int oldZTRS = 0;
				if (!oldZTRSString.isEmpty()) {
					oldZTRS = Integer.parseInt(oldZTRSString);
				} else {
					oldZTRS = 0;
				}

				List<FensTeam> list = fensTeamMapper.selectAll(user.getId());
				if (list.size() > 0) {
					int chazhi = list.size() - oldZTRS;
					if (chazhi > 0) {
						double zhituiSY = chazhi * 2;

						FensWallet fensWallet = fensWalletMapper.selectAll(user.getId());

						Double lockCPA = fensWallet.getLockCpa() + zhituiSY;
						// 到账时间
						Date date2 = TimeUtil.getTimeStamp();
						FensWallet wallet2 = new FensWallet();
						// 钱包可用余额增加
						wallet2.setLockCpa(lockCPA);
						wallet2.setCpaCount(fensWallet.getCpaCount() + zhituiSY);
						wallet2.setId(fensWallet.getId());
						// 更新钱包可用cpa
						int result2 = fensWalletMapper.updateByPrimaryKeySelective(wallet2);
						if (result2 != 1) {
							ServerLog.getLogger().warn("更新钱包可用失败，粉丝id：" + user.getId());
						}

						FensUser fu = new FensUser();
						fu.setCreater(list.size() + "");
						fu.setId(user.getId());

						int result3 = fensUserMapper.updateByPrimaryKeySelective(fu);
						if (result3 != 1) {
							ServerLog.getLogger().warn("更新用户直推失败，粉丝id：" + user.getId());
						}
					}
				}
			}

		} else {
			return JsonResult.build(20049, ErrorCodeConfigUtil.ERROR_MSG_ZH_20049);
		}

		return JsonResult.ok(user);
	}

	// 登入（手机号密码）
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
		Integer user_id = user.getId();
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
		// 添加登入日志（登入状态表）
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		// String ip = IpRequestUtil.getIpAddr(request).toString();
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

		// 直推收益计算
		// String phone = user.getPhone();
		String oldZTRSString = user.getCreater();

		int oldZTRS = 0;
		if (!oldZTRSString.isEmpty()) {
			oldZTRS = Integer.parseInt(oldZTRSString);
		} else {
			oldZTRS = 0;
		}

		// List<FensTeam> list = fensTeamMapper.selectAll(user.getId());
		// if (list.size() > 0) {
		// int chazhi = list.size() - oldZTRS;
		// if (chazhi > 0) {
		// double zhituiSY = chazhi * 2;
		//
		// FensWallet fensWallet = fensWalletMapper.selectAll(user.getId());
		//
		// Double lockCPA = fensWallet.getLockCpa() + zhituiSY;
		// // 到账时间
		// Date date2 = TimeUtil.getTimeStamp();
		// FensWallet wallet2 = new FensWallet();
		// // 钱包可用余额增加
		// wallet2.setLockCpa(lockCPA);
		// wallet2.setCpaCount(fensWallet.getCpaCount() + zhituiSY);
		// wallet2.setId(fensWallet.getId());
		// // 更新钱包可用cpa
		// int result2 = fensWalletMapper.updateByPrimaryKeySelective(wallet2);
		// if (result2 != 1) {
		// ServerLog.getLogger().warn("更新钱包可用失败，粉丝id：" + user.getId());
		// }
		//
		// FensUser fu = new FensUser();
		// fu.setCreater(list.size() + "");
		// fu.setId(user.getId());
		//
		// int result3 = fensUserMapper.updateByPrimaryKeySelective(fu);
		// if (result3 != 1) {
		// ServerLog.getLogger().warn("更新用户直推失败，粉丝id：" + user.getId());
		// }
		// }
		// }

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

	// 粉丝算力
	@Override
	public PageInfo<FensComputingPower> selectComputingPower(Integer page, Integer num, Integer fensUserId) {
		PageHelper.startPage(page, num);
		List<FensComputingPower> list = fensComputingPowerMapper.selectAll(fensUserId);
		PageInfo<FensComputingPower> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	// 粉丝算力求和
	@Override
	public JsonResult selectSum(Integer fensUserId) {
		if (fensUserId == null) {
			return JsonResult.build(500, "不是当前用户");
		}
		Double sum = fensComputingPowerMapper.sum(fensUserId);
		Double sum2 = fensMinerMapper.sum(fensUserId);
		Double count = sum + sum2;
		return JsonResult.ok(count);
	}

	// 添加粉丝算力
	@Override
	public JsonResult addselectComputingPower(FensComputingPower fensComputingPower) {
		int result = fensComputingPowerMapper.insertSelective(fensComputingPower);
		if (result == 1) {
			return JsonResult.ok();
		}
		return JsonResult.build(500, "新增数据失败");
	}

	// 修改粉丝算力
	@Override
	public JsonResult updateComputingPower(FensComputingPower fensComputingPower) {
		int result = fensComputingPowerMapper.updateByPrimaryKeySelective(fensComputingPower);
		if (result == 1) {
			return JsonResult.ok();
		}
		return JsonResult.build(500, "修改数据失败");
	}

	// 粉丝登入状态列表
	@Override
	public PageInfo<FensLoginState> selectStatus(Integer page, Integer num) {
		PageHelper.startPage(page, num);
		List<FensLoginState> list = fensLoginStateMapper.selectAll();
		PageInfo<FensLoginState> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	// 插入粉丝登入状态
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
			getTreeChildRecord(listParentRecord, phone);
			pageInfo.setPages(listParentRecord.size());

			return pageInfo;
		}
		PageHelper.startPage(page, num);
		List<FensUser> list = fensUserMapper.selectAllUser(phone);
		PageInfo<FensUser> pageInfo = new PageInfo<>(list);
		pageInfo.getTotal();

		// pageInfo.setPages(getEmployeeBysup(phone));

		return pageInfo;
	}

	// 亲友团列表old--20180619之前版本
	@Override
	public PageInfo<FensUser> selectListQINYOU(String phone) {
		List<FensUser> list = fensUserMapper.selectAllUser(phone);
		PageInfo<FensUser> pageInfo = new PageInfo<>(list);

		return pageInfo;
	}
	
	// 亲友团列表
	@Override
	public JsonResult selectListQINYOUJson(String phone) {
		List<FensUser> list = fensUserMapper.selectAllUser(phone);
		
		JSONArray jsonList = new JSONArray();
		/**
		 * 添加粉丝团数及算力值显示----start
		 */
		if(list.size() > 0){
			
			for(int i = 0; i < list.size(); i++){
				JSONObject jo = new JSONObject();
				FensUserInfo fui = fensUserInfoMapper.selectByFensUserId(list.get(i).getId());

				jo.put("fensName", list.get(i).getName());
				jo.put("fensPhone", list.get(i).getPhone());
				
				if(fui != null){
					jo.put("fensTeamPower", fui.getFensComputingPower());
					jo.put("fensTeamNum", fui.getFensCount());
					jo.put("fensGrade", fui.getFensGrade());
				}else{
					jo.put("fensTeamPower", 0);
					jo.put("fensTeamNum", 0);
					jo.put("fensGrade", 0);
				}
				
				jsonList.add(jo);
			}
			
		}
		/**
		 * 添加粉丝团数及算力值显示----end
		 */
		
		return JsonResult.ok(jsonList);
	}

	// 粉丝团列表
	@Override
	public List<FensUser> selectListFens(String phone) {
		List<FensUser> list = fensUserMapper.selectAllUserNoTJ();

		PageInfo<FensUser> pageInfo = new PageInfo<>(list);

		List<FensUser> listParentRecord = new ArrayList<FensUser>();
		List<Integer> listminer = new ArrayList<Integer>();
		getTreeChildRecord(listParentRecord, listminer, phone, list);

		System.out.println(listParentRecord.size() + "----end-------");

		double suanli = 0.00;
		if (listParentRecord.size() > 0) {
			List<FensMiner> mlist = fensMinerMapper.allMinerList();

			if (mlist.size() > 0) {
				for (int i = 0; i < mlist.size(); i++) {
					if (listminer.contains(mlist.get(i).getFensUserId())) {
						suanli += mlist.get(i).getMinerComputingPower();
					}
				}
			}
		}

		return listParentRecord;
	}

	// 粉丝等级判断、查询接口
	@Override
	public JSONObject selectFensUserGrade(String phone, Integer uid) {
		JSONObject jr = new JSONObject();
//		int gradeFlag = 0;// 会员等级标记，初始默认为无等级
		
		
		
		
		
		
		
		
		System.out.println("---->>执行");
        List<FensUser> allList = fensUserMapper.selectAllUserESPTdel();
		List<FensUser> list = fensUserMapper.selectAllUserNoTJ();// 全部用户列表

		List<FensMiner> mlist = fensMinerMapper.allMinerList();// 全部矿机列表
		FensUserInfo fuInfo = new FensUserInfo();
		
		List<FensUser> realList = new ArrayList<FensUser>();//全部用户 去重电话列表
		List<String> realListOnlyPhone = new ArrayList<String>();//全部用户 去重电话列表只留电话
		
		String isGradeCan = "yes";//是否能够冲击下一等级
		Date returnDate = null;//冲击下一等级的截止时间
		Date dt = new Date();
		Format f = new SimpleDateFormat("yyyy-MM-dd mm:hh:ss");
		Calendar c = Calendar.getInstance();
		
		for(int i = 0; i < list.size(); i++){
			if(!realListOnlyPhone.contains(list.get(i).getPhone())){
				realList.add(list.get(i));
				realListOnlyPhone.add(list.get(i).getPhone());
			}
		}
		
		// 1直推粉丝列表
		List<FensUser> ztlist = new ArrayList<FensUser>();
		/*
		 * 2 粉丝团用户获取
		 */
		List<FensUser> listParentRecord = new ArrayList<FensUser>();// 粉丝团用户列表
		List<Integer> listminer = new ArrayList<Integer>();// 粉丝团用户ID集合
		
        if(allList.size() > 0){
        	for(int j = 0; j < allList.size(); j++){ //第50个节点有问题
        		int gradeFlag = 0;// 会员等级标记，初始默认为无等级

        		
        		
        		System.out.println("------one size:"+list.size());
        		
        		System.out.println(j+"----------------------"+allList.get(j).getPhone());
        		

        		System.out.println("------two size:"+realList.size());
        		System.out.println("------two size:"+realListOnlyPhone.size());
        		

        		FensUser fm = fensUserMapper.selectByPrimaryKey(allList.get(j).getId());

        		/*
        		 * A、普通节点：1、直推粉丝10+；2、粉丝团用户500+；3、自己及粉丝团CA2矿机3+；4、自己及粉丝团算力12+
        		 * B、高级节点：1、粉丝团用户3000+；2、算力80+；3、粉丝普通节点2+；4、CA3矿机5+；
        		 * C、超级节点：1、粉丝用户10000+；2、算力300G+；3、粉丝高级节点3+；4、粉丝及自己CA4矿机5+
        		 */

        		//// 普通节点--start
        		int AFensNum = 0;// 直推粉丝数初始化
        		int AFensTeamNum = 0;// 粉丝团用户初始化
        		int AMinerCA2Num = 0;// 粉丝团及自己CA2矿机初始化
        		int AMinerCA3Num = 0;// 粉丝团及自己CA3矿机初始化
        		int AMinerCA4Num = 0;// 粉丝团及自己CA4矿机初始化
        		double APCPower = 0.00;// 自己及粉丝团算力初始化
        		
        		

        		// 1直推粉丝列表
        		ztlist = fensUserMapper.selectAllUser(allList.get(j).getPhone());
        		AFensNum = ztlist.size();
        		/*
        		 * 2 粉丝团用户获取
        		 */
        		listParentRecord = new ArrayList<FensUser>();// 粉丝团用户列表
        		listminer = new ArrayList<Integer>();// 粉丝团用户ID集合
        		getTreeChildRecord(listParentRecord, listminer, allList.get(j).getPhone(), realList);// 递归获取粉丝团列表

        		AFensTeamNum = listParentRecord.size();

        		System.out.println(listParentRecord.size() + "--fens grade--end--");

        		/*
        		 * 3、4粉丝团算力计算、矿机核算
        		 */
        		if (listParentRecord.size() > 0) {

        			if (mlist.size() > 0) {
        				for (int i = 0; i < mlist.size(); i++) {
        					if (listminer.contains(mlist.get(i).getFensUserId())) {
        						System.out.println(mlist.get(i).getFensUserId());
        						//注册赠送的不计入算力
        						if(!mlist.get(i).getIsUserGoumai().equals("1")){
	        						APCPower += mlist.get(i).getMinerComputingPower();
	        					}
        						
        						//注册赠送的不计入
        						if (mlist.get(i).getMinerType() == 1 && mlist.get(i).getBak1().equals("2")
        								&& mlist.get(i).getIsDelete() == 0 && (!mlist.get(i).getIsUserGoumai().equals("1"))) {
        							AMinerCA2Num = AMinerCA2Num + 1;
        						}
        						//注册赠送的不计入
        						if (mlist.get(i).getMinerType() == 1 && mlist.get(i).getBak1().equals("3")
        								&& mlist.get(i).getIsDelete() == 0 && (!mlist.get(i).getIsUserGoumai().equals("1"))) {
        							AMinerCA3Num = AMinerCA3Num + 1;
        						}
        						//赠送的不计入
        						if (mlist.get(i).getMinerType() == 1 && mlist.get(i).getBak1().equals("4")
        								&& mlist.get(i).getIsDelete() == 0 && (!mlist.get(i).getIsUserGoumai().equals("1"))) {
        							AMinerCA4Num = AMinerCA4Num + 1;
        						}
        					}
        				}
        			}
        		}

        		System.out.println(listParentRecord.size());

        		// A、普通节点：1、直推粉丝10+；2、粉丝团用户500+；3、自己及粉丝团CA2矿机3+；4、自己及粉丝团算力12+
        		if (AFensNum >= 10 && AFensTeamNum >= 500 && AMinerCA2Num >= 3 && APCPower >= 12) {
        			gradeFlag = 1;
        		}
        		//// 普通节点--end

        		//// 中级节点--start
        		// B、高级节点：1、粉丝团用户3000+；2、算力80+；3、粉丝普通节点2+；4、CA3矿机5+；
        		if (gradeFlag == 1) {
        			if (AFensTeamNum >= 3000 && AMinerCA3Num >= 5 && APCPower >= 80) {
        				gradeFlag = 1;//初始化时，全部设置为1
        			}
        		}
        		//// 中级节点--end

        		//// 高级节点--start
        		// C、超级节点：1、粉丝用户10000+；2、算力300G+；3、粉丝高级节点3+；4、粉丝及自己CA4矿机5+；
        		if (gradeFlag == 2) {
        			if (AFensTeamNum >= 10000 && AMinerCA4Num >= 5 && APCPower >= 300) {
        				gradeFlag = 1;//初始化时，全部设置为1
        			}
        		}
        		//// 高级节点--end

        		/*
        		 * 进行下一节点冲击是否合格及截止时间判定
        		 */
        		int n = 28;//粉丝注册时间至冲击下一等级日期的间隔，默认为冲击普通节点
        		String nextGrade = "";
        		
        		if (gradeFlag == 0) {
        			c.setTime(fm.getCreateDate());
        			c.add(Calendar.DAY_OF_MONTH, n);// 注册日期+n天

        			if (c.getTime().getTime() >= dt.getTime()) {
        				isGradeCan = "yes";
        				nextGrade = "普通节点";
        				returnDate = fm.getCreateDate();
        			} else {
        				n = 70;
        				c.setTime(fm.getCreateDate());
        				c.add(Calendar.DAY_OF_MONTH, n);// 注册日期+n天

        				if (c.getTime().getTime() >= dt.getTime()) {
        					isGradeCan = "yes";
        					nextGrade = "高级节点";
        					returnDate = fm.getCreateDate();
        				} else {
        					n = 130;
        					c.setTime(fm.getCreateDate());
        					c.add(Calendar.DAY_OF_MONTH, n);// 注册日期+n天

        					if (c.getTime().getTime() >= dt.getTime()) {
        						isGradeCan = "yes";
        						nextGrade = "超级节点";
        						returnDate = fm.getCreateDate();
        					} else {
        						isGradeCan = "no";
        					}
        				}
        			}
        		}

        		/*
        		 *  冲击一个级别时间不够则到截止到下一个级别
        		 */
        		if (gradeFlag == 1) {
        			n = 70;
        			c.setTime(fm.getCreateDate());
        			c.add(Calendar.DAY_OF_MONTH, n);// 注册日期+n天

        			if (c.getTime().getTime() >= dt.getTime()) {
        				isGradeCan = "yes";
        				nextGrade = "高级节点";
        				returnDate = fm.getCreateDate();
        			} else {
        				n = 130;
        				c.setTime(fm.getCreateDate());
        				c.add(Calendar.DAY_OF_MONTH, n);// 注册日期+n天

        				if (c.getTime().getTime() >= dt.getTime()) {
        					isGradeCan = "yes";
        					nextGrade = "超级节点";
        					returnDate = fm.getCreateDate();
        				} else {
        					isGradeCan = "no";
        				}
        			}
        		}
        		if (gradeFlag == 2) {
        			n = 130;
        			c.setTime(fm.getCreateDate());
        			c.add(Calendar.DAY_OF_MONTH, n);// 注册日期+n天

        			if (c.getTime().getTime() >= dt.getTime()) {
        				isGradeCan = "yes";
        				nextGrade = "超级节点";
        				returnDate = fm.getCreateDate();
        			} else {
        				isGradeCan = "no";
        			}
        		}
        		if (returnDate != null) {
        			c.setTime(returnDate);
        			c.add(Calendar.DAY_OF_MONTH, n);// 注册日期+n天
        		}
        		
        		fuInfo.setFensUserId(allList.get(j).getId());
        		fuInfo.setBak1(AMinerCA2Num+"");// 粉丝团CA2矿机数
        		fuInfo.setBak2(AMinerCA3Num+"");// 粉丝团CA3矿机数
        		fuInfo.setBak3(AMinerCA4Num+"");// 粉丝团CA4矿机数
        		fuInfo.setFensCount((long) AFensTeamNum);// 粉丝团用户数量
        		fuInfo.setFensComputingPower(APCPower);// 粉丝团算力
        		
        		
        		/*
        		 * 有则更新，无则插入
        		 * 若更新时，不更新等级
        		 * 插入时，插入等级
        		 */
        		if(fensUserInfoMapper.selectByFensUserId(allList.get(j).getId()) == null){
	        		fuInfo.setFensGrade(gradeFlag);// 节点等级
	        		fensUserInfoMapper.insertSelective(fuInfo);
        		}else{
        			fensUserInfoMapper.updateByPrimaryKeySelectiveByFensUserId(fuInfo);
        		}
        		
        		System.out.println("--------------------:"+j);
        		
        		// 1直推粉丝列表
        		ztlist.clear();
        		listParentRecord.clear();// 粉丝团用户列表
        		listminer.clear();// 粉丝团用户ID集合
        		
        	}
        }

		return jr;
	}
	
	// 查询粉丝节点、收益等信息
	@Override
	public JSONObject getFensUserGrade(String phone, Integer uid) {
		JSONObject jr = new JSONObject();
		int gradeFlag = 0;// 会员等级标记，初始默认为无等级
		
		FensUser fm = fensUserMapper.selectByPrimaryKey(uid);
		FensUserInfo fuInfo = fensUserInfoMapper.selectByFensUserId(uid);

		/*
		 * A、普通节点：1、直推粉丝10+；2、粉丝团用户500+；3、自己及粉丝团CA2矿机3+；4、自己及粉丝团算力12+
		 * B、高级节点：1、粉丝团用户3000+；2、算力80+；3、粉丝普通节点2+；4、CA3矿机5+；
		 * C、超级节点：1、粉丝用户10000+；2、算力300G+；3、粉丝高级节点3+；4、粉丝及自己CA4矿机5+
		 */

		//// 普通节点--start
		Integer AFensNum = 0;// 直推粉丝数初始化
		Integer AFensTeamNum = 0;// 粉丝团用户初始化
		Integer AMinerCA2Num = 0;// 粉丝团及自己CA2矿机初始化
		Integer AMinerCA3Num = 0;// 粉丝团及自己CA3矿机初始化
		Integer AMinerCA4Num = 0;// 粉丝团及自己CA4矿机初始化
		double APCPower = 0.00;// 自己及粉丝团算力初始化

		// 1直推粉丝列表
		List<FensUser> ztlist = fensUserMapper.selectAllUser(phone);
		AFensNum = ztlist.size();
		
		/*
		 * 2 粉丝团用户获取
		 * 3、4粉丝团算力计算、矿机核算
		 */

		if(fuInfo != null){
			
			AFensTeamNum = fuInfo.getFensCount().intValue();// 粉丝团用户初始化
			APCPower = fuInfo.getFensComputingPower();
			
			if(fuInfo.getBak1() != null){
				AMinerCA2Num = Integer.valueOf(fuInfo.getBak1());
			}
			
			if (fuInfo.getBak2() != null) {
				AMinerCA3Num = Integer.valueOf(fuInfo.getBak2());
			}

			if (fuInfo.getBak3() != null) {
				AMinerCA4Num = Integer.valueOf(fuInfo.getBak3());
			}
			
		}
		
		/*
		 * 
		 * 普通升高级，高级升超级，需要有下一级节点要求,在此进行判断
		 * 
		 */
		int normalJIEDIAN = 0;
		int gaojieJIEDIAN = 0;
		
//		normalJIEDIAN = fensUserInfoMapper.selectTotalChildFensOne(uid);
//		gaojieJIEDIAN = fensUserInfoMapper.selectTotalChildFensTwo(uid);
		
		
		
		List<FensUser> childrenUserList = fensUserMapper.selectAllUser(phone);
		
		for(int i = 0; i < childrenUserList.size(); i++){
			
			FensUserInfo fui = fensUserInfoMapper.selectByFensUserId(childrenUserList.get(i).getId());
			
			if(fui != null){
				if(fui.getFensGrade() == 1){
					normalJIEDIAN ++;
				}
				
				if(fui.getFensGrade() == 2){
					gaojieJIEDIAN ++;
				}
			}
			
		}
		
		System.out.println(normalJIEDIAN+"<-1------------2->"+gaojieJIEDIAN);
		
		/*
		 * 进行下一节点冲击是否合格及截止时间判定
		 */
		String isGradeCan = "yes";//是否能够冲击下一等级
		Date returnDate = null;//冲击下一等级的截止时间
		Date dt = new Date();
		Format f = new SimpleDateFormat("yyyy-MM-dd mm:hh:ss");
		Calendar c = Calendar.getInstance();
		int n = 28;//粉丝注册时间至冲击下一等级日期的间隔，默认为冲击普通节点
		String nextGrade = "";
		
		c.setTime(fm.getCreateDate());
		c.add(Calendar.DAY_OF_MONTH, n);// 注册日期+n天

		// A、普通节点：1、直推粉丝10+；2、粉丝团用户500+；3、自己及粉丝团CA2矿机3+；4、自己及粉丝团算力12+
//		if (AFensNum >= 10 && AFensTeamNum >= 500 && AMinerCA2Num >= 3 && APCPower >= 12 && c.getTime().getTime() >= dt.getTime()) {
		if (AFensNum >= 10 && AFensTeamNum >= 500 && AMinerCA2Num >= 3 && APCPower >= 12) {
			gradeFlag = 1;
			
			FensUserInfo fuinfo = new FensUserInfo();
			fuinfo.setFensUserId(uid);
			fuinfo.setFensGrade(gradeFlag);
			
			fensUserInfoMapper.updateByPrimaryKeySelectiveByFensUserId(fuinfo);
		}
		//// 普通节点--end

		//// 中级节点--start
		// B、高级节点：1、粉丝团用户3000+；2、算力80+；3、粉丝普通节点2+；4、CA3矿机5+；
		if (gradeFlag == 1) {
			n = 70;
			
			c.setTime(fm.getCreateDate());
			c.add(Calendar.DAY_OF_MONTH, n);
			if (AFensTeamNum >= 3000 && AMinerCA3Num >= 5 && APCPower >= 80 && normalJIEDIAN >=2 && c.getTime().getTime() >= dt.getTime()) {
				gradeFlag = 2;
				
				FensUserInfo fuinfo = new FensUserInfo();
				fuinfo.setFensUserId(uid);
				fuinfo.setFensGrade(gradeFlag);
				
				fensUserInfoMapper.updateByPrimaryKeySelectiveByFensUserId(fuinfo);
			}
		}
		//// 中级节点--end

		//// 高级节点--start
		// C、超级节点：1、粉丝用户10000+；2、算力300G+；3、粉丝高级节点3+；4、粉丝及自己CA4矿机5+；
		if (gradeFlag == 2) {
			n = 130;
			
			c.setTime(fm.getCreateDate());
			c.add(Calendar.DAY_OF_MONTH, n);
			if (AFensTeamNum >= 10000 && AMinerCA4Num >= 5 && APCPower >= 300 && gaojieJIEDIAN >= 3 && c.getTime().getTime() >= dt.getTime()) {
				gradeFlag = 3;
				
				FensUserInfo fuinfo = new FensUserInfo();
				fuinfo.setFensUserId(uid);
				fuinfo.setFensGrade(gradeFlag);
				
				fensUserInfoMapper.updateByPrimaryKeySelectiveByFensUserId(fuinfo);
			}
		}
		//// 高级节点--end

		if (gradeFlag == 0) {
			
			//实时更新等级信息
//			FensUserInfo fuinfo = new FensUserInfo();
//			fuinfo.setFensUserId(uid);
//			fuinfo.setFensGrade(gradeFlag);
//			fensUserInfoMapper.updateByPrimaryKeySelectiveByFensUserId(fuinfo);
			
			c.setTime(fm.getCreateDate());
			c.add(Calendar.DAY_OF_MONTH, n);// 注册日期+n天

			if (c.getTime().getTime() >= dt.getTime()) {
				isGradeCan = "yes";
				nextGrade = "普通节点";
				returnDate = fm.getCreateDate();
			} else {
				n = 70;
				c.setTime(fm.getCreateDate());
				c.add(Calendar.DAY_OF_MONTH, n);// 注册日期+n天

				if (c.getTime().getTime() >= dt.getTime()) {
					isGradeCan = "yes";
					nextGrade = "高级节点";
					returnDate = fm.getCreateDate();
				} else {
					n = 130;
					c.setTime(fm.getCreateDate());
					c.add(Calendar.DAY_OF_MONTH, n);// 注册日期+n天

					if (c.getTime().getTime() >= dt.getTime()) {
						isGradeCan = "yes";
						nextGrade = "超级节点";
						returnDate = fm.getCreateDate();
					} else {
						isGradeCan = "no";
					}
				}
			}
		}

		/*
		 *  冲击一个级别时间不够则到截止到下一个级别
		 */
		if (gradeFlag == 1) {
			n = 70;
			c.setTime(fm.getCreateDate());
			c.add(Calendar.DAY_OF_MONTH, n);// 注册日期+n天

			if (c.getTime().getTime() >= dt.getTime()) {
				isGradeCan = "yes";
				nextGrade = "高级节点";
				returnDate = fm.getCreateDate();
			} else {
				n = 130;
				c.setTime(fm.getCreateDate());
				c.add(Calendar.DAY_OF_MONTH, n);// 注册日期+n天

				if (c.getTime().getTime() >= dt.getTime()) {
					isGradeCan = "yes";
					nextGrade = "超级节点";
					returnDate = fm.getCreateDate();
				} else {
					isGradeCan = "no";
				}
			}
		}
		if (gradeFlag == 2) {
			n = 130;
			c.setTime(fm.getCreateDate());
			c.add(Calendar.DAY_OF_MONTH, n);// 注册日期+n天

			if (c.getTime().getTime() >= dt.getTime()) {
				isGradeCan = "yes";
				nextGrade = "超级节点";
				returnDate = fm.getCreateDate();
			} else {
				isGradeCan = "no";
			}
		}
		if (returnDate != null) {
			c.setTime(returnDate);
			c.add(Calendar.DAY_OF_MONTH, n);// 注册日期+n天

			jr.put("endTime", f.format(c.getTime()));
		}

		
		/**
		 *
		 * 将节点等级达到的，进行分红及算力奖励
		 * 先
		 * 
		 **/
		/*分红  总量按50000为基数分  普通节点50个  高级节点 10个  超级节点 2个
		 * 
		 * 普通节点：当天获得前一天交易cpa总量手续费30%的收益分红，
		 * 高级节点：当天获得前一天交易cpa总量手续费20%的收益分红，
		 * 超级节点：当天获得前一天交易cpa总量手续费10%的收益分红，
		 * 
		 * 算力  只赠送一次
		 * 普通节点：初次达到获得0.055
		 * 高级节点：初次达到获得0.55
		 * 超级节点：初次达到获得5.5
		 */
		
		//// 分红----start
		double cpaGiftBeishu = 60;//总量按50000为基数分  普通节点50个  高级节点 10个  超级节点 2个
		double cpaTotal = 0.00;
		
		////设置时间
		Calendar c2 = Calendar.getInstance();
		Date dt2 = new Date();
		c2.setTime(dt2);
		c2.add(Calendar.DAY_OF_MONTH, -1);
		
		Date yesterday = c2.getTime();
		System.out.println("昨天是:" + f.format(yesterday));
		
		cpaTotal = fensTransactionMapper.jylYesterdaySum();//查询昨天交易量
		if(cpaTotal > 0){
			
			List<FensEarn> eranList =  fensEarnMapper.selectIsGiftFensEarn(uid);
			
			if(eranList.size() <= 0 ){
				Integer eranType = 0;
				
				if(gradeFlag == 1){
					eranType = 41;
					cpaGiftBeishu = 50;
				}
				if(gradeFlag == 2){
					eranType = 42;
					cpaGiftBeishu = 10;
				}
				if(gradeFlag == 3){
					eranType = 43;
					cpaGiftBeishu = 2;
				}

				double shijiCpa = cpaTotal*0.2/cpaGiftBeishu;
				
				FensEarn fEarn = new FensEarn();
				
				fEarn.setFensUserId(uid);
				fEarn.setCreateDate(TimeUtil.getTimeStamp());
				fEarn.setIsDelete(0);
				fEarn.setEarnType(eranType);
				fEarn.setEarnDate(TimeUtil.getTimeStamp());
				fEarn.setEarnState(2);//收益状态 1代表不锁定  2代表锁定
				fEarn.setEarnCount(shijiCpa);
				
				fensEarnMapper.insertSelective(fEarn);
			}
			
		}
		
		//// 分红----end
		
		//// 算力奖励----start
		
		if(gradeFlag == 1){
			FensComputingPower fcp = new FensComputingPower();
			fcp.setType(41);//41：普通节点；42：高级节点；43：超级节点
			fcp.setFensUserId(uid);
			fcp.setCreateDate(TimeUtil.getTimeStamp());
			fcp.setIsDelete(0);
			
			List<FensComputingPower> fcpList = fensComputingPowerMapper.selectFensGradePower(fcp);
			if(fcpList.size() <= 0){
				fcp.setComputingPower(0.055);
				
				int fcpResult = fensComputingPowerMapper.insertSelective(fcp);
			}
		}
		
		if(gradeFlag == 2){
			FensComputingPower fcp = new FensComputingPower();
			fcp.setType(42);//41：普通节点；42：高级节点；43：超级节点
			fcp.setFensUserId(uid);
			fcp.setCreateDate(TimeUtil.getTimeStamp());
			fcp.setIsDelete(0);
			
			List<FensComputingPower> fcpList = fensComputingPowerMapper.selectFensGradePower(fcp);
			if(fcpList.size() <= 0){
				fcp.setComputingPower(0.55);
				
				int fcpResult = fensComputingPowerMapper.insertSelective(fcp);
			}
		}
		
		if(gradeFlag == 3){
			FensComputingPower fcp = new FensComputingPower();
			fcp.setType(43);//41：普通节点；42：高级节点；43：超级节点
			fcp.setFensUserId(uid);
			fcp.setCreateDate(TimeUtil.getTimeStamp());
			fcp.setIsDelete(0);
			
			List<FensComputingPower> fcpList = fensComputingPowerMapper.selectFensGradePower(fcp);
			if(fcpList.size() <= 0){
				fcp.setComputingPower(5.5);
				
				int fcpResult = fensComputingPowerMapper.insertSelective(fcp);
			}
		}
		
		//// 算力奖励----end
		
		/*
		 * 返回json数据结果
		 */
		jr.put("grade", gradeFlag);//当前等级
		jr.put("nextgrade", nextGrade);//有希望冲击的下一等级
		jr.put("suanli", APCPower);
		jr.put("fensteamNum", AFensTeamNum);
		jr.put("isGradeCan", isGradeCan);

		return jr;
	}
	
	@Override
	public JSONObject getFensUserGradeLittle(String phone, Integer uid) {
		JSONObject jr = new JSONObject();
		int gradeFlag = 0;// 会员等级标记，初始默认为无等级
		
		FensUser fm = fensUserMapper.selectByPrimaryKey(uid);
		FensUserInfo fuInfo = fensUserInfoMapper.selectByFensUserId(uid);

		//// 普通节点--start
		Integer AFensNum = 0;// 直推粉丝数初始化
		Integer AFensTeamNum = 0;// 粉丝团用户初始化
		Integer AMinerCA2Num = 0;// 粉丝团及自己CA2矿机初始化
		Integer AMinerCA3Num = 0;// 粉丝团及自己CA3矿机初始化
		Integer AMinerCA4Num = 0;// 粉丝团及自己CA4矿机初始化
		double APCPower = 0.00;// 自己及粉丝团算力初始化

		// 1直推粉丝列表
		List<FensUser> ztlist = fensUserMapper.selectAllUser(phone);
		AFensNum = ztlist.size();
		
		/*
		 * 2 粉丝团用户获取
		 * 3、4粉丝团算力计算、矿机核算
		 */

		if(fuInfo != null){
			
			AFensTeamNum = fuInfo.getFensCount().intValue();// 粉丝团用户初始化
			APCPower = fuInfo.getFensComputingPower() + fensMinerMapper.sum(uid);;
			
			if(fuInfo.getBak1() != null){
				AMinerCA2Num = Integer.valueOf(fuInfo.getBak1());
			}
			
			if (fuInfo.getBak2() != null) {
				AMinerCA3Num = Integer.valueOf(fuInfo.getBak2());
			}

			if (fuInfo.getBak3() != null) {
				AMinerCA4Num = Integer.valueOf(fuInfo.getBak3());
			}
			
		}
		
		/*
		 * 返回json数据结果
		 */
		jr.put("grade", gradeFlag);//当前等级
		jr.put("suanli", APCPower);
		jr.put("fensteamNum", AFensTeamNum);

		return jr;
	}

	// 粉丝团列表2
	@Override
	public JSONObject selectListFens2(String phone) {
		List<FensUser> list = fensUserMapper.selectAllUserNoTJ();

		PageInfo<FensUser> pageInfo = new PageInfo<>(list);

		List<FensUser> listParentRecord = new ArrayList<FensUser>();
		List<Integer> listminer = new ArrayList<Integer>();
		getTreeChildRecord(listParentRecord, listminer, phone, list);

		System.out.println(listParentRecord.size() + "----end-------");

		double suanli = 0.00;
		if (listParentRecord.size() > 0) {
			List<FensMiner> mlist = fensMinerMapper.allMinerList();

			if (mlist.size() > 0) {
				for (int i = 0; i < mlist.size(); i++) {
					if (listminer.contains(mlist.get(i).getFensUserId())) {
						suanli += mlist.get(i).getMinerComputingPower();
					}
				}
			}
		}

		JSONObject jr = new JSONObject();
		jr.put("fensList", listParentRecord.size());
		jr.put("fensuanli", suanli);

		return jr;
	}

	/**
	 * 说明方法描述：递归查询子节点
	 * 
	 * @param listParentRecord
	 *            粉丝团集合
	 * @param parentUuid
	 *            父节点id
	 * @return
	 */

	private List<FensUser> getTreeChildRecord(List<FensUser> listParentRecord, String parentUuid) {
		// List<FensUser> listParentRecord = new ArrayList<FensUser>();
		List<FensUser> childList = fensUserMapper.selectAllUser(parentUuid);

		// 遍历tmpList，找出所有的根节点和非根节点
		if (childList != null && childList.size() > 0) {
			for (FensUser record : childList) {
				listParentRecord.add(record);
				getTreeChildRecord(listParentRecord, record.getPhone());
			}
		}
		return listParentRecord;
	}

	@Override
	public JsonResult updateInfo(FensUser fensUser) {

		FensUser user = fensUserMapper.selectRegister(fensUser);
		if (user == null) {
			return JsonResult.build(20022, ErrorCodeConfigUtil.ERROR_MSG_ZH_20022);
		}

		FensUser fensUser2 = new FensUser();
		fensUser2.setAttachment(fensUser.getAttachment());
		fensUser2.setId(user.getId());
		int result = fensUserMapper.updateByPrimaryKeySelective(fensUser2);
		if (result == 1) {
			return JsonResult.ok();
		}

		return JsonResult.build(500, "网络服务异常，请稍后重试");
	}

	@Override
	public JsonResult updateInfo2(FensUser fensUser) {

		FensUser user = fensUserMapper.selectRegister(fensUser);
		if (user == null) {
			return JsonResult.build(20022, ErrorCodeConfigUtil.ERROR_MSG_ZH_20022);
		}

		FensUser fensUser2 = new FensUser();
		fensUser2.setAttachment(String.valueOf(1));
		fensUser2.setId(user.getId());
		int result = fensUserMapper.updateByPrimaryKeySelective(fensUser2);
		if (result == 1) {
			return JsonResult.ok();
		}

		return JsonResult.build(500, "网络服务异常，请稍后重试");
	}

	// 待审核
	@Override
	public JsonResult selectDSH(Integer uid) {
		List<FensTransaction> list = fensTransactionMapper.selectDSH(uid);
		if (list.size() > 0) {
			return JsonResult.ok(list);
		}
		return JsonResult.build(500, "无审核订单");
	}

	/**
	 * 说明方法描述：递归查询子节点
	 * 
	 * @param listParentRecord
	 *            粉丝团集合
	 * @param parentUuid
	 *            父节点id
	 * @return
	 */

	private List<FensUser> getTreeChildRecord(List<FensUser> listParentRecord, List<Integer> listminer,
			String parentUuid, List<FensUser> allList) {
		System.out.println("parentID:" + parentUuid);
		// 遍历tmpList，找出所有的根节点和非根节点
		if (allList.size() > 0) {
			for (int i = 0; i < allList.size(); i++) {

				String refereePhone = allList.get(i).getRefereePhone();

				if (refereePhone != null && parentUuid != null) {

					if (allList.get(i).getRefereePhone().equals(parentUuid)) {

						listParentRecord.add(allList.get(i));
						if (allList.get(i).getIsDelete() == 0) {// 正常用户才计算算力
							listminer.add(allList.get(i).getId());
						}

						getTreeChildRecord(listParentRecord, listminer, allList.get(i).getPhone(), allList);

					}
				}
			}
		}
		return listParentRecord;
	}

	// 会员审计
	@Override
	public JsonResult checkFens(Integer id, String phone) {

		JSONObject jsonCheckReg = checkReg(id);
		JSONObject jsonCheckPayStatus = checkPayStatus(id);
		JSONObject jsonCheckTrader = checkCPATrader(id);
		JSONObject jsonCheckPayAuth = checkPayAuth(id);
		JSONObject jsonCheckJuneActivity = checkJuneActivity(id);

		return null;
	}

	/**
	 * 检查非法注册情况（只统计6月份的：开放注册时间为：6--24点）
	 * 
	 * @param phone
	 * @return
	 */
	private JSONObject checkReg(Integer id) {

		JSONObject jo = new JSONObject();

		FensUser fUser = fensUserMapper.selectRegJuneFF(id);
		Date regDate = fUser.getCreateDate();

		int regHours = regDate.getHours();
		int isRegHF = 0;// 0:合法；1：不合法
		if ((regHours >= 6 && regHours <= 24)) {
			isRegHF = 0;
		} else {
			isRegHF = 1;
		}

		jo.put("regDate", regDate);
		jo.put("isRegHF", isRegHF);

		return jo;
	}

	/**
	 * 检查支付绑定状态 最早交易时间早于最早支付绑定时间则非法
	 * 
	 * @param id
	 * @return
	 */
	private JSONObject checkPayStatus(Integer id) {

		JSONObject jo = new JSONObject();

		FensTransaction fensTransaction = fensTransactionMapper.selectByPrimaryKeyDESC(id);
		BankCard bankCard = bankcardMapper.selectAll(id).get(0);

		int isJYHF = 0;
		int isBindingPay = 0;
		Date date = null;

		if (bankCard == null && fensTransaction != null) {
			isJYHF = 1;
		}

		if (bankCard != null && fensTransaction != null) {
			long chazhi = TimeUtil.diffSeconds(bankCard.getCreateDate().getTime(),
					fensTransaction.getCreateDate().getTime());
			if (chazhi < 0) {
				isJYHF = 1;
			}
		}

		if (bankCard != null) {
			isBindingPay = 1;
			date = bankCard.getCreateDate();
		}

		jo.put("isJYHF", isJYHF);
		jo.put("isBindingPay", isBindingPay);
		jo.put("bindingDate", date);

		return jo;
	}

	/**
	 * 检查粉丝团成员向领导人出售cpa人数的比例 一种：在指定范围内交易比例超过10%--20%则非法 另外一种：交易的订单 超过粉丝团成员的一定比例
	 * 
	 * @param id
	 * @return 总订单量
	 */
	private JSONObject checkFensTraderStatus(Integer id) {

		JSONObject jo = new JSONObject();

		return jo;
	}

	/**
	 * 
	 * 检查粉丝cpa交易情况
	 * 
	 * @param id
	 * @return 总订单量
	 */
	private JSONObject checkCPATrader(Integer id) {

		JSONObject jo = new JSONObject();

		// 查询买单总数量
		int buyCount = fensTransactionMapper.selectBuyCount(id);

		// 查询买单cap总数量
		Double buyCpaCount = fensTransactionMapper.selectBuyCpaCount(id);

		// 查询卖单总数量
		int sellCount = fensTransactionMapper.selectSellCount(id);

		// 查询卖单cpa总数量
		Double sellCpaCount = fensTransactionMapper.selectSellCpaCount(id);

		jo.put("buyCount", buyCount);
		jo.put("buyCpaCount", buyCpaCount);
		jo.put("sellCount", sellCount);
		jo.put("sellCpaCount", sellCpaCount);

		return jo;
	}

	/**
	 * 
	 * 检查粉丝cpa登录情况
	 * 
	 * @param id
	 * @return
	 */
	private JSONObject checkFensLogin(Integer id) {

		JSONObject jo = new JSONObject();

		return jo;
	}

	/**
	 * 
	 * 检查粉丝6月份活动情况
	 * 
	 * @param id
	 * @return
	 */
	private JSONObject checkJuneActivity(Integer id) {
		Integer liu_zhi_tui = 0;

		JSONObject jo = new JSONObject();
		FensUser fensUser = fensUserMapper.selectByPrimaryKey(id);
		if (!StringUtils.isEmpty(fensUser)) {
			if (!StringUtils.isEmpty(fensUser.getCreater())) {
				liu_zhi_tui = Integer.valueOf(fensUser.getCreater());
			}
		}
		jo.put("liu_zhi_tui", liu_zhi_tui);
		return jo;
	}

	/**
	 * 
	 * 检查粉丝钱包情况
	 * 
	 * @param id
	 * @return
	 */
	private JSONObject checkCPAWallet(Integer id) {

		JSONObject jsonObject = new JSONObject();
		// 可用cpa
		Double ableCpa = 0.00;
		// 锁定cpa
		Double lockCpa = 0.00;
		// 总cpa
		Double zongCpa = 0.00;

		FensWallet fensWallet = fensWalletMapper.selectAll(id);
		if (!StringUtils.isEmpty(fensWallet)) {
			ableCpa = fensWallet.getAbleCpa();
			lockCpa = fensWallet.getLockCpa();
			zongCpa = fensWallet.getCpaCount();
		}

		jsonObject.put("ableCpa", ableCpa);
		jsonObject.put("lockCpa", lockCpa);
		jsonObject.put("zongCpa", zongCpa);

		return jsonObject;
	}

	/**
	 * 
	 * 检查粉丝直推及粉丝团情况
	 * 
	 * @param id
	 * @return
	 */
	private JSONObject checkFensTeam(String phone) {
		List<FensUser> list = fensUserMapper.selectAllUser(phone);
		JSONObject jo = new JSONObject();
		jo.put("list", list.size());
		return jo;
	}

	/**
	 * 
	 * 检查粉丝支付、认证情况
	 * 
	 * @param id
	 * @return
	 */
	private JSONObject checkPayAuth(Integer id) {

		JSONObject jo = new JSONObject();
		Integer renzheng = 0;

		FensUser fensUser = fensUserMapper.selectByPrimaryKey(id);

		if (!StringUtils.isEmpty(fensUser)) {
			if (!StringUtils.isEmpty(fensUser.getAttachment())) {
				renzheng = Integer.valueOf(fensUser.getAttachment());
			}
		}
		jo.put("renzheng", renzheng);
		return jo;
	}

	/**
	 * 
	 * 检查粉丝矿机情况
	 * 
	 * @param id
	 * @return
	 */
	private JSONObject checkMiner(Integer id) {

		JSONObject jo = new JSONObject();

		// 矿机总量
		int minerSum = fensMinerMapper.minerSum(id);
		// 矿机的总价格
		Double minerPrice = fensMinerMapper.minerPrice(id);
		// 矿机的提取收益
		Double minerEarn = fensMinerMapper.minerEarn(id);

		jo.put("minerSum", minerSum);
		jo.put("minerPrice", minerPrice);
		jo.put("minerEarn", minerEarn);

		return jo;
	}

	@Override
	public JsonResult selectGradePowerGift(Integer uid) {
		
		FensComputingPower fcp = new FensComputingPower();
		fcp.setFensUserId(uid);
		
		List<FensComputingPower> list = fensComputingPowerMapper.selectFensGradePower(fcp);
		if(list.size() > 0){
			return JsonResult.ok(list);
		}
		
		return JsonResult.build(500, "暂无数据");
	}

	@Override
	public JsonResult selectGradeEran(Integer uid) {
		
		List<FensEarn> list = fensEarnMapper.selectGradeGiftFensEarn(uid);
		if(list.size() > 0){
			return JsonResult.ok(list);
		}
		
		return JsonResult.build(500, "暂无数据");
	}

	@Override
	public JsonResult addGradePowerGift(Integer uid) {
		return null;
	}

	@Override
	public JsonResult addEarnGift(Integer valueOf) {
		
		Double earnNum = fensEarnMapper.selectGradeGiftFensEarnLockSum(valueOf);
		if (earnNum != null) {
			if (earnNum > 0) {
				FensWallet fw = new FensWallet();
				fw = fensWalletMapper.selectByFens(valueOf);

				if (fw != null) {
					FensWallet fw2 = new FensWallet();
					fw2.setId(fw.getId());
					fw2.setAbleCpa(earnNum + fw.getAbleCpa());
					fw2.setCpaCount(earnNum + fw.getCpaCount());

					int result = fensWalletMapper.updateByPrimaryKeySelective(fw2);
					if (result != 1) {
						return JsonResult.build(500, "暂无数据可提取");
					}
					fensEarnMapper.updateByFensUserID(valueOf);
					return JsonResult.ok();
				} else {
					return JsonResult.build(500, "暂无数据可提取");
				}

			} else {
				return JsonResult.build(500, "暂无数据可提取");
			}
		} else {
			return JsonResult.build(500, "暂无数据可提取");
		}
		
	}

	@Override
	public JSONObject selectSelfGrade(String phone, Integer uid) {

		JSONObject jr = new JSONObject();
//		int gradeFlag = 0;// 会员等级标记，初始默认为无等级
		
		System.out.println("---->>执行");
        List<FensUser> allList = fensUserMapper.selectAllUserESPTdel();
		List<FensUser> list = fensUserMapper.selectAllUserNoTJ();// 全部用户列表

		List<FensMiner> mlist = fensMinerMapper.allMinerList();// 全部矿机列表
		FensUserInfo fuInfo = new FensUserInfo();
		
		List<FensUser> realList = new ArrayList<FensUser>();//全部用户 去重电话列表
		List<String> realListOnlyPhone = new ArrayList<String>();//全部用户 去重电话列表只留电话
		
		String isGradeCan = "yes";//是否能够冲击下一等级
		Date returnDate = null;//冲击下一等级的截止时间
		Date dt = new Date();
		Format f = new SimpleDateFormat("yyyy-MM-dd mm:hh:ss");
		Calendar c = Calendar.getInstance();
		
		for(int i = 0; i < list.size(); i++){
			if(!realListOnlyPhone.contains(list.get(i).getPhone())){
				realList.add(list.get(i));
				realListOnlyPhone.add(list.get(i).getPhone());
			}
		}
		
		// 1直推粉丝列表
		List<FensUser> ztlist = new ArrayList<FensUser>();
		/*
		 * 2 粉丝团用户获取
		 */
		List<FensUser> listParentRecord = new ArrayList<FensUser>();// 粉丝团用户列表
		List<Integer> listminer = new ArrayList<Integer>();// 粉丝团用户ID集合
		
		if (allList.size() > 0) {
			int gradeFlag = 0;// 会员等级标记，初始默认为无等级

			System.out.println("------one size:" + list.size());
			System.out.println("------two size:" + realList.size());
			System.out.println("------two size:" + realListOnlyPhone.size());

			FensUser fm = fensUserMapper.selectByPrimaryKey(uid);

			/*
			 * A、普通节点：1、直推粉丝10+；2、粉丝团用户500+；3、自己及粉丝团CA2矿机3+；4、自己及粉丝团算力12+
			 * B、高级节点：1、粉丝团用户3000+；2、算力80+；3、粉丝普通节点2+；4、CA3矿机5+；
			 * C、超级节点：1、粉丝用户10000+；2、算力300G+；3、粉丝高级节点3+；4、粉丝及自己CA4矿机5+
			 */

			//// 普通节点--start
			int AFensNum = 0;// 直推粉丝数初始化
			int AFensTeamNum = 0;// 粉丝团用户初始化
			int AMinerCA1Num = 0;// CA1矿机初始化
			int AMinerCA2Num = 0;// 粉丝团及自己CA2矿机初始化
			int AMinerCA3Num = 0;// 粉丝团及自己CA3矿机初始化
			int AMinerCA4Num = 0;// 粉丝团及自己CA4矿机初始化
			int AMinerCA1Num2 = 0;// 赠送的数量CA1矿机初始化
			int AMinerCA2Num2 = 0;// 赠送的数量粉丝团及自己CA2矿机初始化
			int AMinerCA3Num2 = 0;// 赠送的数量粉丝团及自己CA3矿机初始化
			int AMinerCA4Num2 = 0;// 赠送的数量粉丝团及自己CA4矿机初始化
			double APCPower = 0.00;// 自己及粉丝团算力初始化

			// 1直推粉丝列表
			ztlist = fensUserMapper.selectAllUser(phone);
			AFensNum = ztlist.size();
			/*
			 * 2 粉丝团用户获取
			 */
			listParentRecord = new ArrayList<FensUser>();// 粉丝团用户列表
			listminer = new ArrayList<Integer>();// 粉丝团用户ID集合
			getTreeChildRecord(listParentRecord, listminer, phone, realList);// 递归获取粉丝团列表

			AFensTeamNum = listParentRecord.size();

			System.out.println(listParentRecord.size() + "--fens grade--end--");

			/*
			 * 3、4粉丝团算力计算、矿机核算
			 */
			if (listParentRecord.size() > 0) {

				if (mlist.size() > 0) {
					for (int i = 0; i < mlist.size(); i++) {
						if (listminer.contains(mlist.get(i).getFensUserId())) {
							System.out.println(mlist.get(i).getFensUserId());
							// 赠送的不计入算力
							if (mlist.get(i).getIsUserGoumai() == null) {
								APCPower += mlist.get(i).getMinerComputingPower();
							}

							// 赠送的不计入
							if (mlist.get(i).getMinerType() == 1 && mlist.get(i).getBak1().equals("1")
									&& mlist.get(i).getIsDelete() == 0 && mlist.get(i).getIsUserGoumai() == null) {
								AMinerCA1Num = AMinerCA1Num + 1;
							}
							
							if (mlist.get(i).getMinerType() == 1 && mlist.get(i).getBak1().equals("2")
									&& mlist.get(i).getIsDelete() == 0 && mlist.get(i).getIsUserGoumai() == null) {
								AMinerCA2Num = AMinerCA2Num + 1;
							}

							if (mlist.get(i).getMinerType() == 1 && mlist.get(i).getBak1().equals("3")
									&& mlist.get(i).getIsDelete() == 0 && mlist.get(i).getIsUserGoumai() == null) {
								AMinerCA3Num = AMinerCA3Num + 1;
							}

							if (mlist.get(i).getMinerType() == 1 && mlist.get(i).getBak1().equals("4")
									&& mlist.get(i).getIsDelete() == 0 && mlist.get(i).getIsUserGoumai() == null) {
								AMinerCA4Num = AMinerCA4Num + 1;
							}
							
							
							
							// 赠送的不计入
							if (mlist.get(i).getMinerType() == 1 && mlist.get(i).getBak1().equals("1")
									&& mlist.get(i).getIsDelete() == 0 && mlist.get(i).getIsUserGoumai() != null) {
								AMinerCA1Num2 = AMinerCA1Num2 + 1;
							}
							
							if (mlist.get(i).getMinerType() == 1 && mlist.get(i).getBak1().equals("2")
									&& mlist.get(i).getIsDelete() == 0 && mlist.get(i).getIsUserGoumai() != null) {
								AMinerCA2Num2 = AMinerCA2Num2 + 1;
							}

							if (mlist.get(i).getMinerType() == 1 && mlist.get(i).getBak1().equals("3")
									&& mlist.get(i).getIsDelete() == 0 && mlist.get(i).getIsUserGoumai() != null) {
								AMinerCA3Num2 = AMinerCA3Num2 + 1;
							}

							if (mlist.get(i).getMinerType() == 1 && mlist.get(i).getBak1().equals("4")
									&& mlist.get(i).getIsDelete() == 0 && mlist.get(i).getIsUserGoumai() != null) {
								AMinerCA4Num2 = AMinerCA4Num2 + 1;
							}
						}
					}
				}
			}

			System.out.println(listParentRecord.size());

			// A、普通节点：1、直推粉丝10+；2、粉丝团用户500+；3、自己及粉丝团CA2矿机3+；4、自己及粉丝团算力12+
			if (AFensNum >= 10 && AFensTeamNum >= 500 && AMinerCA2Num >= 3 && APCPower >= 12) {
				gradeFlag = 1;
			}
			//// 普通节点--end

			//// 中级节点--start
			// B、高级节点：1、粉丝团用户3000+；2、算力80+；3、粉丝普通节点2+；4、CA3矿机5+；
			if (gradeFlag == 1) {
				if (AFensTeamNum >= 3000 && AMinerCA3Num >= 5 && APCPower >= 80) {
					gradeFlag = 1;// 初始化时，全部设置为1
				}
			}
			//// 中级节点--end

			//// 高级节点--start
			// C、超级节点：1、粉丝用户10000+；2、算力300G+；3、粉丝高级节点3+；4、粉丝及自己CA4矿机5+；
			if (gradeFlag == 2) {
				if (AFensTeamNum >= 10000 && AMinerCA4Num >= 5 && APCPower >= 300) {
					gradeFlag = 1;// 初始化时，全部设置为1
				}
			}
			//// 高级节点--end
			
			
			System.out.println("粉丝团数："+AFensTeamNum);
			System.out.println("复投CA1矿机数："+AMinerCA1Num);
			System.out.println("复投CA2矿机数："+AMinerCA2Num);
			System.out.println("复投CA3矿机数："+AMinerCA3Num);
			System.out.println("复投CA4矿机数："+AMinerCA4Num);
			System.out.println("赠送CA1矿机数："+AMinerCA1Num2);
			System.out.println("赠送CA2矿机数："+AMinerCA2Num2);
			System.out.println("赠送CA3矿机数："+AMinerCA3Num2);
			System.out.println("赠送CA4矿机数："+AMinerCA4Num2);
			System.out.println("总算力："+APCPower);
			
			
			for(int j=0;j<listParentRecord.size();j++){
				if(!listParentRecord.get(j).getAttachment().equals("1"))
					System.out.println(listParentRecord.get(j).getId()+"----"+listParentRecord.get(j).getName()+"-----"+listParentRecord.get(j).getPhone());
			}
		}
		
		return null;
	}

}
