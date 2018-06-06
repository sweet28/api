function Gift() {
  var self = this, _$gift;
  var uid = localStorage.getItem("uid");
  
  var flag = checkLogin();

  function comptime() {
    $.ajax({
      type: "post",
      url: getAPIURL() + "user/miner/slh",
      dataType: "json",
      data: {
    	  "sh": localStorage.getItem("phone")
      },
      success: function (data) {
    	  if(data.status == 200){
    		  $("#qytuan").html(data.data*0.05);
    	  }else{
    		  $("#qytuan").html(0);
    	  }
    	  
      }, headers: {
        "Authorization": "Bearer " + getTOKEN()
      }
    });
  }
  
  function kuangjiList() {
	    $.ajax({
	      type: "post",
	      url: getAPIURL() + "user/miner/sllb",
	      dataType: "json",
	      data: {
	    	  "sh": localStorage.getItem("phone")
	      },
	      success: function (data) {
	    	  if(data.status == 200){
	    		    var list = data.data;
		  	        if (list.length <= 0) {
		  	        	$("#gift").html("暂无数据");
		  	        } else {
		  	        	var html = "";
		  	        	$.each( list, function(index, content){
		  	        		var runs = content.bak2;
		  	        		var xh = content.bak1;
		  	        		if(runs==0){
		  	        			runs="运行中";
		  	        		}else if(runs==1){
		  	        			runs="死亡";
		  	        		}else if(runs==2){
		  	        			runs="冻结";
		  	        		}
		  	        		
		  	        		if(xh==1){
		  	        			xh="CA1";
		  	        		}else if(xh==2){
		  	        			xh="CA2";
		  	        		}else if(xh==3){
		  	        			xh="CA3";
		  	        		}else if(xh==4){
		  	        			xh="CA4";
		  	        		}
		  	        		
		  	        		var nowDate = Date.parse(new Date());
		  	        		var rundate = nowDate - content.createDate;
		  	        		
		  	        		rundate = rundate/1000/3600/24;
		  	        		if(rundate > 15){
		  	        			rundate=15;
		  	        		}
		  	        		
		  	        		var runHours = rundate*24;
		  	        		
		  	        		html += "<ul>" + (index+1) +
		  	        					"<li>" +
		  									"<div class='text'>" +
		  										"<a href=''>"+ xh +"</a>" +
		  										"<p>运行时长：<b>"+runHours+"</b></p>" +
		  										"<p>奖励算力：<b>"+content.minerComputingPower*0.05+"</b></p>" +
		  									"</div>" +
		  									"<div class='look'>" +
		  										"<a href='#'>"+runs+"</a>" +
		  									"</div>" +
		  								"</li>" +
		  							"</ul>";
		  				});
		  	        	$("#gift").html(html);
		  	        }
	    	  }else{
	    		  $("#gift").html("暂无数据");
	    	  }
	    	  
	      }, headers: {
	        "Authorization": "Bearer " + getTOKEN()
	      }
	    });
	  }

  (function () {
    _$gift = $("#gift");
    comptime();
    kuangjiList();
  })();
}
var gift;
$(function () {
  gift = new Gift();
});