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

	@RequestMapping("/traderBuyJD")
	public String traderBuyJD() {
		return "trader/traderBuyJD";
	}

	@RequestMapping("/traderBuyFK")
	public String traderBuyFK() {
		return "trader/traderBuyFK";
	}

	@RequestMapping("/traderBuySK")
	public String traderBuySK() {
		return "trader/traderBuySK";
	}

	@RequestMapping("/traderSellJD")
	public String traderSellJD() {
		return "trader/traderSellJD";
	}

	@RequestMapping("/traderSellFK")
	public String traderSellFK() {
		return "trader/traderSellFK";
	}

	@RequestMapping("/traderSellSK")
	public String traderSellSK() {
		return "trader/traderSellSK";
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

	@RequestMapping("/traderDetail")
	public String traderDetail() {
		return "trader/traderDetail";
	}

	@RequestMapping("/trader")
	public String trader() {
		return "trader/trader";
	}

	@RequestMapping("/traderXiangQing")
	public String traderXiangQing() {
		return "trader/traderXiangQing";
	}

	@RequestMapping("/myEarnings")
	public String myEarnings() {
		return "personal/myEarnings";
	}

	@RequestMapping("/myEarnings2")
	public String myEarnings2() {
		return "personal/myEarnings2";
	}

	@RequestMapping("/myEarnings3")
	public String myEarnings3() {
		return "personal/myEarnings3";
	}

	@RequestMapping("/myEarnings4")
	public String myEarnings4() {
		return "personal/myEarnings4";
	}

	@RequestMapping("/myFens")
	public String myFens() {
		return "personal/myFens";
	}

	@RequestMapping("/myInvite")
	public String myInvite() {
		return "personal/myInvite";
	}

	@RequestMapping("/myInfo")
	public String myInfo() {
		return "personal/myInfo";
	}

	@RequestMapping("/myPay")
	public String myPay() {
		return "personal/myPay";
	}

	@RequestMapping("/mySafeCenter")
	public String mySafeCenter() {
		return "personal/mySafeCenter";
	}

	@RequestMapping("/sysNotice")
	public String sysNotice() {
		return "personal/sysNotice";
	}

	@RequestMapping("/myWorkOrder")
	public String myWorkOrder() {
		return "personal/myWorkOrder";
	}

	@RequestMapping("/myWorkOrderList")
	public String myWorkOrderList() {
		return "personal/myWorkOrderList";
	}

	@RequestMapping("/anquan")
	public String anquan() {
		return "personal/anquan";
	}

	@RequestMapping("/news")
	public String news() {
		return "personal/news";
	}

}
