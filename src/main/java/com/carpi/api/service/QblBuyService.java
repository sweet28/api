package com.carpi.api.service;

import com.arttraining.commons.util.JsonResult;
import com.carpi.api.pojo.QuanBaoLiRecord;

public interface QblBuyService {

	// 购买券宝理商品券(一型矿机数量)
	public JsonResult buyqbl(QuanBaoLiRecord quanBaoLiRecord);

	// 购买券宝理商品券(二型矿机数量)
	public JsonResult buyqbl2(QuanBaoLiRecord quanBaoLiRecord);

	// 购买券宝理商品券(三型矿机数量)
	public JsonResult buyqbl3(QuanBaoLiRecord quanBaoLiRecord);

}
