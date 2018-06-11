function agoumai(type,grade){
	$(".more").hide();
	
//	if(grade==3){
//		swal({
//			  title: "操作完成!",
//			  text: "条件审核，排队购买!",
//			  icon: "success",
//			  button: "确定",
//		});
//		return false;
//	}
	
	var sec = localStorage.getItem("sec");
	if(sec!='1'){
		swal({
			  title: "操作完成!",
			  text: "未认证用户不能交易!",
			  icon: "success",
			  button: "确定",
		});
		return false;
	}
	
	  var methodd;
	  if(type==1){
		  methodd = "buyAMiner";
	  }
	  if(type==2){
		  methodd = "buyBMiner";
	  }
	  var flag = checkLogin();
	  var tmp = getTimestamp();
	  var rad = getRandom();
	  var ton = getTom();
      var str = "uid="+localStorage.getItem("uid")+"lx="+grade+"tmp="+tmp+"rad="+rad+"tom="+ton;
      console.log(str);
	  $.ajax({
		    type: "post",
		    url: getAPIURL() + "kuangjy/jy/"+methodd,
		    dataType: "json",
		    data: {
		    	"uid":localStorage.getItem("uid"),
		    	"lx":grade,
		    	"tmp":tmp,
		        "rad":rad,
		        "tom":ton,
		        "token":commingSoon1(str)
		    },
		    success: function (data) {
		    	var dd = data.data;
		    	console.log(111);
		    	if(data.status==200){
		    		swal({
		    			  title: "操作完成!",
		    			  text: "购买申请成功，待审核通过后发放矿机!",
		    			  icon: "success",
		    			  button: "确定",
		    		});
		    		$(".more").show();
		    	}else{
		    		console.log("0000:"+data.status+"----::"+data.msg);
		    		
		    		swal({
		    			  title: "购买失败!",
		    			  text: data.msg,
		    			  icon: "error",
		    			  button: "确定",
		    		});
		    		$(".more").show();
		    	}
		    },
		    error: function (XMLHttpRequest, textStatus, errorThrown) {
		    	console.log(444);
		    	swal({
	    			  title: "购买失败!",
	    			  text: "交易人数较多，请稍后重试!",
	    			  icon: "error",
	    			  button: "确定",
	    		});
		    	$(".more").show();
		    }
	    });
	    
  }
  

$(function(){
//	先判断是否登录
	var flag = checkLogin();
  
	$("#uname").html("欢迎，"+localStorage.getItem("name"));
	
});