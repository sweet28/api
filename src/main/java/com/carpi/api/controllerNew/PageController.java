package com.carpi.api.controllerNew;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/cpa")
public class PageController {

	@RequestMapping(value = "/minerBuy" , method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String minerBuy(HttpServletRequest request, HttpServletResponse response) {
//		return "minner/minerBuy";
		return "hello world";
	}
	
	@RequestMapping("/minerHouse")
	public String minerHouse() {
		return "redirect:minner/minerHouse";
	}
	
	@RequestMapping("/minerRun")
	public String minerRun() {
		return "redirect:personal/minerRun";
	}
	
	@RequestMapping("/myMiner")
	public String myMiner() {
		return "personal/myMiner";
	}
	
	@RequestMapping("/personal")
	public String personal() {
		return "personal/personal";
	}
	
	@RequestMapping("/traderCenter")
	public String traderCenter() {
		return "trader/traderCenter";
	}
}
