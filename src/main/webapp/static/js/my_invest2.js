var ddNum;
var ddTime;
var price;
var totalCount;
var ddState;
var syTime;
var totalPrice;
var mjxm;
var bank;
var bankCard;
var bankDetail;
var bankAttr;

var tradeId;
var uid;
var fensID;
$(function () {
	tradeId = window.location.search.substring(1);
	$.ajax({
	    type: "post",
	    url: getAPIURL() + "miner/record/detail",
	    dataType: "json",
	    data: {
	    	"id":tradeId,
	    	"traderState":2
	    },
	    success: function (data) {
	    	console.log(data);
	    	if(data.id == tradeId){
	    		ddNum = data.orderNumber;
	    		ddTime =data.createDate;
	    		price = data.entrustPrice;
	    		totalCount = data.traderCount;
	    		ddState = data.traderState;
	    		syTime = "请在订单生成后24小时内内支付。";
	    		totalPrice = data.moneyCount;
	    		
	    		uid = data.traderId;
	    		fensID = data.fensUserId;
	    		
	    		console.log(uid+":-------101-------:"+fensID);
	    		
	    		$("#ddNum").val(ddNum);
	    		$("#createTime").val(ddTime);
	    		$("#pricecpa").val(price);
	    		$("#cpaCount").val(totalCount);
	    		$("#syTime").val(syTime);
	    		$("#totalPrice").val("￥"+totalPrice);
	    		if(ddState=='1'){
	    			$("#ddState").val("待付款");
	    			$("#modifypassword_btn2").css({"display": "none"});
	    		}else if(ddState=='2'){
	    			$("#ddState").val("已付款，待确认");
	    			$("#modifypassword_btn").attr('disabled',"true");
	    			$("#modifypassword_btn").css({'background':'gray'});
	    			

	    			$("#modifypassword_btn").css({"display": "none"});
	    		}else if(ddState=='3'){
	    			$("#ddState").val("已完成");
	    			$("#modifypassword_btn").attr('disabled',"true");
	    			$("#modifypassword_btn").css({'background':'gray'});
	    			

	    			$("#modifypassword_btn").css({"display": "none"});

	    			$("#modifypassword_btn2").css({"display": "none"});
	    		}
	    		
	    		$("#bankAttr").val(ddNum.substring(10));
	    		
	    		var fensId = data.fensUserId;
	    		
	    		console.log("fensID----------:"+fensId);
	    		$.ajax({
	    		      type: "post",
	    		      url: getAPIURL() + "bank/list",
	    		      dataType: "json",
	    		      data: {
	    		    	  "fensUserId":fensId,
	    		    	  "pageSize":100,
	    		    	  "pageNum":0
	    		      },
	    		      success: function (data) {
	    		        var list = data.list;
	    		        console.log(list);
	    		        if (list.length <= 0) {
	    		        	console.log("没有账号信息");
	    		        } else {
	    		        	$.each( list, function(index, content){
	    		        		var runs = content.isApply;
	    		        		if(runs==0){
	    		        			runs="未使用";
	    		        		}else if(runs==1){
	    		        			runs="使用中";
	    		        			
	    		        			$("#mjxm").val(content.name);
	    		        			$("#bank").val(content.bank);
	    		        			$("#bankCard").val(content.cardNumber);
	    		        			$("#bankDetail").val(content.openBranch);
	    		        		}
	    					});
	    		        }
	    		      }, headers: {
	    		        "Authorization": "Bearer " + getTOKEN()
	    		      }
	    		    });
	    		
	    	}else{
	    		layer.open({
			          content: '交易拥堵，请稍后重新购买。0'
			          , btn: '确定'
			      	});
			    return false;
	    	}
	    },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	    	layer.open({
		          content: '交易拥堵，请稍后重新购买。1'
		          , btn: '确定'
		      	});
		    return false;
	    }
	});
});

function cpaNext2(){
	console.log("tid:"+uid+"------fid:"+fensID);
	if(localStorage.getItem("uid")==uid){
		console.log(888);
		$.ajax({
		    type: "post",
		    url: getAPIURL() + "miner/record/updateRecord",
		    dataType: "json",
		    data: {
		    	"id":tradeId,
		    	"traderState":2
		    },
		    success: function (data) {
		    	console.log(data);
		    	if(data.status==200){
		    		ddState = data.traderState;
		    		layer.open({
			            content: '提交成功，待卖家确认。'
			            , btn: ['确定']
			            , yes: function (index) {
			              window.location.href = "../page/invest.html";
			            }
			          });
		    	}else{
		    		layer.open({
				          content: '交易拥堵，请稍后重新购买。0'
				          , btn: '确定'
				      	});
				    return false;
		    	}
		    },
		    error: function (XMLHttpRequest, textStatus, errorThrown) {
		    	layer.open({
			          content: '交易拥堵，请稍后重新购买。1'
			          , btn: '确定'
			      	});
			    return false;
		    }
		});
	}else{
		layer.open({
	          content: '这不是你的订单，你无权操作。'
	          , btn: '确定'
	      	});
	    return false;
	}
}

function cpaNext3(){
	console.log("tid:"+uid+"------fid:"+fensID);
	if(localStorage.getItem("uid")==fensID){
		console.log(999);
		$.ajax({
		    type: "post",
		    url: getAPIURL() + "miner/record/updateRecordCJ",
		    dataType: "json",
		    data: {
		    	"id":tradeId,
		    	"traderState":3
		    },
		    success: function (data) {
		    	console.log(data);
		    	if(data.status==200){
		    		ddState = data.traderState;
		    		layer.open({
			            content: '提交成功。'
			            , btn: ['确定']
			            , yes: function (index) {
			              window.location.href = "../page/invest.html";
			            }
			          });
		    	}else{
		    		layer.open({
				          content: '交易拥堵，请稍后重新购买。0'
				          , btn: '确定'
				      	});
				    return false;
		    	}
		    },
		    error: function (XMLHttpRequest, textStatus, errorThrown) {
		    	layer.open({
			          content: '交易拥堵，请稍后重新购买。1'
			          , btn: '确定'
			      	});
			    return false;
		    }
		});
	}else{
		layer.open({
	          content: '这不是你的订单，你无权操作。'
	          , btn: '确定'
	      	});
	    return false;
	}
}