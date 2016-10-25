package com.arttraining.api.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.IUserStuService;

@Controller
@RequestMapping("/users")
public class UserStuController {

	@Resource
	private IUserStuService userService;

	@RequestMapping("/show")
	public String toIndex(HttpServletRequest request, Model model) {
		int userId = Integer.parseInt(request.getParameter("id"));
		UserStu user = this.userService.getUserStuById(userId);
		model.addAttribute("user", user);
		return "User";
	}
}
