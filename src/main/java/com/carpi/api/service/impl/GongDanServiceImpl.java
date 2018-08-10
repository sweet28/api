package com.carpi.api.service.impl;

import java.util.List;

import org.aspectj.weaver.ast.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.arttraining.commons.util.JsonResult;
import com.arttraining.commons.util.TimeUtil;
import com.carpi.api.dao.FangKuiMapper;
import com.carpi.api.dao.FensUserMapper;
import com.carpi.api.dao.GongDanMapper;
import com.carpi.api.pojo.FangKui;
import com.carpi.api.pojo.FensUser;
import com.carpi.api.pojo.GongDan;
import com.carpi.api.service.GongDanService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class GongDanServiceImpl implements GongDanService {

	@Autowired
	private GongDanMapper gongDanMapper;

	@Autowired
	private FangKuiMapper fangKuiMapper;
	
	@Autowired
	private FensUserMapper fensUserMapper;

	// 提交工单
	@Override
	public JsonResult addGongdan(GongDan gongDan) {
		if (gongDan.getFensUserId() == null) {
			return JsonResult.build(500, "系统错误");
		}
		if (gongDan.getType() == null) {
			return JsonResult.build(500, "系统错误1");
		}
		if (gongDan.getProblem() == null || "".equals(gongDan.getProblem().trim())) {
			return JsonResult.build(500, "请输入问题表述");
		}

		//判断是否存在该会员
		FensUser fensUser = fensUserMapper.selectByPrimaryKey(gongDan.getFensUserId());
		if (StringUtils.isEmpty(fensUser)) {
			return JsonResult.build(500, "不存在该会员");
		}
		
		int result = gongDanMapper.insertSelective(gongDan);
		if (result != 1) {
			return JsonResult.build(500, "提交失败，请联系客服");
		}
		return JsonResult.ok();
	}

	// 查询历史工单
	@Override
	public PageInfo<GongDan> selectGondan(Integer pageNum, Integer pageSize, Integer fensUserId) {
		PageHelper.startPage(pageNum, pageSize);
		List<GongDan> list = gongDanMapper.selectList(fensUserId);
		PageInfo<GongDan> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	// 提交反馈（填写）
	@Override
	public JsonResult insert(FangKui fangKui) {
		int result = fangKuiMapper.insertSelective(fangKui);
		if (result != 1) {
			return JsonResult.build(500, "填写失败");
		}
		return JsonResult.ok();
	}

	// 反馈详情(列表)
	@Override
	public JsonResult select(Integer gongdan_id) {
		List<FangKui> list = fangKuiMapper.selectList(gongdan_id);
		if (list.isEmpty()) {
			return JsonResult.build(500, "无工单数据");
		}
		return JsonResult.ok(list);
	}

	//工单详情
	@Override
	public JsonResult selectOne(Integer id) {
		GongDan gongDan = gongDanMapper.selectByPrimaryKey(id);
		if (StringUtils.isEmpty(gongDan)) {
			return JsonResult.build(500, "详情出错");
		}
		return JsonResult.ok(gongDan);
	}

}
