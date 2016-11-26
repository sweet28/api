package com.arttraining.api.controller;

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

import com.arttraining.api.bean.HomePageAttBean;
import com.arttraining.api.bean.MasterAssessmentBean;
import com.arttraining.api.bean.MasterAssessmentReBean;
import com.arttraining.api.bean.MasterCommentListBean;
import com.arttraining.api.bean.MasterCommentReBean;
import com.arttraining.api.bean.MasterCommentUserBean;
import com.arttraining.api.bean.OrderWorkBean;
import com.arttraining.api.pojo.WorksAttchment;
import com.arttraining.api.service.impl.AssessmentService;
import com.arttraining.api.service.impl.OrdersService;
import com.arttraining.api.service.impl.WorksAttchmentService;
import com.arttraining.api.service.impl.WorksTecCommentService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.ImageUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.google.gson.Gson;

@Controller
@RequestMapping("/assessments")
public class MasterAssessmentsController {
	@Resource
	private AssessmentService assessmentService;
	@Resource
	private OrdersService ordersService;
	@Resource
	private WorksAttchmentService worksAttchmentService;
	@Resource
	private WorksTecCommentService worksTecCommentService;
	/***
	 * 根据用户ID获取名师待测评列表
	 * 传递的参数:access_token--验证
	 * uid--用户ID self--分页时传递的位置ID
	 */
	@RequestMapping(value = "/list/no", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object listNo(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下两个是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		//以下参数不是必选参数
		String self=request.getParameter("self");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-self:"+self);
		//以下用于分页
		Integer limit = ConfigUtil.PAGESIZE;
		Integer offset=-1;
		//返回对象
		MasterAssessmentReBean assessmentReBean = new MasterAssessmentReBean();
		if(access_token==null || uid==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			if(self==null || self.equals("")) {
				 offset=-1;
			} else if(!NumberUtil.isInteger(self)) {
				 offset=-10;
			} else
				offset=Integer.valueOf(self);
			
			if(offset==-10) {
				errorCode = "20033";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
			} else {
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//根据名师ID获取名师待测评列表--状态为0
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("limit", limit);
				map.put("offset", offset);
				map.put("uid", i_uid);
				map.put("status", ConfigUtil.STATUS_4);
				System.out.println("==111");
				List<MasterAssessmentBean> assessmentList = this.assessmentService.getAssessmentNoListByMaster(map);
				System.out.println("==2222");
				if(assessmentList.size()>0) {
					//循环读取测评信息
					for (MasterAssessmentBean assessment : assessmentList) {
						Integer type = 0;
						Integer order_id = assessment.getOrder_id();
						String order_number=assessment.getOrder_number();
						map.put("type", type);
						map.put("order_id", order_id);
						map.put("order_number", order_number);
						//获取作品相关的信息
						OrderWorkBean work = this.ordersService.getWorkInfoByListMy(map);
						if(work!=null) {
							assessment.setWork_id(work.getWork_id());
							assessment.setWork_title(work.getWork_title());
							assessment.setWork_pic(work.getWork_pic());
						}
					}
					assessmentReBean.setAssessments(assessmentList);
					errorCode = "0";
					errorMessage = "ok";
				} else {
					errorCode = "20007";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
				}
			}
		}
		assessmentReBean.setError_code(errorCode);
		assessmentReBean.setError_msg(errorMessage);
		
		Gson gson=new Gson();
		ServerLog.getLogger().warn(gson.toJson(assessmentReBean));
		return gson.toJson(assessmentReBean);
	}
	/***
	 * 根据用户ID获取已测评明细
	 * 传递的参数:access_token--验证
	 * uid--用户ID self--分页时传递的位置ID
	 */
	@RequestMapping(value = "/list/yes", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object listYes(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下两个是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		//以下参数不是必选参数
		String self=request.getParameter("self");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-self:"+self);
		//以下用于分页
		Integer limit = ConfigUtil.PAGESIZE;
		Integer offset=-1;
		//返回对象
		MasterAssessmentReBean assessmentReBean = new MasterAssessmentReBean();
		
		if(access_token==null || uid==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			if(self==null || self.equals("")) {
				 offset=-1;
			} else if(!NumberUtil.isInteger(self)) {
				 offset=-10;
			} else
				offset=Integer.valueOf(self);
			
			if(offset==-10) {
				errorCode = "20033";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
			} else {
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//根据名师ID获取名师待测评列表--状态为1
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("limit", limit);
				map.put("offset", offset);
				map.put("uid", i_uid);
				map.put("status", ConfigUtil.STATUS_5);
				List<MasterAssessmentBean> assessmentList = this.assessmentService.getAssessmentYesListByMaster(map);
				if(assessmentList.size()>0) {
					//循环读取测评信息
					for (MasterAssessmentBean assessment : assessmentList) {
						Integer type = 0;
						Integer order_id = assessment.getOrder_id();
						String order_number=assessment.getOrder_number();
						map.put("type", type);
						map.put("order_id", order_id);
						map.put("order_number", order_number);
						//获取作品相关的信息
						OrderWorkBean work = this.ordersService.getWorkInfoByListMy(map);
						if(work!=null) {
							assessment.setWork_id(work.getWork_id());
							assessment.setWork_title(work.getWork_title());
							assessment.setWork_pic(work.getWork_pic());
						}
					}
					assessmentReBean.setAssessments(assessmentList);
					errorCode = "0";
					errorMessage = "ok";
				} else {
					errorCode = "20007";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
				}
			}
		}
		assessmentReBean.setError_code(errorCode);
		assessmentReBean.setError_msg(errorMessage);
		
		Gson gson=new Gson();
		ServerLog.getLogger().warn(gson.toJson(assessmentReBean));
		return gson.toJson(assessmentReBean);
	}
	/***
	 * 根据用户ID获取名师测评详情
	 * 传递的参数: access_token--验证
	 * uid--用户ID work_id--作品ID
	 */
	@RequestMapping(value = "/master/show", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object masterShow(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下是必选参数
		String access_token=request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String work_id=request.getParameter("work_id");
		//以下不是必选参数
		String self=request.getParameter("self");
		Integer offset=-1;
		Integer limit=ConfigUtil.PAGESIZE;
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-self:"+self+
				"-work_id:"+work_id);
		//返回对象
		MasterCommentReBean commentReBean = new MasterCommentReBean();
		
		if(access_token==null || uid==null || work_id==null) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(access_token.equals("") || uid.equals("") || work_id.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} else if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(work_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		} else {
			if(self==null || self.equals("")) {
				offset=-1;
			} else if(!NumberUtil.isInteger(self)) {
				offset=-10;
			} else
				offset=Integer.valueOf(self);
			
			if(offset==-10) {
				errorCode = "20033";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
			} else {
				//作品ID
				Integer i_work_id=Integer.valueOf(work_id);
				//用户ID
				Integer i_uid=Integer.valueOf(uid);
				//1.先依据作品ID来获取作品详情
				commentReBean = this.worksTecCommentService.getOneWorkByMasterShow(i_work_id);
				if(commentReBean==null) {
					commentReBean = new MasterCommentReBean();
					errorCode = "20007";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
				} else {
					//2.依据作品ID去查询附件
					WorksAttchment work_att=this.worksAttchmentService.getOneAttByWorkId(i_work_id);
					if(work_att!=null) {
						String att_type = work_att.getType();
						if(att_type!=null && !att_type.equals("")) {
							Integer att_id = work_att.getId();
							String duration= work_att.getDuration();
							String thumbnail=work_att.getThumbnail();
							String store_path=work_att.getStorePath();
							List<HomePageAttBean> attList = ImageUtil.parseAttPathByType(att_id, att_type, duration, thumbnail, store_path, 6);
							commentReBean.setAtt(attList);
						}
					}
					//3.依据名师ID来获取点评列表信息
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("limit", limit);
					map.put("offset",offset);
					map.put("uid",i_uid);
					
					List<MasterCommentListBean> commentList=this.worksTecCommentService.getCommentListByMasterShow(map);
					if(commentList.size()>0) {
					//循环读取点评列表信息 用于获取名师或者爱好者用户的头像和名称
						for (MasterCommentListBean comment : commentList) {
							//用户ID和类型
							Integer user_id=comment.getVisitor_id();
							String user_type=comment.getType();
							map.put("user_id",user_id);
							map.put("user_type",user_type);
							MasterCommentUserBean user = this.worksTecCommentService.getCommentUserByMasterShow(map);
							if(user!=null) {
								comment.setName(user.getName());
								comment.setPic(user.getPic());
							}
						}
						commentReBean.setTec_comments_list(commentList);
						errorCode = "0";
						errorMessage = "ok";
					 }
				}
		   }
		}
		commentReBean.setError_code(errorCode);
		commentReBean.setError_msg(errorMessage);
		
		Gson gson =new Gson();
		ServerLog.getLogger().warn(gson.toJson(commentReBean));
		return gson.toJson(commentReBean);
	}
}
