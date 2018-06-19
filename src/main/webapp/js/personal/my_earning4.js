function Gift() {
  var self = this, _$gift;
  var uid = localStorage.getItem("uid");
  
  $("#jibie").html("加载中…");
  $("#shijian").html("计算中…");
  
  var flag = checkLogin();
  console.log(100);
  function comptime() {
    $.ajax({
	      type: "post",
	      url: getAPIURL() + "user/fens/selectGradePowerGift",
	      dataType: "json",
	      data: {
	    	  "uid": localStorage.getItem("uid")
	      },
	      success: function (data) {
	    	  console.log(data);
	    	  
	    	  if(data.status == 200){
	    		  var list = data.data;
	    		  var html = "";
	    		  $.each( list, function(index, fcp){
	    			  var giftType = "节点奖励:";
	    			  
	    			  if(fcp.type == 41){
	    				  var giftType = "普通节点奖励:";
	    			  }
					  if(fcp.type == 42){
						  var giftType = "高级节点奖励:";
					  }
					  if(fcp.type == 43){
						  var giftType = "超级节点奖励:";
					  }
					  
	    			  html += "<p>"+giftType+"<b>"+fcp.computingPower+"</b></p>";
	    		  });
	    		  
	    		  $("#fcpgift").html(html);
	    	  }else{
	    		  $("#fcpgift").html(data.msg);
	    	  }
	      },error:function(){
	    	  console.log(333);
	      }, headers: {
	        "Authorization": "Bearer " + getTOKEN()
	      }
    });
	  console.log("hello cpa");
	  $("#fenstuan").html(localStorage.getItem("fensteamnum"));
	  $("#fenssl").html(localStorage.getItem("fensteampower"));
	  $("#fensgrade").html(localStorage.getItem("fensgrade"));
  }
  console.log(111);
  
  function earnGift() {
	    $.ajax({
		      type: "post",
		      url: getAPIURL() + "user/fens/selectGradeEran",
		      dataType: "json",
		      data: {
		    	  "uid": localStorage.getItem("uid")
		      },
		      success: function (data) {
		    	  console.log(data);
		    	  
		    	  if(data.status == 200){
		    		  var list = data.data;
		    		  var html = "";
		    		  $.each( list, function(index, feran){
		    			  var giftType = "分红:";
		    			  
		    			  if(feran.type == 41){
		    				  var giftType = "普通节点收益分红:";
		    			  }
						  if(feran.type == 42){
							  var giftType = "高级节点收益分红:";
						  }
						  if(feran.type == 43){
							  var giftType = "超级节点收益分红:";
						  }
						  
						  var state = "未提取";
						  if(feran.earnState == 1){
							  state = "已提取";
						  }
						  
		    			  html += "<p>"+giftType+"<b>"+feran.earnCount+"</b>-获取时间:"+fmtDate(feran.earnDate)+"--" + state + "</p>";
		    		  });
		    		  
		    		  $("#earngift").html(html);
		    	  }else{
		    		  $("#earngift").html(data.msg);
		    	  }
		      },error:function(){
		    	  console.log(333);
		      }, headers: {
		        "Authorization": "Bearer " + getTOKEN()
		      }
	    });
		  console.log("hello cpa");
		  $("#fenstuan").html(localStorage.getItem("fensteamnum"));
		  $("#fenssl").html(localStorage.getItem("fensteampower"));
		  $("#fensgrade").html(localStorage.getItem("fensgrade"));
	  }

  (function () {
    _$gift = $("#gift");
    comptime();
    earnGift();
    
  })();
}
var gift;
$(function () {
  gift = new Gift();
});

function addGiftCoupon(){
	console.log("gift coupon");
	$("#tiquCoupon").hide();
	
	$.ajax({
	      type: "post",
	      url: getAPIURL() + "user/fens/addEarnGift",
	      dataType: "json",
	      data: {
	    	  "uid": localStorage.getItem("uid")
	      },
	      success: function (data) {
	    	  console.log(data);
	    	  
	    	  if(data.status == 200){
	    		  alert("提取成功");
	    		  $("#tiquCoupon").show();
	    	  }else{
	    		  alert(data.msg);
	    		  $("#tiquCoupon").show();
	    	  }
	      },error:function(){
	    	  console.log(333);
	    	  $("#tiquCoupon").show();
	      }, headers: {
	        "Authorization": "Bearer " + getTOKEN()
	      }
	});
}

