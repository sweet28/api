package com.carpi.api.controllerNew;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cpa")
public class PageController {

	@RequestMapping(value = "/minerBuy")
	public String minerBuy() {
		return "miner/minerBuy";
	}
	
	@RequestMapping("/minerHouse")
	public String minerHouse() {
		return "miner/minerHouse";
	}
	
	@RequestMapping("/minerRun")
	public String minerRun() {
		return "personal/minerRun";
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
