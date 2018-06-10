function Gift() {
  var self = this, _$gift;
  var uid = localStorage.getItem("uid");
  
  var flag = checkLogin();

  function comptime() {
	  var uid = localStorage.getItem("uid");
		var page = 100;
		var row = 0;
		var flag = checkLogin();
		var tmp = getTimestamp();
		var rad = getRandom();
		var ton = getTom();
		var str = "uid="+uid+"pg="+page+"ts="+row+"tmp="+tmp+"rad="+rad+"tom="+ton;
	  $.ajax({
	      type: "post",
	      url: getAPIURL() + "user/miner/kucunABList",
	      dataType: "json",
	      data: {
	    	  "uid":uid,
	    	  "pg":page,
	    	  "ts":row,
	          "rad":rad,
	          "tom":ton,
	          "token":commingSoon1(str)
	      },
	      success: function (data) {
	    	  console.log(data+"-------")
			    var list = data.list;
	  	        if (list.length <= 0) {
	  	        	$("#abminer").html("暂无数据");
	  	        } else {
	  	        	
	  	        	var html = "";
	  	        	$.each( list, function(index, content){
	  	        		var runs = content.bak2;
	  	        		var xh = content.minerType;
	  	        		
	  	        		if(xh==1){
	  	        			xh="CA矿机";
	  	        		}else if(xh==2){
	  	        			xh="CB矿机";
	  	        		}
	  	        		
	  	        		var sl = content.minerComputingPower;
	  	        		
	  	        		var sec = localStorage.getItem("sec");
	  	        		var conte = "实名审核后可解冻";
	  	        		if(sec == "1"){
	  	        			conte = "<a href='javascript:zhuanru("+content.id+","+content.minerType+");'>转入运行池</a>";
	  	            	}else if(sec == "2"){
	  	            		conte = "认证审核未通过";
	  	            	}
	  	        		
	  	        		
	  				   // html += "<tr><td class='first'>"+(index+1)+"</td><td>"+xh+"</td><td>"+sl+"</td><td>"+conte+"</td></tr>";
	  				    
	  				    html += "<ul>" + (index+1) +
			  						"<li>" +
										"<div class='text'>" +
											"<p>品类：<b>"+xh+"</b></p>" +
											"<p>算力：<b>"+sl+"</b></p>" +
										"</div>" +
										"<div class='look'>" +
											conte +
										"</div>" +
									"</li>" +
							   "</ul>";
	  				});
	  	        	
	  	        	$("#abminer").html(html);
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


function zhuanru(kid,type){
	
	$.ajax({
		type: "post",
	      url: getAPIURL() + "kuangjy/jy/zhuanruyxc",
	      dataType: "json",
	      data: {
	    	  "uid":localStorage.getItem("uid"),
	    	  "type":type,
	    	  "kid":kid
	      },
	      success: function (data) {
	        if (data.status == 200) {
	        	swal({
	          	  title: '操作成功',
	          	  type: 'success',
	          	  showCancelButton: true,
	          	  confirmButtonText: "确定", 
	          	  cancelButtonText: "取消",
	          	  closeOnConfirm: true, 
	          	  closeOnCancel: true
	          	}).then(function(isConfirm) {
	          	  if (isConfirm === true) {
	          		  window.location.href = "myABKC";
	          	  } else if (isConfirm === false) {
	          		window.location.href = "myABKC";
	          	  } else {
	          		window.location.href = "myABKC";
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
	      		  title: "网络错误!",
	      		  icon: "error",
	      		  button: "确定",
	      	  	});
			return false;
	    }
	});
}
