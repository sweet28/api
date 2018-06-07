var ddNum;
var ddTime;
var price;
var totalCount;
var ddState;
var syTime;
var totalPrice;
var mjxm;
var mjsj;
var bank;
var bankCard;
var bankDetail;
var bankAttr;

var mrxm;
var mrsj;

var tradeId;
var uid;
var fensID;
var type;

function Gift() {
	
	$(function () {
		tradeId = window.location.search.substring(1);
		
		console.log(tradeId);
		$.ajax({
		    type: "post",
		    url: getAPIURL() + "miner/record/detail",
		    dataType: "json",
		    data: {
		    	"id":tradeId
		    },
		    success: function (data) {
		    	console.log(data);
		    	if(data.id == tradeId){
		    		console.log(222);
		    		ddNum = data.orderNumber;
		    		ddTime =data.createDate;
		    		price = data.entrustPrice;
		    		totalCount = data.traderCount;
		    		ddState = data.traderState;
		    		ddType = data.traderType;//1：买单；2：卖单
		    		type = ddType;
		    		syTime = "请在订单生成后24小时内支付。";
		    		totalPrice = data.entrustPrice*6.5*data.traderCount;//data.moneyCount;
		    		
		    		uid = data.traderId;
		    		fensID = data.fensUserId;
		    		
		    		if(ddState=='1'){
		    			$("#ddState").html("状态:"+"待付款");
		    		}else if(ddState=='2'){
		    			$("#ddState").html("状态:"+"已付款，待确认");
		    		}else if(ddState=='4'){
		    			$("#ddState").html("状态:"+"待审核卖家CPA合法性");
		    		}else if(ddState=='3'){
		    			$("#ddState").html("状态:"+"已完成");
		    			$("#modifypassword_btn").css({"display": "none"});
		    			$("#modifypassword_btn2").css({"display": "none"});
		    		}
		    		
		    		$("#ddNum").html("订单号:"+ddNum);
		    		$("#createTime").html("订单生成时间:"+ddTime);
		    		$("#pricecpa").html("单价:"+"$"+price);
		    		$("#cpaCount").html("CPA数量:"+totalCount);
		    		$("#syTime").html(syTime);
		    		$("#totalPrice").html("总价:"+"￥"+totalPrice);

		    		$("#modifypassword_btn").hide();
		    		$("#modifypassword_btn2").hide();
		    		
		    		console.log(ddState+"<-state---type->"+ddType+"---反反复复付---fid->"+fensID+"---tid->"+uid+"----nowID"+localStorage.getItem("uid"));
		    		if(ddState==1 && ddType==2 && localStorage.getItem("uid")==fensID){
		    			console.log(1);
		    			$("#modifypassword_btn").show();
		    		}
		    		if(ddState==1 && ddType==1 && localStorage.getItem("uid")==uid){
		    			console.log(2);
		    			$("#modifypassword_btn").show();
		    		}
		    		if(ddState==2 && ddType==2 && localStorage.getItem("uid")==uid){
		    			console.log(3);
		    			$("#modifypassword_btn2").show();
		    		}
		    		if(ddState==2 && ddType==1 && localStorage.getItem("uid")==fensID){
		    			console.log(4);
		    			$("#modifypassword_btn2").show();
		    		}
		    		
		    		$("#bankAttr").html("打款备注:"+ddNum.substring(10)+"（在转账时请一定填写此备注！！！）");
		    		
		    		var userid;//卖家ID
		    		if(ddType==2){//1：买单；2：卖单
		    			userid = data.traderId;
		    			
		    			$.ajax({
		    			    type: "post",
		    			    url: getAPIURL() + "fenuser/info",
		    			    dataType: "json",
		    			    data: {
		    			    	"id":data.traderId
		    			    },
		    			    success: function (data) {
		    			    	mjsj = data.phone;
		    			    	if(ddState!='4'){
		    			    		$("#mjsj").html("手机:"+mjsj);
		    			    	}
		    			    },
		    			    error: function (XMLHttpRequest, textStatus, errorThrown) {
		    			    }
		    			  });
		    			
		    			$.ajax({
		    			    type: "post",
		    			    url: getAPIURL() + "fenuser/info",
		    			    dataType: "json",
		    			    data: {
		    			    	"id":data.fensUserId
		    			    },
		    			    success: function (data) {
		    			    	mrsj = data.phone;
		    			    	mrxm = data.name;
		    			    	if(ddState!='4'){
			    			    	$("#mrsj").html("手机:"+mrsj);
			    			    	$("#mrxm").html("姓名:"+mrxm);
		    			    	}
		    			    },
		    			    error: function (XMLHttpRequest, textStatus, errorThrown) {
		    			    }
		    			  });
		    			
		    		}
		    		if(ddType==1){//1：买单；2：卖单
		    			userid = data.fensUserId;
		    			
		    			$.ajax({
		    			    type: "post",
		    			    url: getAPIURL() + "fenuser/info",
		    			    dataType: "json",
		    			    data: {
		    			    	"id":userid
		    			    },
		    			    success: function (data) {
		    			    	mjsj = data.phone;
		    			    	if(ddState!='4'){
		    			    		$("#mjsj").html("手机:"+mjsj);
		    			    	}
		    			    },
		    			    error: function (XMLHttpRequest, textStatus, errorThrown) {
		    			    }
		    			  });
		    			
		    			$.ajax({
		    			    type: "post",
		    			    url: getAPIURL() + "fenuser/info",
		    			    dataType: "json",
		    			    data: {
		    			    	"id":data.traderId
		    			    },
		    			    success: function (data) {
		    			    	mrsj = data.phone;
		    			    	mrxm = data.name;
		    			    	if(ddState!='4'){
			    			    	$("#mrsj").html("手机:"+mrsj);
			    			    	$("#mrxm").html("姓名:"+mrxm);
		    			    	}
		    			    },
		    			    error: function (XMLHttpRequest, textStatus, errorThrown) {
		    			    }
		    			  });
		    		}
		    		
		    		$.ajax({
		    		      type: "post",
		    		      url: getAPIURL() + "bank/list",
		    		      dataType: "json",
		    		      data: {
		    		    	  "fensUserId":userid,
		    		    	  "pageSize":100,
		    		    	  "pageNum":0
		    		      },
		    		      success: function (data) {
		    		        var list = data.list;
		    		        if (list.length <= 0) {
		    		        	console.log("没有账号信息");
		    		        } else {
		    		        	console.log(list);
		    		        	$.each( list, function(index, content){
		    		        		var runs = content.isApply;
		    		        		if(content.bak1==1){
		    		        			if(ddState!='4'){
			    		        			$("#mjxm").html("姓名:"+content.name);
			    		        			$("#alipayID").html("账号:"+content.cardNumber);
		    		        			}
		    		        		}else if(content.bak1==2){
		    		        			if(ddState!='4'){
			    		        			$("#mjxm").html("姓名:"+content.name);
			    		        			$("#weixinID").html("账号:"+content.cardNumber);
		    		        			}
		    		        		}else if(content.bak1==3){
		    		        			if(ddState!='4'){
			    		        			$("#mjxm").html("姓名:"+content.name);
			    		        			$("#imtokenID").html("账号:"+content.cardNumber);
		    		        			}
		    		        		}else{
		    		        			if(ddState!='4'){
			    		        			$("#mjxm").html("姓名:"+content.name);
			    		        			$("#bank").html("账号类型:"+content.bank);
			    		        			$("#bankCard").html("账号:"+content.cardNumber);
			    		        			$("#bankDetail").html("补充说明/分支:"+content.openBranch);
		    		        			}
		    		        		}
		    					});
		    		        }
		    		      }, headers: {
		    		        "Authorization": "Bearer " + getTOKEN()
		    		      }
		    		    });
		    		
		    	}else{
		    		swal({
			  			  title: "不能操作他人ding",
			  			  icon: "error",
			  			  button: "确定",
			  		});
				    return false;
		    	}
		    },
		    error: function (XMLHttpRequest, textStatus, errorThrown) {
		    	swal({
		  			  title: "交易拥堵，请稍后重新交易。1",
		  			  icon: "error",
		  			  button: "确定",
		  		});
			    return false;
		    }
		});
	});

	
  var self = this, _$gift;

  (function () {
    _$gift = $("#a_miner");
//    comptime();
  })();
}
var gift;
$(function () {
  gift = new Gift();
});

function submitYFK(){
	var methodName;
	if(type==2){//1：买单；2：卖单
		methodName = "sellDanYiFu";
	}
	if(type==1){//1：买单；2：卖单
		methodName = "buyDanYiFu";
	}
	
    var flag = checkLogin();
    var tmp = getTimestamp();
    var rad = getRandom();
    var ton = getTom();
    var str = "id="+tradeId+"uid="+localStorage.getItem("uid")+"tmp="+tmp+"rad="+rad+"tom="+ton; 
	$.ajax({
	    type: "post",
	    url: getAPIURL() + "kuangjy/jy/"+methodName,
	    dataType: "json",
	    data: {
	    	"id":tradeId,
	    	"uid":localStorage.getItem("uid"),
	    	  "tmp":tmp,
	          "rad":rad,
	          "tom":ton,
	          "token":commingSoon1(str)
	    },
	    success: function (data) {
	    	if(data.status==200){
	    		ddState = data.traderState;
	    		swal({ 
	    			  title: "成功", 
	    			  text: "提交成功，待卖家确认！", 
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
	    				  window.location.href = "traderDetail?"+tradeId;
	    			  } else { 
	    				  window.location.href = "traderDetail?"+tradeId;
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
	    },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	    	swal({
	  			  title: "交易拥堵，请稍后重新交易。1",
	  			  icon: "error",
	  			  button: "确定",
	  		});
		    return false;
	    }
	});
}

function submitYSK(){
	var userid;
	if(type==2){//1：买单；2：卖单
		userid = uid;
	}
	if(type==1){//1：买单；2：卖单
		userid = fensID;
	}
    var flag = checkLogin();
    var tmp = getTimestamp();
    var rad = getRandom();
    var ton = getTom();
    var str = "id="+tradeId+"uid="+localStorage.getItem("uid")+"tmp="+tmp+"rad="+rad+"tom="+ton; 
	if(localStorage.getItem("uid")==userid){
		console.log(999);
		$.ajax({
		    type: "post",
		    url: getAPIURL() + "kuangjy/jy/RecordCJ",
		    dataType: "json",
		    data: {
		    	"id":tradeId,
		    	"type":type,
		    	"td":uid,
		    	"fud":fensID,
		    	  "tmp":tmp,
		          "rad":rad,
		          "tom":ton,
		          "token":commingSoon1(str)
		    },
		    success: function (data) {
		    	if(data.status==200){
		    		ddState = data.traderState;
		    		swal({ 
		    			  title: "成功", 
		    			  text: "提交成功！", 
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
		    				  window.location.href = "traderDetail?"+tradeId;
		    			  } else { 
		    				  window.location.href = "traderDetail?"+tradeId;
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
		    },
		    error: function (XMLHttpRequest, textStatus, errorThrown) {
		    	swal({
		  			  title: '交易拥堵，请稍后重新交易。',
		  			  icon: "error",
		  			  button: "确定",
		  		});
			    return false;
		    }
		});
	}else{
		swal({
  			  title: '这不是你的订单，你无权操作。',
  			  icon: "error",
  			  button: "确定",
  		});
	    return false;
	}
}

