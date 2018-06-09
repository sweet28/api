package com.carpi.api.controllerNew;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.QuanBaoLiRecord;
import com.carpi.api.service.QblBuyService;

@Controller
@RequestMapping("/mq")
public class QblBuyController {

	@Autowired
	private QblBuyService qblBuyService;

	// 购买券宝理商品券(一型矿机数量)
	@RequestMapping(value = "/yx", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult buyqbl(HttpServletRequest request, HttpServletResponse response) {
		// 粉丝id
		String fensUserId = request.getParameter("uid");
		// 券id
		String qid = request.getParameter("id");
		
		QuanBaoLiRecord quanBaoLiRecord = new QuanBaoLiRecord();
		quanBaoLiRecord.setFensUserId(Integer.valueOf(fensUserId));
		quanBaoLiRecord.setQuanId(Integer.valueOf(qid));
		return qblBuyService.buyqbl(quanBaoLiRecord);
	}

	// 购买券宝理商品券(2型矿机数量)
	@RequestMapping(value = "/ex", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult buyqbl2(HttpServletRequest request, HttpServletResponse response) {
		// 粉丝id
		String fensUserId = request.getParameter("uid");
		// 券id
		String qid = request.getParameter("id");

		// name position 额度 day周期
		QuanBaoLiRecord quanBaoLiRecord = new QuanBaoLiRecord();
		quanBaoLiRecord.setFensUserId(Integer.valueOf(fensUserId));
		quanBaoLiRecord.setId(Integer.valueOf(qid));
		return qblBuyService.buyqbl2(quanBaoLiRecord);
	}

	// 购买券宝理商品券(3型矿机数量)
	@RequestMapping(value = "/sx", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult buyqbl3(HttpServletRequest request, HttpServletResponse response) {
		// 粉丝id
		String fensUserId = request.getParameter("uid");
		// 券id
		String qid = request.getParameter("id");

		// name position 额度 day周期
		QuanBaoLiRecord quanBaoLiRecord = new QuanBaoLiRecord();
		quanBaoLiRecord.setFensUserId(Integer.valueOf(fensUserId));
		quanBaoLiRecord.setId(Integer.valueOf(qid));
		return qblBuyService.buyqbl3(quanBaoLiRecord);
	}
}
