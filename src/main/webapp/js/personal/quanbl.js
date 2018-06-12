(function(){
	var uid = localStorage.getItem("uid");
	$.ajax({
		type : "post",
		url : getAPIURL() + "quan/dd",
		dataType : "json",
		data : {
			"uid" : uid
		},
		success:function(data){
			var html = "";
			var list = data.data;
			if(data.status == 200){
				for(var i = 0; i<list.length; i++){
					var type = list[i].orderType;
					if(type == 1){
						type = "待匹配";
					}else if(type == 2){
						type = "购买待打款";
					}else if(type == 3){
						type = "收益进行中";
					}else if(type == 4){
						type = "周期结束";
					}else if(type == 5){
						type = "提取带匹配";
					}else if(type == 6){
						type = "提取匹配待打款";
					}else if(type == 7){
						type = "收益完成";
					}else if(type == 8){
						type = "待审核";
					}
					
					html += "<li><a href='quan_detail?"+list[i].id+"'><div class='couponBox'><div class='title'><div class='tit'><span>"+list[i].name+"</span></div>" +
							"<div class='end'><span>"+type+"</span></div></div><div class='desc'><div class='cd cd1'><b>"+list[i].earnProportion*100+"%</b>" +
							"<p>预计周期收益率</p></div><div class='cd cd2'><b>"+list[i].day+"天</b><p>周期</p></div></div></div></a></li>";
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