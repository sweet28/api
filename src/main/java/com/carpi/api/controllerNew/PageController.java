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
	
	@RequestMapping("/traderCenterForHYF")
	public String traderCenterForHYF() {
		return "trader/traderCenterForHYF";
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

	@RequestMapping("/newsDetail")
	public String newsDetail() {
		return "personal/newsDetail";
	}
	
	@RequestMapping("/main")
	public String main() {
		return "main";
	}
	
	@RequestMapping("/fang_kui")
	public String fang_kui() {
		return "personal/fang_kui";
	}
	
	@RequestMapping("/myABKC")
	public String myABMKC() {
		return "personal/myABKC";
	}
	
	@RequestMapping("/quanbl")
	public String quanbl() {
		return "quanbaoli/quanbl";
	}
	
	@RequestMapping("/knowledge")
	public String knowledge() {
		return "sys/knowledge";
	}
	
	@RequestMapping("/whitePaper")
	public String whitePaper() {
		return "sys/whitePaper";
	}
	
	@RequestMapping("/cpaIntro")
	public String cpaIntro() {
		return "sys/cpaIntro";
	}
	
	@RequestMapping("/minerRule")
	public String minerRule() {
		return "sys/minerRule";
	}
	
	@RequestMapping("/couponFactoring")
	public String couponFactoring() {
		return "sys/couponFactoring";
	}
	
	@RequestMapping("/activitiesMay")
	public String activitiesMay() {
		return "sys/activitiesMay";
	}
	
	
	@RequestMapping("/couponGift")
	public String couponGift() {
		return "quanbaoli/couponGift";
	}
	
	@RequestMapping("/quan_detail")
	public String quan_detail() {
		return "quanbaoli/quan_detail";
	}
	
	@RequestMapping("/quan_detail2")
	public String quan_detail2() {
		return "quanbaoli/quan_detail2";
	}
	
	@RequestMapping("/quan_detail3")
	public String quan_detail3() {
		return "quanbaoli/quan_detail3";
	}
	
	@RequestMapping("/quan_list")
	public String quan_list() {
		return "quanbaoli/quan_list";
	}
	
	@RequestMapping("/quan_SK")
	public String quan_SK() {
		return "quanbaoli/quan_SK";
	}
	
	@RequestMapping("/quan_FKXQ")
	public String quan_FKXQ() {
		return "quanbaoli/quan_FKXQ";
	}
	
	@RequestMapping("/quan_DSK")
	public String quan_DSK() {
		return "quanbaoli/quan_DSK";
	}
	
	@RequestMapping("/quan_DSK_XQ")
	public String quan_DSK_XQ() {
		return "quanbaoli/quan_DSK_XQ";
	}
	
	@RequestMapping("/quanbl2")
	public String quanbl2() {
		return "quanbaoli/quanbl2";
	}
	
	@RequestMapping("/quanbl3")
	public String quanbl3() {
		return "quanbaoli/quanbl3";
	}
	
	@RequestMapping("/couponScoreList")
	public String couponScoreList() {
		return "quanbaoli/couponScoreList";
	}
	
	@RequestMapping("/couponScoreDetail")
	public String couponScoreDetail() {
		return "quanbaoli/couponScoreDetail";
	}
	
	@RequestMapping("/couponScoreDetailInfo")
	public String couponOrderDetailInfo() {
		return "quanbaoli/couponScoreDetailInfo";
	}
	
	@RequestMapping("/quanOrderDetail")
	public String quanOrderDetail() {
		return "quanbaoli/quanOrderDetail";
	}
	
	@RequestMapping("/quanOrderDetailInfo")
	public String quanOrderDetailInfo() {
		return "quanbaoli/quanOrderDetailInfo";
	}
	
	@RequestMapping("/fensteamnum")
	public String ft() {
		return "fensteamnum";
	}
	
}
