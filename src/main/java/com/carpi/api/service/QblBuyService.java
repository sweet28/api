package com.carpi.api.service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.QuanBaoLiRecord;

public interface QblBuyService {

	// 券保理1星券(7天)
	public JsonResult buyqbl(QuanBaoLiRecord quanBaoLiRecord);

	// 券保理1星券(21天)
	public JsonResult buyqbl2(QuanBaoLiRecord quanBaoLiRecord);

	// 券保理2星券(15天)
	public JsonResult buyqbl3(QuanBaoLiRecord quanBaoLiRecord);
	
	// 券保理3星券(10天)
	public JsonResult buyqbl4(QuanBaoLiRecord quanBaoLiRecord);
	
	//券的总数量
	public JsonResult count(Integer id);

}
