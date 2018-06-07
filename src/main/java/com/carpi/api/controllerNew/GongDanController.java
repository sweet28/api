package com.carpi.api.controllerNew;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.commons.util.JsonResult;
import com.arttraining.commons.util.TimeUtil;
import com.carpi.api.pojo.GongDan;
import com.carpi.api.service.GongDanService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/gd")
public class GongDanController {

	@Autowired
	private GongDanService gongDanService;

	// 提交工单
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult addGongdan(HttpServletRequest request, HttpServletResponse response) {
		// 粉丝Id
		String fensUserId = request.getParameter("uid");
		// 问题类型
		String type = request.getParameter("tp");
		// 问题表述
		String problem = request.getParameter("pb");
		// 问题截图地址
		String img = request.getParameter("img");

		GongDan gongDan = new GongDan();
		gongDan.setFensUserId(Integer.valueOf(fensUserId));
		gongDan.setType(Integer.valueOf(type));
		gongDan.setProblem(problem);
		gongDan.setImg(img);
		gongDan.setBak1("1");
		gongDan.setCreateDate(TimeUtil.getTimeStamp());
		return gongDanService.addGongdan(gongDan);
	}

	// 查询历史工单
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<GongDan> selectGondan(HttpServletRequest request, HttpServletResponse response) {
		// 当前页
		String pageNum = request.getParameter("pg");
		// 条数
		String pageSize = request.getParameter("ts");
		// 粉丝id
		String fensUserId = request.getParameter("uid");
		// // 问题类型
		// String type = request.getParameter("tp");

		return gongDanService.selectGondan(Integer.valueOf(pageNum), Integer.valueOf(pageSize),
				Integer.valueOf(fensUserId));
	}

}
