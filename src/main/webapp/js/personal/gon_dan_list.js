(function(){
	$.ajax({
		 type: "post",
		 url: getAPIURL() + "/gd/list",
		 dataType: "json",
		 data:{
			 "pg":0,
			 "ts":100
		 },
		 success:function(data){
			 console.log("11111------"+data.list);
			 console.log("22222-----"+data.list);
			 var list = data.list;
				 var html = "";
				 for(var i = 0;i<list.length; i++){
					 
					 var leixin = list[i].type;
					 
					 if(leixin == 1){
						 leixin = "交易"
						 
					 }else if(leixin == 2){
						 leixin = "矿机"
						 
					 }else if(leixin == 3){
						 leixin = "其他"
						 
					 }
					 html += "<div class='title'>"+
						         "<span>交易类型</span>" +
						         "<span>："+leixin +"</span>" + "</div>" +
					             "<div class='desc'>" +
						         "<span>问题描述</span>" +
						         "<span>*</span>" +
					             "</div>" + 
					             "<div class='text'>"+
						         "<textarea readonly='readonly' placeholder='请具体且准确的描述您的问题，这有助于我们更高效的帮助您！'>"+list[i].problem+"</textarea>"+
					             "</div>"
				 }
				 $("#liebiao").html(html);
			 
		 }
	});
})();