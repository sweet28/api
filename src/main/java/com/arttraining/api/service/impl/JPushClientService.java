package com.arttraining.api.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arttraining.api.dao.BBSMapper;
import com.arttraining.api.dao.MessagePushMapper;
import com.arttraining.api.dao.StatusesMapper;
import com.arttraining.api.dao.TokenMapper;
import com.arttraining.api.dao.UserOrgMapper;
import com.arttraining.api.dao.UserStuMapper;
import com.arttraining.api.dao.UserTechMapper;
import com.arttraining.api.dao.WorksMapper;
import com.arttraining.api.pojo.BBS;
import com.arttraining.api.pojo.MessagePush;
import com.arttraining.api.pojo.Statuses;
import com.arttraining.api.pojo.Token;
import com.arttraining.api.pojo.UserOrg;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.pojo.UserTech;
import com.arttraining.api.pojo.Works;
import com.arttraining.api.service.IJPushClientService;
import com.arttraining.commons.util.JPushClientUtilV2;
import com.arttraining.commons.util.TimeUtil;

@Service("jPushClientService")
public class JPushClientService implements IJPushClientService {
	@Resource
	private BBSMapper bbsDao;
	@Resource
	private StatusesMapper statusDao;
	@Resource
	private WorksMapper workDao;
	@Resource
	private MessagePushMapper msgDao;
	@Resource
	private TokenMapper tokenDao;
	@Resource
	private UserStuMapper userStuDao;
	@Resource
	private UserTechMapper userTecDao;
	@Resource
	private UserOrgMapper userOrgDao;
	
	@Override
	public String insertMsgPushByLike(String type, Map<String,Object> param) {
		// TODO Auto-generated method stub
		//获取点赞人ID和类型
		Integer uid=(Integer)param.get("uid");
		String utype=(String)param.get("utype");
		Integer status_id=(Integer)param.get("like_id");
		String status_type=(String)param.get("status_type");
		
		//1.先依据status_id/status_type查询,插入到消息推送表中
		//获取当前时间
		Date date = new Date();
		String time=TimeUtil.getTimeByDate(date);
		MessagePush msg = new MessagePush();
		msg.setCreateTime(date);
		msg.setOrderCode(time);
		msg.setVisitor(uid);
		msg.setVisitorType(utype);
		msg.setMsgType(type);
		msg.setStatusId(status_id);
		msg.setStatusType(status_type);
		Integer owner=0;
		String owner_type="";
		String attr="";
		if(status_type.equals("status")) {
			BBS bbs = this.bbsDao.selectByPrimaryKey(status_id);
			owner=bbs.getOwner();
			owner_type=bbs.getOwnerType();
			attr=bbs.getAttachment();
		} else if(status_type.equals("g_stus")) {
			Statuses status = this.statusDao.selectByPrimaryKey(status_id);
			owner=status.getOwner();
			owner_type=status.getOwnerType();
			attr=status.getAttachment();
		} else if(status_type.equals("work")) {
			Works work = this.workDao.selectByPrimaryKey(status_id);
			owner=work.getOwner();
			owner_type=work.getOwnerType();
			attr=work.getAttachment();
		}
		msg.setOwnerId(owner);
		msg.setOwnerType(owner_type);
		msg.setStatusPic(attr);
		this.msgDao.insertSelective(msg);
		//2.然后依据owner/owner_type查询未读的消息数
		Integer msg_num=this.msgDao.selectUnreadMsgByUid(owner, owner_type);
		//2.然后封装extra_value ---json数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg_num",msg_num);
		map.put("follow_num",0);
		map.put("fans_num",0);
		map.put("works_num",0);
		map.put("bbs_num",0);
		String value=JPushClientUtilV2.enclose_extra_value_jsonV2(map);
		String extra_value=JPushClientUtilV2.enclose_push_extra_json_dataV2(type, value);
		return extra_value;
	}
	@Override
	public String inserMsgPushByWorkOrder(String type,String user_type, Map<String, Object> param) {
		// TODO Auto-generated method stub
		Integer works_num=0;
		Integer uid=(Integer)param.get("stu_id");
		if(user_type.equals("stu")) {
			UserStu userStu = this.userStuDao.selectByPrimaryKey(uid);
			works_num=userStu.getWorkNum();
		}
		//2.然后封装extra_value ---json数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg_num",0);
		map.put("follow_num",0);
		map.put("fans_num",0);
		map.put("works_num",works_num);
		map.put("bbs_num",0);
		String value=JPushClientUtilV2.enclose_extra_value_jsonV2(map);
		String extra_value=JPushClientUtilV2.enclose_push_extra_json_dataV2(type, value);
		return extra_value;
	}
	
	@Override
	public String insertMsgPushByByCommentAndReply(String type,Map<String, Object> param) {
		// TODO Auto-generated method stub
		//动态所属人ID和类型
		Integer owner=(Integer)param.get("owner");
		String owner_type=(String)param.get("owner_type");
		//获取评论人ID和类型
		Integer comm_uid=(Integer)param.get("uid");
		String comm_utype=(String)param.get("utype");
		//评论内容
		String comm_content=(String)param.get("content");
		//动态ID和类型
		Integer comm_status_id=(Integer)param.get("status_id");
		String comm_status_type=(String)param.get("status_type");
		//获取当前时间
		Date date = new Date();
		String time=TimeUtil.getTimeByDate(date);
		//1.自定义一个消息对象
		MessagePush msg = new MessagePush();
		msg.setCreateTime(date);
		msg.setOrderCode(time);
		msg.setVisitor(comm_uid);
		msg.setVisitorType(comm_utype);
		msg.setMsgType(type);
		msg.setStatusId(comm_status_id);
		msg.setStatusType(comm_status_type);
		msg.setOwnerId(owner);
		msg.setOwnerType(owner_type);
		msg.setMsgContent(comm_content);
		
		String attr="";
		if(comm_status_type.equals("status")) {
			BBS bbs = this.bbsDao.selectByPrimaryKey(comm_status_id);
			attr=bbs.getAttachment();
		} else if(comm_status_type.equals("g_stus")) {
			Statuses status = this.statusDao.selectByPrimaryKey(comm_status_id);
			attr=status.getAttachment();
		} else if(comm_status_type.equals("work")) {
			Works work = this.workDao.selectByPrimaryKey(comm_status_id);
			attr=work.getAttachment();
		}
		msg.setStatusPic(attr);
		this.msgDao.insertSelective(msg);
		
		//2.然后依据owner/owner_type查询未读的消息数
		Integer msg_num=this.msgDao.selectUnreadMsgByUid(owner, owner_type);
		//3.最后封装extra_value ---json数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg_num",msg_num);
		map.put("follow_num",0);
		map.put("fans_num",0);
		map.put("works_num",0);
		map.put("bbs_num",0);
		String value=JPushClientUtilV2.enclose_extra_value_jsonV2(map);
		String extra_value=JPushClientUtilV2.enclose_push_extra_json_dataV2(type, value);
		return extra_value;
	}
	
	@Override
	public String insertMsgPushByFollow(String type, Map<String,Object> param) {
		// TODO Auto-generated method stub
		//获取关注人/被关注人ID和类型
		Integer uid=(Integer)param.get("uid");
		String utype=(String)param.get("utype");
		Integer fan_id=(Integer)param.get("fan_id");
		String fan_type=(String)param.get("fan_type");
		String follow_type=(String)param.get("follow_type");
		
		//1.先依据follow_type查询,如果是关注 则不需要插入到消息推送表中
		//如果是粉丝 则需要插入到消息推送表中
		Integer msg_num=0;
		Integer follow_num=0;
		Integer fans_num=0;
		if(follow_type.equals("follow")) {
			//关注量
			switch (utype) {
			case "stu":
				UserStu follow_userStu = this.userStuDao.selectByPrimaryKey(uid);
				follow_num=follow_userStu.getFollowNum();
				break;
			case "tec":
				UserTech follow_userTec =this.userTecDao.selectByPrimaryKey(uid);
				follow_num=follow_userTec.getFollowNum();
				break;
			case "org":
				UserOrg follow_userOrg=this.userOrgDao.selectByPrimaryKey(uid);
				follow_num=follow_userOrg.getFollowNum();
				break;
			default:
				break;
			}
			
		} else if(follow_type.equals("fans")) {
			//获取当前时间
			Date date = new Date();
			String time=TimeUtil.getTimeByDate(date);
			MessagePush msg = new MessagePush();
			msg.setCreateTime(date);
			msg.setOrderCode(time);
			msg.setVisitor(uid);
			msg.setVisitorType(utype);
			msg.setMsgType(type);
			msg.setStatusId(fan_id);
			msg.setStatusType(fan_type);
			msg.setOwnerId(fan_id);
			msg.setOwnerType(fan_type);
			
			this.msgDao.insertSelective(msg);
			//2.然后依据fan_id/fan_type查询未读的消息数
			msg_num=this.msgDao.selectUnreadMsgByUid(fan_id, fan_type);
			
			//粉丝量
			switch (fan_type) {
			case "stu":
				UserStu follow_userStu = this.userStuDao.selectByPrimaryKey(fan_id);
				fans_num=follow_userStu.getFansNum();
				break;
			case "tec":
				UserTech follow_userTec =this.userTecDao.selectByPrimaryKey(fan_id);
				fans_num=follow_userTec.getFansNum();
				break;
			case "org":
				UserOrg follow_userOrg=this.userOrgDao.selectByPrimaryKey(fan_id);
				fans_num=follow_userOrg.getFansNum();
				break;
			default:
				break;
			}
		}
		//2.然后封装extra_value ---json数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg_num",msg_num);
		map.put("follow_num",follow_num);
		map.put("fans_num",fans_num);
		map.put("works_num",0);
		map.put("bbs_num",0);
		String value=JPushClientUtilV2.enclose_extra_value_jsonV2(map);
		String extra_value=JPushClientUtilV2.enclose_push_extra_json_dataV2(type, value);
		return extra_value;
	}
	@Override
	public String insertMsgPushByTecCommentAndReply(String type,Integer work_id, Integer uid,
			String utype, Integer tec_id) {
		// TODO Auto-generated method stub
		//获取当前时间
		Date date = new Date();
		String time=TimeUtil.getTimeByDate(date);
		MessagePush msg = new MessagePush();
		msg.setCreateTime(date);
		msg.setOrderCode(time);
		msg.setVisitor(tec_id);
		msg.setVisitorType("tec");
		msg.setMsgType(type);
		msg.setStatusId(work_id);
		msg.setStatusType("work");
		msg.setOwnerId(uid);
		msg.setOwnerType(utype);
		//依据work_id查询封面
		Works work = this.workDao.selectByPrimaryKey(work_id);
		String attr=work.getAttachment();
		msg.setStatusPic(attr);
		
		this.msgDao.insertSelective(msg);
		//2.然后依据uid/utype查询未读的消息数
		Integer msg_num=this.msgDao.selectUnreadMsgByUid(uid, utype);
		
		//2.然后封装extra_value ---json数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg_num",msg_num);
		map.put("follow_num",0);
		map.put("fans_num",0);
		map.put("works_num",0);
		map.put("bbs_num",0);
		String value=JPushClientUtilV2.enclose_extra_value_jsonV2(map);
		String extra_value=JPushClientUtilV2.enclose_push_extra_json_dataV2(type, value);
		return extra_value;
	}
	@Override
	public String insertMsgPushByPublishBBS(String type, Integer uid,
			String utype) {
		// TODO Auto-generated method stub
		Integer bbs_num=0;
		//依据uid/utype查询帖子数
		UserStu userStu = this.userStuDao.selectByPrimaryKey(uid);
		bbs_num=userStu.getBbsNum();
		//封装json数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg_num",0);
		map.put("follow_num",0);
		map.put("fans_num",0);
		map.put("works_num",0);
		map.put("bbs_num",bbs_num);
		String value=JPushClientUtilV2.enclose_extra_value_jsonV2(map);
		String extra_value=JPushClientUtilV2.enclose_push_extra_json_dataV2(type, value);
		return extra_value;
	}
	
	@Override
	public void encloseMsgPush(String user_type, Integer user_id, String type,Map<String,Object> param) {
		// TODO Auto-generated method stub
		//首先获取发布人的人最新token
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("user_type", user_type);
		Token t = this.tokenDao.selectOneTokenInfo(map);
		//别名
		String alias = "";
		//推送类型
		String push_type = "";
		//alert消息提示内容
		String alert = "";
		//alert额外封装的数据
		String alert_extra="";
		//推送的消息内容
		String push_content = "";
		//内容类型
		String content_type = "";
		//内容额外封装的数据
		String extra_value="";
		
		if(t!=null) {
			alias=t.getToken();
			//判断推送类型
			switch (type) {
			case "like_bbs":
				push_type="msg";
				param.put("status_type", "status");
				extra_value=this.insertMsgPushByLike(type,param);
				break;
			case "like_work":
				push_type="msg";
				param.put("status_type", "work");
				extra_value=this.insertMsgPushByLike(type,param);
				break;
			case "like_gstus":
				push_type="msg";
				param.put("status_type", "g_stus");
				extra_value=this.insertMsgPushByLike(type,param);
				break;
			case "follow":
				push_type="msg";
				extra_value=this.insertMsgPushByFollow(type, param);
				break;
			case "publish_bbs":
				push_type="msg";
				extra_value=this.insertMsgPushByPublishBBS(type, user_id, user_type);
				break;
			case "tec_reply":
			case "tec_comment":
				push_type="alert_msg";
				//获取点评老师的ID
				Integer com_tec_id=(Integer)param.get("tec_id");
				//获取作品ID
				Integer com_work_id=(Integer)param.get("work_id");
				//获取返回的内容JSon数据
				extra_value=this.insertMsgPushByTecCommentAndReply(type, com_work_id, user_id, user_type, com_tec_id);
				//自定义alert消息内容
				UserTech tec_comm = this.userTecDao.selectByPrimaryKey(com_tec_id);
				alert="亲,"+tec_comm.getName()+"老师点评了你的作品哟";
				alert_extra=JPushClientUtilV2.enclose_push_extra_json_dataV2(type, ""+com_work_id);
				break;
			case "comment_work":
				push_type="alert_msg";
				//获取评论人ID和类型
				Integer comm_w_uid=(Integer)param.get("uid");
				Integer comm_w_id=(Integer)param.get("status_id");
				//自定义alert消息内容
				UserStu stu_comm_w = this.userStuDao.selectByPrimaryKey(comm_w_uid);
				alert = "亲," + stu_comm_w.getName()+ "评论了你的作品哟";
				alert_extra=JPushClientUtilV2.enclose_push_extra_json_dataV2(type, ""+comm_w_id);
				//自定义msg消息内容
				extra_value=this.insertMsgPushByByCommentAndReply(type, param);
				break;
			case "comment_gstus":
				push_type="alert_msg";
				//获取评论人ID和类型
				Integer comm_g_uid=(Integer)param.get("uid");
				Integer comm_g_id=(Integer)param.get("status_id");
				UserStu stu_comm_g = this.userStuDao.selectByPrimaryKey(comm_g_uid);
				//自定义alert消息内容
				alert = "亲," + stu_comm_g.getName()+ "评论了你的动态哟";
				alert_extra=JPushClientUtilV2.enclose_push_extra_json_dataV2(type, ""+comm_g_id);
				//自定义msg消息内容
				extra_value=this.insertMsgPushByByCommentAndReply(type, param);
				break;
			case "comment_bbs":
				push_type="alert_msg";
				//获取评论人ID和类型
				Integer comm_b_uid=(Integer)param.get("uid");
				Integer comm_b_id=(Integer)param.get("status_id");
				//自定义通知栏消息内容
				UserStu stu_comm_b = this.userStuDao.selectByPrimaryKey(comm_b_uid);
				alert = "亲," + stu_comm_b.getName()+ "评论了你的帖子哟";
				alert_extra=JPushClientUtilV2.enclose_push_extra_json_dataV2(type, ""+comm_b_id);
				//自定义msg消息内容
				extra_value=this.insertMsgPushByByCommentAndReply(type, param);
				break;
			case "reply_bbs":
				push_type="alert_msg";
				//获取评论人ID和类型
				Integer reply_b_uid=(Integer)param.get("uid");
				Integer reply_b_id=(Integer)param.get("status_id");
				//自定义通知栏消息内容
				UserStu stu_reply_b = this.userStuDao.selectByPrimaryKey(reply_b_uid);
				alert = "亲," + stu_reply_b.getName()+ "回复了你的帖子哟";
				alert_extra=JPushClientUtilV2.enclose_push_extra_json_dataV2(type, ""+reply_b_id);
				//自定义msg消息内容
				extra_value=this.insertMsgPushByByCommentAndReply(type, param);
				break;
			case "reply_gstus":
				push_type="alert_msg";
				//获取评论人ID和类型
				Integer reply_g_uid=(Integer)param.get("uid");
				Integer reply_g_id=(Integer)param.get("status_id");
				//自定义通知栏消息内容
				UserStu stu_reply_g = this.userStuDao.selectByPrimaryKey(reply_g_uid);
				alert = "亲," + stu_reply_g.getName()+ "回复了你的动态哟";
				alert_extra=JPushClientUtilV2.enclose_push_extra_json_dataV2(type, ""+reply_g_id);
				//自定义msg消息内容
				extra_value=this.insertMsgPushByByCommentAndReply(type, param);
				break;
			case "reply_work":
				push_type="alert_msg";
				//获取评论人ID和类型
				Integer reply_w_uid=(Integer)param.get("uid");
				Integer reply_w_id=(Integer)param.get("status_id");
				//自定义通知栏消息内容
				UserStu stu_reply_w = this.userStuDao.selectByPrimaryKey(reply_w_uid);
				alert = "亲," + stu_reply_w.getName()+ "回复了你的作品哟";
				alert_extra=JPushClientUtilV2.enclose_push_extra_json_dataV2(type, ""+reply_w_id);
				//自定义msg消息内容
				extra_value=this.insertMsgPushByByCommentAndReply(type, param);
				break;
			case "stu_ass":
				if(user_type.equals("stu")) {
					push_type="msg";
					//自定义msg消息内容
					extra_value=this.inserMsgPushByWorkOrder(type, user_type, param);
				} else {
					push_type="alert";
					String stu_name=(String)param.get("stu_name");
					String tec_name=(String)param.get("tec_name");
					Integer ass_work_id=(Integer)param.get("work_id");
					//自定义alert消息内容
					alert=tec_name+"老师,您好 "+stu_name+"同学请您帮忙点评他的作品哟";
					alert_extra=JPushClientUtilV2.enclose_push_extra_json_dataV2(type, ""+ass_work_id);
				}
				break;
			default:
				break;
			}
			System.out.println("extra_value:"+extra_value);
			//end
			JPushClientUtilV2.enclose_push_data_aliasV2(user_type, push_type, alias,
					alert, push_content, content_type, 
					extra_value, alert_extra);
		}
	}
}
