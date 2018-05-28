package com.carpi.api.controllerNew;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.FensUser;
import com.carpi.api.service.FensUserService;

@Controller
@RequestMapping("/user/fens")
public class FensUserNewController {

	@Autowired
	private FensUserService fensUserService;

	// 注册
	@RequestMapping(value = "/zzcc", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
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

		FensUser fensUser = new FensUser();
		fensUser.setPhone(phone);
		fensUser.setName(name);
		fensUser.setPwd(pwd);
		fensUser.setRefereePhone(refereePhone);

		return fensUserService.register(fensUser, code_type, code, IDcardNumber);
	}

	// 登入
	@RequestMapping(value = "/ddll", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
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

}
