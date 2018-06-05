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
	
	@RequestMapping("/myMinerKC")
	public String myMinerKC() {
		return "personal/myMinerKC";
	}
	
	@RequestMapping("/myMinerB")
	public String myMinerB() {
		return "personal/myMinerB";
	}
	
	@RequestMapping("/myMinerBKC")
	public String myMinerBKC() {
		return "personal/myMinerBKC";
	}
	
	@RequestMapping("/personal")
	public String personal() {
		return "personal/personal";
	}
	
	@RequestMapping("/traderCenter")
	public String traderCenter() {
		return "trader/traderCenter";
	}
	
	@RequestMapping("/traderCenterSell")
	public String traderCenterSell() {
		return "trader/traderCenterSell";
	}
	
	@RequestMapping("/traderMyGD")
	public String traderMyGD() {
		return "trader/traderMyGD";
	}
	
	@RequestMapping("/traderMyDFK")
	public String traderMyDFK() {
		return "trader/traderMyDFK";
	}
	
	@RequestMapping("/traderMyYFK")
	public String traderMyYFK() {
		return "trader/traderMyYFK";
	}
	
	@RequestMapping("/traderMyDone")
	public String traderMyDone() {
		return "trader/traderMyDone";
	}
	
	@RequestMapping("/traderMyCheck")
	public String traderMyCheck() {
		return "trader/traderMyCheck";
	}
	
}
