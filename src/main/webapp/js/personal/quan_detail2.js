var uid = localStorage.getItem("uid");
var str;
(function(){
	var url = location.search; //获取url中"?"符后的字串 
	var theRequest = new Object(); 
	if (url.indexOf("?") != -1) { 
		str = url.substr(1); 
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
	
	$.ajax({
		 type: "post",
		 url: getAPIURL() + "quan/chuchang",
		 dataType: "json",
		 data:{
			 "id":str,
			 "type":2,
			 "dakuantype":1
		 },
		 success:function(data){
			 console.log(data);
			 var list = data.data
			 if(data.status == 200){
				 var html="";
				 for(var i=0;i<list.length;i++){
					 var shouyi = list[i].quan.earnProportion;
					 var orderType = list[i].pip.dakuanType
					 if(orderType == 1){
						 order = "待付款";
					 }else if(orderType == 2){
						 order = "已付款待确认";
					 }else if(orderType == 3){
						 order = "已确认收款";
					 }
					 html += "<li style='margin-top: 18px;'><p>状态："+ order+"</p> <span>订单号："+list[i].quan.orderNumber+"</span><p>收益比例："
					 + shouyi*100+"%</p><span>出局额度：￥："+list[i].quan.outPrice+"</span><span style='float: right; background: #E91E63; display: inline-block; width: 20%; height: 30px; text-align: center; line-height: 30px;'><a style='font-weight: bold; color: #fff;' href='quan_FKXQ?"+list[i].id+"'>详情</a>";
				 }
				 $("#a_miner").html(html);
			 }
			 
		 }
	});
})();
