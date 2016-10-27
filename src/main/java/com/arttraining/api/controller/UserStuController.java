package com.arttraining.api.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.api.bean.UserStuHeadUpdateReBean;
import com.arttraining.api.bean.UserStuReBean;
import com.arttraining.api.bean.UserStuUpdateReBean;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.ICityService;
import com.arttraining.api.service.IIdentityService;
import com.arttraining.api.service.IUserOrgService;
import com.arttraining.api.service.IUserStuService;
import com.arttraining.commons.util.TokenUtil;

/***
 * SSM (Spring SpringMVC Mybaits)
 * SSH (Struts Spring Hibernate)
 * MVC --Model View Controller
 * 模型 视图 控制器
 * 控制器 类似于Struts框架的action --- SpringMVC(Controller)
 * 视图 类似于Spring 	   ---- Spring(mapping)
 * 模型 类似于Hibernate   ---- Mybaits(dao)
 * dao--可以理解成处理数据库的对象
 * 
 * **/

//爱好者用户接口相关的方法
@Controller
@RequestMapping("/users")
public class UserStuController {

	@Resource
	private IUserStuService userService;
	@Resource
	private ICityService cityService;
	@Resource
	private IIdentityService identityService;
	@Resource
	private IUserOrgService userOrgService;

	//依据用户uid查询爱好者用户信息
	@RequestMapping("/show/{uid}")
	public @ResponseBody UserStuReBean show(@PathVariable("uid") Integer id) {
		UserStu user = this.userService.getUserStuById(id);
		
		//定义错误码和错误信息变量
		String errorCode="0";
		String errorMsg="ok";
		//这里本来想对error_code和error_msg做一个判断,但是考虑这种判断应该封装在公共类里面,故暂时未判断
		//返回指定接口对应的返回参数
		UserStuReBean userReBean = new UserStuReBean();
		//返回错误码和错误信息
		userReBean.setError_code(errorCode);
		userReBean.setError_msg(errorMsg);
		userReBean.setUid(id);
		userReBean.setUser_code(user.getUserCode());
		userReBean.setName(user.getName());
		userReBean.setMobile(user.getUserMobile());
		userReBean.setHead_pic(user.getHeadPic());
		userReBean.setSex(user.getSex());
		//城市  身份 机构都是外键ID 依据外键去查询对应的name
		Integer cityId = user.getCity();	
		String city = this.cityService.getNameById(cityId);
		userReBean.setCity(city);
		
		Integer identityId = user.getIdentity();
		String identity = this.identityService.getNameById(identityId);
		userReBean.setIdentity(identity);
		
		Integer orgId = user.getOrg();
		String org = this.userOrgService.getNameById(orgId);
		userReBean.setOrg(org);
		
		userReBean.setIntentional_college(user.getIntentionalCollege());
		userReBean.setSpecialty(user.getSpecialty());
		userReBean.setSchool(user.getSchool());
		userReBean.setEmail(user.getEmail());
		userReBean.setScore(user.getScore());
		userReBean.setRank(user.getRank());
		
		return userReBean;
	}
	//根据用户ID修改用户头像信息
	@RequestMapping("/update_head/{access_token}/{uid}/{head_pic}")
	public @ResponseBody UserStuHeadUpdateReBean updateHead(@PathVariable("access_token") String access_token,
			@PathVariable("uid") Integer id, @PathVariable("head_pic") String head_pic) {
			
		//定义错误码和错误信息变量
		String errorCode="";
		String errorMsg="";
		
		UserStuHeadUpdateReBean userReBean = new UserStuHeadUpdateReBean();
		
		//先判断是否有权限访问该接口
		boolean tokenFlag = TokenUtil.checkToken(access_token);
		if(!tokenFlag) {
			errorCode="10014";
			errorMsg="Insufficient app permissions";
			userReBean.setError_code(errorCode);
			userReBean.setError_msg(errorMsg);
		}
		else {
			errorCode="0";
			errorMsg="ok";
					
			userReBean.setError_code(errorCode);
			userReBean.setError_msg(errorMsg);
			//依据uid查询相应的爱好者用户信息
			//这里先新建一个UserStu用于修改部分用户信息
			UserStu user = new UserStu();
			user.setId(id);	
			//设置修改后的头像
			user.setHeadPic(head_pic);
			//返回修改结果 大于0表示修改成功 ==0表示修改失败
			int rtn = this.userService.updateUserStuBySelective(user);
			
			//这里我先不判断返回结果 因为文档里面尚未涉及修改失败的错误信息
			//修改之后 依据id获取最新对应的用户信息
			user = this.userService.getUserStuById(id);
			userReBean.setError_code(errorCode);
			userReBean.setError_msg(errorMsg);
			userReBean.setUid(id);
			userReBean.setUser_code(user.getUserCode());
			userReBean.setName(user.getName());
			userReBean.setHead_pic(user.getHeadPic());
		}
		return userReBean;
	}
	//根据用户ID修改用户密码
	@RequestMapping("/update_pwd/{access_token}/{uid}/{new_pwd}")
	public @ResponseBody UserStuUpdateReBean updatePwd(@PathVariable("access_token") String access_token,
			@PathVariable("uid") Integer id, @PathVariable("new_pwd") String new_pwd) {
		//定义错误码和错误信息变量
		String errorCode="";
		String errorMsg="";
				
		UserStuUpdateReBean userReBean = new UserStuUpdateReBean();
		
		//先判断是否有权限访问该接口
		boolean tokenFlag = TokenUtil.checkToken(access_token);
		if(!tokenFlag) {
			errorCode="10014";
			errorMsg="Insufficient app permissions";
			userReBean.setError_code(errorCode);
			userReBean.setError_msg(errorMsg);
		}
		else {
			errorCode="0";
			errorMsg="ok";
					
			userReBean.setError_code(errorCode);
			userReBean.setError_msg(errorMsg);
			//依据uid查询相应的爱好者用户信息
			UserStu user = new UserStu();
			user.setId(id);	
			//设置修改后的密码
			user.setPwd(new_pwd);
			
			//返回修改结果 大于0表示修改成功 ==0表示修改失败
			int rtn = this.userService.updateUserStuBySelective(user);
			
			//这里我先不判断返回结果 因为文档里面尚未涉及修改失败的错误信息
			user = this.userService.getUserStuById(id);
			userReBean.setError_code(errorCode);
			userReBean.setError_msg(errorMsg);
			userReBean.setUid(id);
			userReBean.setUser_code(user.getUserCode());
			userReBean.setName(user.getName());
			
		}	
		return userReBean;
	}
	//根据用户ID设置用户信息
	@RequestMapping("/set_info/{access_token}/{uid}/{name}/{sex}/{city}/{identity}/{school}/{org}/{specialty}/{intentional_college}/{email}")
	public @ResponseBody UserStuUpdateReBean setInfo(@PathVariable("access_token") String access_token,
			@PathVariable("uid") Integer id, @PathVariable("name") String name,
			@PathVariable("sex") String sex, @PathVariable("city") Integer city,
			@PathVariable("identity") Integer identity,
			@PathVariable("school") String school, @PathVariable("org") Integer org,
			@PathVariable("specialty") String specialty,
			@PathVariable("intentional_college") String intentional_college,
			@PathVariable("email") String email) {
		
		System.out.println(specialty);
		//定义错误码和错误信息变量
		String errorCode="";
		String errorMsg="";
								
		UserStuUpdateReBean userReBean = new UserStuUpdateReBean();
						
		//先判断是否有权限访问该接口
		boolean tokenFlag = TokenUtil.checkToken(access_token);
		if(!tokenFlag) {
			errorCode="10014";
			errorMsg="Insufficient app permissions";
			userReBean.setError_code(errorCode);
			userReBean.setError_msg(errorMsg);
		}
		else {
			errorCode="0";
			errorMsg="ok";
									
			userReBean.setError_code(errorCode);
			userReBean.setError_msg(errorMsg);
			//依据uid查询相应的爱好者用户信息
			UserStu user = new UserStu();
			user.setId(id);	
			//设置修改用户个人信息
			user.setName(name);
			user.setSex(sex);
			user.setCity(city);
			user.setIdentity(identity);
			user.setSchool(school);
			user.setOrg(org);
			user.setSpecialty(specialty);
			user.setIntentionalCollege(intentional_college);
			user.setEmail(email);
							
			//返回修改结果 大于0表示修改成功 ==0表示修改失败
			int rtn = this.userService.updateUserStuBySelective(user);
					
			//这里我先不判断返回结果 因为文档里面尚未涉及修改失败的错误信息
			user = this.userService.getUserStuById(id);
			userReBean.setError_code(errorCode);
			userReBean.setError_msg(errorMsg);
			userReBean.setUid(id);
			userReBean.setUser_code(user.getUserCode());
			userReBean.setName(user.getName());
							
		}	
		return userReBean;

	}
	//根据用户ID修改用户手机号码
	@RequestMapping("/change_mobile/{access_token}/{uid}/{mobile}")
	public @ResponseBody UserStuUpdateReBean changeMobile(@PathVariable("access_token") String access_token,
			@PathVariable("uid") Integer id, @PathVariable("mobile") String mobile) {
		
		//定义错误码和错误信息变量
		String errorCode="";
		String errorMsg="";
						
		UserStuUpdateReBean userReBean = new UserStuUpdateReBean();
				
		//先判断是否有权限访问该接口
		boolean tokenFlag = TokenUtil.checkToken(access_token);
		if(!tokenFlag) {
			errorCode="10014";
			errorMsg="Insufficient app permissions";
			userReBean.setError_code(errorCode);
			userReBean.setError_msg(errorMsg);
		}
		else {
			errorCode="0";
			errorMsg="ok";
							
			userReBean.setError_code(errorCode);
			userReBean.setError_msg(errorMsg);
			//依据uid查询相应的爱好者用户信息
			UserStu user = new UserStu();
			user.setId(id);	
			//设置修改后的手机号码
			user.setUserMobile(mobile);
					
			//返回修改结果 大于0表示修改成功 ==0表示修改失败
			int rtn = this.userService.updateUserStuBySelective(user);
			
			//这里我先不判断返回结果 因为文档里面尚未涉及修改失败的错误信息
			user = this.userService.getUserStuById(id);
			userReBean.setError_code(errorCode);
			userReBean.setError_msg(errorMsg);
			userReBean.setUid(id);
			userReBean.setUser_code(user.getUserCode());
			userReBean.setName(user.getName());
					
		}	
		return userReBean;
}
	
}
