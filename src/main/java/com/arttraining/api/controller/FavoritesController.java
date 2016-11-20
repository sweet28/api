package com.arttraining.api.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arttraining.api.bean.FavoritesListBean;
import com.arttraining.api.bean.FavoritesListReBean;
import com.arttraining.api.bean.HomeLikeOrCommentBean;
import com.arttraining.api.bean.HomePageAttBean;
import com.arttraining.api.bean.HomePageStatusesBean;
import com.arttraining.api.pojo.Favorites;
import com.arttraining.api.pojo.UserStu;
import com.arttraining.api.service.impl.FavoritesService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.ServerLog;
import com.arttraining.commons.util.TimeUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/favorites")
public class FavoritesController {
	@Resource
	private FavoritesService favoritesService;
	
	/**
	 * 添加收藏
	 * 传递的参数:access_token--验证
	 * uid--用户ID utype--用户类型 type--收藏类型
	 * favorite_id--被收藏信息ID
	 * 
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object create(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下5个参数是必选参数
		String access_token = request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		String type=request.getParameter("type");
		String favorite_id=request.getParameter("favorite_id");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-favorite_id:"+favorite_id+"-utype:"+utype+
				"-type:"+type);
		
		if(access_token==null || uid==null || utype==null || favorite_id==null || type==null){
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(access_token.equals("") || uid.equals("") || utype.equals("") 
				|| favorite_id.equals("") || type.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(!NumberUtil.isInteger(uid) || !NumberUtil.isInteger(favorite_id)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			//用户ID
			Integer i_uid=Integer.valueOf(uid);
			//被收藏ID
			Integer i_favorite_id=Integer.valueOf(favorite_id);
			
			Date date = new Date();
			String time = TimeUtil.getTimeByDate(date);
			//新增收藏信息
			Favorites favorites = new Favorites();
			favorites.setCreateTime(Timestamp.valueOf(time));
			favorites.setVisitor(i_uid);
			favorites.setVisitorType(utype);
			favorites.setHost(i_favorite_id);
			favorites.setHostType(type);
			favorites.setOrderCode(time);
			
			//更新用户收藏量
			UserStu user = new UserStu();
			user.setId(i_uid);
			user.setFavoriteNum(1);
				
			try {
				this.favoritesService.insertOneFavoriteAndUpdateNum(favorites, user);
				errorCode = "0";
				errorMessage = "ok";
			} catch (Exception e) {
				errorCode = "20053";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20053;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_CODE, errorCode);
		jsonObject.put(ConfigUtil.PARAMETER_ERROR_MSG, errorMessage);
		jsonObject.put("uid",0);
		jsonObject.put("user_code","");
		jsonObject.put("name","");
		ServerLog.getLogger().warn(jsonObject.toString());
		return jsonObject;

	}

	/**
	 * 根据用户ID获取收藏列表
	 * 传递的参数:access_token--验证
	 * uid--用户ID utype--用户类型
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Object list(HttpServletRequest request, HttpServletResponse response) {
		String errorCode = "";
		String errorMessage = "";
		
		//以下5个参数是必选参数
		String access_token = request.getParameter("access_token");
		String uid=request.getParameter("uid");
		String utype=request.getParameter("utype");
		
		ServerLog.getLogger().warn("access_token:"+access_token+"-uid:"+uid+"-utype:"+utype);
		//分页时所用
		String self = request.getParameter("self");
		Integer limit = ConfigUtil.PAGESIZE;
		Integer offset = -1;
		
		//返回的收藏结果
		FavoritesListReBean favoriteReBean = new FavoritesListReBean();
		
		if(access_token==null || uid==null || utype==null){
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		}
		else if(access_token.equals("") || uid.equals("") || utype.equals("")) {
			errorCode = "20032";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20032;
		} 
		else if(!NumberUtil.isInteger(uid)) {
			errorCode = "20033";
			errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
		}
		else {
			if(self==null || self.equals("")) {
				offset=-1;
			}
			else if(!NumberUtil.isInteger(self)) {
				offset=-10;
			}
			else
				offset=Integer.valueOf(self);
			
			if(offset==-10) {
				errorCode = "20033";
				errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20033;
			}
			else {
				//用户ID 
				Integer i_uid=Integer.valueOf(uid);
				//先是以用户ID和类型 去收藏表查询用户所收藏的列表信息
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("uid", i_uid);
				map.put("utype", utype);
				map.put("offset", offset);
				map.put("limit", limit);
				
				favoriteReBean = this.favoritesService.getFavoritesListByUid(map);
				if(favoriteReBean==null) {
					favoriteReBean=new FavoritesListReBean();
					errorCode = "20007";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
				}
				else {
					//获取收藏列表信息
					List<FavoritesListBean> favoriteList = favoriteReBean.getFavorites();
					if(favoriteList.size()>0) {
						//循环读取收藏列表信息
						for (FavoritesListBean favorite : favoriteList) {
							//被收藏信息ID和被收藏类型
							Integer b_fav_id=favorite.getB_fav_id();
							String fav_type=favorite.getFav_type();
							//传递的信息来获取相应的帖子信息
							Map<String, Object> infoMap = new HashMap<String, Object>();
							infoMap.put("fav_id", b_fav_id);
							infoMap.put("fav_type", fav_type);

							HomePageStatusesBean status = this.favoritesService.getOneStatusByFavorite(infoMap);
							if(status==null) {
								status= new HomePageStatusesBean();
							}
							else {
								//获取点赞或点评信息
								Integer s_id = status.getStus_id();
								infoMap.put("s_id", s_id);  
								infoMap.put("u_id", i_uid);
								infoMap.put("u_type", utype);
								
								HomeLikeOrCommentBean isExistLike = this.favoritesService.getIsLikeOrCommentOrAtt(infoMap);
								status.setIs_like((String)infoMap.get("is_like"));
								status.setIs_comment((String)infoMap.get("is_comment"));
								if(isExistLike!=null) {
									String att_type = isExistLike.getAtt_type();
									if(att_type!=null && !att_type.equals("")) {
										Integer att_id = isExistLike.getAtt_id();
										String duration= isExistLike.getDuration();
										String thumbnail=isExistLike.getThumbnail();
										String store_path=isExistLike.getStore_path();
										List<HomePageAttBean> attList = this.parseAttPath(att_id, att_type, duration, thumbnail, store_path);
										status.setAtt(attList);
									}
							   }
							}
							//设置收藏信息
							favorite.setStatuses(status);
						}
						errorCode="0";
						errorMessage="ok";
					}
					else {
						errorCode="20007";
						errorMessage=ErrorCodeConfigUtil.ERROR_MSG_ZH_20007;
					}
					favoriteReBean.setFavorites(favoriteList);
				}
			}
		}
		favoriteReBean.setError_code(errorCode);
		favoriteReBean.setError_msg(errorMessage);
		
		Gson gson = new Gson();
		ServerLog.getLogger().warn(gson.toJson(favoriteReBean));
		return gson.toJson(favoriteReBean);
	}
	//封装一个方法用于解析json数据 然后将其拆解
		public List<HomePageAttBean> parseAttPath(Integer att_id,String att_type,
				String duration,String thumbnail,String store_path) {
			List<HomePageAttBean> attList = new ArrayList<HomePageAttBean>();
			HomePageAttBean h = null;
			 
			String path="";
			//首先判断是否是Json
			JSONArray jsonArray = JSONArray.parseArray(store_path);
			for (Iterator iterator = jsonArray.iterator(); iterator.hasNext();) {
		          JSONObject jsonObject=(JSONObject)iterator.next();
		          h = new HomePageAttBean();
		          h.setAtt_id(att_id);
		          h.setAtt_type(att_type);
		          
		          h.setDuration(duration);
		          path = ConfigUtil.QINIU_BUCKET_COM_URL+"/"+jsonObject.getString("store_path");
		          h.setStore_path(path);
		          h.setThumbnail(thumbnail);
		          attList.add(h);
		    }
			
			return attList;
		}
}
