package com.carpi.api.controllerNew;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.FensMiner;
import com.carpi.api.service.FensMinerService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("user/miner")
public class FensMinerNewController {

	@Autowired
	private FensMinerService fensMinerService;

	// 根据粉丝id查询矿机
	@RequestMapping(value = "/minerList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult minerList2(HttpServletRequest request, HttpServletResponse response) {
		String fensUserId = request.getParameter("uid");
		if (StringUtils.isEmpty(fensUserId)) {
			return JsonResult.build(500, "请重新输入");
		}
		return fensMinerService.selectMinner(Integer.valueOf(fensUserId));
	}

	// 根据粉丝id查询矿机
	@RequestMapping(value = "/kjlb", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FensMiner> minerList(HttpServletRequest request, HttpServletResponse response) {
		// 当前页
		String page = request.getParameter("pg");
		// 每页的条数
		String row = request.getParameter("ts");
		// 粉丝id
		String fensUserId = request.getParameter("uid");

		return fensMinerService.selectAMinner(Integer.valueOf(page), Integer.valueOf(row), Integer.valueOf(fensUserId));
	}

	// 根据粉丝id查询A矿机
	@RequestMapping(value = "/kuA", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FensMiner> minerAList(HttpServletRequest request, HttpServletResponse response) {
		// 当前页
		String page = request.getParameter("pg");
		// 每页的条数
		String row = request.getParameter("ts");
		// 粉丝id
		String fensUserId = request.getParameter("uid");
		return fensMinerService.selectAMinner(Integer.valueOf(page), Integer.valueOf(row), Integer.valueOf(fensUserId));
	}

	// 根据粉丝id查询A矿机矿池
	@RequestMapping(value = "/kuAListKC", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FensMiner> minerAListKC(HttpServletRequest request, HttpServletResponse response) {
		// 当前页
		String page = request.getParameter("pg");
		// 每页的条数
		String row = request.getParameter("ts");
		// 粉丝id
		String fensUserId = request.getParameter("uid");

		return fensMinerService.selectAMinnerKC(Integer.valueOf(page), Integer.valueOf(row),
				Integer.valueOf(fensUserId));
	}

	// 根据粉丝id查询AB矿机库存
	@RequestMapping(value = "/kucunABList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FensMiner> minerABListKC(HttpServletRequest request, HttpServletResponse response) {
		// 当前页
		String page = request.getParameter("pg");
		// 每页的条数
		String row = request.getParameter("ts");
		// 粉丝id
		String fensUserId = request.getParameter("uid");

		return fensMinerService.selectABMinnerKC(Integer.valueOf(page), Integer.valueOf(row),
				Integer.valueOf(fensUserId));
	}

	// 根据粉丝id查询B矿机
	@RequestMapping(value = "/kuB", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FensMiner> minerBList(HttpServletRequest request, HttpServletResponse response) {
		// 当前页
		String page = request.getParameter("pg");
		// 每页的条数
		String row = request.getParameter("ts");
		// 粉丝id
		String fensUserId = request.getParameter("uid");

		return fensMinerService.selectBMinner(Integer.valueOf(page), Integer.valueOf(row), Integer.valueOf(fensUserId));
	}

	// 根据粉丝id查询B矿机矿池
	@RequestMapping(value = "/kuBListKC", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<FensMiner> minerBListKC(HttpServletRequest request, HttpServletResponse response) {
		// 当前页
		String page = request.getParameter("pg");
		// 每页的条数
		String row = request.getParameter("ts");
		// 粉丝id
		String fensUserId = request.getParameter("uid");

		return fensMinerService.selectBMinnerKC(Integer.valueOf(page), Integer.valueOf(row),
				Integer.valueOf(fensUserId));
	}

	// 解冻
	@RequestMapping(value = "/minerjd", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult minerJD(HttpServletRequest request, HttpServletResponse response) {
		// id
		String id = request.getParameter("id");
		FensMiner miner = new FensMiner();
		miner.setId(Integer.valueOf(id));
		return fensMinerService.thawABMiner(miner);
	}

	// 解冻
	@RequestMapping(value = "/minerjd2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult minerJD2(HttpServletRequest request, HttpServletResponse response) {
		// id
		String id = request.getParameter("uid");
		FensMiner miner = new FensMiner();
		miner.setFensUserId(Integer.valueOf(id));
		return fensMinerService.thawABMiner2(miner);
	}

	// 粉丝算力和（亲友团算力和）
	@RequestMapping(value = "/slh", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult qinyoutuanHe(HttpServletRequest request, HttpServletResponse response) {
		// 手机号
		String phone = request.getParameter("sh");
		if (StringUtils.isEmpty(phone)) {
			return JsonResult.build(500, "请重新登入");
		}
		return fensMinerService.qinyoutuanHe(phone);
	}

	// 粉丝算力列表（或者收益列表）
	@RequestMapping(value = "/sllb", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult suanLiList(HttpServletRequest request, HttpServletResponse response) {
		// 手机号
		String phone = request.getParameter("sh");
		if (StringUtils.isEmpty(phone)) {
			return JsonResult.build(500, "请重新登入");
		}
		return fensMinerService.suanLiList(phone);
	}

	// 粉丝算力列表（或者收益列表）
	@RequestMapping(value = "/sllb2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult suanLiList2(HttpServletRequest request, HttpServletResponse response) {
		// 手机号
		String phone = request.getParameter("sh");
		if (StringUtils.isEmpty(phone)) {
			return JsonResult.build(500, "请重新登入");
		}
		return fensMinerService.suanLiList2(phone);
	}

	// 亲友团收益（矿机价格的1%）
	@RequestMapping(value = "/syh", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult shouYiHe(HttpServletRequest request, HttpServletResponse response) {
		// 手机号
		String phone = request.getParameter("sh");
		if (StringUtils.isEmpty(phone)) {
			return JsonResult.build(500, "请重新登入");
		}
		return fensMinerService.shouYiHe(phone);
	}

	// 算力叠加到矿机
	@RequestMapping(value = "/kjdj", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult kuanJiSuanLiHe(HttpServletRequest request, HttpServletResponse response) {
		// 叠加算力
		String diejia = request.getParameter("djsl");
		// 粉丝id
		String fensUserId = request.getParameter("uid");
		// 矿机id
		String id = request.getParameter("id");
		// 矿机id
		String phone = request.getParameter("phone");

		if (StringUtils.isEmpty(diejia)) {

			return JsonResult.build(500, "请重新登入");
		}
		if (StringUtils.isEmpty(fensUserId)) {

			return JsonResult.build(500, "请重新登入");
		}
		if (StringUtils.isEmpty(id)) {

			return JsonResult.build(500, "请重新登入");
		}
		if (StringUtils.isEmpty(phone)) {

			return JsonResult.build(500, "请重新登入");
		}
		
		return fensMinerService.kuanJiSuanLiHe(Double.valueOf(diejia), Integer.valueOf(fensUserId),
				Integer.valueOf(id), phone);
	}

	// 收益提取接口
	@RequestMapping(value = "/sytq", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult syTiQu(HttpServletRequest request, HttpServletResponse response) {
		// 矿机id
		String id = request.getParameter("id");
		// 手机号码
		String phone = request.getParameter("sh");
		// 粉丝id
		String fensUserId = request.getParameter("uid");

		if (StringUtils.isEmpty(id)) {

			return JsonResult.build(500, "请重新登入");
		}
		if (StringUtils.isEmpty(phone)) {

			return JsonResult.build(500, "请重新登入");
		}
		if (StringUtils.isEmpty(fensUserId)) {

			return JsonResult.build(500, "请重新登入");
		}
		return fensMinerService.syTiQu(Integer.valueOf(id), phone, Integer.valueOf(fensUserId));
	}

	// 粉丝算力（个人）
	@RequestMapping(value = "/slgeren", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult geRen(HttpServletRequest request, HttpServletResponse response) {
		// 手机号
		String fensUserId = request.getParameter("uid");
		if (StringUtils.isEmpty(fensUserId)) {
			return JsonResult.build(500, "请重新登入");
		}
		return fensMinerService.geRen(Integer.valueOf(fensUserId));
	}

}
