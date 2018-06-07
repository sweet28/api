(function(){
	$.ajax({
		 type: "post",
		 url: getAPIURL() + "/gd/list",
		 dataType: "json",
		 data:{
			 "pg":0,
			 "ts":100,
			 "uid":localStorage.getItem("uid")
		 },
		 success:function(data){
			 var list = data.list;
				 var html = "";
				 for(var i = 0;i<list.length; i++){
					 
					 var leixin = list[i].type;
					 var chuli = list[i].bak1;
					 
					 if(chuli == 1){
						 chuli = "处理中";
					 }
					 if(chuli == 2){
						 chuli = "已处理";
					 }
					 if(leixin == 1){
						 leixin = "交易"
						 
					 }else if(leixin == 2){
						 leixin = "矿机"
						 
					 }else if(leixin == 3){
						 leixin = "其他"
						 
					 }
					 html += "<div class='title'>"+
						         "<span>工单类型</span>" +
						         "<span>："+leixin +"</span>&nbsp;&nbsp;&nbsp;" +
						         "<span>工单号:"+list[i].id+"</span></div>" +
					             "<div class='desc'>" +
						         "<span>问题表述：</span><span>"+chuli+"</span>" +
					             "</div>" + 
					             "<div class='text'>"+
						         "<textarea readonly='readonly' placeholder='请具体且准确的描述您的问题，这有助于我们更高效的帮助您！'>"+list[i].problem+"</textarea>"+
					             "</div>"
				 }
				 $("#liebiao").html(html);
			 
		 }
	});
})();