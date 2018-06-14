package com.carpi.api.controllerNew;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cpa/quan/bao/li")
public class QuanSmsPageController {

	@RequestMapping(value = "/quanMsg")
	public String minerBuy() {
		return "quanMsg";
	}
	
}
