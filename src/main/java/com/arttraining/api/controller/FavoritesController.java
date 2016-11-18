package com.arttraining.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.arttraining.api.bean.FavoriteCreateBean;
import com.arttraining.api.pojo.Favorites;
import com.arttraining.api.service.impl.FavoritesService;
import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.ErrorCodeConfigUtil;
import com.arttraining.commons.util.NumberUtil;
import com.arttraining.commons.util.TimeUtil;

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
			//先获取被收藏信息ID的拥有者ID和类型
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", type);
			map.put("favorite_id",i_favorite_id);
			
			FavoriteCreateBean user = this.favoritesService.getUserInfoByFavoriteCreate(map); 
			if(user!=null) {
				//新增收藏信息
				Favorites favorites = new Favorites();
				favorites.setCreateTime(TimeUtil.getTimeStamp());
				favorites.setForeignKey(i_favorite_id);
				favorites.setVisitor(i_uid);
				favorites.setVisitorType(utype);
				favorites.setHost(user.getUid());
				favorites.setHostType(user.getUtype());
				
				try {
					this.favoritesService.insertOneFavorite(favorites);
					errorCode = "0";
					errorMessage = "ok";
				} catch (Exception e) {
					errorCode = "20053";
					errorMessage = ErrorCodeConfigUtil.ERROR_MSG_ZH_20053;
				}
			}
			else {
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
		
		return jsonObject;

	}

	/**
	 * 根据用户ID获取收藏列表
	 * 传递的参数:access_token--验证
	 * uid--用户ID utype--用户类型
	 */
}
