var str;
var shoukuanId;
var dakuangId;
(function(){

	$("#modifypassword_btn").hide();
	
	$("#modifypassword_btn2").hide();
	
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
				 dakuangId = quan.dakuangId;
				 shoukuanId = quan.shoukuanId;
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
	    			    	$("#mrsj3").html(mjsj);
	    			    	$("#mrxm3").html("姓名："+data.name);
	    			    },
	    			    error: function (XMLHttpRequest, textStatus, errorThrown) {
	    			    }
	    			  });
				 $.ajax({
	    			    type: "post",
	    			    url: getAPIURL() + "fenuser/info",
	    			    dataType: "json",
	    			    data: {
	    			    	"id":quan.shoukuanId
	    			    },
	    			    success: function (data) {
	    			    	mjsj = data.phone;
	    			    	console.log(mjsj);
	    			    	$("#mrsjj3").html(mjsj);
	    			    },
	    			    error: function (XMLHttpRequest, textStatus, errorThrown) {
	    			    }
	    			  });
				 
				 $.ajax({
	    		      type: "post",
	    		      url: getAPIURL() + "fs/bank/zh",
	    		      dataType: "json",
	    		      data: {
	    		    	  "uid": quan.shoukuanId
	    		      },
	    		      success: function (data) {
	    		        var list = data.data;
	    		        if (list.length <= 0) {
	    		        } else {
	    		        	$.each( list, function(index, content){
	    		        		var runs = content.isApply;
	    		        		console.log(content);
	    		        		if(content.bak1==1){
		    		        			$("#mjxm3").html("姓名:"+content.name);
		    		        			$("#alipayID3").html("账号:"+content.cardNumber);
	    		        		}else if(content.bak1==2){
		    		        			$("#mjxm3").html("姓名:"+content.name);
		    		        			$("#weixinID3").html("账号:"+content.cardNumber);
	    		        		}
//	    		        		else if(content.bak1==3){
//		    		        			$("#mjxm3").html("姓名:"+content.name);
//		    		        			$("#imtokenID3").html("账号:"+content.cardNumber);
//	    		        		}
	    		        		else{
		    		        			$("#mjxm3").html("姓名: "+content.name);
		    		        			$("#bank3").html("账号类型:"+content.bank);
		    		        			$("#bankCard3").html("账号:"+content.cardNumber);
		    		        			$("#bankDetail3").html("补充说明/分支:"+content.openBranch);
	    		        		}
	    					});
	    		        }
	    		      }, headers: {
	    		        "Authorization": "Bearer " + getTOKEN()
	    		      }
	    		    });
				$("#ddNum3").html("订单号："+quan.bak2);
				$("#createTime3").html("订单生成时间："+new Date(quan.createDate).format("yyyy-MM-dd hh:mm:ss"));
				var order = quan.dakuanType;
				if(order == 1){
				   order = "买家待打款";
				   if(dakuangId == localStorage.getItem("uid")){
						$("#modifypassword_btn").show();
				   }
				}else if(order == 2){
					order = "卖家待收款";
					if(shoukuanId == localStorage.getItem("uid")){
						$("#modifypassword_btn2").show();
					}
				}else if(order == 3){
					order = "已确认收款";
				}
				
				$("#pricecpa3").html("订单状态："+order);
				$("#mon3").html("￥："+quan.bak1);
				$("#mrxm3").html("姓名："+quan.daName);
				
			 }
			 
		 }
		 
	});
})();

var flag = checkLogin();
var tmp = getTimestamp();
var rad = getRandom();
var ton = getTom();

function fk(){
//	var flag = checkLogin();
	var tmp = getTimestamp();
	var rad = getRandom();
	var ton = getTom();
	var stri = "pipeiId="+str+"tmp="+tmp+"rad="+rad+"tom="+ton; 
	$.ajax({
	    type: "post",
	    url: getAPIURL() + "quan/fuk",
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
	    			  text: "付款成功，待卖家确认！", 
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
	    				  window.location.href = "quanOrderDetailInfo?"+str;
	    			  } else { 
	    				  window.location.href = "quanOrderDetailInfo?"+str;
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


function shouk(){
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
	    			  text: "确认收款成功！", 
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
	    				  window.location.href = "quanOrderDetailInfo?"+str;
	    			  } else { 
	    				  window.location.href = "quanOrderDetailInfo?"+str;
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