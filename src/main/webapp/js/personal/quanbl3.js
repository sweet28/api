(function(){
	var uid = localStorage.getItem("uid");
	$.ajax({
		type : "post",
		url : getAPIURL() + "quan/dsk",
		dataType : "json",
		data : {
			"uid" : uid,
			"type":20
		},
		success:function(data){
			var html = "";
			var list = data.data;
			if(data.status == 200){
				for(var i = 0; i<list.length; i++){
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
					 
					 var recordType = "<b style='color:red;'>券保理订单</b>";
					 var content = "<a style='font-weight: bold; color: #fff;' href='couponOrderDetailInfo?" + list[i].id + "'>详情</a>";
					 if(list[i].type == 2){
						 recordType = "<b style='color:blue;'>券积分订单</b>";
						 content = "<a style='font-weight: bold; color: #fff;' href='quanOrderDetailInfo?" + list[i].id + "'>详情</a>";
					 }
					 
					 html += "<li style='margin-top: 18px;'>" +
			 					"<span>类型：" + recordType + "</span>" +
					 			"<p>状态：" + dkTypeStr + "</p>" +
					 			"<span>订单号：" + list[i].bak2 + "</span>" +
					 			/*"<p>打款人：" + list[i].daName +"</p>" +*/
					 			"<span style='float: right; background: #E91E63; display: inline-block; width: 20%; height: 30px; text-align: center; line-height: 30px;'>" +
					 				content +
				 				"</span>" +
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