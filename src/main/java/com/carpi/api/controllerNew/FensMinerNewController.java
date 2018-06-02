package com.carpi.api.controllerNew;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
		//id
		String id = request.getParameter("id");
		FensMiner miner = new FensMiner();
		miner.setId(Integer.valueOf(id));
		return fensMinerService.thawABMiner(miner);
	}
}
