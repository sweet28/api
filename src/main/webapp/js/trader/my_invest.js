var count = 0;
var bzPirce = 0.1;
var state;
var trader_id;
var id=7278;
var isDelete;
//$(function () {
//	var url = window.location.search.substring(1);
//	
//	var urlList = url.split("&");
//	var tradeId = urlList[0];
//	var tradeType = urlList[1];
//	
//	var price = $("#cpaprice");
//	var cpaCount = $("#cpa_count");
//	
//	if(tradeType=="mr"){
//		$("#modifypassword_btnmr").show();
//		$("#modifypassword_btncs").hide();
//	}
//	if(tradeType=="cs"){
//		$("#modifypassword_btnmr").hide();
//		$("#modifypassword_btncs").show();
//	}
//	
//	$.ajax({
//	    type: "post",
//	    url: getAPIURL() + "miner/record/detail",
//	    dataType: "json",
//	    data: {
//	    	"id":tradeId
//	    },
//	    success: function (data) {
//	    	state = data.traderState;
//	    	isDelete = data.isDelete;
//	    	if(state!=0){
//	    		swal({
//		          	  title: '该单已被抢，请选择其他订单。',
//		          	  type: 'error',
//		          	  showCancelButton: true,
//		          	  confirmButtonText: "确定", 
//		          	  cancelButtonText: "取消",
//		          	  closeOnConfirm: true, 
//		          	  closeOnCancel: true
//		          	}).then(function(isConfirm) {
//		          	  if (isConfirm === true) {
//		          		 // window.location.href = getAPIURL()+"cpa/traderCenter";
//		          	  } else if (isConfirm === false) {
//		          		//  window.location.href = getAPIURL()+"cpa/traderCenter";
//		          	  } else {
//		          		//  window.location.href = getAPIURL()+"cpa/traderCenter";
//		          	  }
//	          	});
//			    return false;
//	    	}else if(isDelete!=0){
//	    		swal({
//		          	  title: '该单已被撤销，请选择其他订单。',
//		          	  type: 'error',
//		          	  showCancelButton: true,
//		          	  confirmButtonText: "确定", 
//		          	  cancelButtonText: "取消",
//		          	  closeOnConfirm: true, 
//		          	  closeOnCancel: true
//		          	}).then(function(isConfirm) {
//		          	  if (isConfirm === true) {
//		          		  //window.location.href = getAPIURL()+"cpa/traderCenter";
//		          	  } else if (isConfirm === false) {
//		          		 // window.location.href = getAPIURL()+"cpa/traderCenter";
//		          	  } else {
//		          		 // window.location.href = getAPIURL()+"cpa/traderCenter";
//		          	  }
//	          	});
//			    return false;
//	    	}else if(data.id == tradeId){
//	    		id = data.id;
//	    		bzPirce = data.entrustPrice;
//	    		count = data.traderCount;
//	    		trader_id = data.traderId;
//	    		
//	    		price.val(data.entrustPrice);
//	    		cpaCount.val(data.traderCount);
//	    	}else{
//	    		swal({
//		          	  title: '交易拥堵，请稍后重新交易。',
//		          	  type: 'error',
//		          	  showCancelButton: true,
//		          	  confirmButtonText: "确定", 
//		          	  cancelButtonText: "取消",
//		          	  closeOnConfirm: true, 
//		          	  closeOnCancel: true
//		          	}).then(function(isConfirm) {
//		          	  if (isConfirm === true) {
//		          		//  window.location.href = getAPIURL()+"cpa/traderCenter";
//		          	  } else if (isConfirm === false) {
//		          		//  window.location.href = getAPIURL()+"cpa/traderCenter";
//		          	  } else {
//		          		//  window.location.href = getAPIURL()+"cpa/traderCenter";
//		          	  }
//	          	});
//			    return false;
//	    	}
//	    },
//	    error: function (XMLHttpRequest, textStatus, errorThrown) {
//	    	swal({
//	          	  title: '交易拥堵，请稍后重新交易。1',
//	          	  type: 'error',
//	          	  showCancelButton: true,
//	          	  confirmButtonText: "确定", 
//	          	  cancelButtonText: "取消",
//	          	  closeOnConfirm: true, 
//	          	  closeOnCancel: true
//	          	}).then(function(isConfirm) {
//	          	  if (isConfirm === true) {
//	          		 // window.location.href = getAPIURL()+"cpa/traderCenter";
//	          	  } else if (isConfirm === false) {
//	          		//  window.location.href = getAPIURL()+"cpa/traderCenter";
//	          	  } else {
//	          		//  window.location.href = getAPIURL()+"cpa/traderCenter";
//	          	  }
//        	});
//		    return false;
//	    }
//	});
//});

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
	          content: '交易拥堵，请稍后重新交易。2'
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
	loading.open();
    var flag = checkLogin();
    var tmp = getTimestamp();
    var rad = getRandom();
    var ton = getTom();
    var str = "id="+id+"uid="+localStorage.getItem("uid")+"tmp="+tmp+"rad="+rad+"tom="+ton; 
	
	$.ajax({
	      type: "post",
	      url: getAPIURL() + "kuangjy/jy/sellDanJieDan",
	      dataType: "json",
	      data:{
	    	  "id":id,
	    	  "uid":localStorage.getItem("uid"),
	    	  "tmp":tmp,
	          "rad":rad,
	          "tom":ton,
	          "token":commingSoon1(str)
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
		            content: data.msg
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
	console.log(888888888888888);
	var price = $("#cpaprice");
	var cpaCount = $("#cpa_count");
	
    var flag = checkLogin();
    var tmp = getTimestamp();
    var rad = getRandom();
    var ton = getTom();
    var str = "id="+id+"uid="+localStorage.getItem("uid")+"tmp="+tmp+"rad="+rad+"tom="+ton; 
	console.log("sssss:::"+str);
	$.ajax({
	      type: "post",
	      url: getAPIURL() + "kuangjy/jy/buyDanJieDan",
	      dataType: "json",
	      data:{
	    	  "id":id,
	    	  "uid":localStorage.getItem("uid"),
	    	  "tmp":tmp,
	          "rad":rad,
	          "tom":ton,
	          "token":commingSoon1(str)
	      },
	      success: function (data) {
	    	  console.log("--------------111---------"+data);
	        if (data.status==200) {
	        	swal({
		          	  title: '交易提交成功，待系统扫描CPA资产合法性通过后，进行交易。',
		          	  type: 'success',
		          	  showCancelButton: true,
		          	  confirmButtonText: "确定", 
		          	  cancelButtonText: "取消",
		          	  closeOnConfirm: true, 
		          	  closeOnCancel: true
		          	}).then(function(isConfirm) {
		          	  if (isConfirm === true) {
		          		  window.location.href = getAPIURL()+"cpa/traderCenter";
		          	  } else if (isConfirm === false) {
		          		  window.location.href = getAPIURL()+"cpa/traderCenter";
		          	  } else {
		          		  window.location.href = getAPIURL()+"cpa/traderCenter";
		          	  }
	          	});
	        } else {
	        	swal({
		          	  title: data.msg,
		          	  type: 'error',
		          	  showCancelButton: true,
		          	  confirmButtonText: "确定", 
		          	  cancelButtonText: "取消",
		          	  closeOnConfirm: true, 
		          	  closeOnCancel: true
		          	}).then(function(isConfirm) {
		          	  if (isConfirm === true) {
		          		  window.location.href = getAPIURL()+"cpa/traderCenter";
		          	  } else if (isConfirm === false) {
		          		  window.location.href = getAPIURL()+"cpa/traderCenter";
		          	  } else {
		          		  window.location.href = getAPIURL()+"cpa/traderCenter";
		          	  }
	          	});
	        }
	      }
	});
}