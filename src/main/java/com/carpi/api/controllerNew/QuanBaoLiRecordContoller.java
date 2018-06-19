package com.carpi.api.controllerNew;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.QuanBaoLiRecord;
import com.carpi.api.service.QuanBaoLiRecordService;
import com.carpi.api.service.TiQuService;

@Controller
@RequestMapping("/quan")
public class QuanBaoLiRecordContoller {

	@Autowired
	private QuanBaoLiRecordService quanBaoLiRecordService;
	
	@Autowired
	private TiQuService tiQuService;

	// 券宝理列表
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult selectToday(HttpServletRequest request, HttpServletResponse response) {
		return quanBaoLiRecordService.selectList();
	}

	// 券宝理个人订单
	@RequestMapping(value = "/dd", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult selectOne(HttpServletRequest request, HttpServletResponse response) {
		// 粉丝id
		String fensUserId = request.getParameter("uid");
		if (fensUserId == null) {
			return JsonResult.build(500, "请重新登入");
		}
		QuanBaoLiRecord quanBaoLiRecord = new QuanBaoLiRecord();
		// 类型
		String type = request.getParameter("type");
		if (StringUtils.isEmpty(type)) {
			quanBaoLiRecord.setFensUserId(Integer.valueOf(fensUserId));
			return quanBaoLiRecordService.selectOne(quanBaoLiRecord);
		}
		quanBaoLiRecord.setFensUserId(Integer.valueOf(fensUserId));
		quanBaoLiRecord.setOrderType(Integer.valueOf(type));
		return quanBaoLiRecordService.selectOne(quanBaoLiRecord);
	}
	
	// 券宝理领导人券积分信息汇总
	@RequestMapping(value = "/couponGiftInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult couponGiftInfo(HttpServletRequest request, HttpServletResponse response) {
		// 粉丝id
		String fensUserId = request.getParameter("uid");
		if (fensUserId == null) {
			return JsonResult.build(500, "请重新登入");
		}
		String phone = request.getParameter("phone");
		if (phone == null) {
			return JsonResult.build(500, "请重新登入");
		}
		return quanBaoLiRecordService.couponGiftInfo(Integer.valueOf(fensUserId),phone);
	}
	
	// 券宝理领导人券积分信息汇总
	@RequestMapping(value = "/couponGiftList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult couponGiftList(HttpServletRequest request, HttpServletResponse response) {
		// 粉丝id
		String fensUserId = request.getParameter("uid");
		if (fensUserId == null) {
			return JsonResult.build(500, "请重新登入");
		}
		String phone = request.getParameter("phone");
		if (phone == null) {
			return JsonResult.build(500, "请重新登入");
		}
		return quanBaoLiRecordService.couponGiftList(Integer.valueOf(fensUserId),phone);
	}
	
	// 券宝理领导人券积分信息汇总
	@RequestMapping(value = "/couponGiftListInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult couponGiftListInfo(HttpServletRequest request, HttpServletResponse response) {
		// 粉丝id
		String id = request.getParameter("id");
		if (id == null) {
			return JsonResult.build(500, "请重新登入");
		}
		return quanBaoLiRecordService.couponGiftListInfo(Integer.valueOf(id));
	}

	// 查询券详情
	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult xiangQing(@PathVariable("id") Integer id) {
		return quanBaoLiRecordService.xiangQing(id);
	}

	// 券保理出场
	@RequestMapping(value = "/chuchang", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult quanChuChang(@RequestParam("id") Integer id, @RequestParam("type") Integer type,
			@RequestParam("dakuantype") Integer dakuantype) {
		return quanBaoLiRecordService.quanChuChang(id, type, dakuantype);
	}

	// 根据id查询匹配订单信息
	@RequestMapping(value = "/pi/{id}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult pipeixiangQing(@PathVariable("id") Integer id) {
		return quanBaoLiRecordService.pipeixiangQing(id);
	}

	// 券保理/积分买家付款
	@RequestMapping(value = "/fuk", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult fuk(@RequestParam("pipeiId") Integer pipeiId) {
		return quanBaoLiRecordService.fuk(pipeiId);
	}

	// 券保理卖家收款
	@RequestMapping(value = "/shouk", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult shouk(@RequestParam("pipeiId") Integer pipeiId) {
		return quanBaoLiRecordService.shouk(pipeiId);
	}
	
	// 券保理/积分买家付款
	@RequestMapping(value = "/fukCoupon", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult fukCoupon(@RequestParam("pipeiId") Integer pipeiId) {
		return quanBaoLiRecordService.fukCoupon(pipeiId, 1);
	}
	
	// 券积分卖家收款
	@RequestMapping(value = "/shoukCoupon", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult shoukuanCoupon(@RequestParam("pipeiId") Integer pipeiId) {
		return quanBaoLiRecordService.shoukuanCoupon(pipeiId, 2);
	}
	
	@RequestMapping(value = "/tiqu", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult tiqu(@RequestParam("uid") Integer fensUserId) {
		return tiQuService.addQuanJiFen(fensUserId);
	}
}
