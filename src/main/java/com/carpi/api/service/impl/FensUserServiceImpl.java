package com.carpi.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.carpi.api.dao.APoolMapper;
import com.carpi.api.dao.BPoolMapper;
import com.carpi.api.dao.FensAuthenticationMapper;
import com.carpi.api.dao.FensComputingPowerMapper;
import com.carpi.api.dao.FensLoginStateMapper;
import com.carpi.api.dao.FensMinerMapper;
import com.carpi.api.dao.FensTeamMapper;
import com.carpi.api.dao.FensTransactionMapper;
import com.carpi.api.dao.FensUserMapper;
import com.carpi.api.dao.FensWalletMapper;
import com.carpi.api.pojo.APool;
import com.carpi.api.pojo.BPool;
import com.carpi.api.pojo.FensAuthentication;
import com.carpi.api.pojo.FensComputingPower;
import com.carpi.api.pojo.FensLoginState;
import com.carpi.api.pojo.FensMiner;
import com.carpi.api.pojo.FensTeam;
import com.carpi.api.pojo.FensTransaction;
import com.carpi.api.pojo.FensUser;
import com.carpi.api.pojo.FensWallet;
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

	@Autowired
	private APoolMapper apoolMapper;

	@Autowired
	private BPoolMapper bpoolMapper;

	@Autowired
	private FensWalletMapper fensWalletMapper;
	
	@Autowired
	private FensTransactionMapper fensTransactionMapper;

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

		SMSCheckCode smsCheckCode = new SMSCheckCode();
		smsCheckCode.setMobile(fensUser.getPhone());
//		smsCheckCode.setRemarks(code_type);
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
				//粉丝身份证图片
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
		if (fensUser2 == null) {
			return JsonResult.build(500, "原登入密码错误，请重新出入");
		}

		FensUser fensUser3 = new FensUser();
		// 新密码
		fensUser3.setPwd(MD5.encodeString(MD5.encodeString(newPwd + ConfigUtil.MD5_PWD_STR) + ConfigUtil.MD5_PWD_STR));
		fensUser3.setId(fensUserId);
		int result = fensUserMapper.updateByPrimaryKeySelective(fensUser3);
		if (result != 1) {
			return JsonResult.build(500, "修改密码错误");
		}
		return JsonResult.ok();
	}

	// 交易密码
	@Override
	public JsonResult jiaoYi(FensUser fensUser) {
		// 根据手机号码
		FensUser user = fensUserMapper.selectRegister(fensUser);
		if (user.getCapitalPwd() == null) {
			FensUser fensUser2 = new FensUser();
			fensUser2.setCapitalPwd(MD5.encodeString(
					MD5.encodeString(fensUser.getCapitalPwd() + ConfigUtil.MD5_PWD_STR) + ConfigUtil.MD5_PWD_STR));
			fensUser2.setId(fensUser.getId());
			int result = fensUserMapper.updateByPrimaryKeySelective(fensUser2);
			if (result != 1) {
				return JsonResult.build(500, "设置交易密码错误");
			}
		}
		return JsonResult.ok();
	}

	// 修改交易密码
	@Override
	public JsonResult updateJiaoYi(String oldCapitalPwd, String newCapitalPwd, Integer fensUserId) {
		FensUser fensUser = new FensUser();
		if ("".equals(oldCapitalPwd) || oldCapitalPwd == null) {
			fensUser.setCapitalPwd(null);
		} else {
			fensUser.setCapitalPwd(MD5
					.encodeString(MD5.encodeString(oldCapitalPwd + ConfigUtil.MD5_PWD_STR) + ConfigUtil.MD5_PWD_STR));
		}
		fensUser.setId(fensUserId);
		// 根据旧密码查询用户
		FensUser fensUser2 = fensUserMapper.selectOldCapitalPwd(fensUser);
		if (fensUser2 == null) {
			return JsonResult.build(500, "原交易密码错误，请重新出入");
		}
		FensUser fensUser3 = new FensUser();
		// 新密码
		fensUser3.setCapitalPwd(
				MD5.encodeString(MD5.encodeString(newCapitalPwd + ConfigUtil.MD5_PWD_STR) + ConfigUtil.MD5_PWD_STR));
		fensUser3.setId(fensUserId);
		int result = fensUserMapper.updateByPrimaryKeySelective(fensUser3);
		if (result != 1) {
			return JsonResult.build(500, "修改密码错误");
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

//		List<FensTeam> list = fensTeamMapper.selectAll(user.getId());
//		if (list.size() > 0) {
//			int chazhi = list.size() - oldZTRS;
//			if (chazhi > 0) {
//				double zhituiSY = chazhi * 2;
//
//				FensWallet fensWallet = fensWalletMapper.selectAll(user.getId());
//
//				Double lockCPA = fensWallet.getLockCpa() + zhituiSY;
//				// 到账时间
//				Date date2 = TimeUtil.getTimeStamp();
//				FensWallet wallet2 = new FensWallet();
//				// 钱包可用余额增加
//				wallet2.setLockCpa(lockCPA);
//				wallet2.setCpaCount(fensWallet.getCpaCount() + zhituiSY);
//				wallet2.setId(fensWallet.getId());
//				// 更新钱包可用cpa
//				int result2 = fensWalletMapper.updateByPrimaryKeySelective(wallet2);
//				if (result2 != 1) {
//					ServerLog.getLogger().warn("更新钱包可用失败，粉丝id：" + user.getId());
//				}
//
//				FensUser fu = new FensUser();
//				fu.setCreater(list.size() + "");
//				fu.setId(user.getId());
//
//				int result3 = fensUserMapper.updateByPrimaryKeySelective(fu);
//				if (result3 != 1) {
//					ServerLog.getLogger().warn("更新用户直推失败，粉丝id：" + user.getId());
//				}
//			}
//		}

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

		// // 遍历tmpList，找出所有的根节点和非根节点
		// if (childList != null && childList.size() > 0) {
		// for (FensUser record : childList) {
		// // 对比找出父节点
		// if (record.getPhone().equals(parentUuid)) {
		// listParentRecord.add(record);
		// } else {
		// listNotParentRecord.add(record);
		// }
		//
		// }
		// }
		// // 查询子节点
		// if (listParentRecord.size() > 0) {
		// for (FensUser record : listParentRecord) {
		// // 递归查询子节点
		// getTreeChildRecord(listNotParentRecord, record.getPhone());
		// }
		// }
		// return listParentRecord;
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

	
	//待审核
	@Override
	public JsonResult selectDSH() {
		List<FensTransaction> list = fensTransactionMapper.selectDSH();
		if (list.size() > 0 || list.isEmpty()) {
			return JsonResult.ok(list);
		}
		return JsonResult.build(500, "无审核订单");
	}

}
