package com.arttraining.api.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.arttraining.api.beanv3.EntranceAdmissionBean;
import com.arttraining.api.beanv3.EntranceCategoryBean;
import com.arttraining.api.beanv3.EntranceCollegeBean;
import com.arttraining.api.beanv3.EntranceLineBean;
import com.arttraining.api.beanv3.EntranceProvinceBean;
import com.arttraining.api.service.impl.EntranceService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.ListSortUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;

@Controller
@RequestMapping("/entrance")
public class EntranceController {
	@Resource
	private EntranceService entranceService;
	
	/**
	 * 输入统考术科成绩和文化分,推荐相应的院校列表接口
	 */
	@RequestMapping(value = "/college/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object collegeList(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String major_score=request.getParameter("major_score");
		String culture_score=request.getParameter("culture_score");
		String province=request.getParameter("province");
		String category=request.getParameter("category");
		String subject=request.getParameter("subject");
		
		ServerLog.getLogger().warn("major_score:"+major_score+"-culture_score:"+culture_score
				+"-province:"+province+"-category:"+category+"-subject:"+subject);
		
		List<EntranceCollegeBean> collegeList= new ArrayList<EntranceCollegeBean>();
		if(major_score==null || culture_score==null || 
				province==null || category==null) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(major_score.equals("") || culture_score.equals("")
				|| province.equals("") || category.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isDouble(major_score)
				|| !NumberUtil.isDouble(culture_score)) {
			errorCode="20033";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			//专业分和文化分
			Double d_major_score=Double.valueOf(major_score);
			Double d_culture_score=Double.valueOf(culture_score);
			//获取当前年份
			Date date = new Date();
			SimpleDateFormat format=new SimpleDateFormat("yyyy");
			String year=format.format(date);
			//1.首先判断是否达到省最低录取控制分数线--术科分/文化分
			//2.然后继续判断输入的分数与平均分数线的差距分/趋势
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("major_score", d_major_score);
			map.put("culture_score", d_culture_score);
			map.put("province", province);
			map.put("category", category);
			map.put("subject", subject);
			map.put("year", year);
			
			collegeList=this.entranceService.getCollegeList(map);
			if(collegeList.size()>0) {
				//定义一个正数list
				List<EntranceCollegeBean> riseList=new ArrayList<EntranceCollegeBean>();
				//定义一个负数list
				List<EntranceCollegeBean> declineList=new ArrayList<EntranceCollegeBean>();
				
				//循环判断分数趋势
				for (EntranceCollegeBean college : collegeList) {
					double score=college.getScore();
					if(score>0) {
						college.setTrend("rise");
						riseList.add(college);
					} else {
						college.setTrend("decline");
						declineList.add(college);
					}
				}
				
				collegeList.clear();
				ListSortUtil<EntranceCollegeBean> sortList = new ListSortUtil<EntranceCollegeBean>();  
				if(riseList.size()>0) {
					//正数排序
					sortList.sort(riseList, "score", "desc");  
					collegeList.addAll(riseList);
				} 
				if(declineList.size()>0) {
					//负数排序
			        sortList.sort(declineList, "score", "asc");  
			    	collegeList.addAll(declineList);
				}
		        errorCode="0";
				errorMessage="ok";
			} else {
				collegeList= new ArrayList<EntranceCollegeBean>();
				errorCode="20007";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("data", collegeList);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		
		return jsonObject;
	}
	
	/**
	 * 选择专业类别列表
	 */
	@RequestMapping(value = "/category/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object categoryList(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下不是必选参数
		String category=request.getParameter("category");
		
		ServerLog.getLogger().warn("category:"+category);
		
		//查询专业类别列表
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("category", category);
				
		List<EntranceCategoryBean> categoryList=this.entranceService.getCategoryList(map);
		if(categoryList.size()>0) {
			errorCode="0";
			errorMessage="ok";
		} else {
			categoryList=new ArrayList<EntranceCategoryBean>();
			errorCode="20007";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("data", categoryList);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		
		return jsonObject;
	}
	
	/**
	 * 选择报考生源地列表
	 * 
	 */
	@RequestMapping(value = "/province/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object provinceList(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下不是必选参数
		String province=request.getParameter("province");
		
		ServerLog.getLogger().warn("province:"+province);
		
		//查询生源地列表
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("province", province);
		
		List<EntranceProvinceBean> provinceList=this.entranceService.getProvinceList(map);
		if(provinceList.size()>0) {
			errorCode="0";
			errorMessage="ok";
		} else {
			provinceList=new ArrayList<EntranceProvinceBean>();
			errorCode="20007";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("data", provinceList);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		
		return jsonObject;
	}
	
	/**
	 * 查询艺术类术科统考本科资格线列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/qualify/line/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object lineList(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String province=request.getParameter("province");
		
		ServerLog.getLogger().warn("province:"+province);
		
		List<EntranceLineBean> lineList=new ArrayList<EntranceLineBean>();
		
		if(province==null || province.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else {
			lineList=this.entranceService.getLineList(province);
			if(lineList.size()>0) {
				errorCode="0";
				errorMessage="ok";
			} else {
				lineList=new ArrayList<EntranceLineBean>();
				errorCode="20007";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("data", lineList);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		
		return jsonObject;
	}
	/**
	 * 查看历年来各批次艺术类高校(专业)录取最低控制分数线列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/admission/score/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object scoreList(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String province=request.getParameter("province");
				
		ServerLog.getLogger().warn("province:"+province);
		
		List<EntranceAdmissionBean> batchList=new ArrayList<EntranceAdmissionBean>();
		
		if(province==null || province.equals("")) {
			errorCode="20032";
			errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else {
			batchList=this.entranceService.getBatchScoreList(province);
			if(batchList.size()>0) {
				errorCode="0";
				errorMessage="ok";
			} else {
				batchList=new ArrayList<EntranceAdmissionBean>();
				errorCode="20007";
				errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("data", batchList);
		
		ServerLog.getLogger().warn(jsonObject.toString());
		
		return jsonObject;
	}
}
