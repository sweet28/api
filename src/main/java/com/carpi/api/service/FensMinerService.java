package com.carpi.api.service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.FensMiner;
import com.github.pagehelper.PageInfo;

public interface FensMinerService {

	//根据粉丝id查询矿机（列表）
	public PageInfo<FensMiner> selectMinner(Integer page,Integer row,Integer fensUserId);
	
	//新增粉丝矿机
	public JsonResult addMiner(FensMiner fensMiner);
	
	//修改粉丝矿机
	public JsonResult updateMiner(FensMiner fensMiner);

	PageInfo<FensMiner> selectAMinner(Integer page, Integer row, Integer fensUserId);

	PageInfo<FensMiner> selectBMinner(Integer page, Integer row, Integer fensUserId);

	JsonResult thawABMiner(FensMiner miner);

	PageInfo<FensMiner> selectAMinnerKC(Integer page, Integer row, Integer fensUserId);

	PageInfo<FensMiner> selectBMinnerKC(Integer page, Integer row, Integer fensUserId);
	
	//转入运行池
	public JsonResult zhuanyxc(Integer id,Integer fensUserId,String type);

	public PageInfo<FensMiner> selectABMinnerKC(Integer page, Integer row, Integer fensUserId);

	public JsonResult shuaxinyxc(Integer fensUserId);
	
	//粉丝算力和（亲友团算力和）
	public JsonResult qinyoutuanHe(String phone);
	
	//粉丝算力列表（或者收益列表）
	public JsonResult suanLiList(String phone);
	
	//亲友团收益（矿机价格的1%）
	public JsonResult shouYiHe(String phone);
	
	//算力叠加到矿机
	public JsonResult kuanJiSuanLiHe(Double diejia,Integer fensUserId,Integer id);
	
	//收益提取接口
	public JsonResult syTiQu(Integer id,String phone,Integer fensUserId);
	
	//粉丝算力（个人）
	public JsonResult geRen(Integer fensUserId);
	
	//粉丝算力列表（个人）
	public JsonResult geRenList(Integer fensUserId);
	
}
