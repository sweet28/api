(function(){
	var uid = localStorage.getItem("uid");
	$.ajax({
		type : "post",
		url : getAPIURL() + "quan/couponGiftList",
		dataType : "json",
		data : {
			"uid" : uid,
			"phone":localStorage.getItem("phone")
		},
		success:function(data){
			var html = "";
			console.log(data.data);
			var list = data.data;
			
			if(data.status == 200){
				for(var i = 0; i<list.length; i++){
					var type = list[i].tiquType;
					if(type == 1){
						type = "待提取匹配";
					}else if(type == 2){
						type = "待打款";
					}else if(type == 3){
						type = "待收款";
					}else if(type == 4){
						type = "提取完成";
					}
					
					html += "<li><a href='couponScoreDetail?"+list[i].id+"'>" +
									"<div class='couponBox'>" +
										"<div class='title'>" +
											"<div class='tit'>" +
												"<span>券积分提取订单"+list[i].id+"</span>" +
											"</div>" +
											"<div class='end'>" +
												"<span>"+type+"</span>" +
											"</div>" +
										"</div>" +
										"<div class='desc'>" +
											"<div class='cd cd1'>" +
												"<b>"+list[i].tiquName+"</b>" +
												"<p>券积分提取人</p>" +
											"</div>" +
											"<div class='cd cd2'>" +
												"<b>"+list[i].tixianJiner+"￥</b>" +
												"<p>提取金额</p>" +
											"</div>" +
										"</div>" +
									"</div>" +
								"</a>" +
							"</li>";
				}
				$("#lieb").html(html);
			}else{
				$("#lieb").html("<p class='nothing'>无更多记录</p>");
			}
		},
		error:function(data){
			alert("交易拥堵，请稍后重试");
		}
	});
})();

Date.prototype.format = function(fmt) { 
    var o = { 
       "M+" : this.getMonth()+1,                 //月份 
       "d+" : this.getDate(),                    //日 
       "h+" : this.getHours(),                   //小时 
       "m+" : this.getMinutes(),                 //分 
       "s+" : this.getSeconds(),                 //秒 
       "q+" : Math.floor((this.getMonth()+3)/3), //季度 
       "S"  : this.getMilliseconds()             //毫秒 
   }; 
   if(/(y+)/.test(fmt)) {
           fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
   }
    for(var k in o) {
       if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
   return fmt; 
}