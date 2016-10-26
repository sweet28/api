package com.arttraining.api.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.IUserStuService;

@Controller
@RequestMapping("/users")
public class UserStuController {

	@Resource
	private IUserStuService userService;

	@RequestMapping("/show/{id}")
	public @ResponseBody UserStu toIndex(@PathVariable("id") Integer id) {
		UserStu user = this.userService.getUserStuById(id);

		return user;
	}
}
