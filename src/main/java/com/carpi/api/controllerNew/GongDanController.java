package com.carpi.api.controllerNew;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.descriptor.web.FragmentJarScannerCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arttraining.commons.util.JsonResult;
import com.arttraining.commons.util.TimeUtil;
import com.carpi.api.pojo.FangKui;
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
	
	//工单详情
	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult selectAll(@PathVariable("id") Integer id) {
		if (id == null) {
			return JsonResult.build(500, "无工单详情");
		}
		return gongDanService.selectOne(id);
	}

	// 反馈添加
	@RequestMapping(value = "/fan_kui", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult fankui(HttpServletRequest request, HttpServletResponse response) {
		// 工单id
		String gongdanId = request.getParameter("gd_id");
		if (StringUtils.isEmpty(gongdanId)) {
			return JsonResult.build(500, "请选择工单");
		}
		// 反馈详情
		String fankuiConent = request.getParameter("conent");
		if (StringUtils.isEmpty(fankuiConent)) {
			return JsonResult.build(500, "请输入反馈详情");
		}
		// 处理中
		String fankuiType = request.getParameter("fk_tp");
		if (StringUtils.isEmpty(fankuiType)) {
			return JsonResult.build(500, "请选择工单");
		}else if (!"1".equals(fankuiType)) {
			return JsonResult.build(500, "该工单已处理结束，如还需反馈，请重新填写工单提交");
		}
		// 粉丝Id
		String fensUserId = request.getParameter("uid");
		if (StringUtils.isEmpty(fensUserId)) {
			return JsonResult.build(500, "请重新登入系统");
		}
		// 粉丝姓名
		String fensName = request.getParameter("fs_name");
		if (StringUtils.isEmpty(fensName)) {
			return JsonResult.build(500, "请重新登入");
		}

		FangKui fangKui = new FangKui();

		fangKui.setGongdanId(Integer.valueOf(gongdanId));
		fangKui.setFankuiConent(fankuiConent);
		fangKui.setFankuiType(Integer.valueOf(fankuiType));
		//发送
		fangKui.setType(1);
		fangKui.setFensUserId(Integer.valueOf(fensUserId));
		fangKui.setFensName(fensName);
		fangKui.setCreateDate(TimeUtil.getTimeStamp());

		return gongDanService.insert(fangKui);
	}
	
	//反馈详情(根据工单id)(列表)
	@RequestMapping(value = "/fk_list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult select(@RequestParam("gd_id") Integer gongdan_id) {
		return gongDanService.select(gongdan_id);
	}

}
