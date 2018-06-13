var str;
(function(){
	var url = location.search; //获取url中"?"符后的字串 
	var theRequest = new Object(); 
	if (url.indexOf("?") != -1) { 
	    str = url.substr(1); 
	}
	$.ajax({
		 type: "post",
		 url: getAPIURL() + "quan/pi/"+str,
		 dataType: "json",
		 data:{
			 "id":str
		 },
		 success:function(data){
			 console.log(data);
			 if(data.status == 200){
				 var quan = data.data
				 $.ajax({
	    			    type: "post",
	    			    url: getAPIURL() + "fenuser/info",
	    			    dataType: "json",
	    			    data: {
	    			    	"id":quan.dakuangId
	    			    },
	    			    success: function (data) {
	    			    	mjsj = data.phone;
	    			    	console.log(mjsj);
	    			    	$("#mrsj2").html(mjsj);
	    			    },
	    			    error: function (XMLHttpRequest, textStatus, errorThrown) {
	    			    }
	    			  });
				$("#ddnum").html("订单号："+quan.bak2);
				$("#createTime").html("订单生成时间："+new Date(quan.createDate).format("yyyy-MM-dd hh:mm:ss"));
				var order = quan.dakuanType;
				if(order == 1){
				   order = "买家待打款";
				}else if(order == 2){
					order = "卖家待收款";
				}else if(order == 3){
					order = "已确认收款";
				}
				$("#daitype").html("订单状态："+order);
				$("#mon").html("￥："+quan.bak1);
				$("#mrxm2").html("姓名："+quan.daName);
				$("#").html();
				$("#").html();
				$("#").html();
				$("#").html();
				$("#").html();
				
			 }
			 
		 }
		 
	});
})();

function sk(){
//	var flag = checkLogin();
	var tmp = getTimestamp();
	var rad = getRandom();
	var ton = getTom();
	var stri = "pipeiId="+str+"tmp="+tmp+"rad="+rad+"tom="+ton; 
	$.ajax({
	    type: "post",
	    url: getAPIURL() + "quan/shouk",
	    dataType: "json",
	    data:{
	    	"pipeiId":str,
	    	"tmp":tmp,
	        "rad":rad,
	        "tom":ton,
	        "token":commingSoon1(stri)
	    },
	    success:function(data){
	    	if(data.status==200){
	    		swal({ 
	    			  title: "成功", 
	    			  text: "收款成功，感谢您的参与", 
	    			  type: "success",
	    			  showCancelButton: true, 
	    			  confirmButtonColor: "#DD6B55",
	    			  confirmButtonText: "确定", 
	    			  cancelButtonText: "取消",
	    			  closeOnConfirm: false, 
	    			  closeOnCancel: false	
	    			},
	    			function(isConfirm){ 
	    			  if (isConfirm) { 
	    				  window.location.href = "quan_detail2?"+str;
	    			  } else { 
	    				  window.location.href = "quan_detail2?"+str;
	    			  } 
	    	    });
	    	}else{
	    		swal({
		  			  title: data.msg,
		  			  icon: "error",
		  			  button: "确定",
		  		});
			    return false;
	    	}
	    }
	});
}

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