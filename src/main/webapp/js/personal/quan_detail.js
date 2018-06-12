
(function(){
	var url = location.search; //获取url中"?"符后的字串 
	var theRequest = new Object(); 
	if (url.indexOf("?") != -1) { 
		var str = url.substr(1); 
	}
	$.ajax({
	     type: "post",
		 url: getAPIURL() + "quan/"+str,
		 dataType: "json",
		 data:{
			 "id":str
		 },
		 success:function(data){
			 console.log(data);
			 if(data.status == 200){
				 var quan = data.data.ddxx;
				 console.log(quan);
				 console.log(quan.earnProportion*100+"%");
				 $("#name").html(quan.name);
				 $("#sybil").html(quan.earnProportion*100+"%");
				 $("#day").html(quan.day+"天");
				 $("#erdu").html("￥："+quan.position);
				 $("#chujued").html("￥："+quan.outPrice);
				 $("#shouyi").html("￥："+(quan.outPrice-quan.position));
				 //截止日期
				 //购买日期
				 $("#jzdate").html(fmtDate(quan.createDate));
				 //计息时间
				 if(quan.interestDate == null || quan.interestDate==""){
					 $("#jxdate").html("尚未开始");
				 }else{
					 $("#jxdate").html(fmtDate(quan.interestDate));
				 }
				 //到期时间
				 if(quan.expiryTime == null || quan.expiryTime==""){
					 $("#daoqi").html("尚未开始");
				 }else{
					 $("#daoqi").html(fmtDate(quan.expiryTime));
				 }
			 }
		 }
	});
})();
