
(function(){
	var url = location.search; //获取url中"?"符后的字串 
	var theRequest = new Object(); 
	if (url.indexOf("?") != -1) { 
		var str = url.substr(1); 
	}
	$.ajax({
	     type: "post",
		 url: getAPIURL() + "quan/couponGiftListInfo",
		 dataType: "json",
		 data:{
			 "id":str
		 },
		 success:function(data){
			 console.log(data);
			 if(data.status == 200){
				 var quan = data.data.tiquInfo;
				 $("#sybil").html(quan.tiquName);
				 $("#day").html(quan.tixianJiner+"￥");
				 //计息时间
				 
				 var list = data.data.orderList;
				 if(list.length > 0){
					 var html = "";
					 for(var i = 0; i < list.length; i++){
						 
						 var dkType = list[i].dakuanType;
						 var dkTypeStr = "";
						 if(dkType == 1){
							 dkTypeStr = "待打款";
						 }
						 if(dkType == 2){
							 dkTypeStr = "待收款";
						 }
						 if(dkType == 3){
							 dkTypeStr = "已完成";
						 }
						 
						 html += "<li style='margin-top: 18px;'>" +
						 			"<p>状态：" + dkTypeStr + "</p>" +
						 			"<span>订单号：" + list[i].bak2 + "</span>" +
						 			"<p>打款人：" + list[i].daName +"</p>" +
						 			"<span style='float: right; background: #E91E63; display: inline-block; width: 20%; height: 30px; text-align: center; line-height: 30px;'>" +
						 				"<a style='font-weight: bold; color: #fff;' href='couponScoreDetailInfo?" + list[i].id + "'>详情</a>" +
					 				"</span>" +
				 				"</li>";
					 }
					 $("#a_miner").html(html);
				 }
			 }
		 }
	});
})();

