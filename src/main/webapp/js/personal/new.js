var id;
(function(){
	$.ajax({
		 type: "post",
		 url: getAPIURL() + "/news/list",
		 dataType: "json",
		 data:{
			 "pg":0,
			 "ts":100
		 },
		 success:function(data){
			 var list = data.list;
				 var html = "";
				 
				 for(var i = 0;i<list.length; i++){
					 var time1 = list[i].createDate;
						 
					 //time1 = time1.format("yyyy-MM-dd hh:mm:ss");
					 time1 = fmtDate(time1);
					 var title = "CPA公告";
					 if(list[i].bak1 != null){
						 title = list[i].bak1;
					 }
					 
					 html += "<div class='title'>"+
						         "<span>"+title+"</span>" +
						         "<span>：</span>&nbsp;&nbsp;&nbsp;" +
					             "<div class='desc'>" +
						         "<span>时间："+time1+"</span><a style='font-weight:bold;color: red;margin-left: 35%;' href='newsDetail?"+list[i].id+"'>详情查看</a>" +
					             "</div>" + 
					             "<div class='text'>"+
						         "<textarea readonly='readonly' placeholder='请具体且准确的描述您的问题，这有助于我们更高效的帮助您！'>"+list[i].conent+"</textarea>"+
					             "</div>"
				 }
				 $("#liebiao").html(html);
			 
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