package com.carpi.api.controllerNew;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.Aminer;
import com.carpi.api.pojo.AminerRecord;
import com.carpi.api.pojo.Bminer;
import com.carpi.api.pojo.FensTransaction;
import com.carpi.api.service.FensRecordServcie;
import com.carpi.api.service.JiaoYiService;
import com.carpi.api.service.MinerRecordService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/kuangjy/jy")
public class MinerRecordNewController {

	@Autowired
	private MinerRecordService minerRecordService;

	@Autowired
	private FensRecordServcie fensRecordServcie;

	@Autowired
	private JiaoYiService jiaoYiService;

	// 根据粉丝id查询交易记录
	@RequestMapping(value = "/jjylb", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FensTransaction> selectRecord(HttpServletRequest request, HttpServletResponse response) {
		// 当前页
		String page = request.getParameter("dqy");
		// 每页条数
		String row = request.getParameter("ts");
		// TraderId(挂单人id)
		String traderId = request.getParameter("trddi");
		// fensUserId(接单人id)
		String fensUserId = request.getParameter("jddi");
		// 交易类型（买单买单）
		String traderType = request.getParameter("type");
		// 交易状态 0代表挂单中；1代表成交待付款；2代表成交已付款待卖家确认；3代表已完成 traderState
		String traderState = request.getParameter("jystu");

		FensTransaction fensTransaction = new FensTransaction();
		fensTransaction.setTraderId(Integer.valueOf(traderId));
		fensTransaction.setFensUserId(Integer.valueOf(fensUserId));
		fensTransaction.setTraderType(Integer.valueOf(traderType));
		fensTransaction.setTraderState(Integer.valueOf(traderState));

		return fensRecordServcie.selectRecord(Integer.valueOf(page), Integer.valueOf(row), fensTransaction);
	}

	// 根据粉丝待付款交易记录
	@RequestMapping(value = "/dfklb", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<FensTransaction> selectDFKRecord(HttpServletRequest request, HttpServletResponse response) {
		// TraderId(挂单人id)
		String traderId = request.getParameter("trddi");
		// fensUserId(接单人id)
		String fensUserId = request.getParameter("jddi");

		FensTransaction fensTransaction = new FensTransaction();

		fensTransaction.setTraderId(Integer.valueOf(traderId));
		fensTransaction.setFensUserId(Integer.valueOf(fensUserId));

		return fensRecordServcie.selectDFKRecord(fensTransaction);
	}

	// 根据粉丝待收款交易记录
	@RequestMapping(value = "/dsklb", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<FensTransaction> selectDSKRecord(HttpServletRequest request, HttpServletResponse response) {
		// TraderId(挂单人id)
		String traderId = request.getParameter("trddi");
		// fensUserId(接单人id)
		String fensUserId = request.getParameter("jddi");

		FensTransaction fensTransaction = new FensTransaction();

		fensTransaction.setTraderId(Integer.valueOf(traderId));
		fensTransaction.setFensUserId(Integer.valueOf(fensUserId));

		return fensRecordServcie.selectDSKRecord(fensTransaction);
	}

	// 根据粉丝完成交易记录
	@RequestMapping(value = "/ywclb", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<FensTransaction> selectYWCRecord(HttpServletRequest request, HttpServletResponse response) {

		// TraderId(挂单人id)
		String traderId = request.getParameter("trddi");
		// fensUserId(接单人id)
		String fensUserId = request.getParameter("jddi");

		FensTransaction fensTransaction = new FensTransaction();

		fensTransaction.setTraderId(Integer.valueOf(traderId));
		fensTransaction.setFensUserId(Integer.valueOf(fensUserId));
		return fensRecordServcie.selectYWCRecord(fensTransaction);
	}

	// 根据粉丝挂单交易记录
	@RequestMapping(value = "/gdlb", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<FensTransaction> selectGDRecord(HttpServletRequest request, HttpServletResponse response) {
		// TraderId(挂单人id)
		String traderId = request.getParameter("trddi");
		FensTransaction fensTransaction = new FensTransaction();
		fensTransaction.setTraderId(Integer.valueOf(traderId));
		return fensRecordServcie.selectGDRecord(fensTransaction);
	}

	// 根据粉丝id查询成交交易记录
	@RequestMapping(value = "/cjlb", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FensTransaction> selectCJRecord(HttpServletRequest request, HttpServletResponse response) {
		// 当前页
		String page = request.getParameter("dqy");
		// 每页条数
		String row = request.getParameter("ts");
		// TraderId(挂单人id)
		String traderId = request.getParameter("trddi");
		// fensUserId(接单人id)
		String fensUserId = request.getParameter("jddi");
		// 交易类型（买单买单）
		String traderType = request.getParameter("type");
		// 交易状态 0代表挂单中；1代表成交待付款；2代表成交已付款待卖家确认；3代表已完成 traderState
		String traderState = request.getParameter("jystu");

		FensTransaction fensTransaction = new FensTransaction();
		fensTransaction.setTraderId(Integer.valueOf(traderId));
		fensTransaction.setFensUserId(Integer.valueOf(fensUserId));
		fensTransaction.setTraderType(Integer.valueOf(traderType));
		fensTransaction.setTraderState(Integer.valueOf(traderState));

		return fensRecordServcie.selectCJRecord(Integer.valueOf(page), Integer.valueOf(row), fensTransaction);
	}

	// 根据粉丝id查询交易记录
	@RequestMapping(value = "/xiangq", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public FensTransaction selectRecord2(HttpServletRequest request, HttpServletResponse response) {
		// 根据id查询（id）
		String id = request.getParameter("xiangid");
		return fensRecordServcie.selectRecordByID(Integer.valueOf(id));
	}

	// 根据粉丝交易记录增加
	@RequestMapping(value = "/zjjl", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult addRecord1(HttpServletRequest request, HttpServletResponse response) {
		// 交易CPA数
		String traderCount = request.getParameter("jysl");
		// 委托价格EntrustPrice
		String entrustPrice = request.getParameter("jgwt");
		// TraderId(挂单人id)
		String traderId = request.getParameter("trddi");
		// fensTransaction.getTraderType() == 1 或者 2
		String traderType = request.getParameter("mmtype");

		FensTransaction fensTransaction = new FensTransaction();
		fensTransaction.setTraderCount(Double.valueOf(traderCount));
		fensTransaction.setEntrustPrice(Double.valueOf(entrustPrice));
		fensTransaction.setTraderId(Integer.valueOf(traderId));
		fensTransaction.setTraderType(Integer.valueOf(traderType));

		return fensRecordServcie.addRecord(fensTransaction);
	}

	// 根据粉丝交易记录修改(原updateRecord)
	// @RequestMapping(value = "/xgjl", method = RequestMethod.POST, produces =
	// "application/json;charset=UTF-8")
	// @ResponseBody
	// public JsonResult updateRecord(FensTransaction fensTransaction) {
	// return fensRecordServcie.updateRecord(fensTransaction);
	// }

	// 接单人接单(买单)
	@RequestMapping(value = "/buyDanJieDan", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult buyDanJieDan(HttpServletRequest request, HttpServletResponse response) {
		// 但当前用户id
		String fensUserId = request.getParameter("uid");
		// 当前订单id
		String id = request.getParameter("id");
		FensTransaction fensTransaction = new FensTransaction();
		fensTransaction.setFensUserId(Integer.valueOf(fensUserId));
		fensTransaction.setId(Integer.valueOf(id));
		return jiaoYiService.buyDanJieDan(fensTransaction);
	}

	// 买单人已付款（买单）
	@RequestMapping(value = "/buyDanYiFu", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult buyDanYiFu(HttpServletRequest request, HttpServletResponse response) {
		// 但当前用户id
		String traderId = request.getParameter("uid");
		// 当前订单id
		String id = request.getParameter("id");
		FensTransaction fensTransaction = new FensTransaction();
		fensTransaction.setTraderId(Integer.valueOf(traderId));
		fensTransaction.setId(Integer.valueOf(id));
		return jiaoYiService.buyDanYiFu(fensTransaction);
	}

	// 接单人接单(卖单)
	@RequestMapping(value = "/sellDanJieDan", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult sellDanJieDan(HttpServletRequest request, HttpServletResponse response) {
		// 但当前用户id
		String fensUserId = request.getParameter("uid");
		// 当前订单id
		String id = request.getParameter("id");
		FensTransaction fensTransaction = new FensTransaction();
		fensTransaction.setFensUserId(Integer.valueOf(fensUserId));
		fensTransaction.setId(Integer.valueOf(id));
		return jiaoYiService.sellDanJieDan(fensTransaction);
	}

	// 买单人已付款（卖单）
	@RequestMapping(value = "/sellDanYiFu", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult sellDanYiFu(HttpServletRequest request, HttpServletResponse response) {
		// 但当前用户id
		String fensUserId = request.getParameter("uid");
		// 当前订单id
		String id = request.getParameter("id");
		FensTransaction fensTransaction = new FensTransaction();
		fensTransaction.setFensUserId(Integer.valueOf(fensUserId));
		fensTransaction.setId(Integer.valueOf(id));
		return jiaoYiService.sellDanYiFu(fensTransaction);
	}

	// 根据粉丝交易记录修改(原updateRecordCJ)
	@RequestMapping(value = "/RecordCJ", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult updateRecordCJ(HttpServletRequest request, HttpServletResponse response) {
		// 交易类型
		String traderType = request.getParameter("type");
		// FensUserId(接单人id)
		String fensUserId = request.getParameter("fud");
		// TraderId(挂单人Id)
		String traderId = request.getParameter("td");
		// id(单子)
		String id = request.getParameter("uid");
		FensTransaction fensTransaction = new FensTransaction();
		fensTransaction.setTraderType(Integer.valueOf(traderType));
		fensTransaction.setFensUserId(Integer.valueOf(fensUserId));
		fensTransaction.setTraderId(Integer.valueOf(traderId));
		fensTransaction.setId(Integer.valueOf(id));

		return fensRecordServcie.updateRecordCJ(fensTransaction);
	}

	// 购买a矿机
	@RequestMapping(value = "/buyAMiner", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult buyAMiner(HttpServletRequest request, HttpServletResponse response) {
		// 购买的矿机类型
		String type = request.getParameter("lx");
		// FensUserId(粉丝id)
		String fensUserId = request.getParameter("uid");
		Aminer aminer = new Aminer();
		aminer.setType(Integer.valueOf(type));
		aminer.setFensUserId(Integer.valueOf(fensUserId));
		return minerRecordService.buyAMiner(aminer);
	}

	// 购买b矿机
	@RequestMapping(value = "/buyBMiner", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult buyBMiner(HttpServletRequest request, HttpServletResponse response) {
		// FensUserId(粉丝id)
		String fensUserId = request.getParameter("uid");
		// 购买的矿机类型
		String type = request.getParameter("lx");
		Bminer bminer = new Bminer();
		bminer.setFensUserId(Integer.valueOf(fensUserId));
		bminer.setType(Integer.valueOf(type));
		return minerRecordService.buyBMiner(bminer);
	}

}
