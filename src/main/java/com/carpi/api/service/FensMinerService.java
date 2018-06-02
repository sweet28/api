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
}
