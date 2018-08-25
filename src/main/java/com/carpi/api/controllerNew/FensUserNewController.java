package com.carpi.api.controllerNew;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.arttraining.api.bean.SimpleReBean;
import com.arttraining.api.pojo.Token;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.impl.TokenService;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.JsonResult;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TokenUtil;
import com.carpi.api.pojo.FensComputingPower;
import com.carpi.api.pojo.FensTeam;
import com.carpi.api.pojo.FensUser;
import com.carpi.api.service.FensUserService;
import com.carpi.api.service.JiaoYiService;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;

@Controller
@RequestMapping("/user/fens")
public class FensUserNewController {

	@Autowired
	private FensUserService fensUserService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private JiaoYiService jiaoYiService;

	// 注册
	@RequestMapping(value = "/zc", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult register(HttpServletRequest request, HttpServletResponse response) {
		// 手机验证码类型
		String code_type = request.getParameter("yan_ma");
		// 身份证号码
		String IDcardNumber = request.getParameter("sf_cd");
		// 手机验证码
		String code = request.getParameter("ym");
		// 手机号码
		String phone = request.getParameter("sh");
		// 密码
		String pwd = request.getParameter("mn");
		// 邀请人手机号码
		String refereePhone = request.getParameter("yqrph");
		// 用户姓名
		String name = request.getParameter("xn");
		// 用户身份证图片
		String img = request.getParameter("img");

		if (StringUtils.isEmpty(img)) {
			return JsonResult.build(500, "上传的身份证图片有误，请重新上传");
		}

		FensUser fensUser = new FensUser();
		fensUser.setPhone(phone);
		fensUser.setName(name);
		fensUser.setPwd(pwd);
		fensUser.setRefereePhone(refereePhone);
		fensUser.setBak3(img);

		return fensUserService.register(fensUser, code_type, code, IDcardNumber);
	}

	// 登入(手机号和密码)
	@RequestMapping(value = "/dl", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult login(HttpServletRequest request, HttpServletResponse response) {
		// 手机号码
		String phone = request.getParameter("sh");
		// 密码
		String pwd = request.getParameter("mn");

		FensUser fensUser = new FensUser();
		fensUser.setPhone(phone);
		fensUser.setPwd(pwd);

		return fensUserService.login(fensUser);
	}

	@RequestMapping(value = "/dl2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult login2(HttpServletRequest request, HttpServletResponse response) {
		// 手机号码
		String phone = request.getParameter("sh");
		// 验证码
		String code = request.getParameter("ym");
		// 验证码类型
		String code_type = request.getParameter("ym_tp");

		return fensUserService.login2(phone, code, code_type);
	}

	// 登入(短信验证码)

	// 根据ID查个人信息
	@RequestMapping(value = "/userxx", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public FensUser info(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("uid");
		FensUser fensUser = new FensUser();
		fensUser.setId(Integer.valueOf(id));
		return fensUserService.info(fensUser);
	}

	// 忘记密码
	@RequestMapping(value = "/wjmm", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult forgetPwd(HttpServletRequest request, HttpServletResponse response) {
		// 手机验证码类型
		String code_type = request.getParameter("yan_ma");
		// 手机验证码
		String code = request.getParameter("ym");
		// 手机号码
		String phone = request.getParameter("sh");
		// 新密码
		String pwd = request.getParameter("xin_mm");
		FensUser fensUser = new FensUser();
		fensUser.setPhone(phone);
		fensUser.setPwd(pwd);
		return fensUserService.forgetPwd(fensUser, code_type, code);
	}

	// 修改密码
	@RequestMapping(value = "/xgmm", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult updatePwd(HttpServletRequest request, HttpServletResponse response) {
		// 旧密码
		String OldPwd = request.getParameter("old_mm");
		// 新密码
		String newPwd = request.getParameter("new_mm");
		// 粉丝id (fensUserId)
		String fensUserId = request.getParameter("uid");

		return fensUserService.updatePwd(OldPwd, newPwd, Integer.valueOf(fensUserId));
	}

	// 设置交易密码
	@RequestMapping(value = "/szjymm", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult jiaoYi(HttpServletRequest request, HttpServletResponse response) {
		// 交易密码
		String capitalPwd = request.getParameter("old_jymm");
		// 粉丝id
		String id = request.getParameter("uid");
		// 手机验证码
		String code = request.getParameter("code");
		// 手机号码
		String phone = request.getParameter("sh");
		FensUser fensUser = new FensUser();
		fensUser.setId(Integer.valueOf(id));
		fensUser.setPhone(phone);
		fensUser.setCapitalPwd(capitalPwd);
		return fensUserService.jiaoYi(fensUser, code);
	}

	// 校验交易密码
	@RequestMapping(value = "/jiaoyan", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult zijin(HttpServletRequest request, HttpServletResponse response) {
		// 交易密码
		String capitalPwd = request.getParameter("old_jymm");
		// 粉丝id
		String id = request.getParameter("uid");
		return fensUserService.zijin(Integer.valueOf(id), capitalPwd);
	}

	// 修改交易密码
	@RequestMapping(value = "/xgjymm", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult updateJiaoYi(HttpServletRequest request, HttpServletResponse response) {
		// 旧交易密码
		String oldCapitalPwd = request.getParameter("old_jymm");
		// 新交易密码
		String newCapitalPwd = request.getParameter("new_jymm");
		// 粉丝id
		String fensUserId = request.getParameter("uid");
		// 手机验证码
		String code = request.getParameter("code");
		// 手机
		String phone = request.getParameter("sh");

		return fensUserService.updateJiaoYi(oldCapitalPwd, newCapitalPwd, Integer.valueOf(fensUserId), code, phone);
	}

	// 修改信息
	@RequestMapping(value = "/xgxx", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult updateInfo(HttpServletRequest request, HttpServletResponse response) {
		// 手机号码
		String phone = request.getParameter("sh");
		FensUser fensUser = new FensUser();
		fensUser.setPhone(phone);
		return fensUserService.updateInfo2(fensUser);
	}

	// 粉丝团列表
	@RequestMapping(value = "/fentlb", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FensTeam> slectAll(HttpServletRequest request, HttpServletResponse response) {
		// 当前页
		String page = request.getParameter("pg");
		// 每页多少条
		String num = request.getParameter("ts");
		// 粉丝id
		String fensUserId = request.getParameter("uid");
		// type传all查询所有
		String type = request.getParameter("tp");

		return fensUserService.selectAll(Integer.valueOf(page), Integer.valueOf(num), Integer.valueOf(fensUserId),
				type);
	}

	// 粉丝算力列表明细
	@RequestMapping(value = "/sllb", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FensComputingPower> suanlilist(HttpServletRequest request, HttpServletResponse response) {
		// 当前页
		String page = request.getParameter("pg");
		// 每页多少条
		String num = request.getParameter("ts");
		// 粉丝id
		String fensUserId = request.getParameter("uid");

		return fensUserService.selectComputingPower(Integer.valueOf(page), Integer.valueOf(num),
				Integer.valueOf(fensUserId));
	}

	// 添加粉丝算力明细
	// @RequestMapping(value = "/add", method = RequestMethod.POST, produces =
	// "application/json;charset=UTF-8")
	// @ResponseBody
	// public JsonResult addselectComputingPower(FensComputingPower
	// fensComputingPower) {
	// return fensUserService.addselectComputingPower(fensComputingPower);
	// }

	// 修改粉丝算力
	// @RequestMapping(value = "/update", method = RequestMethod.POST, produces
	// =
	// "application/json;charset=UTF-8")
	// @ResponseBody
	// public JsonResult updateComputingPower(FensComputingPower
	// fensComputingPower)
	// {
	// return fensUserService.updateComputingPower(fensComputingPower);
	// }

	// 粉丝算力值和
	@RequestMapping(value = "/he", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult selectSum(HttpServletRequest request, HttpServletResponse response) {
		// 粉丝id
		String fensUserId = request.getParameter("uid");

		return fensUserService.selectSum(Integer.valueOf(fensUserId));
	}

	// // 粉丝团列表2
	// @RequestMapping(value = "/list2", method = RequestMethod.POST, produces =
	// "application/json;charset=UTF-8")
	// @ResponseBody
	// public PageInfo<FensUser> slectAllUser(HttpServletRequest request,
	// HttpServletResponse response) {
	// // 当前页
	// String page = request.getParameter("pg");
	// // 每页多少条
	// String num = request.getParameter("ts");
	// // 手机号码
	// String phone = request.getParameter("sh");
	// // type传all查询所有
	// String type = request.getParameter("tp");
	//
	// return fensUserService.selectAllUser(Integer.valueOf(page),
	// Integer.valueOf(num), phone, type);
	// }

	// 粉丝团列表
	// @RequestMapping(value = "/listFens", method = RequestMethod.POST,
	// produces =
	// "application/json;charset=UTF-8")
	// @ResponseBody
	// public List<FensUser> slectListFens(HttpServletRequest request,
	// HttpServletResponse response) {
	// // 手机号码
	// String phone = request.getParameter("sh");
	// System.out.println("controller:" + phone);
	//
	// return fensUserService.selectListFens(phone);
	// }

	// 粉丝团列表
	@RequestMapping(value = "/listFens2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JSONObject slectListFens2(HttpServletRequest request, HttpServletResponse response) {
		// 手机号码
		String phone = request.getParameter("sh");
		System.out.println("controller:" + phone);

		return fensUserService.selectListFens2(phone);
	}

	// 亲友团列表
	@RequestMapping(value = "/listQINYOU", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FensUser> slectListQINYOU(HttpServletRequest request, HttpServletResponse response) {
		// 手机号码
		String phone = request.getParameter("sh");

		return fensUserService.selectListQINYOU(phone);
	}

	// 亲友团列表2
	@RequestMapping(value = "/listQINYOUJson", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult selectListQINYOUJson(HttpServletRequest request, HttpServletResponse response) {
		// 手机号码
		String phone = request.getParameter("sh");

		return fensUserService.selectListQINYOUJson(phone);
	}

	// 粉丝团列表old
	@RequestMapping(value = "/selectFensUserGrade222", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JSONObject selectFensUserGrade(HttpServletRequest request, HttpServletResponse response) {
		// 手机号码
		String phone = request.getParameter("sh");
		String uid = request.getParameter("uid");
		System.out.println("controller:" + phone);

		return fensUserService.selectFensUserGrade(phone, Integer.valueOf(uid));
	}

	// 粉丝团列表old
	@RequestMapping(value = "/selectSelfGrade222", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JSONObject selectSelfGrade(HttpServletRequest request, HttpServletResponse response) {
		// 手机号码
		String phone = request.getParameter("sh");
		String uid = request.getParameter("uid");
		System.out.println("controller:" + phone);

		return fensUserService.selectSelfGrade(phone, Integer.valueOf(uid));
	}

	// 粉丝团列表
	@RequestMapping(value = "/getFensUserGrade", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JSONObject getFensUserGrade(HttpServletRequest request, HttpServletResponse response) {
		// 手机号码
		String phone = request.getParameter("sh");
		String uid = request.getParameter("uid");
		System.out.println("controller:" + phone);

		return fensUserService.getFensUserGrade(phone, Integer.valueOf(uid));
	}

	// 粉丝团列表 只返回算力跟人数
	@RequestMapping(value = "/getFensUserGradeLittle", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JSONObject getFensUserGradeLittle(HttpServletRequest request, HttpServletResponse response) {
		// 手机号码
		String phone = request.getParameter("sh");
		String uid = request.getParameter("uid");
		System.out.println("controller:" + phone);

		return fensUserService.getFensUserGradeLittle(phone, Integer.valueOf(uid));
	}

	// 安全退出
	@RequestMapping(value = "/exit", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult exitTuiChu(HttpServletRequest request, HttpServletResponse response) {
		String accessToken = "";
		accessToken = request.getParameter("access_token");
		ServerLog.getLogger().warn("access_token:" + accessToken);
		if (accessToken == null) {
			return JsonResult.build(20032, ErrorCodeConfigUtil.ERROR_MSG_ZH_20032);
		} else {
			if (accessToken.equals("")) {

				return JsonResult.build(20032, ErrorCodeConfigUtil.ERROR_MSG_ZH_20032);

			} else {
				// coffee add 1215
				// 1.先判断token是否存在 如果存在 直接修改 如果不存在 直接删除token
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("token", accessToken);
				Token token = this.tokenService.getOneTokenInfo(map);
				if (token != null) {
					Date date = new Date();
					token.setLoginTime(date);
					token.setIsDeleted(1);
					this.tokenService.updateTokenInfo(token);
					return JsonResult.ok();
				} else {
					// end
					if (TokenUtil.deleteToken(accessToken)) {
						return JsonResult.ok();
					} else {
						return JsonResult.build(20032, ErrorCodeConfigUtil.ERROR_MSG_ZH_20032);

					}
				}
			}
		}

	}

	// 待审核
	@RequestMapping(value = "/dsh", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult selectDSH(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("uid");

		if (id != null) {
			return fensUserService.selectDSH(Integer.parseInt(id));
		} else {
			return JsonResult.build(500, "请重新登录");
		}
	}

	// 粉丝交易量(当天)
	@RequestMapping(value = "/jyl", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult JYLsum(HttpServletRequest request, HttpServletResponse response) {
		return jiaoYiService.JYLsum();
	}

	// 校验粉丝
	@RequestMapping(value = "/checkFens", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult checkFens(HttpServletRequest request, HttpServletResponse response) {

		String id = request.getParameter("uid");
		String phone = request.getParameter("phone");

		if (id == null) {
			return JsonResult.build(500, "会员ID为空");
		}

		if (phone == null) {
			return JsonResult.build(500, "手机号为空");
		}

		return fensUserService.checkFens(Integer.valueOf(id), phone);
	}

	// 获取粉丝节点算力奖励列表
	@RequestMapping(value = "/selectGradePowerGift", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult selectGradePowerGift(HttpServletRequest request, HttpServletResponse response) {

		String id = request.getParameter("uid");

		if (id == null) {
			return JsonResult.build(500, "会员ID为空,请重新登录");
		}

		return fensUserService.selectGradePowerGift(Integer.valueOf(id));
	}

	// 获取算力收益
	@RequestMapping(value = "/addGradePowerGift", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult addGradePowerGift(HttpServletRequest request, HttpServletResponse response) {

		String id = request.getParameter("uid");

		if (id == null) {
			return JsonResult.build(500, "会员ID为空,请重新登录");
		}

		return fensUserService.addGradePowerGift(Integer.valueOf(id));
	}

	// 获取算力收益
	@RequestMapping(value = "/addEarnGift", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult addEarnGift(HttpServletRequest request, HttpServletResponse response) {

		String id = request.getParameter("uid");

		if (id == null) {
			return JsonResult.build(500, "会员ID为空,请重新登录");
		}

		return fensUserService.addEarnGift(Integer.valueOf(id));
	}

	// 获取粉丝节点算力奖励列表
	@RequestMapping(value = "/selectGradeEran", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult selectGradeEran(HttpServletRequest request, HttpServletResponse response) {

		String id = request.getParameter("uid");

		if (id == null) {
			return JsonResult.build(500, "会员ID为空,请重新登录");
		}

		return fensUserService.selectGradeEran(Integer.valueOf(id));
	}

}
