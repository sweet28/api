var count = 0;
var bzPirce = 0.1;
var state;
var trader_id;
var id;
var isDelete;
$(function () {
	var url = window.location.search.substring(1);
	
	console.log(url.split("&"));
	
	var urlList = url.split("&");
	var tradeId = urlList[0];
	var tradeType = urlList[1];
	
	var price = $("#cpaprice");
	var cpaCount = $("#cpa_count");
	
	if(tradeType=="mr"){
		$("#modifypassword_btnmr").show();
		$("#modifypassword_btncs").hide();
	}
	if(tradeType=="cs"){
		$("#modifypassword_btnmr").hide();
		$("#modifypassword_btncs").show();
	}
	
	$.ajax({
	    type: "post",
	    url: getAPIURL() + "miner/record/detail",
	    dataType: "json",
	    data: {
	    	"id":tradeId
	    },
	    success: function (data) {
	    	console.log(data);
	    	state = data.traderState;
	    	isDelete = data.isDelete;
	    	if(state!=0){
	    		layer.open({
			          content: '该单已被抢，请选择其他订单。'
			          , btn: '确定'
			      	});
			    return false;
	    	}else if(isDelete!=0){
	    		layer.open({
			          content: '该单已被撤销，请选择其他订单。'
			          , btn: '确定'
			      	});
			    return false;
	    	}else if(data.id == tradeId){
	    		id = data.id;
	    		bzPirce = data.entrustPrice;
	    		count = data.traderCount;
	    		trader_id = data.traderId;
	    		
	    		price.val(data.entrustPrice);
	    		cpaCount.val(data.traderCount);
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

function cpaNextMaiRu(){
	var price = $("#cpaprice");
	var cpaCount = $("#cpa_count");
	
	if(state!=0){
		layer.open({
	          content: '该单已被抢，请选择其他订单。'
	          , btn: '确定'
	      	});
	    return false;
	}
	
	if(isDelete!=0){
		layer.open({
	          content: '该单已被撤销，请选择其他订单。'
	          , btn: '确定'
	      	});
	    return false;
	}
	
	if(price.val()==null || price.val()==""){
		layer.open({
	          content: '交易拥堵，请稍后重新购买。2'
	          , btn: '确定'
	      	});
	    return false;
	}
	
	if(count <= 0 ){
		layer.open({
	          content: '卖单CPA数量不足，请重新选择卖单。'
	          , btn: '确定'
	      	});
	    return false;
	}
	
	if(count < 1 && count >0){
		if(cpaCount.val() <= 0 ){
			layer.open({
		          content: '数量不能小于0。'
		          , btn: '确定'
		      	});
		    return false;
		}
		
		if(cpaCount.val() > count){
			layer.open({
		          content: '数量不能大于卖单数量。'
		          , btn: '确定'
		      	});
		    return false;
		}
	}else if(count >= 1){
		if(cpaCount.val() < 1 ){
			layer.open({
		          content: '数量不能小于1。'
		          , btn: '确定'
		      	});
		    return false;
		}
		
		if(cpaCount.val() > count){
			layer.open({
		          content: '数量不能大于卖单数量。'
		          , btn: '确定'
		      	});
		    return false;
		}
	}
	
	//交易状态   0代表挂单中；1代表成交待付款；2代表成交已付款待卖家确认；3代表已完成
	var trader_state = 1;
	//交易类型   1代表买方  2代表卖方
//	var trader_type = 1;
	//挂单人id
//	var trader_id = localStorage.getItem("uid");
	//交易CPA数
	var trader_count = cpaCount.val();
	//委托价格(美元)
	var entrust_price = price.val();
	//接单人ID
	var fens_user_id = localStorage.getItem("uid");
	//总金额
	var countPrice = trader_count*entrust_price*6.5;
	
	$.ajax({
	      type: "post",
	      url: getAPIURL() + "miner/record/updateRecord",
	      dataType: "json",
	      data:{
	    	  "id":id,
	    	  "traderId":trader_id,
	    	  "traderState":trader_state,
	    	  "entrustPrice":entrust_price,
	    	  "traderCount":trader_count,
	    	  "moneyCount":countPrice,
	    	  "fensUserId":fens_user_id,
	    	  "attachment":localStorage.getItem("uid")
	      },
	      success: function (data) {
	        if (data.status==200) {
	          layer.open({
	            content: '交易成功。'
	            , btn: ['确定']
	            , yes: function (index) {
	              window.location.href = "../page/my_invest2.html?"+id;
	            }
	          });
	        } else {
	        	layer.open({
		            content: '操作失败，请检查网络服务。'
		            , btn: ['确定']
		            , yes: function (index) {
		  	          window.location.href = "../page/index.html";
		            }
		          });
	        }
	      },
	      headers: {
	        "Authorization": "Bearer " + getTOKEN()
	      }
	});
}

function cpaNextChuShou(){
	var price = $("#cpaprice");
	var cpaCount = $("#cpa_count");
	
	if(state!=0){
		layer.open({
	          content: '该单已被抢，请选择其他订单。'
	          , btn: '确定'
	      	});
	    return false;
	}
	
	if(isDelete!=0){
		layer.open({
	          content: '该单已被撤销，请选择其他订单。'
	          , btn: '确定'
	      	});
	    return false;
	}
	
	if(price.val()==null || price.val()==""){
		layer.open({
	          content: '交易拥堵，请稍后重新购买。2'
	          , btn: '确定'
	      	});
	    return false;
	}
	
	if(count <= 0 ){
		layer.open({
	          content: '卖单CPA数量不足，请重新选择卖单。'
	          , btn: '确定'
	      	});
	    return false;
	}
	
	if(count < 1 && count >0){
		if(cpaCount.val() <= 0 ){
			layer.open({
		          content: '数量不能小于0。'
		          , btn: '确定'
		      	});
		    return false;
		}
		
		if(cpaCount.val() > count){
			layer.open({
		          content: '数量不能大于卖单数量。'
		          , btn: '确定'
		      	});
		    return false;
		}
	}else if(count >= 1){
		if(cpaCount.val() < 1 ){
			layer.open({
		          content: '数量不能小于1。'
		          , btn: '确定'
		      	});
		    return false;
		}
		
		if(cpaCount.val() > count){
			layer.open({
		          content: '数量不能大于卖单数量。'
		          , btn: '确定'
		      	});
		    return false;
		}
	}
	
	//交易状态   0代表挂单中；1代表成交待付款；2代表成交已付款待卖家确认；3代表已完成
	var trader_state = 1;
	//交易CPA数
	var trader_count = cpaCount.val();
	//委托价格(美元)
	var entrust_price = price.val();
	//接单人ID
	var fens_user_id = localStorage.getItem("uid");
	//总金额
	var countPrice = trader_count*entrust_price*6.5;
	
	$.ajax({
	      type: "post",
	      url: getAPIURL() + "miner/record/updateRecord",
	      dataType: "json",
	      data:{
	    	  "id":id,
	    	  "traderId":trader_id,
	    	  "traderState":trader_state,
	    	  "entrustPrice":entrust_price,
	    	  "traderCount":trader_count,
	    	  "traderType":1,
	    	  "moneyCount":countPrice,
	    	  "fensUserId":fens_user_id,
	    	  "attachment":localStorage.getItem("uid")
	      },
	      success: function (data) {
	    	  console.log("-----------------------");
	    	  console.log(data);
	        if (data.status==200) {
	          layer.open({
	            content: '交易提交成功，待人工审核卖方CPA资产合法性通过后，进行交易。'
	            , btn: ['确定']
	            , yes: function (index) {
	              window.location.href = "../page/my_invest2.html?"+id;
	            }
	          });
	        } else {
	        	layer.open({
		            content: '操作失败，请检查网络服务。'
		            , btn: ['确定']
		            , yes: function (index) {
		  	          window.location.href = "../page/index.html";
		            }
		          });
	        }
	      },
	      headers: {
	        "Authorization": "Bearer " + getTOKEN()
	      }
	});
}

//function cpaNext2(){
//	if(state!=0){
//		layer.open({
//	          content: '该单已被抢，请选择其他订单。'
//	          , btn: '确定'
//	      	});
//	    return false;
//	}
//	
//	var price = $("#cpaprice");
//	var cpaCount = $("#cpa_count");
//	
//	console.log("count::"+count);
//	
//	if(price.val()==null || price.val()==""){
//		layer.open({
//	          content: '交易拥堵，请稍后重新购买。3'
//	          , btn: '确定'
//	      	});
//	    return false;
//	}
//	
//	if(count <= 0 ){
//		layer.open({
//	          content: '卖单CPA数量不足，请重新选择卖单。'
//	          , btn: '确定'
//	      	});
//	    return false;
//	}
//	
//	if(count < 1 && count >0){
//		if(cpaCount.val() <= 0 ){
//			layer.open({
//		          content: '数量不能小于0。'
//		          , btn: '确定'
//		      	});
//		    return false;
//		}
//		
//		if(cpaCount.val() > count){
//			layer.open({
//		          content: '数量不能大于卖单数量。'
//		          , btn: '确定'
//		      	});
//		    return false;
//		}
//	}else if(count >= 1){
//		if(cpaCount.val() < 1 ){
//			layer.open({
//		          content: '数量不能小于1。'
//		          , btn: '确定'
//		      	});
//		    return false;
//		}
//		
//		if(cpaCount.val() > count){
//			layer.open({
//		          content: '数量不能大于卖单数量。'
//		          , btn: '确定'
//		      	});
//		    return false;
//		}
//	}
//	
//	//交易状态   0代表挂单中；1代表成交待付款；2代表成交已付款待卖家确认；3代表已完成
//	var trader_state = 1;
//	//交易类型   1代表买方  2代表卖方
//	var trader_type = 1;
//	//交易人id
//	var trader_id = localStorage.getItem("uid");
//	//交易CPA数
//	var trader_count = cpaCount.val();
//	//委托价格(美元)
//	var entrust_price = price.val();
//	//被交易人ID
//	var fens_user_id = fensID;
//	//总金额
//	var countPrice = trader_count*entrust_price*6.5;
//	
//	$.ajax({
//	      type: "post",
//	      url: getAPIURL() + "miner/record/addRecord",
//	      dataType: "json",
//	      data:{
//	    	  "traderId":trader_id,
//	    	  "traderType":trader_type,
//	    	  "traderState":trader_state,
//	    	  "entrustPrice":entrust_price,
//	    	  "traderCount":trader_count,
//	    	  "moneyCount":countPrice,
//	    	  "fensUserId":fens_user_id
//	      },
//	      success: function (data) {
//	        if (data.status==200) {
//	        	console.log("data::"+data+"-----id:"+data.data.id);
//	        	
//	          layer.open({
//	            content: '交易成功。'
//	            , btn: ['确定']
//	            , yes: function (index) {
//	              window.location.href = "../page/my_invest2.html?"+data.data.id;
//	            }
//	          });
//	        } else {
//	        	layer.open({
//		            content: '操作失败，请检查网络服务。'
//		            , btn: ['确定']
//		            , yes: function (index) {
//		  	          window.location.href = "../page/index.html";
//		            }
//		          });
//	        }
//	      },
//	      headers: {
//	        "Authorization": "Bearer " + getTOKEN()
//	      }
//  });
//}