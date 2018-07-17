
function Gift() {
  var self = this, _$gift;
  var uid = localStorage.getItem("uid");
  
  var flag = checkLogin();
  var qyNum;
  
  function comptime() {
	  $.ajax({
	      type: "post",
	      url: getAPIURL() + "user/fens/listQINYOUJson",
	      dataType: "json",
	      data: {
	    	  "sh": localStorage.getItem("phone")
	      },
	      success: function (data) {
			    var list = data.data;
	  	        if (list.length <= 0) {
	  	        	qyNum = list.length;
	  	        	$("#qytuan").html(0);
	  	        	if(qyNum < localStorage.getItem("fensteamnum")){	  
	  	    		    $("#fenstuan").html(localStorage.getItem("fensteamnum"));
		  	    	}else{
		  	    		$("#fenstuan").html(qyNum);
		  	    	}
		  	    	$("#fenssl").html(localStorage.getItem("fensteampower"));
	  	        	$("#gift").html("暂无数据");
	  	        } else {
	  	        	var html = "";
	  	        	$.each( list, function(index, content){
	  	        		var inph = content.fensPhone;
	  	        		
	  	        		var nm = content.fensName;
	  	        		nm = "***"+nm.substring(1);
	  				    html += "<ul>" + (index+1) +
			    					"<li>" +
										"<div class='text'>" +
											"<p>名称：<b>"+nm+"</b></p>" +
											"<p>号码：<b>"+inph+"</b></p>" +
											"<p>级别：<b>"+content.fensGrade+"</b></p>" +
											"<p>个人算力：<b>"+content.fensSelfPower+"</b></p>" +
											"<p>粉丝团人数：<b>"+content.fensTeamNum+"</b></p>" +
											"<p>粉丝团算力：<b>"+content.fensTeamPower+"</b></p>" +
										"</div>" +
										"<div class='look'>" +
											"<a href='#'></a>" +
										"</div>" +
									"</li>" +
							   "</ul>";
	  				});
	  	        	$("#qytuan").html(list.length);
	  	        	
	  	        	qyNum = list.length;
	  	        	if(qyNum < localStorage.getItem("fensteamnum")){	  
	  	    		    $("#fenstuan").html(localStorage.getItem("fensteamnum"));
		  	    	}else{
		  	    		$("#fenstuan").html(qyNum);
		  	    	}
		  	    	$("#fenssl").html(localStorage.getItem("fensteampower"));
	  	        	
	  	        	$("#gift").html(html);
	  	        }
	    	  
	      }, headers: {
	        "Authorization": "Bearer " + getTOKEN()
	      }
	    });
  }
  
  (function () {
    _$gift = $("#gift");
    comptime();
  })();
}
var gift;
$(function () {
  gift = new Gift();
});